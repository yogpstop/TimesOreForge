package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StatCollector;

public class GuiBiomeSelect extends GuiScreen {
	private GuiScreen parent;
	private GuiSlotBiomeList biomeList;

	public GuiBiomeSelect(GuiScreen parentA) {
		super();
		this.parent = parentA;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		controlList.add(new GuiButton(-1, this.width / 2 - 100,
				this.height - 38, StatCollector.translateToLocal("gui.done")));
		this.biomeList = new GuiSlotBiomeList(mc, this.width,
				this.height, 32, this.height-61, 18, this);
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		this.biomeList.drawScreen(i, j, k);
		String title = "SelectBiome";
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 8,
				0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		default:
			break;
		}
	}
}
