package org.yogpstop.tof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;


import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "TimesOreForge", name = "TimesOreForge", version = "@VERSION@")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class TimesOreForge implements IWorldGenerator {
	public static ArrayList<WorldGenMinable> WgenM;

	@SidedProxy(clientSide = "org.yogpstop.tof.ClientProxy", serverSide = "org.yogpstop.tof.CommonProxy")
	public static CommonProxy proxy;

	public static ArrayList<SettingObject> setting;
	public static File settingF;

	public static final HashMap<Integer, Integer> DLumps = new HashMap<Integer, Integer>();
	public static final HashMap<Integer, Integer> DHeight = new HashMap<Integer, Integer>();
	public static final HashMap<Integer, Integer> DBlocks = new HashMap<Integer, Integer>();
	public static final int[] DBlockID = { Block.oreCoal.blockID,
			Block.oreIron.blockID, Block.oreGold.blockID,
			Block.oreDiamond.blockID, Block.oreLapis.blockID,
			Block.oreRedstone.blockID, Block.oreEmerald.blockID,
			Block.dirt.blockID, Block.gravel.blockID };

	@Mod.PreInit
	public void preload(FMLPreInitializationEvent event) {
		DLumps.put(Block.dirt.blockID, 20);
		DHeight.put(Block.dirt.blockID, 128);
		DBlocks.put(Block.dirt.blockID, 32);

		DLumps.put(Block.gravel.blockID, 10);
		DHeight.put(Block.gravel.blockID, 128);
		DBlocks.put(Block.gravel.blockID, 32);

		DLumps.put(Block.oreCoal.blockID, 20);
		DHeight.put(Block.oreCoal.blockID, 128);
		DBlocks.put(Block.oreCoal.blockID, 16);

		DLumps.put(Block.oreDiamond.blockID, 1);
		DHeight.put(Block.oreDiamond.blockID, 16);
		DBlocks.put(Block.oreDiamond.blockID, 7);

		DLumps.put(Block.oreEmerald.blockID, 6);
		DHeight.put(Block.oreEmerald.blockID, 32);
		DBlocks.put(Block.oreEmerald.blockID, 1);

		DLumps.put(Block.oreGold.blockID, 2);
		DHeight.put(Block.oreGold.blockID, 32);
		DBlocks.put(Block.oreGold.blockID, 8);

		DLumps.put(Block.oreIron.blockID, 20);
		DHeight.put(Block.oreIron.blockID, 64);
		DBlocks.put(Block.oreIron.blockID, 8);

		DLumps.put(Block.oreLapis.blockID, 1);
		DHeight.put(Block.oreLapis.blockID, 32);
		DBlocks.put(Block.oreLapis.blockID, 6);

		DLumps.put(Block.oreRedstone.blockID, 8);
		DHeight.put(Block.oreRedstone.blockID, 16);
		DBlocks.put(Block.oreRedstone.blockID, 7);
		settingF = event.getSuggestedConfigurationFile();
		setting = new ArrayList<SettingObject>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingF));
			String line;
			while ((line = br.readLine()) != null) {
				if (line != null && line != "")
					setting.add(new SettingObject(line));
			}
			br.close();
		} catch (IOException e) {
			save();
		}
		LanguageRegistry.instance().loadLocalization(
				"/org/yogpstop/tof/lang/en_US.lang", "en_US", false);
		LanguageRegistry.instance().loadLocalization(
				"/org/yogpstop/tof/lang/ja_JP.lang", "ja_JP", false);
		GameRegistry.registerWorldGenerator(this);
	}

	@Mod.Init
	public void load(FMLInitializationEvent event) {
		parseSet();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event) {
		proxy.setKeyHandler();
	}

	public static void parseSet() {
		WgenM = new ArrayList<WorldGenMinable>(setting.size());
		for (int c = 0; c < setting.size(); c++) {
			WgenM.add(c,
					new WorldGenMinable(setting.get(c).BlockID,
							setting.get(c).Meta, setting.get(c).Blocks));
		}
	}

	private void genM(World world, Random random, int x, int z, int c) {
		for (int l = 0; l < setting.get(c).Lumps; l++) {
			int xa = x * 16 + random.nextInt(16);
			int ya;
			if (setting.get(c).LikeLapis) {
				int cache;
				if ((setting.get(c).Height % 2) == 1) {
					cache = (setting.get(c).Height - 1) / 2;
				} else {
					cache = setting.get(c).Height / 2;
				}
				ya = random.nextInt(cache) + random.nextInt(cache);
			} else {
				ya = random.nextInt(setting.get(c).Height);
			}
			int za = z * 16 + random.nextInt(16);
			if (setting.get(c).AllBiome
					|| setting.get(c).Biome.contains(world
							.getBiomeGenForCoords(xa, za).biomeID))
				WgenM.get(c).generate(world, random, xa, ya, za);
		}
	}

	@Override
	public void generate(Random random, int x, int z, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int c = 0; c < setting.size(); c++) {
			genM(world, random, x, z, c);
		}
	}

	public static void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(settingF));
			for (int i = 0; i < setting.size(); i++) {
				setting.get(i).save(bw);
			}
			bw.close();
		} catch (IOException e) {
			FMLLog.log(Level.SEVERE, "Can't save config");
		}
	}

	public static String getname(int blockid, int meta) {
		StringBuffer sb = new StringBuffer();
		sb.append(blockid);
		if (meta != 0) {
			sb.append(":");
			sb.append(meta);
		}
		sb.append("  ");
		ItemStack cache = new ItemStack(blockid, 1, meta);
		if (cache.getItem() == null) {
			sb.append(StatCollector.translateToLocal("tof.nullblock"));
		} else if (cache.getDisplayName() == null) {
			sb.append(StatCollector.translateToLocal("tof.nullname"));
		} else {
			sb.append(cache.getDisplayName());
		}
		return sb.toString();
	}
}
