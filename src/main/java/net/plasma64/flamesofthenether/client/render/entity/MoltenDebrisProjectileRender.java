package net.plasma64.flamesofthenether.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.plasma64.flamesofthenether.FlamesOfTheNether;
import net.plasma64.flamesofthenether.client.entity.FOTNEntityModelLayers;
import net.plasma64.flamesofthenether.client.model.MoltenDebrisProjectileModel;
import net.plasma64.flamesofthenether.server.entity.item.MoltenDebrisProjectile;


public class MoltenDebrisProjectileRender extends EntityRenderer<MoltenDebrisProjectile> {
    private final MoltenDebrisProjectileModel<MoltenDebrisProjectile> model;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FlamesOfTheNether.MODID, "textures/entity/molten_debris_projectile.png");


    public MoltenDebrisProjectileRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new MoltenDebrisProjectileModel<>(pContext.bakeLayer(FOTNEntityModelLayers.MOLTEN_DEBRIS_PROJECTILE));
    }

    @Override
    public void render(MoltenDebrisProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float age = pEntity.tickCount + pPartialTick;
        pPoseStack.pushPose();
        pPoseStack.translate(0.0D, 0.5D, 0.0D);
        model.setupAnim(pEntity, 0.0F, 0.0F, age, 0.0F, 0.0F);
        VertexConsumer vb = pBuffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(pEntity)));
        model.renderToBuffer(pPoseStack, vb, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MoltenDebrisProjectile pEntity) {
        return TEXTURE;
    }
}
