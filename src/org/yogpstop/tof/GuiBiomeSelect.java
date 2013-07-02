package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class GuiBiomeSelect extends GuiScreen {
	private int ore;
	private GuiSlotBiomeList biomeList;
	private GuiButton all;

	public GuiBiomeSelect(int Aore) {
		super();
		ore = Aore;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.add(new GuiButton(-1, this.width / 2 - 100, this.height - 26, StatCollector.translateToLocal("gui.done")));
		all = new GuiButton(0, this.width / 2 - 100, 20, "");
		buttonList.add(all);
		this.biomeList = new GuiSlotBiomeList(mc, this.width, this.height, 40, this.height - 30, 18, this, ore);
		if (TimesOreForge.setting.get(ore).allBiome) {
			TimesOreForge.setting.get(ore).biomes.clear();
			all.displayString = StatCollector.translateToLocal("tof.allbiome") + ": " + StatCollector.translateToLocal("options.on");
		} else {
			all.displayString = StatCollector.translateToLocal("tof.allbiome") + ": " + StatCollector.translateToLocal("options.off");
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		this.biomeList.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.selectbiome");
		fontRenderer.drawStringWithShadow(title, (this.width - fontRenderer.getStringWidth(title)) / 2, 4, 0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	public void update() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(ore));
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(new GuiOre(ore));
			break;
		case 0:
			TimesOreForge.setting.get(ore).allBiome = (TimesOreForge.setting.get(ore).allBiome ? false : true);
			update();
		default:
			break;
		}
	}
}
