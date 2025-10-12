package net.plasma64.flamesofthenether.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class FOTNSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FlamesOfTheNether.MODID);

    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_STEP = registerSoundEvent("molten_debris_step");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_BREAKING = registerSoundEvent("molten_debris_breaking");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_PLACE = registerSoundEvent("molten_debris_place");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_BREAK = registerSoundEvent("molten_debris_break");

    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_SPAWN = registerSoundEvent("molten_debris_spawn");
    public static final RegistryObject<SoundEvent> ROSE_HEALER_PULSE = registerSoundEvent("rose_healer_pulse");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, name)));
    }

    public static void register (IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
