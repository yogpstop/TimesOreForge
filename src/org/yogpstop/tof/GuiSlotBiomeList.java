package org.yogpstop.tof;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;

public class GuiSlotBiomeList extends GuiSlot {
	private TreeMap<Integer, String> biome = new TreeMap<Integer, String>();
	private int[] biomeid;
	private GuiBiomeSelect parent;

	public GuiSlotBiomeList(Minecraft par1Minecraft, int par2, int par3,
			int par4, int par5, int par6, GuiBiomeSelect parents) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			if (BiomeGenBase.biomeList[i] != null) {
				biome.put(i, BiomeGenBase.biomeList[i].biomeName);
			}
		}
		biomeid = new int[biome.size()];

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
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isSelected(int var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.biome
				.get(biomeid[var1]), (this.parent.width - Minecraft
				.getMinecraft().fontRenderer.getStringWidth(this.biome
				.get(biomeid[var1]))) / 2, var3 + 1, 0xFFFFFF);
	}

}
