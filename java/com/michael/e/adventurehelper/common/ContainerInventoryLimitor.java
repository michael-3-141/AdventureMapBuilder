package com.michael.e.adventurehelper.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerInventoryLimitor extends ContainerAdventureHelper{

	public TileEntityInventoryLimitor te;
	private boolean lastWhitelistState;
	
	public ContainerInventoryLimitor(InventoryPlayer invPlayer, TileEntityInventoryLimitor te) {
		
		this.te = te;
		
		for(int y = 0; y < te.getSizeInventory() / 9; y++)
		{
	        for (int x = 0; x < te.getSizeInventory() / (te.getSizeInventory() / 9); x++)
	        {
	            this.addSlotToContainer(new SlotPhantom(te, x + (y * 9), 8 + x * 18, 7 + y * 18));
	        }
		}
		
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, i * 18 + 76));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 134));
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
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, 0, te.isWhitelist() ? 1 : 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		te.setWhitelist(data == 1);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(Object player : crafters)
		{
			if(lastWhitelistState != te.isWhitelist())
			{
				((ICrafting) player).sendProgressBarUpdate(this, 0, te.isWhitelist() ? 1 : 0);
			}
		}
		
		lastWhitelistState = te.isWhitelist();
	}

}
