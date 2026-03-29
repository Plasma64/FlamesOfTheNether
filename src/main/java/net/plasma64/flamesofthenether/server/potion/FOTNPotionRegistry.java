package net.plasma64.flamesofthenether.server.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;

public class FOTNPotionRegistry {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Potion> ROSES_BLESSING_POTION = POTIONS.register("roses_blessing_potion",
            () -> new Potion(new MobEffectInstance(FOTNEffectRegistry.ROSES_BLESSING.get(), 3600, 0)));
    public static final RegistryObject<Potion> LONG_ROSES_BLESSING_POTION = POTIONS.register("long_roses_blessing_potion",
            () -> new Potion(new MobEffectInstance(FOTNEffectRegistry.ROSES_BLESSING.get(), 9600, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

    public static void setup() {
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.REGENERATION)), Ingredient.of(FOTNItemRegistry.ROSE_QUARTZ.get()), createPotion(FOTNPotionRegistry.ROSES_BLESSING_POTION)));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(FOTNPotionRegistry.ROSES_BLESSING_POTION)), Ingredient.of(Items.REDSTONE), createPotion(FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION)));
    }

    public static ItemStack createPotion(RegistryObject<Potion> potion) {
        return createPotion(potion.get());
    }

    public static ItemStack createPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }

    public static ItemStack createSplashPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion);
    }

    public static ItemStack createLingeringPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
    }
}
