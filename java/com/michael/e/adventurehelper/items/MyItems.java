package com.michael.e.adventurehelper.items;

import net.minecraft.item.Item;

import com.michael.e.adventurehelper.ModInfo;

import cpw.mods.fml.common.registry.GameRegistry;

public class MyItems {

	public static Item wand;
	public static Item key;
	
	public static void init()
	{
		wand = new ItemWand();
		registerItem(wand);
		
		key = new ItemKey();
		registerItem(key);
	}
	
	public static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, ModInfo.MOD_ID + "_" + item.getUnlocalizedName().substring(5));
	}
}
