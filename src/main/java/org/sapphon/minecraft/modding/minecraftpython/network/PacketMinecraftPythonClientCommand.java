package org.sapphon.minecraft.modding.minecraftpython.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.sapphon.minecraft.modding.minecraftpython.command.*;

public class PacketMinecraftPythonClientCommand {

	
	private CommandMinecraftPythonClient command;

	public PacketMinecraftPythonClientCommand(CommandMinecraftPythonClient commandToPackUp){
		this.command = commandToPackUp;
	}
	
	public static PacketMinecraftPythonClientCommand fromBytes(PacketBuffer buf) {
		String text = buf.readString();
		String[] commandAndArgsToDeserialize = text.split(CommandMinecraftPythonAbstract.SERIAL_DIV);
		String commandName = commandAndArgsToDeserialize[0].trim();
		CommandMinecraftPythonClient command;
		if(commandName.equals(CommandMinecraftPythonClient.SPAWNPARTICLE_NAME)){
			command = new CommandMPSpawnParticle(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMinecraftPythonClient.SECRETSETTINGS_NAME)) {
			command = new CommandMPApplyShader(commandAndArgsToDeserialize);
	
		} else if (commandName.equals(CommandMinecraftPythonClient.CHANGESETTINGS_NAME)) {
			command = new CommandMPChangeSettings(commandAndArgsToDeserialize);	
		}else{
			command = null;
		}
		return new PacketMinecraftPythonClientCommand(command);
	}

	public PacketBuffer toBytes(PacketBuffer buffer) {
		buffer.writeString(command.serialize());
		return buffer;
	}

	public CommandMinecraftPythonClient getCommand() {
		return command;
	}
}
