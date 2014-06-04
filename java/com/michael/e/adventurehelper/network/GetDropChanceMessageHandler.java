package com.michael.e.adventurehelper.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.Container;

import com.michael.e.adventurehelper.common.ContainerMonsterEdit;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class GetDropChanceMessageHandler implements IMessageHandler<GetDropChanceMessageHandler.GetDropChanceMessage, GetDropChanceMessageHandler.DropChanceReplyMessage> {
	
	public static class GetDropChanceMessage implements IMessage {
		
		public GetDropChanceMessage() {}
		
		@Override
		public void fromBytes(ByteBuf buf) {
		}

		@Override
		public void toBytes(ByteBuf buf) {
		}
		
	}
	
	public static class DropChanceReplyMessage implements IMessage{

		public DropChanceReplyMessage() {dropChances = new float[5];}
		
		private float[] dropChances;
		
		public DropChanceReplyMessage(float[] dropChances)
		{
			this.dropChances = dropChances;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			for(int i = 0; i < 5; i++)
			{
				dropChances[i] = buf.readFloat();
			}
		}

		@Override
		public void toBytes(ByteBuf buf) {
			for(int i = 0; i < 5; i++)
			{
				buf.writeFloat(dropChances[i]);
			}
		}
		
		public float[] getDropChances() {
			return dropChances;
		}
		
		public void setDropChances(float[] dropChances) {
			this.dropChances = dropChances;
		}
	}

	@Override
	public DropChanceReplyMessage onMessage(GetDropChanceMessage message, MessageContext context) {
        if(!context.side.isServer())
            throw new IllegalStateException("received GetDropChanceMessage " + message + "on client side!");
 
        Container c = context.getServerHandler().playerEntity.openContainer;
        
        if(c instanceof ContainerMonsterEdit)
        {
        	float[] equipmentDropChances = ReflectionHelper.getPrivateValue(EntityLiving.class, ((ContainerMonsterEdit) c).getEntity(), "equipmentDropChances", "field_82174_bp");
        	return new DropChanceReplyMessage(equipmentDropChances);
        }
        return null;
	}
}
