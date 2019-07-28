package org.sapphon.minecraft.modding.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiPythonErrorMessage extends Screen {
	private int field_146444_f;
	private static final String __OBFID = "CL_22715703";
	private String[] messagesAsInput;
	private List<String> messagesToDisplay;
	private String rawMessage;

	public GuiPythonErrorMessage(String message) {
		super(new StringTextComponent("Error Title"));
		this.rawMessage = message;
		this.messagesAsInput = message.split("\r\n");
		this.messagesToDisplay = new ArrayList<String>();

	}

	public void initGui() {
		this.buttons.clear();
		byte b0 = -16;
		boolean flag = true;
		this.buttons
				.add(new Button(0, 0, this.width / 2 - 100, this.height / 4 + 104, "Got It, Close This Message", null));
	}

	protected void actionPerformed(Button buttonPushed) {
		if(buttonPushed.getMessage().equals("Got It, Close This Message")) {
			this.minecraft.displayGuiScreen((Screen) null);
			this.minecraft.setGameFocused(true);
		}
		else{
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
					"Unexpected GUI input on error message screen.  Button: " + buttonPushed.getMessage()));
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();
		++this.field_146444_f;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		try{
		this.drawDefaultBackground();
		this.drawCenteredString(this.font, "Python had a problem understanding you!", this.width / 2, 40,
				16777215);
		int yOffsetOfFirstLine = 80;
		int charactersToPermitPerLine = 60;
		if (populateMessagesToDisplay(charactersToPermitPerLine)) {
			for (int i = 0; i < messagesToDisplay.size(); i++) {
				this.drawCenteredString(this.font, messagesToDisplay.get(i).trim(), this.width / 2,
						yOffsetOfFirstLine + (i * 10), 16777215);
			}
			messagesToDisplay.clear();
		}
		else{
			this.drawCenteredString(this.font, rawMessage.trim(), this.width / 2,
					yOffsetOfFirstLine, 16777215);
		}
		super.drawScreen(par1, par2, par3);
		}catch(Exception e){}
	}

	private boolean populateMessagesToDisplay(int maxCharactersPerLine) {
		try {
			boolean isASyntaxError = messagesAsInput[0].startsWith("SyntaxError");
			if (messagesAsInput[0] != null && isASyntaxError) {
				this.messagesToDisplay.addAll(splitStringIntoLines(messagesAsInput[0], 60));
			} else {
				int indexOfTheWordLine = messagesAsInput[1].indexOf("line");
				String lineNumberString = messagesAsInput[1].substring(indexOfTheWordLine,
						messagesAsInput[1].indexOf(",", indexOfTheWordLine));
				this.messagesToDisplay.add(lineNumberString);
				String importantErrorInfo = messagesAsInput[messagesAsInput.length - 3];
				this.messagesToDisplay.addAll(splitStringIntoLines(importantErrorInfo, maxCharactersPerLine));
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private List<String> splitStringIntoLines(String stringToSplit, int lineLengthInCharacters) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (int i = 0; i <= stringToSplit.length() / lineLengthInCharacters; i++) {
			toReturn.add(stringToSplit.substring(i * lineLengthInCharacters,
					Math.min((i + 1) * lineLengthInCharacters, stringToSplit.length())));
		}
		return toReturn;
	}
}