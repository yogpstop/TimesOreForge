package org.yogpstop.tof;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class SettingObject {
	private WorldGenMinable wGenM;
	public int lumps;
	public int height;

	private int blocks;
	private boolean modified;

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int i) {
		blocks = i;
		modified = true;
	}

	public void shiftBlocks(int i) {
		blocks += i;
		modified = true;
	}

	public void closeGui() {
		if (modified) {
			wGenM = new WorldGenMinable(blockID, meta, blocks);
			modified = false;
		}
	}

	public final short blockID;
	public final int meta;

	public boolean likeLapis;
	public boolean allBiome = true;
	public byte isSetMultiple = 1;
	public short baseBlock = (short) net.minecraft.block.Block.oreCoal.blockID;

	public final ArrayList<Integer> biomes = new ArrayList<Integer>();

	public SettingObject(short ABlockID, int AMeta) {
		blockID = ABlockID;
		meta = AMeta;
		byte index;
		if ((index = Default.get(blockID)) != -1) {
			height = Default.height(index);
			blocks = Default.blocks(index);
			baseBlock = blockID;
		}
		wGenM = new WorldGenMinable(ABlockID, AMeta, blocks);
	}

	public SettingObject(String Line) {
		String[] cache = Line.split(",");
		lumps = Integer.valueOf(cache[0]);
		height = Integer.valueOf(cache[1]);
		blocks = Integer.valueOf(cache[2]);
		blockID = Short.valueOf(cache[3]);
		meta = Integer.valueOf(cache[4]);
		likeLapis = Boolean.valueOf(cache[5]);
		allBiome = Boolean.valueOf(cache[6]);
		isSetMultiple = Byte.valueOf(cache[7]);
		baseBlock = Short.valueOf(cache[8]);
		if (cache.length == 10) {
			String[] cachebiome = cache[9].split(":");
			if (cachebiome.length <= 1 && cachebiome[0].equals(""))
				return;
			for (int i = 0; i < cachebiome.length; i++) {
				biomes.add(Integer.valueOf(cachebiome[i]));
			}
		}
		wGenM = new WorldGenMinable(Integer.valueOf(cache[3]),
				Integer.valueOf(cache[4]), Integer.valueOf(cache[2]));
	}

	public void save(BufferedWriter bw) {
		StringBuffer cache = new StringBuffer();
		cache.append(lumps);
		cache.append(",");
		cache.append(height);
		cache.append(",");
		cache.append(blocks);
		cache.append(",");
		cache.append(blockID);
		cache.append(",");
		cache.append(meta);
		cache.append(",");
		cache.append(likeLapis);
		cache.append(",");
		cache.append(allBiome);
		cache.append(",");
		cache.append(isSetMultiple);
		cache.append(",");
		cache.append(baseBlock);
		cache.append(",");
		int i;
		for (i = 1; i < biomes.size(); i++) {
			cache.append(biomes.get(i - 1));
			cache.append(":");
		}
		if (biomes.size() > 0) {
			cache.append(biomes.get(i - 1));
		}
		cache.append("\n");
		try {
			bw.append(cache.toString());
		} catch (IOException e) {
		}
	}

	public void generate(World world, Random random, int x, int z) {
		for (int l = 0; l < lumps; l++) {
			int xa = x * 16 + random.nextInt(16);
			int ya;
			if (likeLapis) {
				int cache;
				if ((height % 2) == 1) {
					cache = (height - 1) / 2;
				} else {
					cache = height / 2;
				}
				ya = random.nextInt(cache) + random.nextInt(cache);
			} else {
				ya = random.nextInt(height);
			}
			int za = z * 16 + random.nextInt(16);
			if (allBiome
					|| biomes
							.contains(world.getBiomeGenForCoords(xa, za).biomeID))
				wGenM.generate(world, random, xa, ya, za);
		}
	}
}
