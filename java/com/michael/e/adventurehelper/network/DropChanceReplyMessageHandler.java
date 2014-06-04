package com.michael.e.adventurehelper.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;

import com.michael.e.adventurehelper.client.GuiMonsterEdit;
import com.michael.e.adventurehelper.common.ContainerMonsterEdit;
import com.michael.e.adventurehelper.network.GetDropChanceMessageHandler.DropChanceReplyMessage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class DropChanceReplyMessageHandler implements IMessageHandler<GetDropChanceMessageHandler.DropChanceReplyMessage, IMessage>{

	@Override
	public IMessage onMessage(DropChanceReplyMessage message, MessageContext context) {
        if(context.side.isServer())
            throw new IllegalStateException("received DropChanceReplyMessage " + message + "on client side!");
 
        GuiScreen g = Minecraft.getMinecraft().currentScreen;
        Container c = Minecraft.getMinecraft().thePlayer.openContainer;
        
        if(c instanceof ContainerMonsterEdit && g instanceof GuiMonsterEdit)
        {
        	for(int i = 0; i < 5; i++)
        	{
        		((ContainerMonsterEdit) c).getEntity().setEquipmentDropChance(i, message.getDropChances()[i]);
        	}
        	((GuiMonsterEdit) g).setText();
        }
        return null;
	}

}
