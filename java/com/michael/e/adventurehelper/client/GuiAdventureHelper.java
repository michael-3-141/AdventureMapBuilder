package com.michael.e.adventurehelper.client;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.michael.e.adventurehelper.ModInfo;

public abstract class GuiAdventureHelper extends GuiContainer {
	
	public GuiAdventureHelper(Container container, IInventory inventory, ResourceLocation texture) {
		super(container);
		this.container = container;

		this.texture = texture;

		if (inventory instanceof TileEntity) {
			tile = (TileEntity) inventory;
		} else {
			tile = null;
		}

		initLedgers(inventory);
	}
	
	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	protected void initLedgers(IInventory inventory) {
	}


	public static final ResourceLocation LEDGER_TEXTURE = new ResourceLocation(ModInfo.ASSET_FOLDER, "textures/gui/ledger.png");
	public final LedgerManager ledgerManager = new LedgerManager(this);
	public final TileEntity tile;
	public final ResourceLocation texture;
	public final Container container;
	
	public class LedgerManager {

		protected ArrayList<Ledger> ledgers = new ArrayList<Ledger>();
		private GuiAdventureHelper gui;

		public LedgerManager(GuiAdventureHelper gui) {
			this.gui = gui;
		}

		public void add(Ledger ledger) {
			this.ledgers.add(ledger);
			if (SessionVars.getOpenedLedger() != null && ledger.getClass().equals(SessionVars.getOpenedLedger())) {
				ledger.setFullyOpen();
			}
		}

		/**
		 * Inserts a ledger into the next-to-last position.
		 *
		 * @param ledger
		 */
		public void insert(Ledger ledger) {
			this.ledgers.add(ledgers.size() - 1, ledger);
		}

		protected Ledger getAtPosition(int mX, int mY) {

			int xShift = ((gui.width - gui.xSize) / 2) + gui.xSize;
			int yShift = ((gui.height - gui.ySize) / 2) + 8;

			for (int i = 0; i < ledgers.size(); i++) {
				Ledger ledger = ledgers.get(i);
				if (!ledger.isVisible()) {
					continue;
				}

				ledger.currentShiftX = xShift;
				ledger.currentShiftY = yShift;
				if (ledger.intersectsWith(mX, mY, xShift, yShift)) {
					return ledger;
				}

				yShift += ledger.getHeight();
			}

			return null;
		}

		protected void drawLedgers(int mouseX, int mouseY) {

			int xPos = 8;
			for (Ledger ledger : ledgers) {

				ledger.update();
				if (!ledger.isVisible()) {
					continue;
				}

				ledger.draw(xSize, xPos);
				xPos += ledger.getHeight();
			}

			Ledger ledger = getAtPosition(mouseX, mouseY);
			if (ledger != null) {
				int startX = mouseX - ((gui.width - gui.xSize) / 2) + 12;
				int startY = mouseY - ((gui.height - gui.ySize) / 2) - 12;

				String tooltip = ledger.getTooltip();
				int textWidth = fontRendererObj.getStringWidth(tooltip);
				drawGradientRect(startX - 3, startY - 3, startX + textWidth + 3, startY + 8 + 3, 0xc0000000, 0xc0000000);
				fontRendererObj.drawStringWithShadow(tooltip, startX, startY, -1);
			}
		}

		public void handleMouseClicked(int x, int y, int mouseButton) {

			if (mouseButton == 0) {

				Ledger ledger = this.getAtPosition(x, y);

				// Default action only if the mouse click was not handled by the
				// ledger itself.
				if (ledger != null && !ledger.handleMouseClicked(x, y, mouseButton)) {

					for (Ledger other : ledgers) {
						if (other != ledger && other.isOpen()) {
							other.toggleOpen();
						}
					}
					ledger.toggleOpen();
				}
			}

		}
	}

	/**
	 * Side ledger for guis
	 */
	protected abstract class Ledger {
		public int currentShiftX = 0;
		public int currentShiftY = 0;
		protected int overlayColor = 0xffffff;
		protected int limitWidth = 128;
		protected int maxWidth = 124;
		protected int minWidth = 24;
		protected int currentWidth = minWidth;
		protected int maxHeight = 24;
		protected int minHeight = 24;
		protected int currentHeight = minHeight;
		private boolean open;

		public void update() {
			// Width
			if (open && currentWidth < maxWidth) {
				currentWidth += 4;
			} else if (!open && currentWidth > minWidth) {
				currentWidth -= 4;
			}

			// Height
			if (open && currentHeight < maxHeight) {
				currentHeight += 4;
			} else if (!open && currentHeight > minHeight) {
				currentHeight -= 4;
			}
		}

		public int getHeight() {
			return currentHeight;
		}

		public abstract void draw(int x, int y);

		public abstract String getTooltip();

		public boolean handleMouseClicked(int x, int y, int mouseButton) {
			return false;
		}

		public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY) {

			if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + getHeight()) {
				return true;
			}

			return false;
		}

		public void setFullyOpen() {
			open = true;
			currentWidth = maxWidth;
			currentHeight = maxHeight;
		}

		public void toggleOpen() {
			if (open) {
				open = false;
				SessionVars.setOpenedLedger(null);
			} else {
				open = true;
				SessionVars.setOpenedLedger(this.getClass());
			}
		}

		public boolean isVisible() {
			return true;
		}

		public boolean isOpen() {
			return this.open;
		}

		protected boolean isFullyOpened() {
			return currentWidth >= maxWidth;
		}

		protected void drawBackground(int x, int y) {

			GL11.glColor4f(1F, 1F, 1F, 1F);

			mc.renderEngine.bindTexture(LEDGER_TEXTURE);
			drawTexturedModalRect(x, y, 0, 256 - currentHeight, 4, currentHeight);
			drawTexturedModalRect(x + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
			// Add in top left corner again
			drawTexturedModalRect(x, y, 0, 0, 4, 4);

			drawTexturedModalRect(x + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);

			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		}

		protected void drawIcon(IIcon icon, int x, int y) {

			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
			drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
		}
	}
}
