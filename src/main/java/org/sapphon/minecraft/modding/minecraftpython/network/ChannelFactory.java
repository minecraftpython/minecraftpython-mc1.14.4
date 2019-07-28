package org.sapphon.minecraft.modding.minecraftpython.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ChannelFactory {
    public static SimpleChannel createChannel(String name){
        String version = "1";
        String namespace = "minecraftpython";
        return NetworkRegistry.newSimpleChannel(
                new ResourceLocation(namespace, name),
                () -> version,
                version::equals,
                version::equals
        );
    }
}
