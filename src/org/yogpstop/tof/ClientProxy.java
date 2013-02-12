package org.yogpstop.tof;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static int bindKey = Keyboard.KEY_O;

	@Override
	public void setKeyHandler() {
		KeyBinding[] myBinding = { new KeyBinding("TimesOreForgeConfigKey",
				bindKey) };

		boolean[] myBindingRepeat = { false };

		KeyBindingRegistry.registerKeyBinding(new KeyboardHandler(myBinding,
				myBindingRepeat));
	}

}
