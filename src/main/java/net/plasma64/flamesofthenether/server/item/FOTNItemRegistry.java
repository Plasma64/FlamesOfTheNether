package net.plasma64.flamesofthenether.server.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class FOTNItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Item> TAINTED_NETHER_BRICK = ITEMS.register("tainted_nether_brick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROSE_QUARTZ = ITEMS.register("rose_quartz",
            () -> new Item(new Item.Properties()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
