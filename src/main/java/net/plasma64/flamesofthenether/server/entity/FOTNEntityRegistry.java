package net.plasma64.flamesofthenether.server.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.entity.item.MoltenDebrisProjectile;

public class FOTNEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FlamesOfTheNether.MODID);

    public static final RegistryObject<EntityType<MoltenDebrisProjectile>> MOLTEN_DEBRIS_PROJECTILE = ENTITY_TYPES.register("molten_debris_projectile", () -> (EntityType) EntityType.Builder.of(MoltenDebrisProjectile::new, MobCategory.MISC).sized(1.0f, 1.0f).fireImmune().setShouldReceiveVelocityUpdates(true).updateInterval(1).build("molten_debris_projectile"));


    public static void register (IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}


