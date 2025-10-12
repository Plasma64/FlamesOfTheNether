package net.plasma64.flamesofthenether.server.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;
import net.plasma64.flamesofthenether.server.potion.FOTNPotionRegistry;

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
    }
}