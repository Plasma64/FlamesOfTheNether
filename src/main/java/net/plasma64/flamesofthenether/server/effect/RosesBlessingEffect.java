package net.plasma64.flamesofthenether.server.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RosesBlessingEffect extends MobEffect {
    protected RosesBlessingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.getHealth() <= pLivingEntity.getMaxHealth()) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1, true, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }
}
