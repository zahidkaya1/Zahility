package me.zahidkaya.zahility.feature.leveling;

import me.zahidkaya.zahility.registry.ModBlockTags;
import me.zahidkaya.zahility.registry.ModDataComponents;
import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class LevelingImpactHandler {

    // Normal Düzleştirici Kar Topu = 7x7
    private static final int NORMAL_RADIUS = 3;

    // Yaratıcı Düzleştirici Kar Topu = 15x15
    private static final int CREATIVE_RADIUS = 7;

    /*
     * Hedef yüzeyin en fazla 6 blok altındaki
     * boşlukları desteklemek için dolduracağız.
     *
     * Bunun daha altındaki mağaralarla ilgilenmiyoruz.
     */
    private static final int SUPPORT_DEPTH = 6;

    /*
     * Ağaç gibi yüzey elemanlarının altında gerçek zemini
     * bulmak için sadece yakın çevreyi kontrol eder.
     *
     * Dünyanın dibine kadar arama YOK.
     */
    private static final int LOCAL_SURFACE_SCAN_DEPTH = 12;

    private static final int INVALID_SURFACE = Integer.MIN_VALUE;

    private LevelingImpactHandler() {
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {

        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }

        if (!(snowball.level() instanceof ServerLevel level)) {
            return;
        }

        ItemStack projectileStack = snowball.getItem();

        int radius;

        if (projectileStack.is(ModItems.LEVELING_SNOWBALL.get())) {

            radius = NORMAL_RADIUS;

        } else if (projectileStack.is(
                ModItems.CREATIVE_LEVELING_SNOWBALL.get()
        )) {

            radius = CREATIVE_RADIUS;

        } else {
            return;
        }

        Integer targetY = projectileStack.get(
                ModDataComponents.LEVELING_HEIGHT.value()
        );

        if (targetY == null) {

            if (snowball.getOwner() instanceof Player player) {
                player.displayClientMessage(
                        Component.literal(
                                "Önce bir düzleme seviyesi seçmelisin."
                        ),
                        false
                );
            }

            return;
        }

        BlockPos center = snowball.blockPosition();

        int skippedColumns = flattenArea(
                level,
                center,
                targetY,
                radius
        );

        int size = radius * 2 + 1;
        int totalColumns = size * size;

        /*
        * Alanın tamamı korunmuşsa efekt oynatma.
        * En az bir sütun işlendiğinde efekt göster.
        */
        if (skippedColumns < totalColumns) {
        playLevelingEffect(
                level,
                center,
                targetY,
                radius
        );
        }

        if (snowball.getOwner() instanceof Player player) {



            String message =
                    "Alan düzleştirildi: "
                            + size
                            + "x"
                            + size
                            + " | Hedef: Y = "
                            + targetY;

            if (skippedColumns > 0) {
                message +=
                        " | Korunan sütun: "
                                + skippedColumns;
            }

            player.displayClientMessage(
                    Component.literal(message),
                    false
            );
        }
    }

    private static int flattenArea(
            ServerLevel level,
            BlockPos center,
            int targetY,
            int radius
    ) {

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        if (targetY < minY || targetY > maxY) {
            return 0;
        }

        int skippedColumns = 0;

        BlockPos.MutableBlockPos pos =
                new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {

                int x = center.getX() + dx;
                int z = center.getZ() + dz;

                /*
                 * WORLD_SURFACE:
                 * Bu sütundaki en yüksek dünya bloğunu bulur.
                 *
                 * Dağ ne kadar yüksek olursa olsun
                 * buradan hedef Y'ye kadar temizleyebiliriz.
                 */
                int topY = level.getHeight(
                        Heightmap.Types.WORLD_SURFACE,
                        x,
                        z
                ) - 1;

                /*
                 * Yaprakları hesaba katmadan yüzeye yakın
                 * başlangıç noktası alıyoruz.
                 */
                int surfaceProbeY = level.getHeight(
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        x,
                        z
                ) - 1;

                int surfaceY = findLocalSurfaceY(
                        level,
                        x,
                        z,
                        surfaceProbeY
                );

                if (surfaceY == INVALID_SURFACE) {
                    skippedColumns++;
                    continue;
                }

                /*
                 * Kontrolümüz sadece ihtiyaç duyduğumuz bölgeyle sınırlı.
                 *
                 * En aşağı:
                 * targetY - 6
                 *
                 * veya mevcut yüzey daha aşağıdaysa mevcut yüzey.
                 *
                 * Dünyanın dibine bakmıyoruz.
                 */
                int checkBottomY = Math.max(
                        minY,
                        Math.min(
                                surfaceY,
                                targetY - SUPPORT_DEPTH
                        )
                );

                int checkTopY = Math.min(
                        maxY,
                        Math.max(
                                topY,
                                targetY
                        )
                );

                /*
                 * İşlem yapılacak bölgede su varsa
                 * o sütunu olduğu gibi bırak.
                 */
                if (containsWater(
                        level,
                        x,
                        z,
                        targetY,
                        checkTopY
                )) {
                    skippedColumns++;
                    continue;
                }

                /*
                 * Sandık, fırın, barrel, hopper gibi
                 * BlockEntity içeren şeyleri koruyoruz.
                 */
                if (containsBlockEntity(
                        level,
                        x,
                        z,
                        checkBottomY,
                        checkTopY
                )) {
                    skippedColumns++;
                    continue;
                }

                /*
                 * ==========================
                 * 1. YÜKSEK ARAZİYİ TEMİZLE
                 * ==========================
                 *
                 * Burada dikey sınır YOK.
                 *
                 * Hedef Y=60
                 * Dağ Y=110
                 *
                 * ise 110 -> 61 arası temizlenir.
                 */
                if (topY > targetY) {

                    for (int y = topY; y > targetY; y--) {

                        pos.set(x, y, z);

                        level.setBlock(
                                pos,
                                Blocks.AIR.defaultBlockState(),
                                2
                        );
                    }
                }

                /*
                 * ==========================
                 * 2. ALÇAK ARAZİYİ YÜKSELT
                 * ==========================
                 *
                 * Mevcut gerçek yüzey hedef Y'nin altındaysa
                 * aradaki boşluğu Dirt ile doldur.
                 *
                 * Burada dünyanın dibini aramıyoruz.
                 * Elimizde zaten yerel yüzey yüksekliği var.
                 */
                if (surfaceY < targetY) {

                    for (
                            int y = surfaceY + 1;
                            y < targetY;
                            y++
                    ) {

                        pos.set(x, y, z);

                        level.setBlock(
                                pos,
                                Blocks.DIRT.defaultBlockState(),
                                2
                        );
                    }
                }

                /*
                 * =================================
                 * 3. HEDEFİN 6 BLOK ALTINI DESTEKLE
                 * =================================
                 *
                 * Yakındaki mağara boşluğu yüzünden yüzeyin
                 * altında delik kalmasın.
                 *
                 * Ama sadece 6 blok.
                 *
                 * Y=-30'daki mağaraya kadar inmiyoruz.
                 */
                int supportBottom = Math.max(
                        minY,
                        targetY - SUPPORT_DEPTH
                );

                for (
                        int y = targetY - 1;
                        y >= supportBottom;
                        y--
                ) {

                    pos.set(x, y, z);

                    BlockState state =
                            level.getBlockState(pos);

                    if (state.isAir()
                            || state.is(
                                    ModBlockTags.LEVELING_VEGETATION
                            )) {

                        level.setBlock(
                                pos,
                                Blocks.DIRT.defaultBlockState(),
                                2
                        );
                    }
                }

                /*
                 * ==========================
                 * 4. SON YÜZEY
                 * ==========================
                 */
                pos.set(x, targetY, z);

                level.setBlock(
                        pos,
                        Blocks.GRASS_BLOCK.defaultBlockState(),
                        2
                );
            }
        }

        return skippedColumns;
    }

    /*
     * Gerçek yüzeyi bulmak için artık dünyanın dibine inmiyoruz.
     *
     * MOTION_BLOCKING_NO_LEAVES seviyesinden başlayıp
     * sadece LOCAL_SURFACE_SCAN_DEPTH kadar aşağı bakıyoruz.
     *
     * Ağaç/kütük/yaprak/çiçek gibi doğal bitki örtüsünü geçiyoruz.
     */
    private static int findLocalSurfaceY(
            ServerLevel level,
            int x,
            int z,
            int startY
    ) {

        int minY = level.getMinBuildHeight();

        int bottomY = Math.max(
                minY,
                startY - LOCAL_SURFACE_SCAN_DEPTH
        );

        BlockPos.MutableBlockPos pos =
                new BlockPos.MutableBlockPos();

        for (int y = startY; y >= bottomY; y--) {

            pos.set(x, y, z);

            BlockState state =
                    level.getBlockState(pos);

            if (state.isAir()) {
                continue;
            }

            if (state.is(
                    ModBlockTags.LEVELING_VEGETATION
            )) {
                continue;
            }

            if (state.getFluidState().is(FluidTags.WATER)) {
                return INVALID_SURFACE;
            }

            /*
             * İlk gerçek blok bizim yerel yüzeyimiz.
             *
             * Bundan daha aşağı inmiyoruz.
             */
            return y;
        }

        return INVALID_SURFACE;
    }

    /*
     * Sadece işlem yapılacak yükseklik aralığında
     * su arar.
     *
     * Dünyanın tamamını kontrol etmez.
     */
    private static boolean containsWater(
            ServerLevel level,
            int x,
            int z,
            int bottomY,
            int topY
    ) {

        BlockPos.MutableBlockPos pos =
                new BlockPos.MutableBlockPos();

        for (int y = bottomY; y <= topY; y++) {

            pos.set(x, y, z);

            BlockState state =
                    level.getBlockState(pos);

            if (state.getFluidState().is(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }

    /*
     * Sandık, fırın, barrel, hopper vb.
     * veri taşıyan blokların yanlışlıkla silinmesini önler.
     */
    private static boolean containsBlockEntity(
            ServerLevel level,
            int x,
            int z,
            int bottomY,
            int topY
    ) {

        BlockPos.MutableBlockPos pos =
                new BlockPos.MutableBlockPos();

        for (int y = bottomY; y <= topY; y++) {

            pos.set(x, y, z);

            if (level.getBlockEntity(pos) != null) {
                return true;
            }
        }

        return false;
    }

    private static void playLevelingEffect(
        ServerLevel level,
        BlockPos center,
        int targetY,
        int radius
) {

    /*
     * Düzlenen alanın çevresinde yatay bir particle halkası.
     *
     * Normal 7x7  -> 24 nokta
     * Creative 15x15 -> 40 nokta
     */
    int particlePoints =
            radius == NORMAL_RADIUS
                    ? 24
                    : 40;

    double effectRadius = radius + 0.5;
    double effectY = targetY + 1.05;

    for (int i = 0; i < particlePoints; i++) {

        double angle =
                (Math.PI * 2.0 * i)
                        / particlePoints;

        double particleX =
                center.getX()
                        + 0.5
                        + Math.cos(angle)
                        * effectRadius;

        double particleZ =
                center.getZ()
                        + 0.5
                        + Math.sin(angle)
                        * effectRadius;

        level.sendParticles(
                ParticleTypes.COMPOSTER,
                particleX,
                effectY,
                particleZ,
                1,
                0.04,
                0.02,
                0.04,
                0.0
        );
    }

    /*
     * Merkezde çok hafif ikinci bir particle kümesi.
     * Alanın "oturduğu" hissini güçlendirir.
     */
    level.sendParticles(
            ParticleTypes.COMPOSTER,
            center.getX() + 0.5,
            effectY,
            center.getZ() + 0.5,
            radius == NORMAL_RADIUS ? 8 : 14,
            radius * 0.30,
            0.05,
            radius * 0.30,
            0.01
    );

    /*
     * Vanilla çim yerleştirme sesi.
     * Creative biraz daha geniş ve tok duyulur.
     */
    BlockPos soundPos =
            new BlockPos(
                    center.getX(),
                    targetY,
                    center.getZ()
            );

    level.playSound(
            null,
            soundPos,
            SoundEvents.GRASS_PLACE,
            SoundSource.BLOCKS,
            radius == NORMAL_RADIUS ? 0.8F : 1.1F,
            radius == NORMAL_RADIUS ? 1.05F : 0.90F
    );
}
}