package org.yogpstop.tof;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiBiomeSelect extends GuiScreen {
	private int ore;
	private GuiSlotBiomeList biomeList;
	private GuiButton all;

	public GuiBiomeSelect(int Aore) {
		super();
		this.ore = Aore;
	}

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(-1, this.width / 2 - 100, this.height - 26, StatCollector.translateToLocal("gui.done")));
		this.all = new GuiButton(0, this.width / 2 - 100, 20, "");
		this.buttonList.add(this.all);
		this.biomeList = new GuiSlotBiomeList(this.mc, this.width, this.height, 40, this.height - 30, 18, this, this.ore);
		if (TimesOreForge.setting.get(this.ore).allBiome) {
			TimesOreForge.setting.get(this.ore).biomes.clear();
			this.all.displayString = StatCollector.translateToLocal("tof.allbiome") + ": " + StatCollector.translateToLocal("options.on");
		} else {
			this.all.displayString = StatCollector.translateToLocal("tof.allbiome") + ": " + StatCollector.translateToLocal("options.off");
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		this.biomeList.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.selectbiome");
		this.fontRenderer.drawStringWithShadow(title, (this.width - this.fontRenderer.getStringWidth(title)) / 2, 4, 0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	public void update() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(this.ore));
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(new GuiOre(this.ore));
			break;
		case 0:
			TimesOreForge.setting.get(this.ore).allBiome = (TimesOreForge.setting.get(this.ore).allBiome ? false : true);
			update();
		default:
			break;
		}
	}
}
