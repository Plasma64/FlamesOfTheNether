package net.plasma64.flamesofthenether.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.plasma64.flamesofthenether.server.entity.item.MagmaBlockProjectileEntity;


public class MagmaBlockProjectileRender extends EntityRenderer<MagmaBlockProjectileEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public MagmaBlockProjectileRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    @Override
    public void render(MagmaBlockProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
        pPoseStack.translate(-0.5F, 0.0F, -0.5F);
        renderBlock(this.blockRenderer, Blocks.MAGMA_BLOCK.defaultBlockState(), pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public static void renderBlock(BlockRenderDispatcher pBlockRenderDispatcher, BlockState pState, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pBlockRenderDispatcher.renderSingleBlock(pState, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getTextureLocation(MagmaBlockProjectileEntity pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
