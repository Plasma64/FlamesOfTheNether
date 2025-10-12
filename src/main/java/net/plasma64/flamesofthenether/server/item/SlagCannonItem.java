package net.plasma64.flamesofthenether.server.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import net.plasma64.flamesofthenether.server.block.FOTNBlockRegistry;
import net.plasma64.flamesofthenether.server.entity.item.MoltenDebrisProjectile;

import java.util.function.Predicate;

public class SlagCannonItem extends ProjectileWeaponItem {
    public SlagCannonItem(Properties pProperties) {
        super(pProperties);
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
        return UseAnim.BOW;
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
}