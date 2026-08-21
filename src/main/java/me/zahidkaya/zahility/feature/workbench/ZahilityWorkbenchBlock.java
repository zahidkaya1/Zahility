package me.zahidkaya.zahility.feature.workbench;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public final class ZahilityWorkbenchBlock
        extends HorizontalDirectionalBlock {

    public static final MapCodec<ZahilityWorkbenchBlock> CODEC =
            simpleCodec(ZahilityWorkbenchBlock::new);

    /*
     * Tezgâhın baktığı yatay yön.
     */
    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    public ZahilityWorkbenchBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);

        /*
         * Eski dünyalarda veya komutla yerleştirmede
         * kullanılacak varsayılan yön.
         */
        registerDefaultState(
                stateDefinition.any()
                        .setValue(
                                FACING,
                                Direction.NORTH
                        )
        );
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    /*
     * Tezgâh yerleştirildiğinde ön yüzünü
     * oyuncuya doğru çevirir.
     */
    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {
        return defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection()
                                .getOpposite()
                );
    }

    /*
     * Yapı döndürme işlemlerinde yönü korur.
     */
    @Override
    protected BlockState rotate(
            BlockState state,
            Rotation rotation
    ) {
        return state.setValue(
                FACING,
                rotation.rotate(
                        state.getValue(FACING)
                )
        );
    }

    /*
     * Yapı aynalama işlemlerinde yönü düzeltir.
     */
    @Override
    protected BlockState mirror(
            BlockState state,
            Mirror mirror
    ) {
        return state.rotate(
                mirror.getRotation(
                        state.getValue(FACING)
                )
        );
    }

    /*
     * FACING özelliğini bloğun durum sistemine ekler.
     */
    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }

    /*
     * Oyuncu tezgâha sağ tıkladığında
     * Zahility üretim menüsünü açar.
     */
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        if (!level.isClientSide
                && player instanceof ServerPlayer serverPlayer) {

                serverPlayer.openMenu(
                        new SimpleMenuProvider(
                                (
                                        containerId,
                                        playerInventory,
                                        menuPlayer
                                ) -> new ZahilityWorkbenchMenu(
                                        containerId,
                                        playerInventory,
                                        ContainerLevelAccess.create(
                                                level,
                                                pos
                                        )
                                ),
                                Component.translatable(
                                        "menu.zahility.workbench"
                                )
                        ),
                        pos
                );
        }

        return InteractionResult.sidedSuccess(
                level.isClientSide
        );
    }
}