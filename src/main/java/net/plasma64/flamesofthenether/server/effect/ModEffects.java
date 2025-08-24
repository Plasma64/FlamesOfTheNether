package net.plasma64.flamesofthenether.server.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FlamesOfTheNether.MODID);

    public static final RegistryObject<MobEffect> ROSESBLESSING = MOB_EFFECTS.register("roses_blessing", () -> new RosesBlessingEffect(MobEffectCategory.BENEFICIAL, 0));

    public static void register (IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
