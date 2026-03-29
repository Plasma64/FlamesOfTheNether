package net.plasma64.flamesofthenether.server.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.plasma64.flamesofthenether.client.render.item.MagmaCannonRender;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.entity.item.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MagmaCannonItem extends ProjectileWeaponItem implements GeoItem {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean hasPlayedChargingFinishedSound;

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenPlayAndHold("charge");
    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().thenPlay("shoot");

    public MagmaCannonItem(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new MagmaCannonRender();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }

            private static final HumanoidModel.ArmPose MAGMA_CANNON = HumanoidModel.ArmPose.create("magma_cannon", true, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.yRot = -0.1F + model.head.yRot;
                    model.rightArm.xRot = -1.5F + model.head.xRot;
                    model.leftArm.yRot = 0.4F + model.head.yRot;
                    model.leftArm.xRot = -1.5F + model.head.xRot;
                } else {
                    if (arm == HumanoidArm.LEFT) {
                        model.leftArm.yRot = 0.1F + model.head.yRot;
                        model.leftArm.xRot = -1.5F + model.head.xRot;
                        model.rightArm.yRot = -0.4F + model.head.yRot;
                        model.rightArm.xRot = -1.5F + model.head.xRot;
                    }
                }
            });

            @Override
            public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return MAGMA_CANNON;
                    }
                }
                return HumanoidModel.ArmPose.ITEM;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;

                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                    poseStack.translate(0.0, -0.05, 0.0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(FOTNTagRegistry.SLAG_CANNON_AMMO);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 30;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.getAbilities().instabuild && pPlayer.getProjectile(itemStack).isEmpty()) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            pPlayer.startUsingItem(pUsedHand);
            if (!pLevel.isClientSide) {
                triggerAnim(pPlayer, GeoItem.getOrAssignId(itemStack, (ServerLevel) pLevel), "controller", "charge");
            }
            return InteractionResultHolder.consume(itemStack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            ItemStack ammo = player.getProjectile(pStack);
            int chargeTime = getUseDuration(pStack) - pTimeCharged;

            if (!ammo.isEmpty() || player.getAbilities().instabuild) {
                float power = getPowerForTime(chargeTime);

                if (power >= 1.0D) {
                    if (!pLevel.isClientSide) {
                        if (ammo.is(FOTNBlockRegistry.MOLTEN_DEBRIS.get().asItem())) {
                            triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "shoot");
                            MoltenDebrisProjectileEntity moltenDebrisProjectileEntity = new MoltenDebrisProjectileEntity(pLevel, player);
                            moltenDebrisProjectileEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                            pLevel.addFreshEntity(moltenDebrisProjectileEntity);
                            player.getCooldowns().addCooldown(this, 60);
                        } else {
                            if (ammo.is(FOTNBlockRegistry.CRIMSON_BLAST_FUNGUS_BLOCK.get().asItem())) {
                                triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "shoot");
                                CrimsonBlastFungusProjectile crimsonBlastFungusProjectile = new CrimsonBlastFungusProjectile(pLevel, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), player);
                                crimsonBlastFungusProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                                crimsonBlastFungusProjectile.setFuse(40);
                                pLevel.addFreshEntity(crimsonBlastFungusProjectile);
                                player.getCooldowns().addCooldown(this, 60);
                            } else {
                                if (ammo.is(FOTNBlockRegistry.WARPED_BLAST_FUNGUS_BLOCK.get().asItem())) {
                                    triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "shoot");
                                    WarpedBlastFungusProjectile warpedBlastFungusProjectile = new WarpedBlastFungusProjectile(pLevel, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), player);
                                    warpedBlastFungusProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                                    warpedBlastFungusProjectile.setFuse(40);
                                    pLevel.addFreshEntity(warpedBlastFungusProjectile);
                                    player.getCooldowns().addCooldown(this, 60);
                                } else {
                                    if (player.getAbilities().instabuild) {
                                        triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "shoot");
                                        MoltenDebrisProjectileEntity moltenDebrisProjectile = new MoltenDebrisProjectileEntity(pLevel, player);
                                        moltenDebrisProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                                        pLevel.addFreshEntity(moltenDebrisProjectile);
                                        player.getCooldowns().addCooldown(this, 60);
                                    }
                                }
                            }
                        }

                        if (!player.getAbilities().instabuild) {
                            pStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                            ammo.shrink(1);
                        }
                    }

                    pLevel.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    if (!pLevel.isClientSide) {
                        triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "idle");
                    }
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!(pEntity instanceof Player player)) {
            return;
        }

        if (!pIsSelected) {
            if (!pLevel.isClientSide) {
                triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller","idle");
            }
        }
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 40.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller_idle", 0, this::predicate));
        controllers.add(new AnimationController(this, "controller", state -> PlayState.STOP)
                .triggerableAnim("idle", IDLE_ANIM)
                .triggerableAnim("charge", CHARGE_ANIM)
                .triggerableAnim("shoot", SHOOT_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (!pLevel.isClientSide) {
            int chargeTime = getUseDuration(pStack) - pRemainingUseDuration;
            float power = getPowerForTime(chargeTime);
            if (power < 1.0F) {
                hasPlayedChargingFinishedSound = false;
            }

            if (power >= 1.0F && !hasPlayedChargingFinishedSound) {
                hasPlayedChargingFinishedSound = true;
                pLevel.playSound((Player) null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}