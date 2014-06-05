package com.michael.e.adventurehelper.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotPhantom extends Slot{

	public SlotPhantom(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return true;
	}

	public boolean canAdjust() {
		return true;
	}
	
	

}
