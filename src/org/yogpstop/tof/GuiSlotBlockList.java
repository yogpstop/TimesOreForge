package org.yogpstop.tof;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Tessellator;

public class GuiSlotBlockList extends GuiSlot {
	List<ItemStack> blocklist = new ArrayList<ItemStack>();
	private GuiSelectBlock parent;
	public int currentblockid;
	public int currentmeta;

	public GuiSlotBlockList(Minecraft par1Minecraft, int par2, int par3,
			int par4, int par5, int par6, GuiSelectBlock parents) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		for (int i = 0; i < 4096; i++) {
			if (Block.blocksList[i] != null) {
				if (Item.itemsList[i] != null) {
					if (!(Block.blocksList[i].getBlockName() == null && Item.itemsList[i]
							.getItemName() != null)) {
						Block.blocksList[i].getSubBlocks(i, null, blocklist);
					}
				} else {
					Block.blocksList[i].getSubBlocks(i, null, blocklist);
				}
			}
		}
		for (int i = 0; i < blocklist.size(); i++) {
			for (int j = 0; j < TimesOreForge.setting.size(); j++) {
				if (blocklist.get(i).itemID == TimesOreForge.setting.get(j).BlockID
						&& blocklist.get(i).getItemDamage() == TimesOreForge.setting
								.get(j).Meta) {
					blocklist.remove(i);
					i--;
				}
			}
		}
		parent = parents;
	}

	@Override
	protected int getSize() {
		return blocklist.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		currentblockid = this.blocklist.get(var1).itemID;
		currentmeta = this.blocklist.get(var1).getItemDamage();
	}

	@Override
	protected boolean isSelected(int var1) {
		return this.blocklist.get(var1).itemID == currentblockid
				&& currentmeta == this.blocklist.get(var1).getItemDamage();
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
		String name = TimesOreForge.getname(this.blocklist.get(var1).itemID,
				this.blocklist.get(var1).getItemDamage());
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(name,
				(this.parent.width - Minecraft.getMinecraft().fontRenderer
						.getStringWidth(name)) / 2, var3 + 1, 0xFFFFFF);
	}
}
