package com.michael.e.adventurehelper.items;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class ItemWand extends Item {

	public ItemWand() {
		super();
		setCreativeTab(AdventureHelper.tabAdventure);
		setUnlocalizedName("itemWand");
		setMaxStackSize(1);
		setTextureName(ModInfo.ASSET_FOLDER + ":" + "wand");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if(entity instanceof EntityVillager && !player.worldObj.isRemote)
		{
			EntityVillager villager = (EntityVillager) entity;
			try {
				Field trades = villager.getClass().getDeclaredField("buyingList");
				trades.setAccessible(true);
				MerchantRecipeList tradeList = (MerchantRecipeList) trades.get(villager);
				if(tradeList != null)
				tradeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Blocks.dirt), new ItemStack(Items.diamond, 64)));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
					e.printStackTrace();
			}
			return true;
		}
		else if(entity instanceof EntityLiving)
		{
			FMLNetworkHandler.openGui(player, AdventureHelper.instance, 0, player.worldObj, entity.getEntityId(), 0, 0);
		}
		return false;
	}
}
