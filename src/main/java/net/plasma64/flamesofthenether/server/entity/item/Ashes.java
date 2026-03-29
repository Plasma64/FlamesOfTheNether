package net.plasma64.flamesofthenether.server.entity.item;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;

public class Ashes extends ThrowableItemProjectile {
    public Ashes(EntityType<? extends Ashes> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Ashes(Level pLevel, LivingEntity pShooter) {
        super(FOTNEntityRegistry.ASHES.get(), pShooter, pLevel);
    }

    public Ashes(Level pLevel, double pX, double pY, double pZ) {
        super(FOTNEntityRegistry.ASHES.get(), pX, pY, pZ, pLevel);
    }

    public Ashes(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(FOTNEntityRegistry.ASHES.get(), level);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return FOTNItemRegistry.ASHES.get();
    }

    private ParticleOptions getParticle() {
        return new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();

        if (!level().isClientSide) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
        }

        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        BlockState blockState = level().getBlockState(blockPosition());

        if (blockState.is(FOTNBlockRegistry.ASH.get())) {
            int layers = blockState.getValue(BlockStateProperties.LAYERS);

            if (layers < 8) {
                level().setBlock(blockPosition(), blockState.setValue(BlockStateProperties.LAYERS, layers + 1), 3);
            }
        } else {
            if (FOTNBlockRegistry.ASH.get().defaultBlockState().canSurvive(level(), blockPosition()) && blockState.canBeReplaced()) {
                level().setBlock(blockPosition(), FOTNBlockRegistry.ASH.get().defaultBlockState(), 3);
            }
        }
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }
}
