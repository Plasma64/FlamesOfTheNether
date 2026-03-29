package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.plasma64.flamesofthenether.client.particle.FOTNParticleRegistry;
import net.plasma64.flamesofthenether.server.entity.item.WarpedBlastFungusProjectile;

import java.util.Optional;

public class WarpedBlastFungusBlockBlock extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;
    public static final int fuseTime = 60;

    public WarpedBlastFungusBlockBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, Boolean.valueOf(false)));
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pLevel.hasNeighborSignal(pPos)) {
            startExplosion(pLevel, pState, pPos);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (pLevel.hasNeighborSignal(pPos)) {
            startExplosion(pLevel, pState, pPos);
        }
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            WarpedBlastFungusProjectile warpedBlastFungusProjectile = new WarpedBlastFungusProjectile(pLevel, (double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, pExplosion.getIndirectSourceEntity());
            int i = warpedBlastFungusProjectile.getFuse();
            warpedBlastFungusProjectile.setFuse((short) (pLevel.random.nextInt(i / 4) + i / 8));
            pLevel.addFreshEntity(warpedBlastFungusProjectile);
        }
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos blockPos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, blockPos) && pProjectile.getDeltaMovement().length() > 0.6D) {
            if (pProjectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
                startExplosion(pLevel, pState, blockPos);
            }
        }
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pFallDistance > 3.0F && !pEntity.isSuppressingBounce() && pEntity instanceof LivingEntity) {
            startExplosion(pLevel, pState, pPos);
        }
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance * 0.5F);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            Vec3 vec3 = pEntity.getDeltaMovement();
            if (vec3.y < 0.0D) {
                double d0 = pEntity instanceof LivingEntity ? 0.5D : 0.8D;
                pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
            }
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(pLevel, pPos.getX() + 0.5F, pPos.getY() + 0.5F, pPos.getZ() + 0.5F);
        pLevel.removeBlock(pPos, false);
        this.explode(pState, pLevel, pPos);
        areaEffectCloud.setFixedColor(0xb4ffec);
        areaEffectCloud.setRadius(3.5F);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(10);
        areaEffectCloud.setDuration(100);
        areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
        pLevel.addFreshEntity(areaEffectCloud);
        pLevel.sendParticles(FOTNParticleRegistry.WARPED_SPRAY.get(), (double) pPos.getX() + 0.5D, (double) pPos.getY() + 1.0D, (double) pPos.getZ() + 0.5D, 200, 1.3D, 1.3D, 1.3D, 0.02);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(UNSTABLE)) {
            if (pRandom.nextFloat() < 0.8F) {
                pLevel.addParticle(FOTNParticleRegistry.WARPED_FUME.get(), (double) pPos.getX() + 0.5 + (pRandom.nextFloat() - 0.5F), (double) pPos.getY() + 0.5 + (pRandom.nextFloat() - 0.5F), (double) pPos.getZ() + 0.5 + (pRandom.nextFloat() - 0.5F), (pRandom.nextFloat() - 0.5F) * 0.02, 0.3D, (pRandom.nextFloat() - 0.5F) * 0.02);
            }
        }
    }

    private static boolean isWaterThatWouldFlow(BlockPos pPos, Level pLevel) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        if (!fluidstate.is(FluidTags.WATER)) {
            return false;
        } else if (fluidstate.isSource()) {
            return true;
        } else {
            float f = (float)fluidstate.getAmount();
            if (f < 2.0F) {
                return false;
            } else {
                FluidState fluidstate1 = pLevel.getFluidState(pPos.below());
                return !fluidstate1.is(FluidTags.WATER);
            }
        }
    }

    private void explode(BlockState pState, Level pLevel, final BlockPos pPos2) {
        pLevel.removeBlock(pPos2, false);
        boolean flag = Direction.Plane.HORIZONTAL.stream().map(pPos2::relative).anyMatch((blockPos) -> {
            return isWaterThatWouldFlow(blockPos, pLevel);
        });
        final boolean flag1 = flag || pLevel.getFluidState(pPos2.above()).is(FluidTags.WATER);
        ExplosionDamageCalculator explosiondamagecalculator = new ExplosionDamageCalculator() {
            public Optional<Float> getBlockExplosionResistance(Explosion explosion, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
                return blockPos.equals(pPos2) && flag1 ? Optional.of(Blocks.WATER.getExplosionResistance()) : super.getBlockExplosionResistance(explosion, blockGetter, blockPos, blockState, fluidState);
            }
        };
        Vec3 vec3 = pPos2.getCenter();
        pLevel.explode((Entity)null, pLevel.damageSources().explosion(null), explosiondamagecalculator, vec3, 3.0F, true, Level.ExplosionInteraction.BLOCK);
    }

    public void startExplosion(Level level, BlockState state, BlockPos blockPos) {
        level.scheduleTick(blockPos, this, fuseTime);
        level.setBlock(blockPos, state.setValue(UNSTABLE, true), 3);
        level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UNSTABLE);
    }
}
