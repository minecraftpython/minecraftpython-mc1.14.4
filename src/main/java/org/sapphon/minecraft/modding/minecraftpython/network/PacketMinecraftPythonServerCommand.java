package org.sapphon.minecraft.modding.minecraftpython.network;

import net.minecraft.network.PacketBuffer;
import org.sapphon.minecraft.modding.minecraftpython.command.*;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;
//TODO this whole class requires a re-factor to be at all data-driven.  Right now everything is minimally automated and honestly kinda questionable technically
public class PacketMinecraftPythonServerCommand {

	private CommandMinecraftPythonServer command;

	public PacketMinecraftPythonServerCommand() {

	}

	public PacketMinecraftPythonServerCommand(CommandMinecraftPythonServer commandToPackUp){
		this.command = commandToPackUp;
	}

	public static PacketMinecraftPythonServerCommand fromBytes(PacketBuffer buf) {
		String text = buf.readString();
		String[] commandAndArgsToDeserialize = text.split(CommandMinecraftPythonAbstract.SERIAL_DIV);
		String commandName = commandAndArgsToDeserialize[0].trim();
		CommandMinecraftPythonServer command;
		if(commandName.equals(CommandMPSetBlock.SETBLOCK_NAME)){
			command = new CommandMPSetBlock(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMinecraftPythonServer.CREATEEXPLOSION_NAME)) {
			command = new CommandMPCreateExplosion(commandAndArgsToDeserialize);
	
		} else if (commandName.equals(CommandMinecraftPythonServer.SPAWNENTITY_NAME)) {
			command = new CommandMPSpawnEntity(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMinecraftPythonServer.TELEPORT_NAME)) {
			command = new CommandMPTeleport(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMinecraftPythonServer.BROADCAST_NAME)) {
			command = new CommandMPBroadcast(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMinecraftPythonServer.LIGHTNINGBOLT_NAME)) {
			command = new CommandMPSpawnLightningBolt(commandAndArgsToDeserialize);
		} else if(commandName.equals(CommandMinecraftPythonServer.PROPEL_NAME)){
			command = new CommandMPPropelEntity(commandAndArgsToDeserialize);
		}else if(commandName.equals(CommandMinecraftPythonServer.SPAWNITEM_NAME)){
			command = new CommandMPSpawnItem(commandAndArgsToDeserialize);
		}else if(commandName.equals(CommandMinecraftPythonServer.CONSOLECOMMAND_NAME)){
			command = new CommandMPExecuteConsoleCommand(commandAndArgsToDeserialize);
		}
		else {
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
					"A server-side command  (type " + commandName
							+ ")'s packet could not be interpreted."));
			return null;
		}
		return new PacketMinecraftPythonServerCommand(command);
	}

	public PacketBuffer toBytes(PacketBuffer buffer) {
		buffer.writeString(command.serialize());
		return buffer;
	}

	public CommandMinecraftPythonServer getCommand() {
		return command;
	}

}
