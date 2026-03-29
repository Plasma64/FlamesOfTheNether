package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;
import org.jetbrains.annotations.Nullable;

public class MagmaBlockProjectileEntity extends AbstractMagmaCannonProjectileEntity {

    public MagmaBlockProjectileEntity(EntityType<? extends MagmaBlockProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MagmaBlockProjectileEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(FOTNEntityRegistry.MAGMA_BLOCK_PROJECTILE.get(), level);
    }

    public MagmaBlockProjectileEntity(Level pLevel, double pX, double pY, double pZ) {
        super(FOTNEntityRegistry.MAGMA_BLOCK_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public MagmaBlockProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(FOTNEntityRegistry.MAGMA_BLOCK_PROJECTILE.get(), pShooter, pLevel);
    }

    @Override
    protected @Nullable ParticleOptions getImpactParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MAGMA_BLOCK.defaultBlockState());
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticles() {
        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    protected int getTrailParticleTickRate() {
        return 5;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        Entity entity = pResult.getEntity();

        if (!this.level().isClientSide) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), 4);
            this.discard();
        }
    }
}
