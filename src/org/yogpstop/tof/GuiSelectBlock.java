package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class GuiSelectBlock extends GuiScreen {
	private GuiSlotBlockList blocks;

	public GuiSelectBlock() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		blocks = new GuiSlotBlockList(Minecraft.getMinecraft(), this.width, this.height, 24, this.height - 32, 18, this);
		buttonList.add(new GuiButton(-1, this.width / 2 - 150, this.height - 26, 140, 20, StatCollector.translateToLocal("gui.done")));
		buttonList.add(new GuiButton(-2, this.width / 2 + 10, this.height - 26, 140, 20, StatCollector.translateToLocal("gui.cancel")));
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			TimesOreForge.setting.add(new SettingObject((short) blocks.currentblockid, blocks.currentmeta));
		case -2:
			Minecraft.getMinecraft().displayGuiScreen(new GuiSetting());
			break;
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		blocks.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.selectblock");
		fontRenderer.drawStringWithShadow(title, (this.width - fontRenderer.getStringWidth(title)) / 2, 8, 0xFFFFFF);
		super.drawScreen(i, j, k);
	}
}
