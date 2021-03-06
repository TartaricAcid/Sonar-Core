package sonar.core.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeOreStack implements ISonarRecipeObject, ISonarRecipeItem {

	public String oreType;
	public List<ItemStack> cachedRegister;
	public int stackSize;

	public RecipeOreStack(String oreType, int stackSize) {
		this.oreType = oreType;
		this.cachedRegister = OreDictionary.getOres(oreType);
		this.stackSize = stackSize;
	}

	@Override
	public Object getValue() {
		return cachedRegister;
	}

	@Override
	public ItemStack getOutputStack() {
		ItemStack stack = cachedRegister.get(0).copy();
		stack.stackSize = stackSize;
		return stack;
	}

	@Override
	public boolean matches(Object object, RecipeObjectType type) {
		if (object instanceof RecipeOreStack) {
			RecipeOreStack oreStack = (RecipeOreStack) object;
			if (oreStack.oreType.equals(oreType) && oreStack.stackSize >= stackSize) {
				return true;
			}
		} else if (object instanceof String) {
			return oreType.equals(object);
		} else if (object instanceof ItemStack && type.checkStackSize(stackSize, ((ItemStack) object).stackSize)) {
			int oreID = OreDictionary.getOreID(oreType);
			for (int id : OreDictionary.getOreIDs((ItemStack) object)) {
				if (oreID == id) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<ItemStack> getJEIValue() {
		return cachedRegister;
	}

	@Override
	public int getStackSize() {
		return stackSize;
	}
}