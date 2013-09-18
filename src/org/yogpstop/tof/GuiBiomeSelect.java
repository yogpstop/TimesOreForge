/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
