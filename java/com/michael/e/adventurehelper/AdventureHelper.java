package com.michael.e.adventurehelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.michael.e.adventurehelper.blocks.MyBlocks;
import com.michael.e.adventurehelper.common.GuiHandler;
import com.michael.e.adventurehelper.config.ConfigManager;
import com.michael.e.adventurehelper.items.MyItems;
import com.michael.e.adventurehelper.network.ChanceMessageHandler;
import com.michael.e.adventurehelper.network.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, guiFactory = "com.michael.e.adventurehelper.client.ConfigGuiFactory")
public class AdventureHelper {
	
	@Instance(ModInfo.MOD_ID)
	public static AdventureHelper instance;
	
	@SidedProxy(clientSide = "com.michael.e.adventurehelper.network.ClientProxy", serverSide = "com.michael.e.adventurehelper.network.CommonProxy")
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper netHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		ConfigManager.init(e.getSuggestedConfigurationFile());
		MyItems.init();
		MyBlocks.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID);
		netHandler.registerMessage(ChanceMessageHandler.class, ChanceMessageHandler.DropChanceUpdateMessage.class, 0, Side.SERVER);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
	
	public static CreativeTabs tabAdventure = new CreativeTabs(CreativeTabs.getNextID(), "tabAdventure") {
		@Override
		public Item getTabIconItem() {
			return MyItems.wand;
		}
	};

}
