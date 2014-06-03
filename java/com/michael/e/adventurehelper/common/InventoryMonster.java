package com.michael.e.adventurehelper.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryMonster implements IInventory{

	EntityLiving monster;
	
	public InventoryMonster(EntityLiving monster) {
		this.monster = monster;
	}
	
	private ItemStack[] getInventory()
	{
		return monster.getLastActiveItems();
	}
	
	@Override
	public int getSizeInventory() {
		return getInventory().length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return getInventory()[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemstack = getStackInSlot(i);
		
		if(itemstack != null)
		{
			if(itemstack.stackSize <= j)
			{
				setInventorySlotContents(i, null);
			}else{
				itemstack = itemstack.splitStack(j);
				markDirty();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		getInventory()[i] = stack;
		
		if(stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
		
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "monster edit";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}

}
