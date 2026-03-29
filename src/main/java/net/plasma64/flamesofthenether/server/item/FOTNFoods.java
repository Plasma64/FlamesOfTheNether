package net.plasma64.flamesofthenether.server.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;

public class FOTNFoods {
    public static final FoodProperties SOUL_PEPPER = new FoodProperties.Builder().nutrition(4).saturationMod(0.5F).build();
    public static final FoodProperties SOUL_PEPPER_STEW = new FoodProperties.Builder().nutrition(12).saturationMod(0.6F).effect(new MobEffectInstance(FOTNEffectRegistry.SOUL_SIPHON.get(), 1800, 0), 1.0F).build();

}
