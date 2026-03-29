package net.plasma64.flamesofthenether.client.render.item;

import net.plasma64.flamesofthenether.client.model.item.MagmaCannonModel;
import net.plasma64.flamesofthenether.server.item.MagmaCannonItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MagmaCannonRender extends GeoItemRenderer<MagmaCannonItem> {
    public MagmaCannonRender() {
        super(new MagmaCannonModel());
    }
}
