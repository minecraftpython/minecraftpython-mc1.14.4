package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraftforge.fml.network.PacketDistributor;
import org.sapphon.minecraft.modding.minecraftpython.MinecraftPythonMod;

import java.util.ArrayList;

public class CommandQueueServerSide extends CommandQueueAbstract {

	private static CommandQueueServerSide SINGLETON;

	private CommandQueueServerSide() {
		scheduledCommands = new ArrayList<ICommand>();
	}

	public synchronized void scheduleCommand(ICommand command) {
		if (command instanceof CommandMinecraftPythonServer) {
			this.scheduledCommands.add(command);
		} else if (command instanceof CommandMinecraftPythonClient) {
			CommandMinecraftPythonClient cast = (CommandMinecraftPythonClient) command;
			MinecraftPythonMod.clientCommandPacketChannel.send(PacketDistributor.PLAYER.noArg(), new PacketMinecraftPythonClientCommand(
					cast));
		}
	}

	public static ICommandQueue SINGLETON() {
		if (SINGLETON == null) {
			SINGLETON = new CommandQueueServerSide();
		}
		return SINGLETON;
	}

}
