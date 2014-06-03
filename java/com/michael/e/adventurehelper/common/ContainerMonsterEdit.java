package com.michael.e.adventurehelper.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerMonsterEdit extends Container{

	InventoryMonster inv;
	
	public ContainerMonsterEdit(final InventoryPlayer invPlayer, EntityLiving invMonster) {
		inv = new InventoryMonster(invMonster);
		
		addSlotToContainer(new Slot(inv, 0, 79, 7));
		
		addSlotToContainer(new Slot(inv, 1, 8, 61)
		{
			public int getSlotStackLimit() {
				return 1;
			}
			
			public boolean isItemValid(ItemStack stack) {
                if (stack == null) return false;
                return stack.getItem().isValidArmor(stack, 3, invPlayer.player);
			}
			
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemArmor.func_94602_b(3);
            }
		});
		
		addSlotToContainer(new Slot(inv, 2, 8, 61 - 18)
		{
			public int getSlotStackLimit() {
				return 1;
			}
			
			public boolean isItemValid(ItemStack stack) {
                if (stack == null) return false;
                return stack.getItem().isValidArmor(stack, 2, invPlayer.player);
			}
			
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemArmor.func_94602_b(2);
            }
		});
		
		addSlotToContainer(new Slot(inv, 3, 8, 61 - 18 * 2)
		{
			public int getSlotStackLimit() {
				return 1;
			}
			
			public boolean isItemValid(ItemStack stack) {
                if (stack == null) return false;
                return stack.getItem().isValidArmor(stack, 1, invPlayer.player);
			}
			
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemArmor.func_94602_b(1);
            }
		});
		
		addSlotToContainer(new Slot(inv, 4, 8, 61 - 18 * 3)
		{
			public int getSlotStackLimit() {
				return 1;
			}
			
			public boolean isItemValid(ItemStack stack) {
                if (stack == null) return false;
                return stack.getItem().isValidArmor(stack, 0, invPlayer.player);
			}
			
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemArmor.func_94602_b(0);
            }
		});
		
		for(int i=0; i<9; i++)
		{
			addSlotToContainer(new Slot(invPlayer, i, 8 + 18 * i, 141));
		}
		
		for(int y=0; y<3; y++)
		{
			for(int x=0; x<9; x++)
			{
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 83 + 18 * y));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}
	
	public EntityLiving getEntity()
	{
		return inv.monster;
	}

}
