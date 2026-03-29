package net.plasma64.flamesofthenether.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.plasma64.flamesofthenether.misc.FOTNTagRegistry;
import org.jetbrains.annotations.Nullable;

public class ChiseledPolishedMaroonstoneBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ChiseledPolishedMaroonstoneBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(LIT, false);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);

            if (itemStack.is(FOTNTagRegistry.CHISELED_POLISHED_MAROONSTONE_LIGHTING_ITEMS)) {
                if (pState.getValue(LIT)) {
                    return InteractionResult.PASS;
                } else {
                    pLevel.setBlock(pPos, pState.setValue(LIT, true), 3);
                    pLevel.playSound(null, pPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1f, 1f);

                    if (!pPlayer.getAbilities().instabuild) {
                        if (itemStack.isDamageableItem()) {
                            itemStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pHand));
                        } else {
                            itemStack.shrink(1);
                        }
                    }

                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }
}
