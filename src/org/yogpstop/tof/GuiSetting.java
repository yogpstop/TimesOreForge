package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StatCollector;

public class GuiSetting extends GuiScreen {
	public GuiScreen parent;

	public GuiSetting(GuiScreen parentA) {
		super();
		this.parent = parentA;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		controlList.add(new GuiButton(TimesOreForge.Coal, this.width / 2 - 170,
				40, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[0])));
		controlList.add(new GuiButton(TimesOreForge.Iron, this.width / 2 + 20,
				40, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[1])));
		controlList.add(new GuiButton(TimesOreForge.Gold, this.width / 2 - 170,
				70, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[2])));
		controlList.add(new GuiButton(TimesOreForge.Diamond,
				this.width / 2 + 20, 70, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[3])));
		controlList.add(new GuiButton(TimesOreForge.Lapis,
				this.width / 2 - 170, 100, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[4])));
		controlList.add(new GuiButton(TimesOreForge.Redstone,
				this.width / 2 + 20, 100, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[5])));
		controlList.add(new GuiButton(TimesOreForge.Emerald,
				this.width / 2 - 170, 130, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[6])));
		controlList
				.add(new GuiButton(TimesOreForge.Dirt, this.width / 2 + 20,
						130, 150, 20, StatCollector
								.translateToLocal(TimesOreForge.ores[7])));
		controlList.add(new GuiButton(TimesOreForge.Gravel,
				this.width / 2 - 170, 160, 150, 20, StatCollector
						.translateToLocal(TimesOreForge.ores[8])));
		controlList.add(new GuiButton(9, this.width / 2 + 20, 160, 150, 20,
				StatCollector.translateToLocal("itemGroup.misc")));
		controlList.add(new GuiButton(-1, this.width / 2 - 125, 200, 250, 20,
				StatCollector.translateToLocal("gui.done")));
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case 9:
			break;
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		default:
			Minecraft.getMinecraft().displayGuiScreen(
					new GuiConstantOre(this, par1.id));
			break;
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		String title = "Setting of TimesOreForge";
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 15,
				0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		KeyboardHandler.resetcount();
	}
}
