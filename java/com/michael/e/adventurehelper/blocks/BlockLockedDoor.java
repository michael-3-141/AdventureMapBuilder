package com.michael.e.adventurehelper.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.config.ConfigManager;
import com.michael.e.adventurehelper.tileentities.TileEntityDoor;

public class BlockLockedDoor extends BlockContainer {

	public BlockLockedDoor() {
		super(Material.rock);
		setBlockName("blockDoor");
		setCreativeTab(AdventureHelper.tabAdventure);
		setBlockTextureName(ModInfo.ASSET_FOLDER + ":" + "lockedDoor");
		setBlockUnbreakable();
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDoor();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(ConfigManager.debugMode)
		{
			if(world.isRemote)
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof TileEntityDoor)
				{
					player.addChatMessage(new ChatComponentText("Code: " + ((TileEntityDoor) te).getCode()));
					
				}
				return true;
			}
		}
		return false;
	}

}
