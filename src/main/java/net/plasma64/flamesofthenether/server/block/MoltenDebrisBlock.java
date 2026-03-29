package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import org.jetbrains.annotations.Nullable;

public class MoltenDebrisBlock extends Block {
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public MoltenDebrisBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(BURNING, true));
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) pEntity)) {
            if (pState.getValue(BURNING)) {
                pEntity.hurt(pLevel.damageSources().hotFloor(), 1.0F);
                pEntity.setSecondsOnFire(3);
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pNeighborState.is(FOTNTagRegistry.MOLTEN_DEBRIS_HEATING_BLOCKS) || pNeighborState.is(FOTNTagRegistry.MOLTEN_DEBRIS_COOLING_BLOCKS)) {
            pLevel.scheduleTick(pPos, this, 60);
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        boolean isHeated = isNearTemperatureBlock(pLevel, pPos, FOTNTagRegistry.MOLTEN_DEBRIS_HEATING_BLOCKS);
        boolean isCooled = isNearTemperatureBlock(pLevel, pPos, FOTNTagRegistry.MOLTEN_DEBRIS_COOLING_BLOCKS);

        if (isHeated && !pState.getValue(BURNING)) {
            pLevel.setBlock(pPos, pState.setValue(BURNING, true), 3);
            pLevel.playSound(null, pPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            if (isCooled && pState.getValue(BURNING)) {
                pLevel.setBlock(pPos, pState.setValue(BURNING, false), 3);
                pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    private boolean isNearTemperatureBlock(BlockGetter level, BlockPos pos, TagKey<Block> tagKey) {
        for (Direction direction : Direction.values()) {
            BlockState state = level.getBlockState(pos.relative(direction));
            if (state.is(tagKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BURNING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (isNearTemperatureBlock(pContext.getLevel(), pContext.getClickedPos(), FOTNTagRegistry.MOLTEN_DEBRIS_COOLING_BLOCKS)) {
            pContext.getLevel().scheduleTick(pContext.getClickedPos(), this, 60);
        }
        return this.defaultBlockState().setValue(BURNING, true);
    }
}
