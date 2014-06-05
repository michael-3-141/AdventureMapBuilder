package com.michael.e.adventurehelper.network;

import com.michael.e.adventurehelper.common.ContainerInventoryLimitor;

import net.minecraft.inventory.Container;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ButtonClickedMessageHandler implements IMessageHandler<ButtonClickedMessageHandler.ButtonClickedMessage, IMessage> {

	public ButtonClickedMessageHandler() {}
	
	public static class ButtonClickedMessage implements IMessage {

		public ButtonClickedMessage() {
		}
		
		public ButtonClickedMessage(int guiId, int buttonId) {
			super();
			this.guiId = guiId;
			this.buttonId = buttonId;
		}

		private int guiId;
		private int buttonId;
		
		@Override
		public void fromBytes(ByteBuf buf) {
			guiId = buf.readInt();
			buttonId = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(guiId);
			buf.writeInt(buttonId);
		}

		public int getGuiId() {
			return guiId;
		}

		public void setGuiId(int guiId) {
			this.guiId = guiId;
		}

		public int getButtonId() {
			return buttonId;
		}

		public void setButtonId(int buttonId) {
			this.buttonId = buttonId;
		}
		
	}

	@Override
	public IMessage onMessage(ButtonClickedMessage message, MessageContext ctx) {
		
		Container c = ctx.getServerHandler().playerEntity.openContainer;
		if(message.getGuiId() == 0)
		{
			if(c instanceof ContainerInventoryLimitor)
			{
				((ContainerInventoryLimitor) c).te.setWhitelist(!((ContainerInventoryLimitor) c).te.isWhitelist());
			}
		}
		
		return null;
	}
}
