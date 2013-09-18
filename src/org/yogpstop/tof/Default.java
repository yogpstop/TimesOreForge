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

import net.minecraft.block.Block;

public class Default {
	private static final short[] defaultBlocks = { (short) Block.dirt.blockID, (short) Block.gravel.blockID, (short) Block.oreCoal.blockID,
			(short) Block.oreDiamond.blockID, (short) Block.oreEmerald.blockID, (short) Block.oreGold.blockID, (short) Block.oreIron.blockID,
			(short) Block.oreLapis.blockID, (short) Block.oreRedstone.blockID };

	private static final byte[] lumps = { 20, 10, 20, 1, 6, 2, 20, 1, 8 };
	private static final short[] height = { 128, 128, 128, 16, 32, 32, 64, 32, 16 };
	private static final byte[] blocks = { 32, 32, 16, 7, 1, 8, 8, 6, 7 };

	public static byte lumps(byte index) {
		return lumps[index];
	}

	public static short height(byte index) {
		return height[index];
	}

	public static byte blocks(byte index) {
		return blocks[index];
	}

	public static byte get(short blockID) {
		for (byte i = 0; i < 9; i++) {
			if (blockID == defaultBlocks[i]) return i;
		}
		return -1;
	}

	public static short id(byte index) {
		return defaultBlocks[index];
	}
}
