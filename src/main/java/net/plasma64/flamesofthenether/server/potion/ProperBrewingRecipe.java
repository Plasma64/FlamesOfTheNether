package net.plasma64.flamesofthenether.server.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.jetbrains.annotations.NotNull;

//Code by AlexModGuy
public class ProperBrewingRecipe extends BrewingRecipe {

    private final Ingredient input;

    public ProperBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        super(input, ingredient, output);
        this.input = input;
    }

    @Override
    public boolean isInput(@NotNull ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            ItemStack[] matchingStacks = input.getItems();

            if (matchingStacks.length == 0) {
                return stack.isEmpty();
            } else {
                for (ItemStack itemStack : matchingStacks) {
                    if (ItemStack.isSameItemSameTags(itemStack, stack)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}
