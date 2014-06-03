package com.michael.e.adventurehelper.common;

import com.michael.e.adventurehelper.client.GuiInventoryLimitor;
import com.michael.e.adventurehelper.client.GuiMonsterEdit;
import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID){
		case 0:
			return new ContainerMonsterEdit(player.inventory, (EntityLiving) player.worldObj.getEntityByID(x));
		case 1:
			return new ContainerInventoryLimitor(player.inventory, (TileEntityInventoryLimitor) player.worldObj.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID){
		case 0:
			return new GuiMonsterEdit(player.inventory, (EntityLiving) player.worldObj.getEntityByID(x));
		case 1:
			return new GuiInventoryLimitor(player.inventory, (TileEntityInventoryLimitor) player.worldObj.getTileEntity(x, y, z));
		}
		return null;
	}

}
