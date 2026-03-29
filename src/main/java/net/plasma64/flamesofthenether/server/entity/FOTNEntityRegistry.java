package net.plasma64.flamesofthenether.server.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.entity.item.*;

public class FOTNEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FlamesOfTheNether.MODID);

    public static final RegistryObject<EntityType<MagmaBlockProjectileEntity>> MAGMA_BLOCK_PROJECTILE = ENTITY_TYPES.register("magma_block_projectile",
            () -> EntityType.Builder.<MagmaBlockProjectileEntity>of(MagmaBlockProjectileEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(20).setCustomClientFactory(MagmaBlockProjectileEntity::new).setShouldReceiveVelocityUpdates(true).updateInterval(1).build("magma_block_projectile"));

    public static final RegistryObject<EntityType<MoltenDebrisProjectileEntity>> MOLTEN_DEBRIS_PROJECTILE = ENTITY_TYPES.register("molten_debris_projectile",
            () -> EntityType.Builder.<MoltenDebrisProjectileEntity>of(MoltenDebrisProjectileEntity::new, MobCategory.MISC).fireImmune().sized(1.0f, 1.0f).clientTrackingRange(20).setCustomClientFactory(MoltenDebrisProjectileEntity::new).setShouldReceiveVelocityUpdates(true).updateInterval(1).build("molten_debris_projectile"));

    public static final RegistryObject<EntityType<CrimsonBlastFungusProjectile>> CRIMSON_BLAST_FUNGUS_PROJECTILE = ENTITY_TYPES.register("crimson_blast_fungus_projectile",
            () -> EntityType.Builder.<CrimsonBlastFungusProjectile>of(CrimsonBlastFungusProjectile::new, MobCategory.MISC).sized(0.98f, 0.98f).clientTrackingRange(10).setCustomClientFactory(CrimsonBlastFungusProjectile::new).updateInterval(10).build("crimson_blast_fungus_projectile"));

    public static final RegistryObject<EntityType<WarpedBlastFungusProjectile>> WARPED_BLAST_FUNGUS_PROJECTILE = ENTITY_TYPES.register("warped_blast_fungus_projectile",
            () -> EntityType.Builder.<WarpedBlastFungusProjectile>of(WarpedBlastFungusProjectile::new, MobCategory.MISC).sized(0.98f, 0.98f).clientTrackingRange(10).setCustomClientFactory(WarpedBlastFungusProjectile::new).updateInterval(10).build("warped_blast_fungus_projectile"));

    public static final RegistryObject<EntityType<Ashes>> ASHES = ENTITY_TYPES.register("ashes",
            () -> EntityType.Builder.<Ashes>of(Ashes::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).setCustomClientFactory(Ashes::new).setUpdateInterval(20).build("ashes_projectile"));

    public static void register (IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}


