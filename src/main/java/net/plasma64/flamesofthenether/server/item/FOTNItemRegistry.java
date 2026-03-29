package net.plasma64.flamesofthenether.server.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;

public class FOTNItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Item> TAINTED_NETHER_BRICK = ITEMS.register("tainted_nether_brick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROSE_QUARTZ = ITEMS.register("rose_quartz",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGMA_CANNON = ITEMS.register("magma_cannon",
            () -> new MagmaCannonItem(new Item.Properties().stacksTo(1).fireResistant().durability(2031)));

    public static final RegistryObject<Item> SOUL_PEPPER = ITEMS.register("soul_pepper",
            () -> new Item(new Item.Properties().food(FOTNFoods.SOUL_PEPPER)));
    public static final RegistryObject<Item> SOUL_PEPPER_SEEDS = ITEMS.register("soul_pepper_seeds",
            () -> new ItemNameBlockItem(FOTNBlockRegistry.SOUL_PEPPER_BUSH.get(),new Item.Properties()));
    public static final RegistryObject<Item> SOUL_PEPPER_STEW = ITEMS.register("soul_pepper_stew",
            () -> new Item(new Item.Properties().food(FOTNFoods.SOUL_PEPPER_STEW)));

    public static final RegistryObject<Item> ASHES = ITEMS.register("ashes",
            () -> new AshesItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ASH_BRICK = ITEMS.register("ash_brick",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POCKET_ASHES = ITEMS.register("pocket_ashes", () -> new PocketAshesItem(new Item.Properties()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
