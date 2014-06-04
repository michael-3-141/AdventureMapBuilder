package com.michael.e.adventurehelper.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInventoryLimitor extends TileEntity implements IInventory{

	private ItemStack[] items;
	
	public TileEntityInventoryLimitor() {
		items = new ItemStack[5];
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
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
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		items[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "invLimiter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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
	
	public void clearInventoryBySetItems(EntityPlayer player) {
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(getStackInSlot(i) != null)
			{
				Item item = getStackInSlot(i).getItem();
				player.inventory.clearInventory(item, getStackInSlot(i).getItemDamage());
			}
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		items = new ItemStack[getSizeInventory()];
		NBTTagList list = (NBTTagList) compound.getTag("items");
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound item = new NBTTagCompound();
			item = list.getCompoundTagAt(i);
			ItemStack stack = new ItemStack(Items.apple);
			stack.readFromNBT(item);
			items[item.getInteger("slot")] = stack;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(items[i] != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("slot", i);
				items[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
		compound.setTag("items", list);
	}
}
