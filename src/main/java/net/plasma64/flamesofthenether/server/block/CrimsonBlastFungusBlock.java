package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrimsonBlastFungusBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty BLAST_FUNGUS = IntegerProperty.create("blast_fungus",1, 4);
    public static final VoxelShape ONE_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    public static final VoxelShape TWO_AABB = Block.box(2.0D, 0.0D, 2.0D, 12.0D, 4.0D, 11.0D);
    public static final VoxelShape THREE_AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 13.0D);
    public static final VoxelShape FOUR_AABB = Block.box(1.0D, 0.0D, 1.0D, 14.0D, 7.0D, 14.0D);

    public CrimsonBlastFungusBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(BLAST_FUNGUS, Integer.valueOf(Math.min(4, blockstate.getValue(BLAST_FUNGUS) + 1)));
        }
        return this.defaultBlockState().setValue(BLAST_FUNGUS, 1);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(BLAST_FUNGUS)) {
            case 1:
            default:
                return ONE_AABB;
            case 2:
                return TWO_AABB;
            case 3:
                return THREE_AABB;
            case 4:
                return FOUR_AABB;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BLAST_FUNGUS);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(BLAST_FUNGUS) < 4 || super.canBeReplaced(pState, pUseContext);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlockTags.NYLIUM) || pState.is(Blocks.MYCELIUM) || pState.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos.below());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        int state = pState.getValue(BLAST_FUNGUS);
        if (state < 4) {
            pLevel.setBlock(pPos, pState.setValue(BLAST_FUNGUS, 4), 2);
        } else {
            for (int i = 0; i < 5; ++i) {
                BlockPos nearby = pPos.offset(pRandom.nextInt(3) - 1, 0, pRandom.nextInt(3) - 1);
                BlockState nearbyState = pLevel.getBlockState(nearby);
                if (nearbyState.isAir() && this.canSurvive(pState, pLevel, nearby)) {
                    pLevel.setBlock(nearby, this.defaultBlockState().setValue(BLAST_FUNGUS, 1 + pRandom.nextInt(4)), 2);
                }
            }
        }
    }
}
