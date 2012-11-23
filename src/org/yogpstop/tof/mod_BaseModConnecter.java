package org.yogpstop.tof;

import java.util.Random;

import net.minecraft.src.BaseMod;
import net.minecraft.src.World;

public class mod_BaseModConnecter extends BaseMod {

	@Override
	public String getVersion() {
		return "VERSION";
	}

	@Override
	public void load() {

	}

	@Override
	public void generateSurface(World world, Random random, int i, int j) {
		super.generateSurface(world, random, i, j);

		mod_TimesOreForge.make(world, random, i, j);
	}

}
