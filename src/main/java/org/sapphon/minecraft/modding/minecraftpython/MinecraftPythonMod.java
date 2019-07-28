package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.command.ClientTickHandler;
import org.sapphon.minecraft.modding.minecraftpython.command.ServerTickHandler;
import org.sapphon.minecraft.modding.minecraftpython.network.ChannelFactory;
import org.sapphon.minecraft.modding.minecraftpython.network.PacketHandler;
import org.sapphon.minecraft.modding.minecraftpython.network.PacketMinecraftPythonClientCommand;
import org.sapphon.minecraft.modding.minecraftpython.network.PacketMinecraftPythonServerCommand;
import org.sapphon.minecraft.modding.minecraftpython.spells.ThreadFactory;

@Mod("minecraftpython")
public class MinecraftPythonMod {
    public static final String MODID = "minecraftpython";
    public static final String VERSION = "1.14.4-0.7.0";
    public static final int SCRIPT_RUN_COOLDOWN = 1500;
    public static final Logger logger = LogManager.getLogger(MinecraftPythonMod.MODID);

    public MinecraftPythonMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doSharedInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doServerInit);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static MinecraftPythonMod instance;

    public static SimpleChannel serverCommandPacketChannel;
    public static SimpleChannel clientCommandPacketChannel;

    private void doSharedInit(final FMLCommonSetupEvent event) {
        if (isEnabled()) {
            createPacketChannels();
        }
    }

    private void createPacketChannels() {
        serverCommandPacketChannel = ChannelFactory.createChannel("mpservercommand");
        clientCommandPacketChannel = ChannelFactory.createChannel("mpclientcommand");
        serverCommandPacketChannel.registerMessage(1,
                PacketMinecraftPythonServerCommand.class, PacketMinecraftPythonServerCommand::toBytes, PacketMinecraftPythonServerCommand::fromBytes, PacketHandler::onMessage);
        clientCommandPacketChannel.registerMessage(1,
                PacketMinecraftPythonClientCommand.class, PacketMinecraftPythonClientCommand::toBytes, PacketMinecraftPythonClientCommand::fromBytes, PacketHandler::onMessage);

    }

    private void doClientInit(final FMLClientSetupEvent event) {
        if (isEnabled()) {

            if (!ScriptLoaderConstants.resourcePathExists()) {
                ScriptLoaderConstants.setResourcePath(event);
            }
            MinecraftForge.EVENT_BUS.register(
                    new MinecraftPythonKeyHandler(
                            MinecraftPythonScriptLoader.SINGLETON()
                                    .getMagicVessel()));

            if (ModConfigurationFlags.MPPM_WEB()) {
                ThreadFactory.makeJavaGameLoopThread().start();
            }
            MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        }
    }

    private void doServerInit(final FMLDedicatedServerSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
    }

    private boolean isEnabled() {
        return ModConfigurationFlags.MINECRAFT_PYTHON_PROGRAMMING();
    }

}