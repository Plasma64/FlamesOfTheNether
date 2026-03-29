package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.plasma64.flamesofthenether.client.particle.FOTNParticleRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;

import javax.annotation.Nullable;

public class WarpedBlastFungusProjectile extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(WarpedBlastFungusProjectile.class, EntityDataSerializers.INT);
    public static final int DEFAULT_FUSE_TIME = 60;
    @Nullable
    private LivingEntity owner;

    public WarpedBlastFungusProjectile(EntityType<? extends WarpedBlastFungusProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public WarpedBlastFungusProjectile(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(FOTNEntityRegistry.WARPED_BLAST_FUNGUS_PROJECTILE.get(), level);
    }

    public WarpedBlastFungusProjectile(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(FOTNEntityRegistry.WARPED_BLAST_FUNGUS_PROJECTILE.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(getFuse());
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 60);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
                AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX() + 0.5F, this.getY() + 0.5F, this.getZ() + 0.5F);
                areaEffectCloud.setFixedColor(0xb4ffec);
                areaEffectCloud.setRadius(3.5F);
                areaEffectCloud.setRadiusOnUse(-0.5F);
                areaEffectCloud.setWaitTime(10);
                areaEffectCloud.setDuration(100);
                areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
                this.level().addFreshEntity(areaEffectCloud);
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(FOTNParticleRegistry.WARPED_FUME.get(), (double) this.getX() + 0.5 + (this.random.nextFloat() - 0.5F), (double) this.getY() + 0.5 + (this.random.nextFloat() - 0.5F), (double) this.getZ() + 0.5 + (this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F) * 0.02, 0.3D, (this.random.nextFloat() - 0.5F) * 0.02);
            }
        }

    }

    protected void explode() {
        float f = 4.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true, Level.ExplosionInteraction.TNT);

        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(FOTNParticleRegistry.WARPED_SPRAY.get(), (double) this.getX() + 0.5D, (double) this.getY() + 1.0D, (double) this.getZ() + 0.5D, 200, 1.3D, 1.3D, 1.3D, 0.02);
        }
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.15F;
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }
}