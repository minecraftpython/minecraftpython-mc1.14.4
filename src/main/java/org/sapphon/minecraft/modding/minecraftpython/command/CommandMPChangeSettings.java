package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;

public class CommandMPChangeSettings extends CommandMinecraftPythonClient {

	private String targetName;
	
	public CommandMPChangeSettings(String targetPlayerName){
		this.targetName = targetPlayerName;
	}
	
	public CommandMPChangeSettings(String[] commandAndArgsToDeserialize) {
		this(commandAndArgsToDeserialize[1]);
	}

	@Override
	public void doWork() {
			Minecraft.getInstance().gameSettings.fov = 0;
			Minecraft.getInstance().gameSettings.renderDistanceChunks = Math.min(Minecraft.getInstance().gameSettings.renderDistanceChunks, 4);
			Minecraft.getInstance().gameSettings.framerateLimit = 24;
			Minecraft.getInstance().gameSettings.fancyGraphics = false;
			Minecraft.getInstance().gameSettings.saveOptions();
	}

	@Override
	public String serialize() {
		return CHANGESETTINGS_NAME + SERIAL_DIV + targetName;
	}

	@Override
	protected String getTargetPlayerName() {
		return this.targetName;
	}

}
