package net.plasma64.flamesofthenether;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.plasma64.flamesofthenether.client.particle.*;
import net.plasma64.flamesofthenether.misc.FOTNCreativeTabRegistry;
import net.plasma64.flamesofthenether.misc.FOTNSoundEvents;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.block.blockentity.FOTNBlockEntityRegistry;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;
import net.plasma64.flamesofthenether.server.event.FOTNCommonEvents;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;
import net.plasma64.flamesofthenether.server.item.PocketAshesItem;
import net.plasma64.flamesofthenether.server.potion.FOTNPotionRegistry;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FlamesOfTheNether.MODID)
public class FlamesOfTheNether {
    public static final String MODID = "flamesofthenether";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FlamesOfTheNether(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        FOTNCreativeTabRegistry.register(modEventBus);
        FOTNItemRegistry.register(modEventBus);
        FOTNBlockRegistry.register(modEventBus);
        FOTNParticleRegistry.register(modEventBus);
        FOTNSoundEvents.register(modEventBus);
        FOTNEffectRegistry.register(modEventBus);
        FOTNBlockEntityRegistry.register(modEventBus);
        FOTNEntityRegistry.register(modEventBus);
        FOTNPotionRegistry.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(FOTNCommonEvents.class);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FOTNPotionRegistry.setup();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(FOTNItemRegistry.POCKET_ASHES.get(), ResourceLocation.withDefaultNamespace("filled"), (itemStack, level, livingEntity, seed) -> {
                    return PocketAshesItem.getAmmo(itemStack) >= PocketAshesItem.MAX_AMMO ? 1.0F : 0.0F;
                });
            });
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(FOTNParticleRegistry.ROSE_HEALER.get(), RoseHealerParticle.Provider::new);
            event.registerSpriteSet(FOTNParticleRegistry.CRIMSON_FUME.get(), CrimsonFumeParticle.Provider::new);
            event.registerSpriteSet(FOTNParticleRegistry.CRIMSON_SPRAY.get(), CrimsonSprayParticle.Provider::new);
            event.registerSpriteSet(FOTNParticleRegistry.WARPED_FUME.get(), WarpedFumeParticle.Provider::new);
            event.registerSpriteSet(FOTNParticleRegistry.WARPED_SPRAY.get(), WarpedSprayParticle.Provider::new);
        }
    }
}
