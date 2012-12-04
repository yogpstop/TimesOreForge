package org.yogpstop.tof;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StatCollector;

public class GuiBiomeSelect extends GuiScreen {
	public GuiScreen parent;
	private GuiSlotBiomeList biomeList;
	private GuiButton all;
	int i, j;
	float k;

	public GuiBiomeSelect(GuiScreen parentA) {
		super();
		this.parent = parentA;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		controlList.add(new GuiButton(-1, this.width / 2 - 100,
				this.height - 26, StatCollector.translateToLocal("gui.done")));
		all = new GuiButton(0, this.width / 2 - 100, 20, "");
		controlList.add(all);
		this.biomeList = new GuiSlotBiomeList(mc, this.width, this.height, 40,
				this.height - 30, 18, this);
		if (TimesOreForge.setting.get(((GuiOre) parent).ore).AllBiome) {
			TimesOreForge.setting.get(((GuiOre) parent).ore).Biome = new ArrayList<Integer>();
			all.displayString = StatCollector.translateToLocal("tof.allbiome")
					+ ": " + StatCollector.translateToLocal("options.on");
		} else {
			all.displayString = StatCollector.translateToLocal("tof.allbiome")
					+ ": " + StatCollector.translateToLocal("options.off");
		}
	}

	@Override
	public void drawScreen(int I, int J, float K) {
		i = I;
		j = J;
		k = K;
		drawDefaultBackground();
		this.biomeList.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.selectbiome");
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 4,
				0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	public void update() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(parent));
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		case 0:
			TimesOreForge.setting.get(((GuiOre) parent).ore).AllBiome = (TimesOreForge.setting
					.get(((GuiOre) parent).ore).AllBiome ? false : true);
			update();
		default:
			break;
		}
	}
}
