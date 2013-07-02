package org.yogpstop.tof;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
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
			wGenM = new WorldGenMinable(blockID, meta, blocks, Block.stone.blockID);
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
		wGenM = new WorldGenMinable(blockID, meta, blocks, Block.stone.blockID);
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
			if (cachebiome.length <= 1 && cachebiome[0].equals("")) return;
			for (int i = 0; i < cachebiome.length; i++) {
				biomes.add(Integer.valueOf(cachebiome[i]));
			}
		}
		wGenM = new WorldGenMinable(Integer.valueOf(cache[3]), Integer.valueOf(cache[4]), Integer.valueOf(cache[2]), Block.stone.blockID);
	}

	public void save(BufferedWriter bw) {
		try {
			bw.append(Integer.toString(lumps));
			bw.append(",");
			bw.append(Integer.toString(height));
			bw.append(",");
			bw.append(Integer.toString(blocks));
			bw.append(",");
			bw.append(Short.toString(blockID));
			bw.append(",");
			bw.append(Integer.toString(meta));
			bw.append(",");
			bw.append(Boolean.toString(likeLapis));
			bw.append(",");
			bw.append(Boolean.toString(allBiome));
			bw.append(",");
			bw.append(Byte.toString(isSetMultiple));
			bw.append(",");
			bw.append(Short.toString(baseBlock));
			if (!biomes.isEmpty()) {
				bw.append(",");
				for (int i = 1; i < biomes.size(); i++) {
					bw.append(biomes.get(i - 1).toString());
					bw.append(":");
				}
				bw.append(biomes.get(biomes.size()).toString());
			}
			bw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate(World w, Random r, int cx, int cz) {
		for (int l = 0; l < lumps; l++) {
			int x = cx * 16 + r.nextInt(16);
			int y;
			if (likeLapis) {
				int cache = height / 2;
				y = r.nextInt(cache) + r.nextInt(cache);
			} else {
				y = r.nextInt(height);
			}
			int z = cz * 16 + r.nextInt(16);
			System.out.println(allBiome);
			if (allBiome || biomes.contains(w.getBiomeGenForCoords(x, z).biomeID)) wGenM.generate(w, r, x, y, z);
		}
	}
}
