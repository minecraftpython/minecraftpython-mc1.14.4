package org.sapphon.minecraft.modding.minecraftpython.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.world.server.ServerWorld;
import org.sapphon.minecraft.modding.minecraftpython.MinecraftPythonMod;

public class CommandMPSpawnItem extends CommandMinecraftPythonServer {
	private double x;
	public double y;
	private double z;
	private String name;
	private int numberOfItemsToSpawn;
	private String nbtString;
	private CompoundNBT nbtData;

	public CommandMPSpawnItem(double x, double y, double z, String name, int numberOfItemsToSpawn, String nbtString) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.numberOfItemsToSpawn = numberOfItemsToSpawn;
		this.nbtString = nbtString;
	}

	public CommandMPSpawnItem(int x, int y, int z, String name, int numberOfItemsToSpawn, String nbtString) {
		this((double) x, (double) y, (double) z, name, numberOfItemsToSpawn,nbtString);
	}
	
	public CommandMPSpawnItem(String[] commandAndArgsToDeserialize) {
		this(Double.parseDouble(commandAndArgsToDeserialize[1]),
				Double.parseDouble(commandAndArgsToDeserialize[2]),
				Double.parseDouble(commandAndArgsToDeserialize[3]), commandAndArgsToDeserialize[4], Integer.parseInt(commandAndArgsToDeserialize[5]), commandAndArgsToDeserialize[6]);
		try {
				nbtData=JsonToNBT.getTagFromJson(nbtString);
		} catch (CommandSyntaxException e) {
			MinecraftPythonMod.logger.error("Spawnitem encountered unparseable NBT", e);
			nbtData = new CompoundNBT();
		}
	
	}

	
	public void doWork() {
		ServerWorld worldserver = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getWorld(0);// TODO ONLY WORKS IN OVERWORLD FOR NOW
		Item item = ItemLookup.getItemByName(name, worldserver);
		ItemEntity entityWrapperForTheItemWithoutAHandToHoldIt = new ItemEntity(worldserver, x, y, z);
		ItemStack theStack = new ItemStack(item, numberOfItemsToSpawn);
		if(nbtData!=null){theStack.setTag(nbtData);}
		entityWrapperForTheItemWithoutAHandToHoldIt.setItem(theStack);
		worldserver.summonEntity(entityWrapperForTheItemWithoutAHandToHoldIt);
	}

	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.SPAWNITEM_NAME+ CommandMinecraftPythonAbstract.SERIAL_DIV + x + CommandMinecraftPythonAbstract.SERIAL_DIV + y + CommandMinecraftPythonAbstract.SERIAL_DIV + z + CommandMinecraftPythonAbstract.SERIAL_DIV +name + CommandMinecraftPythonAbstract.SERIAL_DIV + numberOfItemsToSpawn + CommandMinecraftPythonAbstract.SERIAL_DIV + nbtString;
	}
}
