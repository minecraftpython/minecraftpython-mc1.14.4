package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;

public class CommandMPGetPlayerName {
    public String execute() {
        return Minecraft.getInstance().player.getDisplayName().getUnformattedComponentText();
    }
}
