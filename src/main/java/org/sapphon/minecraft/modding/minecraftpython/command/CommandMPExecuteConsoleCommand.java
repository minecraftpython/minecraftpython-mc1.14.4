package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import org.sapphon.minecraft.modding.base.ServerGetter;
import org.sapphon.minecraft.modding.minecraftpython.MinecraftPythonMod;

public class CommandMPExecuteConsoleCommand extends
        CommandMinecraftPythonServer {

    private String commandString;
    private String playerName;

    public CommandMPExecuteConsoleCommand(String commandText) {
        this.commandString = commandText;
        this.playerName = Minecraft.getInstance().player.getDisplayName().getUnformattedComponentText();
    }

    public CommandMPExecuteConsoleCommand(String[] commandAndArgsToDeserialize) {
        this.commandString = commandAndArgsToDeserialize[1];
        this.playerName = commandAndArgsToDeserialize[2];
    }


    @Override
    public void doWork() {
        PlayerEntity playerObject = getPlayerByName(playerName);
        try {
            int successValue = ServerGetter.getServer().getCommandManager().handleCommand(playerObject.getCommandSource().withPermissionLevel(99), commandString);
        } catch (Exception e) {
            MinecraftPythonMod.logger.error("Could not execute console command", e);
        }

    }

    private PlayerEntity getPlayerByName(String name) {
        return ServerGetter.getServer().getPlayerList().getPlayerByUsername(name);
    }

    @Override
    public String serialize() {
        //TODO this will never work if it ever needs to, commands must certainly contain commas sometimes...?
        return CONSOLECOMMAND_NAME + SERIAL_DIV + commandString + SERIAL_DIV + playerName;
    }
}
