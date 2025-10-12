package net.plasma64.flamesofthenether.server.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.misc.FOTNSoundTypes;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class FOTNBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FlamesOfTheNether.MODID);

    public static final RegistryObject<Block> TAINTED_NETHERRACK = registerBlock("tainted_netherrack",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICKS = registerBlock("tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final RegistryObject<Block> CRACKED_TAINTED_NETHER_BRICKS = registerBlock("cracked_tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_TAINTED_NETHER_BRICKS = registerBlock("chiseled_tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_SLAB = registerBlock("tainted_nether_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_STAIRS = registerBlock("tainted_nether_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get()).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_FENCE = registerBlock("tainted_nether_brick_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_WALL = registerBlock("tainted_nether_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> GILDED_BLACKSTONE_SLAB = registerBlock("gilded_blackstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_BLACKSTONE_STAIRS = registerBlock("gilded_blackstone_stairs",
            () -> new StairBlock(Blocks.GILDED_BLACKSTONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_BLACKSTONE_WALL = registerBlock("gilded_blackstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).forceSolidOn()));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE = registerBlock("gilded_polished_blackstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_SLAB = registerBlock("gilded_polished_blackstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE.get())));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_STAIRS = registerBlock("gilded_polished_blackstone_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE.get())));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_WALL = registerBlock("gilded_polished_blackstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE.get()).forceSolidOn()));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_BRICKS = registerBlock("gilded_polished_blackstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_CRACKED_POLISHED_BLACKSTONE_BRICKS = registerBlock("gilded_cracked_polished_blackstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE_BRICKS.get())));
    public static final RegistryObject<Block> GILDED_CHISELED_POLISHED_BLACKSTONE = registerBlock("gilded_chiseled_polished_blackstone",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.GILDED_POLISHED_BLACKSTONE.get()).strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("gilded_polished_blackstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_BRICK_STAIRS = registerBlock("gilded_polished_blackstone_brick_stairs",
            () -> new StairBlock(Blocks.GILDED_BLACKSTONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_POLISHED_BLACKSTONE_BRICK_WALL = registerBlock("gilded_polished_blackstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).forceSolidOn()));
    public static final RegistryObject<Block> GOLD_CHAIN = registerBlock("gold_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
    public static final RegistryObject<Block> GOLD_BARS = registerBlock("gold_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BARS)));
    public static final RegistryObject<Block> GOLD_LANTERN = registerBlock("gold_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));
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
            () -> new RoseHealerBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryObject<Block> MOLTEN_DEBRIS = registerBlock("molten_debris",
            () -> new MoltenDebrisBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().lightLevel(litBlockEmission(3, MoltenDebrisBlock.BURNING)).strength(20.0F, 1000.0F).isValidSpawn((pState, pLevel, pPos, pEntityType) -> pEntityType.fireImmune()).hasPostProcess((pState, pLevel, pPos) -> pState.getValue(MoltenDebrisBlock.BURNING)).emissiveRendering((pState, pLevel, pPos) -> pState.getValue(MoltenDebrisBlock.BURNING)).sound(FOTNSoundTypes.MOLTEN_DEBRIS)));


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

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue, BooleanProperty properties) {
        return (state) -> state.getValue(properties) ? pLightValue : 0;
    }
}
