package net.plasma64.flamesofthenether.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;

public class FOTNCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlamesOfTheNether.MODID);

    public static final RegistryObject<CreativeModeTab> MOLTEN_BARRENS = CREATIVE_MODE_TABS.register("molten_barrens",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FOTNItemRegistry.TAINTED_NETHER_BRICK.get()))
                    .title(Component.translatable("creativetab.molten_barrens"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FOTNItemRegistry.TAINTED_NETHER_BRICK.get());
                        pOutput.accept(FOTNItemRegistry.ROSE_QUARTZ.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BLOCK.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ_SLAB.get());
                        pOutput.accept(FOTNBlockRegistry.SMOOTH_ROSE_QUARTZ_STAIRS.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.CHISELED_ROSE_QUARTZ_BLOCK.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_QUARTZ_PILLAR.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHERRACK.get());
                        pOutput.accept(FOTNBlockRegistry.TAINTED_NETHER_BRICKS.get());
                        pOutput.accept(FOTNBlockRegistry.GOLD_CHAIN.get());
                        pOutput.accept(FOTNBlockRegistry.ROSE_HEALER.get());

                    })
                    .build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
