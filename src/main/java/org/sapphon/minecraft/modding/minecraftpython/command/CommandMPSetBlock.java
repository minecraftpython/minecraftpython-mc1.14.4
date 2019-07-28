package org.sapphon.minecraft.modding.minecraftpython.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.sapphon.minecraft.modding.base.BlockFinder;
import org.sapphon.minecraft.modding.minecraftpython.MinecraftPythonMod;

public class CommandMPSetBlock extends CommandMinecraftPythonServer {

	private int x;
	private int y;
	private int z;
	private String blockType;
	private int metadata;
	private String tileEntityNbtData;

	public CommandMPSetBlock(int x, int y, int z, String blockType,
			int metadata, String nbtDataForTileEntity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockType = blockType;
		this.metadata = metadata;
		this.tileEntityNbtData = nbtDataForTileEntity;
	}

	public CommandMPSetBlock(double x, double y, double z,
			String blockType, int metadata, String nbtDataForTileEntity) {
		this((int) x, (int) y, (int) z, blockType, metadata, nbtDataForTileEntity);
	}

	public CommandMPSetBlock(String[] commandAndArgsToDeserialize) {
		this(Integer.parseInt(commandAndArgsToDeserialize[1]), Integer
				.parseInt(commandAndArgsToDeserialize[2]), Integer
				.parseInt(commandAndArgsToDeserialize[3]),
				commandAndArgsToDeserialize[4], Integer
						.parseInt(commandAndArgsToDeserialize[5]), commandAndArgsToDeserialize[6]);
	}

	public void doWork() {
		ServerWorld worldserver = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getWorld(0);// TODO
		Block blocky = BlockFinder.getBlockWithName(blockType);
		
		boolean setBlock = worldserver.setBlockState(new BlockPos(x, y, z), blocky.getStateFromMeta(metadata));
		if (!tileEntityNbtData.isEmpty() && !tileEntityNbtData.equals("{}")) {
			CompoundNBT nbtTagCompound = null;
			try {
				nbtTagCompound = JsonToNBT.getTagFromJson(tileEntityNbtData);
			} catch (CommandSyntaxException e) {
				MinecraftPythonMod.logger.error("Setblock got unparseable NBT", e);
				e.printStackTrace();
			}
			TileEntity tileentity = worldserver.getTileEntity(new BlockPos(x, y, z));

			if (tileentity != null && nbtTagCompound != null) {
				nbtTagCompound.putInt("x", x);
				nbtTagCompound.putInt("y", y);
				nbtTagCompound.putInt("z", z);
				tileentity.read(nbtTagCompound);
			}
		}
	}

	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.SETBLOCK_NAME + SERIAL_DIV + x
				+ SERIAL_DIV + y + SERIAL_DIV + z + SERIAL_DIV + blockType
				+ SERIAL_DIV + metadata + SERIAL_DIV + tileEntityNbtData;
	}

}
