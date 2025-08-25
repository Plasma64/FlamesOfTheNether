package net.plasma64.flamesofthenether.server.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.block.RoseHealerBlock;

import java.util.List;

public class RoseHealerBlockEntity extends BlockEntity {
    private int triggeredCooldown = 0;
    private int pulseNumber = 3;
    private int pulseCooldown = 20;

    public RoseHealerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FOTNBlockEntityRegistry.ROSE_HEALER.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RoseHealerBlockEntity entity) {
        boolean isPowered = entity.isTriggered(state);
        if (!level.isClientSide) {
            if (entity.triggeredCooldown > 0) {
                entity.triggeredCooldown--;
            }
            if (isPowered && entity.pulseNumber > 0) {
                if (entity.pulseCooldown < 0) {
                    entity.firePulse(level, pos);
                    entity.pulseNumber--;
                    entity.pulseCooldown = 20;
                } else {
                    entity.pulseCooldown--;
                }
            }
        }
    }

    public boolean isOnCooldown() {
        return triggeredCooldown > 0;
    }

    public boolean isTriggered(BlockState state) {
        return state.is(FOTNBlockRegistry.ROSE_HEALER.get()) && state.getValue(RoseHealerBlock.TRIGGERED);
    }

    private void firePulse(Level level, BlockPos pos) {
        AABB radius = new AABB(pos).inflate(3);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, radius);

        for (LivingEntity entity: entities) {
            entity.heal(6);
        }
        level.playSound(null, pos, SoundEvents.WARDEN_HEARTBEAT, SoundSource.BLOCKS, 1.0f, 1.5f);
    }

    public void setTriggeredCooldown(int cooldown) {
        this.triggeredCooldown = cooldown;
        this.pulseNumber = 3;
        this.pulseCooldown = 20;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.triggeredCooldown = pTag.getInt("TriggeredCooldown");
        this.pulseNumber = pTag.getInt("PulseNumber");
        this.pulseCooldown = pTag.getInt("PulseCooldown");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("TriggeredCooldown", this.triggeredCooldown);
        pTag.putInt("PulseNumber", this.pulseNumber);
        pTag.putInt("PulseCooldown", this.pulseCooldown);
    }
}
