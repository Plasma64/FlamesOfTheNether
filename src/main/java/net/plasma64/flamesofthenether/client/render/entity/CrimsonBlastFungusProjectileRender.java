package net.plasma64.flamesofthenether.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.entity.item.CrimsonBlastFungusProjectile;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrimsonBlastFungusProjectileRender extends EntityRenderer<CrimsonBlastFungusProjectile> {
    private final BlockRenderDispatcher blockRenderer;

    public CrimsonBlastFungusProjectileRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    @Override
    public void render(CrimsonBlastFungusProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 0.5F, 0.0F);

        int fuse = pEntity.getFuse();

        if ((float)fuse - pPartialTick + 1.0F < 10.0F) {
            float f = 1.0F - ((float)fuse - pPartialTick + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            pPoseStack.scale(f1, f1, f1);
        }

        pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        pPoseStack.translate(-0.5F, -0.5F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, FOTNBlockRegistry.CRIMSON_BLAST_FUNGUS_BLOCK.get().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, fuse / 5 % 2 == 0);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public static void renderModel(PoseStack.Pose pPose, VertexConsumer pConsumer, @Nullable BlockState pState, BakedModel pModel, float pRed, float pGreen, float pBlue, int pPackedLight, int pPackedOverlay, net.minecraftforge.client.model.data.ModelData modelData, net.minecraft.client.renderer.RenderType renderType) {
        RandomSource randomsource = RandomSource.create();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            renderQuadList(pPose, pConsumer, pRed, pGreen, pBlue, pModel.getQuads(pState, direction, randomsource, modelData, renderType), pPackedLight, pPackedOverlay);
        }

        randomsource.setSeed(42L);
        renderQuadList(pPose, pConsumer, pRed, pGreen, pBlue, pModel.getQuads(pState, (Direction) null, randomsource, modelData, renderType), pPackedLight, pPackedOverlay);
    }

    private static void renderQuadList(PoseStack.Pose pPose, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, List<BakedQuad> pQuads, int pItemStack, int pCombinedOverlay) {
        for (BakedQuad bakedquad : pQuads) {
            float f;
            float f1;
            float f2;
            f = Mth.clamp(pRed, 0.0F, 1.0F);
            f1 = Mth.clamp(pGreen, 0.0F, 1.0F);
            f2 = Mth.clamp(pBlue, 0.0F, 1.0F);
            pConsumer.putBulkData(pPose, bakedquad, f, f1, f2, pItemStack, pCombinedOverlay);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(CrimsonBlastFungusProjectile pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
