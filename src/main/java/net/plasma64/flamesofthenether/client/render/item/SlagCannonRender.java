package net.plasma64.flamesofthenether.client.render.item;

import net.plasma64.flamesofthenether.client.model.item.SlagCannonModel;
import net.plasma64.flamesofthenether.server.item.SlagCannonItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SlagCannonRender extends GeoItemRenderer<SlagCannonItem> {
    public SlagCannonRender() {
        super(new SlagCannonModel());
    }
}
