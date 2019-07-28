package org.sapphon.minecraft.modding.minecraftpython.command;

public class PacketHandlerMinecraftPythonClientCommand implements IMessageHandler<PacketMinecraftPythonClientCommand, IMessage> {

	@Override
	public IMessage onMessage(PacketMinecraftPythonClientCommand message,
							  MessageContext ctx) {
		CommandQueueClientSide.SINGLETON().scheduleCommand(message.getCommand());
		return null;
	}

}
