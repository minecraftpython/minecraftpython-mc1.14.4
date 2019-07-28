package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.util.text.StringTextComponent;
import org.sapphon.minecraft.modding.base.ServerGetter;

public class CommandMPBroadcast extends CommandMinecraftPythonServer {

	public String toBroadcast;

	public CommandMPBroadcast(String toBroadcast) {
		this.toBroadcast=toBroadcast;
	}

	public CommandMPBroadcast(String[] commandAndArgsToDeserialize) {
		toBroadcast=commandAndArgsToDeserialize[1];
	}

	@Override
	public void doWork() {
		ServerGetter.getServer().getPlayerList().sendMessage(new StringTextComponent(this.toBroadcast));
	}

	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.BROADCAST_NAME + CommandMinecraftPythonAbstract.SERIAL_DIV + toBroadcast;
	}


}
