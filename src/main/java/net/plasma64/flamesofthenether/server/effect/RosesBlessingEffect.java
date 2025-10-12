package net.plasma64.flamesofthenether.server.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RosesBlessingEffect extends MobEffect {
    protected RosesBlessingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float health_percentage = pLivingEntity.getHealth() / pLivingEntity.getMaxHealth();

        if (health_percentage <= 0.3F) {
            pLivingEntity.heal(6.0F * (pAmplifier + 1));
        } else {
            if (health_percentage <= 0.5F) {
                pLivingEntity.heal(4.0F * (pAmplifier + 1));
            } else {
                if (health_percentage <= 1.0F) {
                    pLivingEntity.heal(2.0F * (pAmplifier + 1));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int tick = 100 >> pAmplifier;

        if (tick > 0) {
            return pDuration % tick == 0;
        } else {
            return true;
        }
    }
}
