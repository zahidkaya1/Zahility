package me.zahidkaya.zahility.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

public class InfiniteSnowballItem extends SnowballItem {

    public InfiniteSnowballItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {

        ItemStack itemStack =
                player.getItemInHand(hand);

        /*
         * Vanilla kar topu fırlatma sesi.
         */
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (
                        level.getRandom().nextFloat()
                                * 0.4F
                                + 0.8F
                )
        );

        /*
         * Projectile yalnızca server tarafında oluşturulur.
         */
        if (!level.isClientSide()) {

            Snowball snowball =
                    new Snowball(level, player);

            /*
             * Handler'ların hangi özel kar topunun
             * fırlatıldığını anlayabilmesi için item bilgisini aktar.
             */
            snowball.setItem(itemStack);

            snowball.shootFromRotation(
                    player,
                    player.getXRot(),
                    player.getYRot(),
                    0.0F,
                    1.5F,
                    1.0F
            );

            level.addFreshEntity(snowball);
        }

        /*
         * Vanilla kullanım istatistiğini koru.
         */
        player.awardStat(
                Stats.ITEM_USED.get(this)
        );

        /*
         * Burada itemStack.shrink(...) veya consume(...)
         * çağrısı yapılmıyor.
         *
         * Bu nedenle kar topu fırlatıldıktan sonra
         * eldeki Sonsuz Kar Topu eksilmez.
         */
        return InteractionResultHolder.sidedSuccess(
                itemStack,
                level.isClientSide()
        );
    }
}