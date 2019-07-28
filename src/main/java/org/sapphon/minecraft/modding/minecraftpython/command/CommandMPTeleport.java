package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class CommandMPTeleport extends CommandMinecraftPythonServer {

	public double x;
	public double y;
	public double z;
	public String teleportingPlayer;

	public CommandMPTeleport(double x, double y, double z){
		this(x,y,z, Minecraft.getInstance().player.getDisplayName().getUnformattedComponentText());
	}
	
	public CommandMPTeleport(double x, double y, double z, String teleportingPlayerDisplayName){
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportingPlayer = teleportingPlayerDisplayName;
	}
	
	public CommandMPTeleport(String[] commandAndArgsToDeserialize) {
		this(Double.parseDouble(commandAndArgsToDeserialize[1]),
				Double.parseDouble(commandAndArgsToDeserialize[2]),
				Double.parseDouble(commandAndArgsToDeserialize[3]), commandAndArgsToDeserialize[4]);
	}


	public void doWork(){
		ServerWorld world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
		List<PlayerEntity> players = new ArrayList<PlayerEntity>(world.playerEntities);
		for (PlayerEntity entityPlayerMP : players) {
			if(entityPlayerMP.getDisplayName().getUnformattedComponentText().equals(teleportingPlayer)){
				entityPlayerMP.setPositionAndUpdate(x,y,z);
				return;
			}
		}
	}
	
	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.TELEPORT_NAME + CommandMinecraftPythonAbstract.SERIAL_DIV + x + CommandMinecraftPythonAbstract.SERIAL_DIV + y + CommandMinecraftPythonAbstract.SERIAL_DIV + z + CommandMinecraftPythonAbstract.SERIAL_DIV + teleportingPlayer;
	}
	
	
}