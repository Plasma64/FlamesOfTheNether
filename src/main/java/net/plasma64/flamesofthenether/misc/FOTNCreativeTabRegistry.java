package net.plasma64.flamesofthenether.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;
import net.plasma64.flamesofthenether.server.potion.FOTNPotionRegistry;

public class FOTNCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlamesOfTheNether.MODID);

    public static final RegistryObject<CreativeModeTab> MOLTEN_BARRENS = CREATIVE_MODE_TABS.register("molten_barrens",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FOTNItemRegistry.TAINTED_NETHER_BRICK.get()))
                    .title(Component.translatable("creativetab.molten_barrens"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHERRACK.get());

                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICK_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICK_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICK_WALL.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICK_FENCE.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_TAINTED_NETHER_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_TAINTED_NETHER_BRICKS.get());

                        pOutput.accept(FOTNBlockRegistry.POLISHED_BLACKSTONE_PILLAR.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_CHECKERED_POLISHED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_CHECKERED_POLISHED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_BLACKSTONE_TILES_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.GILDED_BLACKSTONE_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.GILDED_BLACKSTONE_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.GILDED_BLACKSTONE_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.CHECKERED_POLISHED_GILDED_BLACKSTONE_TILES_WALL.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_WALL.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_POLISHED_GILDED_BLACKSTONE.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_PILLAR.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_GILDED_BLACKSTONE_BRICK_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.CRACKED_QUARTZ_BRICKS.get());

                        pOutput.accept(FOTNBlockRegistry.QUARTZ_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.SMOOTH_QUARTZ_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.QUARTZ_BRICK_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_ROSE_QUARTZ_ORE.get());

                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_ROSE_QUARTZ_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_ROSE_QUARTZ_BLOCK.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BRICK_WALL.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_PILLAR.get());

                        pOutput.accept(FOTNBlockRegistry.MAROONSTONE.get());
                        pOutput.accept(FOTNBlockRegistry.MAROONSTONE_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.MAROONSTONE_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.MAROONSTONE_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CRACKED_POLISHED_MAROONSTONE_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_POLISHED_MAROONSTONE.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_PILLAR.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICK_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICK_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.POLISHED_MAROONSTONE_BRICK_WALL.get());

                        pOutput.accept(FOTNBlockRegistry.GOLD_BARS.get());
                        pOutput.accept(FOTNBlockRegistry.GOLD_CHAIN.get());
                        pOutput.accept(FOTNBlockRegistry.GOLD_LANTERN.get());

                        pOutput.accept(FOTNBlockRegistry.SOUL_ROOTS.get());

                        pOutput.accept(FOTNBlockRegistry.CRIMSON_BLAST_FUNGUS.get());
                        pOutput.accept(FOTNBlockRegistry.CRIMSON_BLAST_FUNGUS_BLOCK.get());

                        pOutput.accept(FOTNBlockRegistry.WARPED_BLAST_FUNGUS.get());
                        pOutput.accept(FOTNBlockRegistry.WARPED_BLAST_FUNGUS_BLOCK.get());

                        pOutput.accept(FOTNBlockRegistry.ASH.get());
                        pOutput.accept(FOTNBlockRegistry.ASH_BLOCK.get());

                        pOutput.accept(FOTNBlockRegistry.ROSE_HEALER.get());

                        pOutput.accept(FOTNBlockRegistry.MOLTEN_DEBRIS.get());

                        pOutput.accept(FOTNItemRegistry.TAINTED_NETHER_BRICK.get());

                        pOutput.accept(FOTNItemRegistry.ROSE_QUARTZ.get());

                        pOutput.accept(FOTNItemRegistry.MAGMA_CANNON.get());

                        pOutput.accept(FOTNItemRegistry.SOUL_PEPPER_SEEDS.get());
                        pOutput.accept(FOTNItemRegistry.SOUL_PEPPER.get());
                        pOutput.accept(FOTNItemRegistry.SOUL_PEPPER_STEW.get());

                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), FOTNPotionRegistry.ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), FOTNPotionRegistry.ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), FOTNPotionRegistry.ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), FOTNPotionRegistry.ROSES_BLESSING_POTION.get()));
                        pOutput.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION.get()));
                    })
                    .build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
