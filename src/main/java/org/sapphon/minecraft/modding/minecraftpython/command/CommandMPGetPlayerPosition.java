package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;

import java.util.Optional;

public class CommandMPGetPlayerPosition{
	
	private String nameOfPlayer = "";
	
	public CommandMPGetPlayerPosition(){
		
	}
	public CommandMPGetPlayerPosition(String playerNameToGetPositionFor){
		nameOfPlayer = playerNameToGetPositionFor;
	}
	
	public int[] execute(){
		PlayerEntity player = getCorrectPlayer();
		return new int[]{
			(int)Math.round(player.posX),
			(int)Math.round(player.posY + player.getEyeHeight()),
			(int)Math.round(player.posZ)
		};
	}

	private PlayerEntity getPlayerEntityByName(String name){
		PlayerEntity toReturn = Minecraft.getInstance().world.getPlayers().stream().filter((x) -> x.getDisplayName().getUnformattedComponentText().equals(this.nameOfPlayer)).findFirst().orElse(null);//Note this compares by getCommandSenderName whereas GameStart uses DisplayNames.  Never been a problem...yet.
		if(toReturn == null){
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception("Problem finding player " + this.nameOfPlayer +  " by name.  Are you sure that player exists on this server?"));
		}
		return toReturn;
	}

	private boolean playerEntityByNameExists(String name){
		return Minecraft.getInstance().world.getPlayers().stream().anyMatch((x) -> x.getDisplayName().getUnformattedComponentText().equals(this.nameOfPlayer));
	}
	
	private PlayerEntity getCorrectPlayer() {
		if(nameOfPlayer.equals("") || this.playerEntityByNameExists(this.nameOfPlayer)){
			 return Minecraft.getInstance().player;
		}
		else{
			return getPlayerEntityByName(this.nameOfPlayer);
		}
	}
}
