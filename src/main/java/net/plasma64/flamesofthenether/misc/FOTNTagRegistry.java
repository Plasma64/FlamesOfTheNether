package net.plasma64.flamesofthenether.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.plasma64.flamesofthenether.FlamesOfTheNether;

public class FOTNTagRegistry {

    //Block Tags
    public static final TagKey<Block> MOLTEN_DEBRIS_COOLING_BLOCKS = registerBlockTag("molten_debris_cooling_blocks");
    public static final TagKey<Block> MOLTEN_DEBRIS_HEATING_BLOCKS = registerBlockTag("molten_debris_heating_blocks");

    //Item Tags
    public static final TagKey<Item> SLAG_CANNON_AMMO = registerItemTag("slag_cannon_ammo");
    public static final TagKey<Item> POCKET_ASHES_AMMO = registerItemTag("pocket_ashes_ammo");
    public static final TagKey<Item> CHISELED_POLISHED_MAROONSTONE_LIGHTING_ITEMS = registerItemTag("chiseled_polished_maroonstone_lighting_items");

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, name));
    }

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, name));
    }
}
