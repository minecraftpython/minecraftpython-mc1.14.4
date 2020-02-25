package org.sapphon.minecraft.modding.minecraftpython.problemhandlers;

import net.minecraft.client.Minecraft;
import org.sapphon.minecraft.modding.gui.GuiPythonErrorMessage;

public class PythonProblemHandler extends AbstractProblemHandler {

    public static void printErrorMessageToDialogBox(Exception e) {
        String fullErrorMessage = getStackTraceFromException(e);
        String pythonErrorMessageFull = fullErrorMessage.split("at org.")[0];
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft != null) {
            System.out.println(fullErrorMessage);
            minecraft.displayGuiScreen(new GuiPythonErrorMessage(pythonErrorMessageFull));
        }
    }
}
