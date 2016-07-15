package wiresegal.psionup.common.crafting.recipe

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.ShapelessOreRecipe
import vazkii.psi.api.cad.ISocketable
import wiresegal.psionup.common.core.helper.IteratorSocketable

/**
 * @author WireSegal
 * Created at 7:22 PM on 7/9/16.
 */
class RecipeSocketTransferShapeless(val result: ItemStack, vararg recipe: Any) : ShapelessOreRecipe(result, *recipe) {

    override fun getCraftingResult(var1: InventoryCrafting): ItemStack {
        val output = result.copy()
        for (i in 0..var1.sizeInventory - 1) {
            val stack = var1.getStackInSlot(i)
            if (stack != null && stack.item is ISocketable && output.item is ISocketable) {
                val outputItem = output.item as ISocketable
                val stackItem = stack.item as ISocketable
                for (slot in IteratorSocketable(stack)) if (outputItem.isSocketSlotAvailable(output, slot.first)) {
                    outputItem.setBulletInSocket(output, slot.first, slot.second)
                }
                val selectedSlot = stackItem.getSelectedSlot(stack)
                if (outputItem.showSlotInRadialMenu(output, selectedSlot))
                    outputItem.setSelectedSlot(output, selectedSlot)
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(stack), output)
                break
            }
        }
        return output
    }

}
