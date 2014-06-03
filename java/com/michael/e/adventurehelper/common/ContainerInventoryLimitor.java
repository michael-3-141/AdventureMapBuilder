package com.michael.e.adventurehelper.common;

import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInventoryLimitor extends Container{

	TileEntityInventoryLimitor te;
	
	public ContainerInventoryLimitor(InventoryPlayer invPlayer, TileEntityInventoryLimitor te) {
		
		this.te = te;
		
        for (int i = 0; i < te.getSizeInventory(); ++i)
        {
            this.addSlotToContainer(new Slot(te, i, 44 + i * 18, 20));
        }
		
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 109));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(te.xCoord, te.yCoord, te.zCoord) < 64;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}

}
