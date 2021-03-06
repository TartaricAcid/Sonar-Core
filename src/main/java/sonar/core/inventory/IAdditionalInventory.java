package sonar.core.inventory;

import net.minecraft.item.ItemStack;


/** implemented on TileEntities which drop extra items, e.g. Speed Upgrades, Energy Upgrades */
public interface IAdditionalInventory {

	public ItemStack[] getAdditionalStacks();
}
