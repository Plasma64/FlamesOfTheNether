package net.plasma64.flamesofthenether.server.block.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;

public class FOTNBlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FlamesOfTheNether.MODID);

    public static final RegistryObject<BlockEntityType<RoseHealerBlockEntity>> ROSE_HEALER =
            BLOCK_ENTITIES.register("rose_healer", () -> BlockEntityType.Builder.of(RoseHealerBlockEntity::new, FOTNBlockRegistry.ROSE_HEALER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
