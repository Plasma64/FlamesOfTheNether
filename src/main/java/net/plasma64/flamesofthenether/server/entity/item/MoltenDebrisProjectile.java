package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import net.plasma64.flamesofthenether.misc.FOTNSoundRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;

public class MoltenDebrisProjectile extends Projectile {
    private boolean hasPlayedSpawnSound;
    private double lx;
    private double ly;
    private double lz;
    private double lxr;
    private double lyr;
    private double lxd;
    private double lyd;
    private double lzd;
    private double lSteps;

    private int age = 0;

    //Credit to AlexModGuy

    public MoltenDebrisProjectile(EntityType pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MoltenDebrisProjectile(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), level);
    }

    public MoltenDebrisProjectile(Level level, LivingEntity shooter) {
        this(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), level);
        float f = shooter instanceof Player ? 0.3F : 0.1F;
        this.setPos(shooter.getX(), shooter.getEyeY() - f, shooter.getZ());
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!hasPlayedSpawnSound) {
            hasPlayedSpawnSound = true;
            this.level().playSound(null, this.blockPosition(), FOTNSoundRegistry.MOLTEN_DEBRIS_SPAWN.get(), SoundSource.NEUTRAL);
        }

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = getX() + vec3.x;
        double d1 = getY() + vec3.y;
        double d2 = getZ() + vec3.z;
        this.updateRotation();

        if (!level().isClientSide) {
            if (level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir) && !isInWaterOrBubble()) {
                this.discard();
            } else {
                this.setDeltaMovement(vec3.scale(0.9));
                if (!isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D, 0.0D));
                }

                BlockState state = this.level().getBlockState(this.blockPosition());
                if (state.is(Blocks.BUBBLE_COLUMN)) {
                    if (state.getValue(BubbleColumnBlock.DRAG_DOWN)) {
                        this.setDeltaMovement(0.0D, -0.3D, 0.0D);
                    } else {
                        this.setDeltaMovement(0.0D, 0.3D, 0.0D);
                    }
                }
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if(hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            if (age >= 600) {
                this.discard();
            } else {
                age++;
            }

        } else {
            for (int i = 0; i < 20; i++) {
                this.level().addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getRandomX(1.5D), this.getY() + 2.0D, this.getRandomZ(1.5D), 0.0, 0.5D, 0.0);
            }
        }

        if (level().isClientSide) {
            if (this.lSteps > 0) {
                double d3 = this.getX() + (this.lx - this.getX()) / this.lSteps;
                double d4 = this.getY() + (this.ly - this.getY()) / this.lSteps;
                double d5 = this.getZ() + (this.lz - this.getZ()) / this.lSteps;
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                --this.lSteps;
                this.setPos(d3, d4, d5);
            } else {
                this.reapplyPosition();
            }
        } else {
            this.setPos(d0, d1, d2);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        if (!level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.BLOCK);
            this.level().playSound(null, this.blockPosition(), FOTNSoundRegistry.MOLTEN_DEBRIS_BREAK.get(), SoundSource.NEUTRAL, 1.5F, 0.8F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.BLOCK);
            this.level().playSound(null, this.blockPosition(), FOTNSoundRegistry.MOLTEN_DEBRIS_BREAK.get(), SoundSource.NEUTRAL, 1.5F, 0.8F);
            this.discard();
        }
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
        this.setDeltaMovement(lxd, lyd, lzd);
    }
}
