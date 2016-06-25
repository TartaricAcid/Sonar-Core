package sonar.core.common.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import sonar.core.SonarCore;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.ISonarInventory;
import sonar.core.inventory.SonarInventory;
import sonar.core.inventory.SonarLargeInventory;

public class TileEntityInventory extends TileEntitySonar implements IInventory {

	public ISonarInventory inv;

	public ISonarInventory getTileInv() {
		return inv;
	}

	public ItemStack[] slots() {
		if (inv instanceof SonarInventory) {
			return ((SonarInventory) inv).slots;
		} else {
			SonarCore.logger.error("INV ERROR: The inventory has no slots in " + this);
			return new ItemStack[inv.getSizeInventory()];
		}
	}

	public ArrayList<ItemStack>[] stacks() {
		if (inv instanceof SonarLargeInventory) {
			return ((SonarLargeInventory) inv).slots;
		} else {
			SonarCore.logger.error("INV ERROR: The inventory has no slots in " + this);
			return new ArrayList[inv.getSizeInventory()];
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		getTileInv().readData(nbt, type);
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		getTileInv().writeData(nbt, type);
	}

	public int getSizeInventory() {
		return getTileInv().getSizeInventory();
	}

	public ItemStack getStackInSlot(int slot) {
		return getTileInv().getStackInSlot(slot);
	}

	public ItemStack decrStackSize(int slot, int var2) {
		return getTileInv().decrStackSize(slot, var2);
	}

	public ItemStack removeStackFromSlot(int slot) {
		return getTileInv().removeStackFromSlot(slot);
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		getTileInv().setInventorySlotContents(i, itemstack);
	}

	public int getInventoryStackLimit() {
		return getTileInv().getInventoryStackLimit();
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {
		getTileInv().openInventory(player);
	}

	public void closeInventory(EntityPlayer player) {
		getTileInv().closeInventory(player);
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getTileInv().isItemValidForSlot(slot, stack);
	}

	public String getName() {
		if (this.blockType == null) {
			return "Sonar Inventory";
		}
		return this.blockType.getLocalizedName();
	}

	public boolean hasCustomName() {
		return false;
	}

	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.blockType.getLocalizedName());
	}

	public int getField(int id) {
		return getTileInv().getField(id);
	}

	public void setField(int id, int value) {
		getTileInv().setField(id, value);
	}

	public int getFieldCount() {
		return getTileInv().getFieldCount();
	}

	public void clear() {
		getTileInv().clear();
	}

}
