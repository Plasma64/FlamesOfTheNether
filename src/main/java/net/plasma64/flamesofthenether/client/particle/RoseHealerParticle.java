package net.plasma64.flamesofthenether.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RoseHealerParticle extends FloorParticle {
    private final SpriteSet spriteSet;
    public float maxSize;

    public RoseHealerParticle(ClientLevel world, double x, double y, double z, double speed, SpriteSet spriteSet, float maxSize) {
        super(world, x, y, z);
        this.maxSize = maxSize;
        alpha = 1;
        quadSize = 0;
        lifetime = 40;

        setSpriteFromAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;

        quadSize = Mth.lerp(0.06F, quadSize, this.maxSize);

        if (age++ >= lifetime) {
            remove();
        } else {
            if (age > (lifetime / 2)) {
                alpha -= 0.04F;
            }
        }

        setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float delta) {
        this.renderFloorParticle(vertexConsumer, camera, delta, (quaternionf) -> {quaternionf.rotateX((float) (-Math.PI * 0.5F));});
        this.renderFloorParticle(vertexConsumer, camera, delta, (quaternionf) -> {quaternionf.rotateX((float) (Math.PI * 0.5F));});
    }

    @Override
    protected int getLightColor(float tint) {
        return 240;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double speed, double pYSpeed, double pZSpeed) {
            return new RoseHealerParticle(pLevel, pX, pY, pZ, speed, spriteSet, 3.5F);
        }
    }
}
