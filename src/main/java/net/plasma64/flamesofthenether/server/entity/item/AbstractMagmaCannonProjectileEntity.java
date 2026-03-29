package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class AbstractMagmaCannonProjectileEntity extends Projectile {
    private int age = 0;
    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public AbstractMagmaCannonProjectileEntity(EntityType<? extends AbstractMagmaCannonProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractMagmaCannonProjectileEntity(EntityType<? extends AbstractMagmaCannonProjectileEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public AbstractMagmaCannonProjectileEntity(EntityType<? extends AbstractMagmaCannonProjectileEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();
        this.spawnTrailParticles();

        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir) && !this.isInWaterOrBubble()) {
            this.discard();
        }

        this.setDeltaMovement(vec3.scale(0.9));
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D, 0.0D));
        }

        if(hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        if (age >= 600) {
            this.discard();
        } else {
            age++;
        }

        if (level().isClientSide) {
            if (this.lSteps > 0) {
                double d5 = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double d6 = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double d7 = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                --this.lSteps;
                this.setPos(d5, d6, d7);
            } else {
                this.reapplyPosition();
            }
        } else {
            this.setPos(d2, d0, d1);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.spawnImpactParticles();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        this.spawnImpactParticles();
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
        this.lx = pX;
        this.ly = pY;
        this.lz = pZ;
        this.lyr = pYRot;
        this.lxr = pXRot;
        this.lSteps = pLerpSteps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double pX, double pY, double pZ) {
        this.lxd = pX;
        this.lyd = pY;
        this.lzd = pZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    protected @Nullable ParticleOptions getImpactParticle() {
        return null;
    }

    protected int getImpactParticleCount() {
        return 20;
    }

    protected @Nullable ParticleOptions getTrailParticles() {
        return null;
    }

    protected int getTrailParticleTickRate() {
        return 3;
    }

    protected void spawnImpactParticles() {
        ParticleOptions impactParticle = this.getImpactParticle();

        if (this.level().isClientSide || impactParticle == null) {
            return;
        }

        ((ServerLevel) this.level()).sendParticles(impactParticle, this.getX(), this.getY(), this.getZ(), getImpactParticleCount(), 0.2, 0.2, 0.2, 0.05);
    }

    protected void spawnTrailParticles() {
        ParticleOptions trailParticle = this.getTrailParticles();

        if (!level().isClientSide || trailParticle == null) {
            return;
        }

        if (this.tickCount % this.getTrailParticleTickRate() != 0) {
            return;
        }

        this.level().addParticle(trailParticle, this.getRandomX(0.5), this.getY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
    }
}
