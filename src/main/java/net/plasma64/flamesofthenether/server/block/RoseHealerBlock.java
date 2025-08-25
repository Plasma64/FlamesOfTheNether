package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.plasma64.flamesofthenether.server.block.blockentity.FOTNBlockEntityRegistry;
import net.plasma64.flamesofthenether.server.block.blockentity.RoseHealerBlockEntity;
import org.jetbrains.annotations.Nullable;

public class RoseHealerBlock extends BaseEntityBlock {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public RoseHealerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, Boolean.FALSE));
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            boolean flag = pState.getValue(TRIGGERED);
            boolean flag1 = pLevel.hasNeighborSignal(pPos);

            if (flag != flag1) {
                pLevel.setBlock(pPos, pState.setValue(TRIGGERED, flag1), 3);
            }
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (flag && entity instanceof RoseHealerBlockEntity roseHealerBlockEntity) {
                if (!roseHealerBlockEntity.isOnCooldown()) {
                    roseHealerBlockEntity.setTriggeredCooldown(200);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIGGERED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(TRIGGERED, pContext.getLevel().hasNeighborSignal(pContext.getClickedPos()));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return FOTNBlockEntityRegistry.ROSE_HEALER.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, FOTNBlockEntityRegistry.ROSE_HEALER.get(), RoseHealerBlockEntity::tick);
    }
}
