package org.sapphon.minecraft.modding.minecraftpython.network;

import net.minecraftforge.fml.network.NetworkEvent;
import org.sapphon.minecraft.modding.minecraftpython.command.CommandQueueClientSide;
import org.sapphon.minecraft.modding.minecraftpython.command.CommandQueueServerSide;

import java.util.function.Supplier;

public class PacketHandler {

    public static void onMessage(PacketMinecraftPythonServerCommand message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            CommandQueueServerSide.SINGLETON().scheduleCommand(message.getCommand());
        });
        ctx.get().setPacketHandled(true);
    }

    public static void onMessage(PacketMinecraftPythonClientCommand message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            CommandQueueClientSide.SINGLETON().scheduleCommand(message.getCommand());
        });
        ctx.get().setPacketHandled(true);
    }
}
