package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.command.PacketHandlerMinecraftPythonServerCommand;
import org.sapphon.minecraft.modding.minecraftpython.command.PacketMinecraftPythonServerCommand;
import org.sapphon.minecraft.modding.minecraftpython.spells.ThreadFactory;

@Mod("minecraftpython")
public class MinecraftPythonMod {
	public static final String MODID = "minecraftpython";
	public static final String VERSION = "1.10.2-0.5.0";
	public static final int SCRIPT_RUN_COOLDOWN = 1500;
	public static final Logger logger = LogManager.getLogger(MinecraftPythonMod.MODID);

	public MinecraftPythonMod(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doSharedInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientInit);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static MinecraftPythonMod instance;

	public static SimpleChannel serverCommandPacketChannel;
	public static SimpleChannel clientCommandPacketChannel;

	private void doSharedInit(final FMLCommonSetupEvent event)
	{
		if (isEnabled()) {
			if(!ScriptLoaderConstants.resourcePathExists()){
				ScriptLoaderConstants.setResourcePath(event);
			}
			serverCommandPacketChannel = NetworkRegistry.INSTANCE
					.newSimpleChannel("MPServerCommand");
			serverCommandPacketChannel.registerMessage(
					PacketHandlerMinecraftPythonServerCommand.class,
					PacketMinecraftPythonServerCommand.class, 0, Side.SERVER);
			clientCommandPacketChannel = NetworkRegistry.INSTANCE
					.newSimpleChannel("MPClientCommand");
			clientCommandPacketChannel.registerMessage(
					PacketHandlerMinecraftPythonClientCommand.class,
					PacketMinecraftPythonClientCommand.class, 0, Side.CLIENT);
		}
	}

	private void doClientInit(final FMLClientSetupEvent event) {
		if (isEnabled()) {
			FMLCommonHandler
					.instance()
					.bus()
					.register(
							new MinecraftPythonKeyHandler(
									MinecraftPythonScriptLoader.SINGLETON()
											.getMagicVessel()));

			if (ModConfigurationFlags.MPPM_WEB()) {
				ThreadFactory.makeJavaGameLoopThread().start();
			}
		}
	}

	private boolean isEnabled() {
		return ModConfigurationFlags.MINECRAFT_PYTHON_PROGRAMMING();
	}
	
}