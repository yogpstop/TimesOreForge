/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.yogpstop.tof;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
		return this.blocks;
	}

	public void setBlocks(int i) {
		this.blocks = i;
		this.modified = true;
	}

	public void shiftBlocks(int i) {
		this.blocks += i;
		this.modified = true;
	}

	public void closeGui() {
		if (this.modified) {
			this.wGenM = new WorldGenMinable(this.blockID, this.meta, this.blocks, Block.stone.blockID);
			this.modified = false;
		}
	}

	public final short blockID;
	public final int meta;

	public boolean likeLapis;
	public boolean allBiome = true;
	public byte isSetMultiple = 1;
	public short baseBlock = (short) net.minecraft.block.Block.oreCoal.blockID;

	public final List<Integer> biomes = new ArrayList<Integer>();

	public SettingObject(short ABlockID, int AMeta) {
		this.blockID = ABlockID;
		this.meta = AMeta;
		byte index;
		if ((index = Default.get(this.blockID)) != -1) {
			this.height = Default.height(index);
			this.blocks = Default.blocks(index);
			this.baseBlock = this.blockID;
		}
		this.wGenM = new WorldGenMinable(this.blockID, this.meta, this.blocks, Block.stone.blockID);
	}

	public SettingObject(String Line) {
		String[] cache = Line.split(",");
		this.lumps = Integer.valueOf(cache[0]);
		this.height = Integer.valueOf(cache[1]);
		this.blocks = Integer.valueOf(cache[2]);
		this.blockID = Short.valueOf(cache[3]);
		this.meta = Integer.valueOf(cache[4]);
		this.likeLapis = Boolean.valueOf(cache[5]);
		this.allBiome = Boolean.valueOf(cache[6]);
		this.isSetMultiple = Byte.valueOf(cache[7]);
		this.baseBlock = Short.valueOf(cache[8]);
		if (cache.length == 10) {
			String[] cachebiome = cache[9].split(":");
			if (cachebiome.length <= 1 && cachebiome[0].equals("")) return;
			for (int i = 0; i < cachebiome.length; i++) {
				this.biomes.add(Integer.valueOf(cachebiome[i]));
			}
		}
		this.wGenM = new WorldGenMinable(Integer.valueOf(cache[3]), Integer.valueOf(cache[4]), Integer.valueOf(cache[2]), Block.stone.blockID);
	}

	public void save(BufferedWriter bw) {
		try {
			bw.append(Integer.toString(this.lumps));
			bw.append(",");
			bw.append(Integer.toString(this.height));
			bw.append(",");
			bw.append(Integer.toString(this.blocks));
			bw.append(",");
			bw.append(Short.toString(this.blockID));
			bw.append(",");
			bw.append(Integer.toString(this.meta));
			bw.append(",");
			bw.append(Boolean.toString(this.likeLapis));
			bw.append(",");
			bw.append(Boolean.toString(this.allBiome));
			bw.append(",");
			bw.append(Byte.toString(this.isSetMultiple));
			bw.append(",");
			bw.append(Short.toString(this.baseBlock));
			if (!this.biomes.isEmpty()) {
				bw.append(",");
				for (int i = 1; i < this.biomes.size(); i++) {
					bw.append(this.biomes.get(i - 1).toString());
					bw.append(":");
				}
				bw.append(this.biomes.get(this.biomes.size()).toString());
			}
			bw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate(World w, Random r, int cx, int cz) {
		for (int l = 0; l < this.lumps; l++) {
			int x = cx * 16 + r.nextInt(16);
			int y;
			if (this.likeLapis) {
				int cache = this.height / 2;
				y = r.nextInt(cache) + r.nextInt(cache);
			} else {
				y = r.nextInt(this.height);
			}
			int z = cz * 16 + r.nextInt(16);
			if (this.allBiome || this.biomes.contains(w.getBiomeGenForCoords(x, z).biomeID)) this.wGenM.generate(w, r, x, y, z);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SettingObject)) return false;
		SettingObject s = (SettingObject) o;
		if (s.blockID == this.blockID && s.meta == this.meta) return true;
		return false;
	}

	@Override
	public int hashCode() {
		return this.blockID | (this.meta << 16);
	}
}
