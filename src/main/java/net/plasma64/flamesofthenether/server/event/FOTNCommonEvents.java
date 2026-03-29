package net.plasma64.flamesofthenether.server.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plasma64.flamesofthenether.misc.FOTNSoundEvents;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;

public class FOTNCommonEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (target.hasEffect(FOTNEffectRegistry.ROSES_BLESSING.get())) {
            if (!source.is(DamageTypes.THORNS)) {
                if (source.getEntity() instanceof LivingEntity attacker) {
                    attacker.hurt(target.damageSources().thorns(target), 2.0F);
                }
            }
        }

        if (source.getEntity() instanceof LivingEntity attacker) {
            if (attacker.hasEffect(FOTNEffectRegistry.SOUL_SIPHON.get())) {
                MobEffectInstance effect = attacker.getEffect(FOTNEffectRegistry.SOUL_SIPHON.get());

                if (effect != null) {
                    int amp = effect.getAmplifier();
                    float damage = event.getAmount();
                    float siphon = (damage / 2.0F) * (amp + 1);

                    attacker.heal(siphon);
                    attacker.level().playSound(null, attacker.blockPosition(), FOTNSoundEvents.SOUL_SIPHON_HEAL.get(), SoundSource.PLAYERS, 1.0F, 1.0F + (attacker.getRandom().nextFloat() * 0.4F));
                }

                if (attacker.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SOUL, attacker.getX(), attacker.getY() + attacker.getBbHeight() * 0.5F, attacker.getZ(), 5, 0.3D, 0.5D, 0.3D, 0.02);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingBlock(LivingEvent event) {
        LivingEntity livingEntity = event.getEntity();

    }
}