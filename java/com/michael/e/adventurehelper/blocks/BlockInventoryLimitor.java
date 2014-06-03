package com.michael.e.adventurehelper.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.michael.e.adventurehelper.AdventureHelper;
import com.michael.e.adventurehelper.ModInfo;
import com.michael.e.adventurehelper.tileentities.TileEntityInventoryLimitor;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockInventoryLimitor extends BlockContainer{

	private IIcon iconHidden;
	private IIcon iconShown;
	
	public BlockInventoryLimitor() {
		super(Material.rock);
		setBlockName("blockInvLimitor");
		setCreativeTab(AdventureHelper.tabAdventure);
		setBlockTextureName(ModInfo.ASSET_FOLDER + ":blockInvClear");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		iconHidden = register.registerIcon(ModInfo.ASSET_FOLDER + ":blockInvClear");
		iconShown = register.registerIcon(ModInfo.ASSET_FOLDER + ":blockInvClearShown");
	}
	
	@Override
	public boolean isNormalCube() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean canCollideCheck(int metaData, boolean boat) {
		if(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(e instanceof EntityPlayer && te instanceof TileEntityInventoryLimitor)
		{
			((TileEntityInventoryLimitor) te).clearInventoryBySetItems((EntityPlayer) e);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityInventoryLimitor();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.capabilities.isCreativeMode)
		{
			FMLNetworkHandler.openGui(player, AdventureHelper.instance, 1, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode ? iconShown : iconHidden;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r) {
		world.markBlockForUpdate(x, y, z);
	}

}
