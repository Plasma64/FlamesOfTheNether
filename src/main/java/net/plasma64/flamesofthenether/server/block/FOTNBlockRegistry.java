package net.plasma64.flamesofthenether.server.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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

    // Molten Barrens

    public static final RegistryObject<Block> TAINTED_NETHERRACK = registerBlock("tainted_netherrack",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NETHERRACK)));

    public static final RegistryObject<Block> TAINTED_NETHER_BRICKS = registerBlock("tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_TAINTED_NETHER_BRICKS = registerBlock("cracked_tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_TAINTED_NETHER_BRICKS = registerBlock("chiseled_tainted_nether_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_SLAB = registerBlock("tainted_nether_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_STAIRS = registerBlock("tainted_nether_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_WALL = registerBlock("tainted_nether_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> TAINTED_NETHER_BRICK_FENCE = registerBlock("tainted_nether_brick_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get())));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));
    public static final RegistryObject<Block> CHECKERED_POLISHED_BLACKSTONE_TILES = registerBlock("checkered_polished_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));
    public static final RegistryObject<Block> CRACKED_CHECKERED_POLISHED_BLACKSTONE_TILES = registerBlock("cracked_checkered_polished_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHISELED_CHECKERED_POLISHED_BLACKSTONE_TILES = registerBlock("chiseled_checkered_polished_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_BLACKSTONE_TILES_STAIRS = registerBlock("checkered_polished_blackstone_tiles_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_BLACKSTONE_TILES_SLAB = registerBlock("checkered_polished_blackstone_tiles_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_BLACKSTONE_TILES_WALL = registerBlock("checkered_polished_blackstone_tiles_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get()).forceSolidOn()));

    public static final RegistryObject<Block> GILDED_BLACKSTONE_STAIRS = registerBlock("gilded_blackstone_stairs",
            () -> new StairBlock(Blocks.GILDED_BLACKSTONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GILDED_BLACKSTONE_SLAB = registerBlock("gilded_blackstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> GILDED_BLACKSTONE_WALL = registerBlock("gilded_blackstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).forceSolidOn()));

    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE = registerBlock("polished_gilded_blackstone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GILDED_BLACKSTONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES = registerBlock("checkered_polished_gilded_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> CRACKED_CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES = registerBlock("cracked_checkered_polished_gilded_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHISELED_CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES = registerBlock("chiseled_checkered_polished_gilded_blackstone_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_STAIRS = registerBlock("checkered_polished_gilded_blackstone_tiles_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_SLAB = registerBlock("checkered_polished_gilded_blackstone_tiles_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get())));
    public static final RegistryObject<Block> CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_WALL = registerBlock("checkered_polished_gilded_blackstone_tiles_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get()).forceSolidOn()));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_STAIRS = registerBlock("polished_gilded_blackstone_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_SLAB = registerBlock("polished_gilded_blackstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_WALL = registerBlock("polished_gilded_blackstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get()).forceSolidOn()));

    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_BRICKS = registerBlock("polished_gilded_blackstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS = registerBlock("cracked_polished_gilded_blackstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_POLISHED_GILDED_BLACKSTONE = registerBlock("chiseled_polished_gilded_blackstone",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_PILLAR = registerBlock("polished_gilded_blackstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS = registerBlock("polished_gilded_blackstone_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICKS.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_BRICK_SLAB = registerBlock("polished_gilded_blackstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get())));
    public static final RegistryObject<Block> POLISHED_GILDED_BLACKSTONE_BRICK_WALL = registerBlock("polished_gilded_blackstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get()).forceSolidOn()));

    public static final RegistryObject<Block> CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));

    public static final RegistryObject<Block> QUARTZ_WALL = registerBlock("quartz_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).forceSolidOn()));

    public static final RegistryObject<Block> SMOOTH_QUARTZ_WALL = registerBlock("smooth_quartz_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ).forceSolidOn()));

    public static final RegistryObject<Block> QUARTZ_BRICK_STAIRS = registerBlock("quartz_brick_stairs",
            () -> new StairBlock(Blocks.QUARTZ_BRICKS::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));
    public static final RegistryObject<Block> QUARTZ_BRICK_SLAB = registerBlock("quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));
    public static final RegistryObject<Block> QUARTZ_BRICK_WALL = registerBlock("quartz_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS).forceSolidOn()));

    public static final RegistryObject<Block> TAINTED_NETHER_ROSE_QUARTZ_ORE = registerBlock("tainted_nether_rose_quartz_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).mapColor(MapColor.TERRACOTTA_PURPLE), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> ROSE_QUARTZ_BLOCK = registerBlock("rose_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> ROSE_QUARTZ_STAIRS = registerBlock("rose_quartz_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get())));
    public static final RegistryObject<Block> ROSE_QUARTZ_SLAB = registerBlock("rose_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get())));
    public static final RegistryObject<Block> ROSE_QUARTZ_WALL = registerBlock("rose_quartz_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get()).forceSolidOn()));

    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ = registerBlock("smooth_rose_quartz",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ_STAIRS = registerBlock("smooth_rose_quartz_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get())));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ_SLAB = registerBlock("smooth_rose_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get())));
    public static final RegistryObject<Block> SMOOTH_ROSE_QUARTZ_WALL = registerBlock("smooth_rose_quartz_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get()).forceSolidOn()));

    public static final RegistryObject<Block> ROSE_QUARTZ_BRICKS = registerBlock("rose_quartz_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get())));
    public static final RegistryObject<Block> CRACKED_ROSE_QUARTZ_BRICKS = registerBlock("cracked_rose_quartz_bricks", () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_ROSE_QUARTZ_BLOCK = registerBlock("chiseled_rose_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> ROSE_QUARTZ_PILLAR = registerBlock("rose_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> ROSE_QUARTZ_BRICK_STAIRS = registerBlock("rose_quartz_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get())));
    public static final RegistryObject<Block> ROSE_QUARTZ_BRICK_SLAB = registerBlock("rose_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get())));
    public static final RegistryObject<Block> ROSE_QUARTZ_BRICK_WALL = registerBlock("rose_quartz_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get()).forceSolidOn()));

    public static final RegistryObject<Block> ROSE_HEALER = registerBlock("rose_healer",
            () -> new RoseHealerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F)));

    public static final RegistryObject<Block> MOLTEN_DEBRIS = registerBlock("molten_debris",
            () -> new MoltenDebrisBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().lightLevel(litBlockEmission(3, MoltenDebrisBlock.BURNING)).strength(20.0F, 1000.0F).isValidSpawn((pState, pLevel, pPos, pEntityType) -> pEntityType.fireImmune()).hasPostProcess((pState, pLevel, pPos) -> pState.getValue(MoltenDebrisBlock.BURNING)).emissiveRendering((pState, pLevel, pPos) -> pState.getValue(MoltenDebrisBlock.BURNING)).sound(FOTNSoundTypes.MOLTEN_DEBRIS)));

    public static final RegistryObject<Block> MAROONSTONE = registerBlock("maroonstone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> MAROONSTONE_STAIRS = registerBlock("maroonstone_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.MAROONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.MAROONSTONE.get())));
    public static final RegistryObject<Block> MAROONSTONE_SLAB = registerBlock("maroonstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.MAROONSTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> MAROONSTONE_WALL = registerBlock("maroonstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.MAROONSTONE.get()).forceSolidOn()));

    public static final RegistryObject<Block> POLISHED_MAROONSTONE = registerBlock("polished_maroonstone",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.MAROONSTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_STAIRS = registerBlock("polished_maroonstone_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.MAROONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE.get())));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_SLAB = registerBlock("polished_maroonstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE.get())));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_WALL = registerBlock("polished_maroonstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE.get()).forceSolidOn()));

    public static final RegistryObject<Block> POLISHED_MAROONSTONE_BRICKS = registerBlock("polished_maroonstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE.get())));
    public static final RegistryObject<Block> CRACKED_POLISHED_MAROONSTONE_BRICKS = registerBlock("cracked_polished_maroonstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_POLISHED_MAROONSTONE = registerBlock("chiseled_polished_maroonstone",
            () -> new ChiseledPolishedMaroonstoneBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE.get()).strength(1.5F, 6.0F).lightLevel(litBlockEmission(7, ChiseledPolishedMaroonstoneBlock.LIT))));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_PILLAR = registerBlock("polished_maroonstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_BRICK_STAIRS = registerBlock("polished_maroonstone_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.MAROONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICKS.get())));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_BRICK_SLAB = registerBlock("polished_maroonstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICKS.get())));
    public static final RegistryObject<Block> POLISHED_MAROONSTONE_BRICK_WALL = registerBlock("polished_maroonstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICKS.get()).forceSolidOn()));

    public static final RegistryObject<Block> GOLD_BARS = registerBlock("gold_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BARS).mapColor(MapColor.GOLD)));

    public static final RegistryObject<Block> GOLD_CHAIN = registerBlock("gold_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN).mapColor(MapColor.GOLD)));

    public static final RegistryObject<Block> GOLD_LANTERN = registerBlock("gold_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).mapColor(MapColor.GOLD)));

    public static final RegistryObject<Block> SOUL_ROOTS = registerBlock("soul_roots",
            () -> new SoulRoots(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak().sound(SoundType.ROOTS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SOUL_PEPPER_BUSH = registerBlock("soul_pepper_bush",
            () -> new SoulPepperBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks().noCollission().sound(FOTNSoundTypes.SOUL_PEPPER_BUSH).pushReaction(PushReaction.DESTROY).lightLevel(state -> state.getValue(SoulPepperBushBlock.AGE) == 2 || state.getValue(SoulPepperBushBlock.AGE) == 3 ? 10 : 0)));

    public static final RegistryObject<Block> CRIMSON_BLAST_FUNGUS = registerBlock("crimson_blast_fungus",
            () -> new CrimsonBlastFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).noOcclusion().sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> WARPED_BLAST_FUNGUS = registerBlock("warped_blast_fungus",
            () -> new WarpedBlastFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noOcclusion().sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> CRIMSON_BLAST_FUNGUS_BLOCK = registerBlock("crimson_blast_fungus_block",
            () -> new CrimsonBlastFungusBlockBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).sound(SoundType.FUNGUS).strength(0.2F, 0.0F)));

    public static final RegistryObject<Block> WARPED_BLAST_FUNGUS_BLOCK = registerBlock("warped_blast_fungus_block",
            () -> new WarpedBlastFungusBlockBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.FUNGUS).strength(0.2F, 0.0F)));

    public static final RegistryObject<Block> ASH = registerBlock("ash",
            () -> new AshLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(FOTNSoundTypes.ASH).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((pState, pLevel, pPos) -> { return pState.getValue(AshLayerBlock.LAYERS) >= 8;})));
    public static final RegistryObject<Block> ASH_BLOCK = registerBlock("ash_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(FOTNSoundTypes.ASH).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));

    public static final RegistryObject<Block> ASH_BRICKS = registerBlock("ash_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> ASH_BRICK_STAIRS = registerBlock("ash_brick_stairs",
            () -> new StairBlock(() -> FOTNBlockRegistry.ASH_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOTNBlockRegistry.ASH_BRICKS.get())));
    public static final RegistryObject<Block> ASH_BRICK_SLAB = registerBlock("ash_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ASH_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> ASH_BRICK_WALL = registerBlock("ash_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FOTNBlockRegistry.ASH_BRICKS.get()).forceSolidOn()));

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
