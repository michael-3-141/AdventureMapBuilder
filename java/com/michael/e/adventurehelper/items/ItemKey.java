package com.michael.e.adventurehelper.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.config.ConfigManager;
import com.michael.e.adventurehelper.tileentities.TileEntityDoor;

public class ItemKey extends Item{
	
	public ItemKey() {
		setUnlocalizedName("itemKey");
		setCreativeTab(AdventureHelper.tabAdventure);
		setTextureName(ModInfo.ASSET_FOLDER + ":" + "key");
		setMaxStackSize(1);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.stackTagCompound = new NBTTagCompound();
	}
	
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)return false;
		TileEntity te = world.getTileEntity(x, y, z);
		if(stack.stackTagCompound == null)stack.stackTagCompound = new NBTTagCompound();
		if(te instanceof TileEntityDoor)
		{
			TileEntityDoor ted = (TileEntityDoor)te;
			if(player.capabilities.isCreativeMode && player.isSneaking())
			{
				int code = itemRand.nextInt();
				stack.stackTagCompound.setInteger("Code", code);
				ted.setCode(code);
				player.addChatMessage(new ChatComponentText("Key succesfully linked to door."));
			}
			else if(!player.capabilities.isCreativeMode)
			{
				int code = stack.stackTagCompound.getInteger("Code");
				if(ted.getCode() == code)
				{
					ted.unlock();
					player.addChatMessage(new ChatComponentText("The key fits perfectly in the door and it opens."));
				}
				else
				{
					player.addChatMessage(new ChatComponentText("The key doesn't fit in the door."));
				}

			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		if(stack.stackTagCompound == null)stack.stackTagCompound = new NBTTagCompound();
		if(ConfigManager.debugMode)
		{
			if(stack.stackTagCompound.hasKey("Code"))
			{
				info.add(EnumChatFormatting.BLUE + "Code: " + stack.stackTagCompound.getString("Code"));
			}
		}
	}

}
