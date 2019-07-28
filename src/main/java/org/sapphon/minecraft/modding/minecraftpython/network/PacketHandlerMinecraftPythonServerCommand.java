package org.sapphon.minecraft.modding.minecraftpython.network;

import org.sapphon.minecraft.modding.minecraftpython.command.CommandQueueServerSide;

public class PacketHandlerMinecraftPythonServerCommand implements
		IMessageHandler<PacketMinecraftPythonServerCommand, IMessage> {
	public PacketHandlerMinecraftPythonServerCommand(){
		
	}
	@Override
	public IMessage onMessage(PacketMinecraftPythonServerCommand message, MessageContext ctx) {
		CommandQueueServerSide.SINGLETON().scheduleCommand(message.command);
		return null;
	}
/*
	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		return null;
	}*/
}
