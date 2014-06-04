package com.michael.e.adventurehelper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.common.ContainerMonsterEdit;
import com.michael.e.adventurehelper.common.InventoryMonster;
import com.michael.e.adventurehelper.network.ChanceMessageHandler;
import com.michael.e.adventurehelper.network.GetDropChanceMessageHandler;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class GuiMonsterEdit extends GuiAdventureHelper{
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.ASSET_FOLDER, "textures/gui/monsteredit.png");
	private EntityLiving entity;
	
	private GuiTextField heldItemChance;
	private GuiTextField helmetChance;
	private GuiTextField chestplateChance;
	private GuiTextField leggingsChance;
	private GuiTextField bootsChance;

	public GuiMonsterEdit(InventoryPlayer invPlayer, EntityLiving inv) {
		super(new ContainerMonsterEdit(invPlayer, inv), new InventoryMonster(inv), texture);
		
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
		heldItemChance.drawTextBox();
		helmetChance.drawTextBox();
		chestplateChance.drawTextBox();
		leggingsChance.drawTextBox();
		bootsChance.drawTextBox();
		
		ledgerManager.drawLedgers(i, j);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		AdventureHelper.netHandler.sendToServer(new GetDropChanceMessageHandler.GetDropChanceMessage());
		
		heldItemChance = new GuiTextField(this.fontRendererObj, 100, 9, 40, 12);
		heldItemChance.setCanLoseFocus(true);
		
		helmetChance = new GuiTextField(this.fontRendererObj, 29, 9, 40, 12);
		helmetChance.setCanLoseFocus(true);
		
		chestplateChance = new GuiTextField(this.fontRendererObj, 29, 27, 40, 12);
		chestplateChance.setCanLoseFocus(true);
		
		leggingsChance = new GuiTextField(this.fontRendererObj, 29, 45, 40, 12);
		leggingsChance.setCanLoseFocus(true);
		
		bootsChance = new GuiTextField(this.fontRendererObj, 29, 63, 40, 12);
		bootsChance.setCanLoseFocus(true);
	}

	public void updateScreen()
	{
		heldItemChance.updateCursorCounter();
		helmetChance.updateCursorCounter();
		chestplateChance.updateCursorCounter();
		leggingsChance.updateCursorCounter();
		bootsChance.updateCursorCounter();
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
		else if (helmetChance.isFocused()) {
			helmetChance.textboxKeyTyped(par1, par2);
			if(helmetChance.getText() != null)
			{
				try{
					float newChance = Float.valueOf(helmetChance.getText());
					AdventureHelper.netHandler.sendToServer(new ChanceMessageHandler.DropChanceUpdateMessage(newChance, 4));
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		}
		else if (chestplateChance.isFocused()) {
			chestplateChance.textboxKeyTyped(par1, par2);
			if(chestplateChance.getText() != null)
			{
				try{
					float newChance = Float.valueOf(chestplateChance.getText());
					AdventureHelper.netHandler.sendToServer(new ChanceMessageHandler.DropChanceUpdateMessage(newChance, 3));
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		}
		else if (leggingsChance.isFocused()) {
			leggingsChance.textboxKeyTyped(par1, par2);
			if(leggingsChance.getText() != null)
			{
				try{
					float newChance = Float.valueOf(leggingsChance.getText());
					AdventureHelper.netHandler.sendToServer(new ChanceMessageHandler.DropChanceUpdateMessage(newChance, 2));
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		}
		else if (bootsChance.isFocused()) {
			bootsChance.textboxKeyTyped(par1, par2);
			if(bootsChance.getText() != null)
			{
				try{
					float newChance = Float.valueOf(bootsChance.getText());
					AdventureHelper.netHandler.sendToServer(new ChanceMessageHandler.DropChanceUpdateMessage(newChance, 1));
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		}
		else{
			super.keyTyped(par1, par2);
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		int x = par1 - guiLeft;
		int y = par2 - guiTop;
		heldItemChance.mouseClicked(x, y, par3);
		helmetChance.mouseClicked(x, y, par3);
		chestplateChance.mouseClicked(x, y, par3);
		leggingsChance.mouseClicked(x, y, par3);
		bootsChance.mouseClicked(x, y, par3);
		ledgerManager.handleMouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}
	
	public void setText() {
		float[] equipmentDropChances = ReflectionHelper.getPrivateValue(EntityLiving.class, entity, "equipmentDropChances", "field_82174_bp");
		heldItemChance.setText(Float.toString(equipmentDropChances[0]));
		helmetChance.setText(Float.toString(equipmentDropChances[4]));
		chestplateChance.setText(Float.toString(equipmentDropChances[3]));
		leggingsChance.setText(Float.toString(equipmentDropChances[2]));
		bootsChance.setText(Float.toString(equipmentDropChances[1]));
	}
	
	@Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		
		ledgerManager.add(new InfoLedger());
	}
	
	public class InfoLedger extends Ledger {

		public InfoLedger() {
			maxHeight=130;
			maxWidth=150;
			overlayColor=0x222222;
		}
		
		@Override
		public String getTooltip() {
			return "Help!";
		}
		
		@Override
		public void draw(int x, int y) {
			drawBackground(x, y);
			
			if(!isFullyOpened() || currentHeight < maxHeight)
			{
				return;
			}
			
			fontRendererObj.drawSplitString("This interface allows you to give monsters aromour and an item to hold in their hand, and set the chance of each item to drop. Setting the chance to 1 will guarantee a drop but will give the item a random amount of damage if its a tool or armour. To guarantee dropping and not damage the item, set the chance to 2.", x+5, y+8, maxWidth-10, 0x000000);
		}
		
	}

}
