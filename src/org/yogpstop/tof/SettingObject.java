package org.yogpstop.tof;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SettingObject {
	public int Lumps;
	public int Height;
	public int Blocks;
	public int BlockID;
	public int Meta;
	public boolean LikeLapis;
	public boolean AllBiome = true;
	public int isSetMultiple = 1;
	public int BaseBlock = net.minecraft.src.Block.oreCoal.blockID;
	public ArrayList<Integer> Biome;

	public SettingObject(int ABlockID, int AMeta) {
		Biome = new ArrayList<Integer>();
		BlockID = ABlockID;
		Meta = AMeta;
		if (TimesOreForge.DLumps.containsKey(BlockID)) {
			Height = TimesOreForge.DHeight.get(BlockID);
			Blocks = TimesOreForge.DBlocks.get(BlockID);
			BaseBlock = BlockID;
		}
	}

	public SettingObject(String Line) {
		Biome = new ArrayList<Integer>();
		String[] cache = Line.split(",");
		Lumps = Integer.valueOf(cache[0]);
		Height = Integer.valueOf(cache[1]);
		Blocks = Integer.valueOf(cache[2]);
		BlockID = Integer.valueOf(cache[3]);
		Meta = Integer.valueOf(cache[4]);
		LikeLapis = Boolean.valueOf(cache[5]);
		AllBiome = Boolean.valueOf(cache[6]);
		isSetMultiple = Integer.valueOf(cache[7]);
		BaseBlock = Integer.valueOf(cache[8]);
		if (cache.length == 10) {
			String[] cachebiome = cache[9].split(":");
			if (cachebiome.length <= 1 && cachebiome[0] == "")
				return;
			for (int i = 0; i < cachebiome.length; i++) {
				Biome.add(Integer.valueOf(cachebiome[i]));
			}
		}
	}

	public void save(BufferedWriter bw) {
		StringBuffer cache = new StringBuffer();
		cache.append(Lumps);
		cache.append(",");
		cache.append(Height);
		cache.append(",");
		cache.append(Blocks);
		cache.append(",");
		cache.append(BlockID);
		cache.append(",");
		cache.append(Meta);
		cache.append(",");
		cache.append(LikeLapis);
		cache.append(",");
		cache.append(AllBiome);
		cache.append(",");
		cache.append(isSetMultiple);
		cache.append(",");
		cache.append(BaseBlock);
		cache.append(",");
		int i;
		for (i = 1; i < Biome.size(); i++) {
			cache.append(Biome.get(i - 1));
			cache.append(":");
		}
		if (Biome.size() > 0) {
			cache.append(Biome.get(i - 1));
		}
		cache.append("\n");
		try {
			bw.append(cache.toString());
		} catch (IOException e) {
		}
	}
}
