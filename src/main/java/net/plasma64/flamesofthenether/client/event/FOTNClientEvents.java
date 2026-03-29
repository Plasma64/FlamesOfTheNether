package net.plasma64.flamesofthenether.client.event;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.client.render.entity.CrimsonBlastFungusProjectileRender;
import net.plasma64.flamesofthenether.client.render.entity.MagmaBlockProjectileRender;
import net.plasma64.flamesofthenether.client.render.entity.MoltenDebrisProjectileRender;
import net.plasma64.flamesofthenether.client.render.entity.WarpedBlastFungusProjectileRender;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;

@Mod.EventBusSubscriber(modid = FlamesOfTheNether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FOTNClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FOTNEntityRegistry.MAGMA_BLOCK_PROJECTILE.get(), MagmaBlockProjectileRender::new);
        event.registerEntityRenderer(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), MoltenDebrisProjectileRender::new);
        event.registerEntityRenderer(FOTNEntityRegistry.CRIMSON_BLAST_FUNGUS_PROJECTILE.get(), CrimsonBlastFungusProjectileRender::new);
        event.registerEntityRenderer(FOTNEntityRegistry.WARPED_BLAST_FUNGUS_PROJECTILE.get(), WarpedBlastFungusProjectileRender::new);
        event.registerEntityRenderer(FOTNEntityRegistry.ASHES.get(), ThrownItemRenderer::new);
    }


}