package com.michael.e.adventurehelper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.common.ContainerInventoryLimitor;
import com.michael.e.adventurehelper.network.ButtonClickedMessageHandler;
import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

public class GuiInventoryLimitor extends GuiAdventureHelper{

	private static final ResourceLocation texture = new ResourceLocation(ModInfo.ASSET_FOLDER, "textures/gui/invlimiter.png");
	private TileEntityInventoryLimitor te;
	private GuiButton whitelist;
	
	public GuiInventoryLimitor(InventoryPlayer invPlayer, TileEntityInventoryLimitor te) {
		super(new ContainerInventoryLimitor(invPlayer, te), te, texture);
		
		this.te = te;
		xSize = 176;
		ySize = 158;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		super.drawGuiContainerForegroundLayer(i, j);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		whitelist = new GuiButton(0, guiLeft + 61, guiTop + 50, 54, 20, "Error");
		buttonList.add(whitelist);
		updateBtns();
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		AdventureHelper.netHandler.sendToServer(new ButtonClickedMessageHandler.ButtonClickedMessage(0, button.id));
		updateBtns();
	}
	
	private void updateBtns() {
		whitelist.displayString = te.isWhitelist() ? "Whitelist" : "Blacklist";
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		updateBtns();
	}
	
	@Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		
		ledgerManager.add(new InfoLedger("This block allows you to clear a players inventory from items he is not allowed to have when he walks through it. Simply place the block at a doorway and set the items. The block is completely invisible to non creative players.", 100, 150));
	}
}
