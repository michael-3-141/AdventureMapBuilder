package com.michael.e.adventurehelper.tileentities;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.michael.e.adventurehelper.blocks.MyBlocks;

public class TileEntityDoor extends TileEntity {

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		code = compound.getInteger("code");
	}


	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("code", code);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	public void unlock()
	{
		//if(!worldObj.isRemote)
		//{
			for(int x = -1; x < 2; x++)
			{
				for(int y = -1; y < 2; y++)
				{
					for(int z = -1; z < 2; z++)
					{
						if(worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z) == MyBlocks.lockedDoor && !(x == 0 && y==0 && z==0))
						{
							TileEntity te = worldObj.getTileEntity(xCoord+x, yCoord+y, zCoord+z);
							if(te instanceof TileEntityDoor)
							{
								worldObj.setBlock(xCoord+x, yCoord+y, zCoord+z, Blocks.air);
							}
						}
					}
				}
			//}
			worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
		}
	}

}
