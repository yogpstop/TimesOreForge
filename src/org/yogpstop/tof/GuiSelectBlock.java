package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StatCollector;

public class GuiSelectBlock extends GuiScreen {
	public GuiScreen parent;
	public GuiSlotBlockList blocks;

	public GuiSelectBlock(GuiScreen parentA) {
		super();
		this.parent = parentA;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		blocks = new GuiSlotBlockList(Minecraft.getMinecraft(),this.width,this.height,24,this.height-32,18,this);
		controlList.add(new GuiButton(-1, this.width / 2 - 150,
				this.height - 26, 140, 20, StatCollector
						.translateToLocal("gui.done")));
		controlList.add(new GuiButton(-2, this.width / 2 + 10,
				this.height - 26, 140, 20, StatCollector
						.translateToLocal("gui.cancel")));
	}
	
	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			TimesOreForge.setting.add(new SettingObject(blocks.currentblockid,blocks.currentmeta));
		case -2:
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		}
	}
	
	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		blocks.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.selectblock");
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 8,
				0xFFFFFF);
		super.drawScreen(i, j, k);
	}
}
