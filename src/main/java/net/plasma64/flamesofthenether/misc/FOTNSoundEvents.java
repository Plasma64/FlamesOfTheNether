package net.plasma64.flamesofthenether.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class FOTNSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FlamesOfTheNether.MODID);

    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_BREAK = registerSoundEvent("block.molten_debris.break");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_STEP = registerSoundEvent("block.molten_debris.step");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_PLACE = registerSoundEvent("block.molten_debris.place");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_HIT = registerSoundEvent("block.molten_debris.hit");
    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_FALL = registerSoundEvent("block.molten_debris.fall");


    public static final RegistryObject<SoundEvent> SOUl_PEPPER_BUSH_BREAK = registerSoundEvent("block.soul_pepper_bush.break");
    public static final RegistryObject<SoundEvent> SOUl_PEPPER_BUSH_PLACE = registerSoundEvent("block.soul_pepper_bush.place");
    public static final RegistryObject<SoundEvent> SOUl_PEPPER_BUSH_PICK_PEPPERS = registerSoundEvent("block.soul_pepper_bush.pick_peppers");
    public static final RegistryObject<SoundEvent> SOUL_PEPPER_BUSH_CALL = registerSoundEvent("block.soul_pepper_bush.call");

    public static final RegistryObject<SoundEvent> ROSE_HEALER_PULSE = registerSoundEvent("block.rose_healer.pulse");

    public static final RegistryObject<SoundEvent> ASH_BREAK = registerSoundEvent("block.ash.break");
    public static final RegistryObject<SoundEvent> ASH_STEP = registerSoundEvent("block.ash.step");
    public static final RegistryObject<SoundEvent> ASH_PLACE = registerSoundEvent("block.ash.place");
    public static final RegistryObject<SoundEvent> ASH_HIT = registerSoundEvent("block.ash.hit");
    public static final RegistryObject<SoundEvent> ASH_FALL = registerSoundEvent("block.ash.fall");

    public static final RegistryObject<SoundEvent> MOLTEN_DEBRIS_SPAWN = registerSoundEvent("entity.molten_debris.spawn");

    public static final RegistryObject<SoundEvent> SOUL_SIPHON_HEAL = registerSoundEvent("effect.soul_siphon_heal");

    public static final RegistryObject<SoundEvent> ASHES_THROW = registerSoundEvent("item.ashes.throw");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, name)));
    }

    public static void register (IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
