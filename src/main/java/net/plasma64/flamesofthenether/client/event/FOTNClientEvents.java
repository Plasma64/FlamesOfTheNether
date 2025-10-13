package net.plasma64.flamesofthenether.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.client.entity.FOTNEntityModelLayers;
import net.plasma64.flamesofthenether.client.model.entity.MoltenDebrisProjectileModel;
import net.plasma64.flamesofthenether.client.render.entity.MoltenDebrisProjectileRender;
import net.plasma64.flamesofthenether.server.entity.FOTNEntityRegistry;

@Mod.EventBusSubscriber(modid = FlamesOfTheNether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FOTNClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FOTNEntityModelLayers.MOLTEN_DEBRIS_PROJECTILE, MoltenDebrisProjectileModel::getLayerDefinition);
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FOTNEntityRegistry.MOLTEN_DEBRIS_PROJECTILE.get(), MoltenDebrisProjectileRender::new);
    }
}