package net.plasma64.flamesofthenether.client.model.item;

import net.minecraft.resources.ResourceLocation;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.item.MagmaCannonItem;
import software.bernie.geckolib.model.GeoModel;

public class MagmaCannonModel extends GeoModel<MagmaCannonItem> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "textures/item/magma_cannon.png");

    @Override
    public ResourceLocation getModelResource(MagmaCannonItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "geo/magma_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MagmaCannonItem animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MagmaCannonItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "animations/magma_cannon.animation.json");
    }
}
