package net.plasma64.flamesofthenether.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class FOTNParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FlamesOfTheNether.MODID);

    public static final RegistryObject<SimpleParticleType> ROSE_HEALER = PARTICLE_TYPES.register("rose_healer",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> CRIMSON_FUME = PARTICLE_TYPES.register("crimson_fume",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> CRIMSON_SPRAY = PARTICLE_TYPES.register("crimson_spray",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> WARPED_FUME = PARTICLE_TYPES.register("warped_fume",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> WARPED_SPRAY = PARTICLE_TYPES.register("warped_spray",
            () -> new SimpleParticleType(false));

    public static void register (IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
