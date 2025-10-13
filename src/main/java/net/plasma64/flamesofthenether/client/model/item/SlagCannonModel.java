package net.plasma64.flamesofthenether.client.model.item;

import net.minecraft.resources.ResourceLocation;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.server.item.SlagCannonItem;
import software.bernie.geckolib.model.GeoModel;

public class SlagCannonModel extends GeoModel<SlagCannonItem> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "textures/item/slag_cannon.png");

    @Override
    public ResourceLocation getModelResource(SlagCannonItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "geo/slag_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SlagCannonItem animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SlagCannonItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "animations/slag_cannon.animation.json");
    }
}
