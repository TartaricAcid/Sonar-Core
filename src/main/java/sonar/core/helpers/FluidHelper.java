package sonar.core.helpers;

import java.util.List;

import sonar.core.api.fluids.StoredFluidStack;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.wrappers.FluidWrapper;

public class FluidHelper extends FluidWrapper {

	public void addFluidToList(List<StoredFluidStack> list, StoredFluidStack stack) {
		int pos = 0;
		for (StoredFluidStack storedTank : list) {
			if (storedTank.equalStack(stack.fluid)) {
				list.get(pos).add(stack);
				return;
			}
			pos++;
		}
		list.add(stack);

	}

	public StoredFluidStack getStackToAdd(long inputSize, StoredFluidStack stack, StoredItemStack returned) {
		StoredFluidStack simulateStack = null;
		if (returned == null || returned.stored == 0) {
			simulateStack = new StoredFluidStack(stack.getFullStack(), inputSize);
		} else {
			simulateStack = new StoredFluidStack(stack.getFullStack(), inputSize - returned.stored);
		}
		return simulateStack;
	}

}
