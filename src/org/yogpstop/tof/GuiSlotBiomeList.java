package org.yogpstop.tof;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

public class GuiSlotBiomeList extends GuiSlot {
	private static final TreeMap<Integer, String> biome = new TreeMap<Integer, String>();
	static {
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			if (BiomeGenBase.biomeList[i] != null) {
				if (!BiomeGenBase.biomeList[i].biomeName.contains("Hell")) biome.put(i, BiomeGenBase.biomeList[i].biomeName);
			}
		}
	}
	private int[] biomeid;
	private GuiButton[] onoff;
	private GuiScreen parent;

	private int ore;

	public GuiSlotBiomeList(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6, GuiScreen parents, int oreID) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		this.biomeid = new int[biome.size()];
		this.onoff = new GuiButton[biome.size()];
		Set<Map.Entry<Integer, String>> entrySet = biome.entrySet();
		Iterator<Map.Entry<Integer, String>> it = entrySet.iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			this.biomeid[i] = entry.getKey();
			i++;
		}
		this.ore = oreID;
		this.parent = parents;
	}

	@Override
	protected int getSize() {
		return biome.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		if (TimesOreForge.setting.get(this.ore).allBiome) return;
		if (TimesOreForge.setting.get(this.ore).biomes.contains(this.biomeid[var1])) {
			TimesOreForge.setting.get(this.ore).biomes.remove(TimesOreForge.setting.get(this.ore).biomes.indexOf(this.biomeid[var1]));
		} else {
			TimesOreForge.setting.get(this.ore).biomes.add(this.biomeid[var1]);
		}
		this.onoff[var1].displayString = (TimesOreForge.setting.get(this.ore).biomes.contains(this.biomeid[var1]) ? StatCollector
				.translateToLocal("options.on") : StatCollector.translateToLocal("options.off"));
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
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		if (TimesOreForge.setting.get(this.ore).allBiome) return;
		this.onoff[var1] = new GuiButton(var1, this.parent.width / 2 - 100, var3 + 2, 30, 10,
				(TimesOreForge.setting.get(this.ore).biomes.contains(this.biomeid[var1]) ? StatCollector.translateToLocal("options.on")
						: StatCollector.translateToLocal("options.off")));
		this.onoff[var1].enabled = false;
		this.onoff[var1].drawButton(Minecraft.getMinecraft(), 0, 0);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(biome.get(this.biomeid[var1]),
				(this.parent.width - Minecraft.getMinecraft().fontRenderer.getStringWidth(biome.get(this.biomeid[var1]))) / 2, var3 + 1, 0xFFFFFF);
	}

}
