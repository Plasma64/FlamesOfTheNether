package net.plasma64.flamesofthenether;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.plasma64.flamesofthenether.client.particle.FOTNParticleRegistry;
import net.plasma64.flamesofthenether.client.particle.RoseHealerParticle;
import net.plasma64.flamesofthenether.misc.FOTNCreativeTabRegistry;
import net.plasma64.flamesofthenether.misc.FOTNSoundRegistry;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.block.blockentity.FOTNBlockEntityRegistry;
import net.plasma64.flamesofthenether.server.effect.FOTNEffectRegistry;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;
import net.plasma64.flamesofthenether.server.event.FOTNCommonEvents;
import net.plasma64.flamesofthenether.server.item.FOTNItemRegistry;
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
        FOTNSoundRegistry.register(modEventBus);
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

            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.REGENERATION)), Ingredient.of(FOTNItemRegistry.ROSE_QUARTZ.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), FOTNPotionRegistry.ROSES_BLESSING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), FOTNPotionRegistry.ROSES_BLESSING_POTION.get())), Ingredient.of(Items.REDSTONE), PotionUtils.setPotion(new ItemStack(Items.POTION), FOTNPotionRegistry.LONG_ROSES_BLESSING_POTION.get()));
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

        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(FOTNParticleRegistry.ROSE_HEALER.get(), RoseHealerParticle.Provider::new);
        }
    }
}
