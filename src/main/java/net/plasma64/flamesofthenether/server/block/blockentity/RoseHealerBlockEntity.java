package net.plasma64.flamesofthenether.server.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.plasma64.flamesofthenether.client.particle.FOTNParticleRegistry;
import net.plasma64.flamesofthenether.misc.FOTNSoundEvents;

import java.util.List;

public class RoseHealerBlockEntity extends BlockEntity {

    private static final int MAX_PULSES = 3;
    private static final int PULSE_INTERVAL = 20;
    private static final int COOLDOWN_TIME = 200;

    private int cooldown = 0;
    private int pulsesRemaining = 0;
    private int pulseTimer = 0;
    private boolean active = false;

    public RoseHealerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FOTNBlockEntityRegistry.ROSE_HEALER.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RoseHealerBlockEntity pEntity) {
        if (pLevel.isClientSide) {
            return;
        }

        if (pEntity.cooldown > 0) {
            pEntity.cooldown--;
        }

        if (pEntity.active) {

            if (pEntity.pulseTimer > 0) {
                pEntity.pulseTimer--;
                return;
            }

            pEntity.firePulse(pLevel, pPos);
            pEntity.pulsesRemaining--;

            if (pEntity.pulsesRemaining <=0) {
                pEntity.active = false;
                pEntity.cooldown = COOLDOWN_TIME;
            } else {
                pEntity.pulseTimer = PULSE_INTERVAL;
            }
        }
    }

    public void onRedstoneTriggered() {
        if (cooldown <= 0 && !active) {
            active = true;
            pulsesRemaining = MAX_PULSES;
            pulseTimer = 0;
        }
    }

    private void firePulse(Level level, BlockPos blockPos) {
        AABB radius = new AABB(blockPos).inflate(3D);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, radius);

        for (LivingEntity entity : entities) {
            entity.heal(6);
        }

        level.playSound(null, blockPos, FOTNSoundEvents.ROSE_HEALER_PULSE.get(), SoundSource.BLOCKS, 1.0f, 1.5f);
        ((ServerLevel) level).sendParticles(FOTNParticleRegistry.ROSE_HEALER.get(), blockPos.getX() + 0.5, blockPos.getY() + 0.01, blockPos.getZ() + 0.5, 1, 0, 0, 0, 0);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.cooldown = pTag.getInt("Cooldown");
        this.pulsesRemaining = pTag.getInt("PulseRemaining");
        this.pulseTimer = pTag.getInt("PulseTimer");
        this.active = pTag.getBoolean("Active");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Cooldown", this.cooldown);
        pTag.putInt("PulseRemaining", this.pulsesRemaining);
        pTag.putInt("PulseTimer", this.pulseTimer);
        pTag.putBoolean("Active", this.active);
    }
}
