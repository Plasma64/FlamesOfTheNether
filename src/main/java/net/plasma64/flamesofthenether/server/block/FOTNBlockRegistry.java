package net.plasma64.flamesofthenether.server.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;

import java.util.function.Supplier;

public class FOTNBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Block> TAINTED_NETHERRACK = registerBlock("tainted_netherrack",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICKS = registerBlock("tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final RegistryObject<Block> GOLD_CHAIN = registerBlock("gold_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
    public static final RegistryObject<Block> ROSE_QUARTZ_BLOCK = registerBlock("rose_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> ROSE_QUARTZ_STAIRS = registerBlock("rose_quartz_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> ROSE_QUARTZ_SLAB = registerBlock("rose_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ = registerBlock("smooth_rose_quartz",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ_STAIRS = registerBlock("smooth_rose_quartz_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ_SLAB = registerBlock("smooth_rose_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> ROSE_QUARTZ_BRICKS = registerBlock("rose_quartz_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> CHISELED_ROSE_QUARTZ_BLOCK = registerBlock("chiseled_rose_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> ROSE_QUARTZ_PILLAR = registerBlock("rose_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> ROSE_HEALER = registerBlock("rose_healer",
            () -> new RoseHealerBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get())));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockAndItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockAndItem(String name, RegistryObject<T> block) {
        return FOTNItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register (IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
