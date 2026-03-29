package net.plasma64.flamesofthenether.server.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class SwiftSoulEffect extends MobEffect {
    private static final UUID SPEED_MODIFIER_SWIFT_SOUL_UUID = UUID.fromString("6641bbb0-bdcd-4e1c-a2fd-79b81416beb5");
    protected SwiftSoulEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        BlockState blockState = pLivingEntity.level().getBlockState(pLivingEntity.getOnPosLegacy());

        if (!blockState.isAir()) {
            if (onSwiftSoulBlock(pLivingEntity)) {
                AttributeInstance attributeinstance = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                AttributeModifier attributeModifier = new AttributeModifier(SPEED_MODIFIER_SWIFT_SOUL_UUID, "Soul speed boost", (double)(0.03F * (1.0F + (float)(pAmplifier + 1) * 0.35F)), AttributeModifier.Operation.ADDITION);

                if (attributeinstance == null) {
                    return;
                }

                if (canSpawnSwiftSoulParticle(pLivingEntity)) {
                    spawnSwiftSoulParticle(pLivingEntity);
                }

                removeSwiftSoul(pLivingEntity);
                attributeinstance.addTransientModifier(attributeModifier);
            } else {
                removeSwiftSoul(pLivingEntity);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }

    protected void removeSwiftSoul(LivingEntity livingEntity) {
        AttributeInstance attributeInstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attributeInstance != null) {
            if (attributeInstance.getModifier(SPEED_MODIFIER_SWIFT_SOUL_UUID) != null) {
                attributeInstance.removeModifier(SPEED_MODIFIER_SWIFT_SOUL_UUID);
            }
        }
    }

    public boolean canSpawnSwiftSoulParticle(LivingEntity livingEntity) {
        return livingEntity.tickCount % 5 == 0 && livingEntity.getDeltaMovement().x != 0.0D && livingEntity.getDeltaMovement().z != 0.0D && !livingEntity.isSpectator() && onSwiftSoulBlock(livingEntity);
    }

    protected void spawnSwiftSoulParticle(LivingEntity livingEntity) {
        Vec3 vec3 = livingEntity.getDeltaMovement();
        livingEntity.level().addParticle(ParticleTypes.SOUL, livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5D) * (double)livingEntity.getBbWidth(), livingEntity.getY() + 0.1D, livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5D) * (double)livingEntity.getBbWidth(), vec3.x * -0.2D, 0.1D, vec3.z * -0.2D);
        float f = livingEntity.getRandom().nextFloat() * 0.4F + livingEntity.getRandom().nextFloat() > 0.9F ? 0.6F : 0.0F;
        livingEntity.playSound(SoundEvents.SOUL_ESCAPE, f, 0.6F + livingEntity.getRandom().nextFloat() * 0.4F);
    }

    protected boolean onSwiftSoulBlock(LivingEntity livingEntity) {
        return livingEntity.level().getBlockState(livingEntity.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        removeSwiftSoul(pLivingEntity);
    }
}
