package org.yogpstop.tof;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiSetting extends GuiScreen {
	private GuiSlotOres oreslot;
	private GuiButton delete;
	private GuiButton setting;

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(-1, this.width / 2 - 125, this.height - 26, 250, 20, StatCollector.translateToLocal("gui.done")));
		this.buttonList.add(this.setting = new GuiButton(0, this.width * 2 / 3 + 10, 20, 100, 20, StatCollector.translateToLocal("menu.options")));
		this.buttonList.add(new GuiButton(3, this.width * 2 / 3 + 10, 80, 100, 20, StatCollector.translateToLocal("tof.addnewore") + "("
				+ StatCollector.translateToLocal("tof.manualinput") + ")"));
		this.buttonList.add(new GuiButton(1, this.width * 2 / 3 + 10, 50, 100, 20, StatCollector.translateToLocal("tof.addnewore") + "("
				+ StatCollector.translateToLocal("tof.fromlist") + ")"));
		this.buttonList.add(this.delete = new GuiButton(2, this.width * 2 / 3 + 10, 110, 100, 20, StatCollector.translateToLocal("selectServer.delete")));
		this.oreslot = new GuiSlotOres(this.mc, this.width * 3 / 5, this.height, 30, this.height - 30, 18, this);
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			TimesOreForge.save();
			this.mc.displayGuiScreen((GuiScreen) null);
			break;
		case 0:
			this.mc.displayGuiScreen(new GuiOre(this.oreslot.currentore));
			break;
		case 2:
			this.mc.displayGuiScreen(new GuiYesNo(this, StatCollector.translateToLocal("tof.deleteblocksure"), TimesOreForge.getname(
					TimesOreForge.setting.get(this.oreslot.currentore).blockID, TimesOreForge.setting.get(this.oreslot.currentore).meta),
					this.oreslot.currentore));
			break;
		case 1:
			this.mc.displayGuiScreen(new GuiSelectBlock(this));
			break;
		case 3:
			this.mc.displayGuiScreen(new GuiManual(this));
			break;
		default:
			break;
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		this.oreslot.drawScreen(i, j, k);
		this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("tof.setting"), this.width / 2, 8, 0xFFFFFF);
		if (TimesOreForge.setting.isEmpty()) {
			this.setting.enabled = false;
			this.delete.enabled = false;
		}
		super.drawScreen(i, j, k);
	}

	@Override
	public void confirmClicked(boolean par1, int par2) {
		if (par1) {
			TimesOreForge.setting.remove(par2);
		}
		this.mc.displayGuiScreen(this);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
			this.mc.thePlayer.closeScreen();
		}
	}
}
