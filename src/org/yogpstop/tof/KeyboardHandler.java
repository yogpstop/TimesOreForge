package org.yogpstop.tof;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyboardHandler extends KeyHandler {
	private static int count = 0;

	public KeyboardHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if (kb.keyCode == this.getKeyBindings()[0].keyCode) {
			count++;
			if (count == 1) {
				Minecraft.getMinecraft().displayGuiScreen(
						new GuiSetting(Minecraft.getMinecraft().currentScreen));
			}
		}
	}

	public static void resetcount() {
		count = 0;
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "TimesOreForge";
	}
}