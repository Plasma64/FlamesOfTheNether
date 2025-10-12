package net.plasma64.flamesofthenether.server.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;

public class FOTNPotionRegistry {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Potion> ROSES_BLESSING_POTION = POTIONS.register("roses_blessing_potion",
            () -> new Potion(new MobEffectInstance(FOTNEffectRegistry.ROSES_BLESSING.getHolder().get().get(), 3600, 0)));
    public static final RegistryObject<Potion> LONG_ROSES_BLESSING_POTION = POTIONS.register("long_roses_blessing_potion",
            () -> new Potion(new MobEffectInstance(FOTNEffectRegistry.ROSES_BLESSING.getHolder().get().get(), 9600, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
