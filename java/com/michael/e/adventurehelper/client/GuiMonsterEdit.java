package com.michael.e.adventurehelper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.common.ContainerMonsterEdit;
import com.michael.e.adventurehelper.network.ChanceMessageHandler;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class GuiMonsterEdit extends GuiContainer{
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.ASSET_FOLDER, "textures/gui/monsteredit.png");
	private GuiTextField heldItemChance;
	private EntityLiving entity;

	public GuiMonsterEdit(InventoryPlayer invPlayer, EntityLiving inv) {
		super(new ContainerMonsterEdit(invPlayer, inv));
		
		entity = inv;
		
		xSize = 177;
		ySize = 165;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		try{
		heldItemChance.drawTextBox();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		heldItemChance = new GuiTextField(this.fontRendererObj, 27, 8, 43, 12);
		float[] equipmentDropChances = ObfuscationReflectionHelper.getPrivateValue(EntityLiving.class, entity, "equipmentDropChances");
		heldItemChance.setText(Float.toString(equipmentDropChances[0]));
		heldItemChance.setFocused(true);
		heldItemChance.setCanLoseFocus(true);
	}

	public void updateScreen()
	{
		heldItemChance.updateCursorCounter();
	}

	protected void keyTyped(char par1, int par2) {
		if (heldItemChance.isFocused()) {
			heldItemChance.textboxKeyTyped(par1, par2);
			if(heldItemChance.getText() != null)
			{
				try{
					float newChance = Float.valueOf(heldItemChance.getText());
					AdventureHelper.netHandler.sendToServer(new ChanceMessageHandler.DropChanceUpdateMessage(newChance, 0));
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		}
		super.keyTyped(par1, par2);
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		heldItemChance.mouseClicked(par1-guiLeft, par2-guiTop, par3);
		super.mouseClicked(par1, par2, par3);
	}

}
