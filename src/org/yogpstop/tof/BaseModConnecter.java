package org.yogpstop.tof;

import java.util.Random;

import net.minecraft.src.BaseMod;
import net.minecraft.src.World;

public class BaseModConnecter extends BaseMod {

	@Override
	public String getVersion() {
		return "0";
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
