package org.yogpstop.tof;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.StatCollector;
import net.minecraft.src.Tessellator;

public class GuiSlotBiomeList extends GuiSlot {
	private TreeMap<Integer, String> biome = new TreeMap<Integer, String>();
	private int[] biomeid;
	private GuiBiomeSelect parent;
	private GuiButton[] onoff;

	public GuiSlotBiomeList(Minecraft par1Minecraft, int par2, int par3,
			int par4, int par5, int par6, GuiBiomeSelect parents) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			if (BiomeGenBase.biomeList[i] != null) {
				if (!BiomeGenBase.biomeList[i].biomeName.contains("Hell"))
					biome.put(i, BiomeGenBase.biomeList[i].biomeName);
			}
		}
		biomeid = new int[biome.size()];
		onoff = new GuiButton[biome.size()];
		Set<Map.Entry<Integer, String>> entrySet = biome.entrySet();
		Iterator<Map.Entry<Integer, String>> it = entrySet.iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			biomeid[i] = entry.getKey();
			i++;
		}
		parent = parents;
	}

	@Override
	protected int getSize() {
		return biome.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		if (TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).AllBiome)
			return;
		if (TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
				.contains(biomeid[var1])) {
			TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
					.remove(TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
							.indexOf(biomeid[var1]));
		} else {
			TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
					.add(biomeid[var1]);
		}
		onoff[var1].displayString = (TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
				.contains(biomeid[var1]) ? StatCollector.translateToLocal("options.on") : StatCollector.translateToLocal("options.off"));
	}

	@Override
	protected boolean isSelected(int var1) {
		return false;
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
		if (TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).AllBiome)
			return;
		onoff[var1] = new GuiButton(
				var1,
				this.parent.width / 2 - 100,
				var3 + 2,
				30,
				10,
				(TimesOreForge.setting.get(((GuiOre) this.parent.parent).ore).Biome
						.contains(biomeid[var1]) ? StatCollector.translateToLocal("options.on") : StatCollector.translateToLocal("options.off")));
		onoff[var1].enabled = false;
		onoff[var1].drawButton(Minecraft.getMinecraft(), 0, 0);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.biome
				.get(biomeid[var1]), (this.parent.width - Minecraft
				.getMinecraft().fontRenderer.getStringWidth(this.biome
				.get(biomeid[var1]))) / 2, var3 + 1, 0xFFFFFF);
	}

}
