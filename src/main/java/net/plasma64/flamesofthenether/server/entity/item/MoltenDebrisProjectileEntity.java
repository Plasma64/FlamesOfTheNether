package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PlayMessages;
import net.plasma64.flamesofthenether.misc.FOTNSoundEvents;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoltenDebrisProjectileEntity extends AbstractMagmaCannonProjectileEntity {
    public MoltenDebrisProjectileEntity(EntityType<? extends MoltenDebrisProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MoltenDebrisProjectileEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), level);
    }

    public MoltenDebrisProjectileEntity(Level pLevel, double pX, double pY, double pZ) {
        super(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public MoltenDebrisProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), pShooter, pLevel);
    }

    @Override
    protected @Nullable ParticleOptions getImpactParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, FOTNBlockRegistry.MOLTEN_DEBRIS.get().defaultBlockState());
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticles() {
        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    protected int getTrailParticleTickRate() {
        return 5;
    }

    private void damageArea(Level level, BlockPos blockPos) {
        AABB radius = new AABB(blockPos).inflate(2D);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, radius);

        for (LivingEntity entity : entities) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), 4);
        }
        level.playSound(null, blockPos, FOTNSoundEvents.MOLTEN_DEBRIS_BREAK.get(), SoundSource.NEUTRAL, 1.0f, this.random.nextFloat() * 0.2F);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!level().isClientSide) {
            damageArea(level(),pResult.getBlockPos());
            this.discard();
        }
    }
}
