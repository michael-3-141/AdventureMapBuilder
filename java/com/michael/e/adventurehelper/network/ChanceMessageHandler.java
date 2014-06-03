package com.michael.e.adventurehelper.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.Container;

import com.michael.e.adventurehelper.common.ContainerMonsterEdit;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ChanceMessageHandler implements IMessageHandler<ChanceMessageHandler.DropChanceUpdateMessage, IMessage>{

	 public ChanceMessageHandler() {}
	 
	    @Override
	    public IMessage onMessage(DropChanceUpdateMessage message, MessageContext context)
	    {
	        if(!context.side.isServer())
	            throw new IllegalStateException("received ExampleMessage " + message + "on client side!");
	 
	        Container c = context.getServerHandler().playerEntity.openContainer;
	        
	        if(c instanceof ContainerMonsterEdit)
	        {
	        	((ContainerMonsterEdit) c).getEntity().setEquipmentDropChance(message.getPos(), message.getChance());
	        }
	        
	 
	        return null;
	    }
	 
	    public static class DropChanceUpdateMessage implements IMessage {
	        private float chance;
	        private int pos;
	 
	        public DropChanceUpdateMessage() {} // needed for Netty to create instances to read into
	 
	        public DropChanceUpdateMessage(float chance, int pos)
	        {
	            this.chance = chance;
	            this.pos = pos;
	        }
	 
	        @Override
	        public void fromBytes(ByteBuf buf)
	        {
	            chance = buf.readFloat();
	            pos = buf.readInt();
	        }
	 
	        @Override
	        public void toBytes(ByteBuf buf)
	        {
	            buf.writeFloat(chance);
	            buf.writeInt(pos);
	        }
	        
	        public float getChance() {
				return chance;
			}
	        
	        public void setChance(float chance) {
				this.chance = chance;
			}
	        
	        public void setPos(int pos) {
				this.pos = pos;
			}
	        
	        public int getPos() {
				return pos;
			}
	    }
}
