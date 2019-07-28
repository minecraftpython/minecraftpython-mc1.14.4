package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MinecraftPythonKeyHandler {
	public static final int CAST_SPELL_KEY_INDEX = 0;
	private static final String[] keyDescriptions = { "key.castspell.desc"};
	private static final int[] defaultKeyValues = {80};	//The 'P' key, I hope
	private final KeyBinding[] keyBindings;
	private IArcane device;
	
	public MinecraftPythonKeyHandler(IArcane magicDeviceToActivateWhenCastSpellIsPressed) {
		this.device = magicDeviceToActivateWhenCastSpellIsPressed;
		keyBindings = new KeyBinding[keyDescriptions.length];
		for (int i = 0; i < keyDescriptions.length; ++i) {
			keyBindings[i] = new KeyBinding(keyDescriptions[i],
					defaultKeyValues[i], "key."
							+ MinecraftPythonMod.MODID + ".category");
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (keyBindings[CAST_SPELL_KEY_INDEX].isPressed()) {
			device.doMagic();
		}
		//more ifs go here when there are more keys
	}
}