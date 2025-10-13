package net.plasma64.flamesofthenether.server.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.plasma64.flamesofthenether.client.render.item.SlagCannonRender;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import net.plasma64.flamesofthenether.server.entity.item.MoltenDebrisProjectile;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SlagCannonItem extends ProjectileWeaponItem implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenLoop("charge");
    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().thenPlay("shoot");

    public SlagCannonItem(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer = new SlagCannonRender();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
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
        return UseAnim.NONE;
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
                        triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "shoot");
                        MoltenDebrisProjectile moltenDebrisProjectile = new MoltenDebrisProjectile(pLevel, player);
                        moltenDebrisProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                        pLevel.addFreshEntity(moltenDebrisProjectile);
                        player.getCooldowns().addCooldown(this, 60);

                        if (!player.getAbilities().instabuild) {
                            pStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                            ammo.shrink(1);
                        }
                    }

                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    if (!pLevel.isClientSide) {
                        triggerAnim(player, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "controller", "idle");
                    }
                }
            }
        }
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 20.0F;
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
}