package org.sapphon.minecraft.modding.minecraftpython.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import org.sapphon.minecraft.modding.minecraftpython.MinecraftPythonMod;

public class CommandMPSpawnEntity extends CommandMinecraftPythonServer {
	public double x;
	public double y;
	public double z;
	public String nameOfEntityToSpawn;
	public String nbtData;

	public CommandMPSpawnEntity(double x, double y, double z,
			String entityName, String nbtData) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.nameOfEntityToSpawn = entityName;
		this.nbtData = nbtData;
	}

	public CommandMPSpawnEntity(int x, int y, int z, String name,
			String nbtData) {
		this((double) x, (double) y, (double) z, name, nbtData);
	}

	public CommandMPSpawnEntity(String[] commandAndArgsToDeserialize) {
		this(Double.parseDouble(commandAndArgsToDeserialize[1]), Double
				.parseDouble(commandAndArgsToDeserialize[2]), Double
				.parseDouble(commandAndArgsToDeserialize[3]),
				commandAndArgsToDeserialize[4], commandAndArgsToDeserialize[5]);
	}

	public void doWork() {
		MinecraftServer worldserver = FMLCommonHandler.instance().getMinecraftServerInstance();
		ServerWorld world = worldserver.getEntityWorld();

		Entity entity = EntityLookup.getEntityByName(this.nameOfEntityToSpawn,
				world);
		if (!nbtData.isEmpty() && !nbtData.equals("{}")) {
			try {
				entity.read(JsonToNBT
						.getTagFromJson(nbtData));
			} catch (CommandSyntaxException e) {
				MinecraftPythonMod.logger.error("Spawnentity command received unparseable NBT", e);
				e.printStackTrace();
			}
		}
		entity.setPositionAndRotation(x, y, z, 0, 0);

		// Tamed horse hack
		if (entity instanceof HorseEntity) {
			HorseEntity horse = (HorseEntity) entity;
			horse.setHorseTamed(true);
			horse.setHorseSaddled(true);
		}
		world.summonEntity(entity);
	}

	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.SPAWNENTITY_NAME
				+ CommandMinecraftPythonAbstract.SERIAL_DIV + x
				+ CommandMinecraftPythonAbstract.SERIAL_DIV + y
				+ CommandMinecraftPythonAbstract.SERIAL_DIV + z
				+ CommandMinecraftPythonAbstract.SERIAL_DIV + nameOfEntityToSpawn
				+ CommandMinecraftPythonAbstract.SERIAL_DIV + nbtData;
	}

}
