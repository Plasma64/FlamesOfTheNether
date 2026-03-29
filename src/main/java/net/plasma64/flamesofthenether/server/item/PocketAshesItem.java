package net.plasma64.flamesofthenether.server.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.plasma64.flamesofthenether.misc.FOTNSoundEvents;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import net.plasma64.flamesofthenether.server.entity.item.Ashes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class PocketAshesItem extends Item {

    public static final Predicate<ItemStack> ASH_AMMO = (itemStack) -> itemStack.is(FOTNTagRegistry.POCKET_ASHES_AMMO);

    public static final int MAX_AMMO = 16;

    public PocketAshesItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!hasAmmo(itemstack)) {
            if (!reloadFromInventory(pPlayer, itemstack)) {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        if (!pLevel.isClientSide) {
            Ashes ashes = new Ashes(pLevel, pPlayer);
            ashes.setItem(new ItemStack(FOTNItemRegistry.ASHES.get()));
            ashes.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), FOTNSoundEvents.ASHES_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pLevel.addFreshEntity(ashes);
        }

        if (!pPlayer.getAbilities().instabuild) {
            setAmmo(itemstack, getAmmo(itemstack) - 1);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public static int getAmmo(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("ash_ammo");
    }

    public static void setAmmo(ItemStack itemStack, int amount) {
        itemStack.getOrCreateTag().putInt("ash_ammo", Mth.clamp(amount, 0, MAX_AMMO));
    }

    public static boolean hasAmmo(ItemStack itemStack) {
        return getAmmo(itemStack) > 0;
    }

    public static boolean canReload(ItemStack itemStack) {
        return getAmmo(itemStack) < MAX_AMMO;
    }

    private boolean reloadFromInventory(Player player, ItemStack itemStack) {
        if (player.getAbilities().instabuild) {
            setAmmo(itemStack, MAX_AMMO);
            return true;
        }

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack ammo = player.getInventory().getItem(i);

            if (ASH_AMMO.test(ammo) && canReload(itemStack)) {
                ammo.shrink(1);
                setAmmo(itemStack, getAmmo(itemStack) + 1);
                player.level().playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.6F, 1.0F);

                return true;
            }
        }
        return false;
    }

    private boolean tryReload(ItemStack itemStack, ItemStack ammo) {
        if (!ASH_AMMO.test(ammo) || !canReload(itemStack)) {
            return false;
        }

        ammo.shrink(1);
        setAmmo(itemStack, getAmmo(itemStack) + 1);
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return getAmmo(pStack) > 0 && getAmmo(pStack) < MAX_AMMO;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(13.0F * getAmmo(pStack) / MAX_AMMO);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0xAAAAAA;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction == ClickAction.SECONDARY) {
            return tryReload(pStack, pOther);
        }
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pAction == ClickAction.SECONDARY) {
            return tryReload(pStack, pSlot.getItem());
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("item.flamesofthenether.pocket_ashes.tooltip", getAmmo(pStack), MAX_AMMO));
    }
}
