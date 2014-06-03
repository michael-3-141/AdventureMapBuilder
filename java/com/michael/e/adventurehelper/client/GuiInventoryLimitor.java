package com.michael.e.adventurehelper.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.michael.e.adventurehelper.common.ContainerInventoryLimitor;
import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

public class GuiInventoryLimitor extends GuiContainer{

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/hopper.png");
	
	public GuiInventoryLimitor(InventoryPlayer invPlayer, TileEntityInventoryLimitor te) {
		super(new ContainerInventoryLimitor(invPlayer, te));
		
		xSize = 176;
		ySize = 133;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
