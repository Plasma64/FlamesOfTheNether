package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;
import org.jetbrains.annotations.Nullable;

public class SoulRoots extends RootsBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public SoulRoots(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide && !pState.getValue(LIT) && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
            double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
            double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                pLevel.setBlock(pPos, pState.setValue(LIT, true), 3);
                pLevel.scheduleTick(pPos, this, 20);
                pLevel.playSound(null, pPos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 1.0F, 0.8F);
                if (pEntity instanceof LivingEntity && EnchantmentHelper.hasSoulSpeed((LivingEntity) pEntity)) {
                    ((LivingEntity) pEntity).addEffect(new MobEffectInstance(FOTNEffectRegistry.SWIFT_SOUL.get(), 60, 0));
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(LIT, false);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            pLevel.setBlock(pPos, pState.setValue(LIT, false), 3);
        }
    }
}
