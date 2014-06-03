package com.michael.e.adventurehelper.blocks;

import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.tileentities.TileEntityDoor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class MyBlocks {

	public static Block lockedDoor;
	public static Block invLimitor;
	
	public static void init() {
		lockedDoor = new BlockLockedDoor();
		registerBlock(lockedDoor);
		GameRegistry.registerTileEntity(TileEntityDoor.class, "TileEntityDoor");
		
		invLimitor = new BlockInventoryLimitor();
		registerBlock(invLimitor);
	}
	
	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, ModInfo.MOD_ID + "_" + block.getUnlocalizedName());
	}
}
