package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StatCollector;

public class GuiSetting extends GuiScreen {
	public GuiScreen parent;
	public GuiSlotOres oreslot;
	public GuiButton delete;

	public GuiSetting(GuiScreen parentA) {
		super();
		this.parent = parentA;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		controlList.add(new GuiButton(-1, this.width / 2 - 125,
				this.height - 26, 250, 20, StatCollector
						.translateToLocal("gui.done")));
		controlList.add(new GuiButton(0, this.width * 2 / 3 + 10, 20, 100, 20,
				StatCollector.translateToLocal("menu.options")));
		controlList
				.add(new GuiButton(1, this.width * 2 / 3 + 10, 50, 100, 20,
						StatCollector.translateToLocal("tof.addnewore")
								+ "("
								+ StatCollector
										.translateToLocal("tof.fromlist") + ")"));
		controlList.add(new GuiButton(3, this.width * 2 / 3 + 10, 80, 100, 20,
				StatCollector.translateToLocal("tof.addnewore") + "("
						+ StatCollector.translateToLocal("tof.manualinput")
						+ ")"));
		controlList
				.add(delete = new GuiButton(2, this.width * 2 / 3 + 10, 110,
						100, 20, StatCollector
								.translateToLocal("selectServer.delete")));
		oreslot = new GuiSlotOres(Minecraft.getMinecraft(), this.width * 3 / 5,
				this.height, 30, this.height - 30, 18, this);
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			TimesOreForge.save();
			TimesOreForge.parseSet();
			KeyboardHandler.resetcount();
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(
					new GuiOre(this, oreslot.currentore));
			break;
		case 2:
			Minecraft.getMinecraft().displayGuiScreen(
					new GuiYesNo(this, StatCollector
							.translateToLocal("tof.deleteblocksure"),
							TimesOreForge.getname(TimesOreForge.setting
									.get(oreslot.currentore).BlockID,
									TimesOreForge.setting
											.get(oreslot.currentore).Meta),
							oreslot.currentore));
			break;
		case 1:
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectBlock(this));
			break;
		case 3:
			Minecraft.getMinecraft().displayGuiScreen(new GuiInputOre(this));
			break;
		default:
			break;
		}
	}

	public void deletetoggle() {
		if (TimesOreForge.DLumps.containsKey(TimesOreForge.setting
				.get(oreslot.currentore).BlockID)) {
			delete.enabled = false;
		} else {
			delete.enabled = true;
		}
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		oreslot.drawScreen(i, j, k);
		String title = StatCollector.translateToLocal("tof.setting");
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 8,
				0xFFFFFF);
		super.drawScreen(i, j, k);
		deletetoggle();
	}

	@Override
	public void confirmClicked(boolean par1, int par2) {
		if (par1) {
			TimesOreForge.setting.remove(par2);
		}
		Minecraft.getMinecraft().displayGuiScreen(this);
	}
}
