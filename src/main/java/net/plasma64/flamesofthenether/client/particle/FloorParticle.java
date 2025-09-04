package net.plasma64.flamesofthenether.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;


public abstract class FloorParticle extends TextureSheetParticle {
    protected FloorParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }
    @Override
    public boolean shouldCull() {
        return false;
    }

    void renderFloorParticle(VertexConsumer vertexConsumer, Camera camera, float delta, Consumer<Quaternionf> rotation) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(delta, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(delta, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(delta, this.zo, this.z) - vec3.z());
        Vector3f vector = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(0.0F, vector.x(), vector.y(), vector.z());
        rotation.accept(quaternionf);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f3 = this.getQuadSize(delta);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        int j = this.getLightColor(delta);
        this.makeCornerVertex(vertexConsumer, avector3f[0], this.getU1(), this.getV1(), j);
        this.makeCornerVertex(vertexConsumer, avector3f[1], this.getU1(), this.getV0(), j);
        this.makeCornerVertex(vertexConsumer, avector3f[2], this.getU0(), this.getV0(), j);
        this.makeCornerVertex(vertexConsumer, avector3f[3], this.getU0(), this.getV1(), j);
    }

    void makeCornerVertex(VertexConsumer vertexConsumer, Vector3f avector3f, float u, float v, int lightColor) {
        vertexConsumer.vertex(avector3f.x(), avector3f.y(), avector3f.z()).uv(u, v).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
    }
}
