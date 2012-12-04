package org.yogpstop.tof;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.Side;

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
