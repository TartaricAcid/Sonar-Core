package sonar.core.handlers.inventories;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import sonar.core.api.SonarAPI;
import sonar.core.api.StorageSize;
import sonar.core.api.inventories.InventoryHandler;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;

public class DSUInventoryProvider extends InventoryHandler {

	public static String name = "DSU-Inventory";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canHandleItems(TileEntity tile, EnumFacing dir) {
		return tile instanceof IDeepStorageUnit;
	}

	@Override
	public StoredItemStack getStack(int slot, TileEntity tile, EnumFacing dir) {
		if (!(slot > 0)) {
			IDeepStorageUnit inv = (IDeepStorageUnit) tile;
			if (inv.getStoredItemType() != null) {
				return new StoredItemStack(inv.getStoredItemType());
			}
		}
		return null;
	}

	@Override
	public StorageSize getItems(List<StoredItemStack> storedStacks, TileEntity tile, EnumFacing dir) {

		IDeepStorageUnit inv = (IDeepStorageUnit) tile;
		if (inv.getStoredItemType() != null) {
			StoredItemStack stack = new StoredItemStack(inv.getStoredItemType());
			if (stack.stored != 0) {
				SonarAPI.getItemHelper().addStackToList(storedStacks, stack);
				return new StorageSize(stack.stored, inv.getMaxStoredCount());
			}
		}
		return new StorageSize(0, inv.getMaxStoredCount());
	}

	@Override
	public StoredItemStack addStack(StoredItemStack add, TileEntity tile, EnumFacing dir, ActionType action) {
		IDeepStorageUnit inv = (IDeepStorageUnit) tile;
		ItemStack stack = inv.getStoredItemType();
		if (stack != null) {
			if (add.equalStack(stack)) {
				long max = inv.getMaxStoredCount();
				long storedItems = stack.stackSize;
				if (max == storedItems) {
					return add;
				}

				storedItems += add.getStackSize();
				if (storedItems > max) {
					long remove = storedItems - max;
					if (!action.shouldSimulate())
						inv.setStoredItemCount((int) max);
					return new StoredItemStack(add.getItemStack(), remove);
				} else {
					if (!action.shouldSimulate())
						inv.setStoredItemCount(stack.stackSize + (int) add.getStackSize());
					return null;
				}
			}
		} else {
			long max = inv.getMaxStoredCount();
			if (!action.shouldSimulate())
				inv.setStoredItemType(add.getItemStack(), (int) add.getStackSize());
			return null;
		}
		return add;
	}

	@Override
	public StoredItemStack removeStack(StoredItemStack remove, TileEntity tile, EnumFacing dir, ActionType action) {
		IDeepStorageUnit inv = (IDeepStorageUnit) tile;
		ItemStack stack = inv.getStoredItemType();
		if (remove.equalStack(stack)) {
			if (remove.getStackSize() >= stack.stackSize) {
				stack = stack.copy();
				remove.stored -= stack.stackSize;
				if (!action.shouldSimulate())
					inv.setStoredItemCount(0);
				return remove;
			} else {
				if (!action.shouldSimulate())
					inv.setStoredItemCount(stack.stackSize - (int) remove.getStackSize());
				return null;
			}
		}
		return remove;
	}

}
