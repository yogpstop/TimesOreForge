package org.yogpstop.tof;

import java.util.Random;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "TimesOreForge", name = "TimesOreForge", version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class TimesOreForge implements IWorldGenerator {

	public static int bindKey = Keyboard.KEY_O;

	public boolean ELikeDefault;

	public static final int Coal = 0;
	public static final int Iron = 1;
	public static final int Gold = 2;
	public static final int Diamond = 3;
	public static final int Lapis = 4;
	public static final int Redstone = 5;
	public static final int Emerald = 6;
	public static final int Dirt = 7;
	public static final int Gravel = 8;
	public static final String[] ores = { "tile.oreCoal.name",
			"tile.oreIron.name", "tile.oreGold.name", "tile.oreDiamond.name",
			"tile.oreLapis.name", "tile.oreRedstone.name",
			"tile.oreEmerald.name", "tile.dirt.name", "tile.gravel.name" };

	public static int[] Lumps;
	public static int[] Height;
	public static int[] Blocks;
	public static int[] BlockID;
	public static int[] Meta;
	public static boolean[] LikeLapis;
	public static WorldGenMinable[] WgenM;

	public static final int[] DLumps = { 20, 20, 2, 1, 1, 8, 6, 20, 10 };
	public static final int[] DHeight = { 128, 64, 32, 16, 32, 16, 32, 128, 128 };
	public static final int[] DBlocks = { 16, 8, 8, 7, 6, 7, 1, 32, 32 };

	public static int[] isSetMultiple;

	@Mod.PreInit
	public void preload(FMLPreInitializationEvent event) {
		Configuration conf = new Configuration(
				event.getSuggestedConfigurationFile());
		try {
			conf.load();

			Property CL = conf.get("1Coal", "maxLumpsPerChunk", 20);
			Property CH = conf.get("1Coal", "maxHeight", 128);
			Property CB = conf.get("1Coal", "maxBlocksPerLump", 16);
			Property C2 = conf.get("1Coal", "LikeLapisGenerate", false);

			Property ED = conf.get("5Emerald", "LikeDefaultOre", false);

			Property OI = conf.get("8other", "ListOfBlockID", "0");
			Property OM = conf.get("8other", "ListOfMetaData", "0");

			CL.comment = "How many lumps is there generated in a chunk?";
			CH.comment = "What position of Y axis is ores generated when the highest?";
			CB.comment = "How many ores is there generated in a lump?";
			C2.comment = "Do you want to be generated ores by the same law as lapis lazuli?";
			OI.comment = "Enter any other ores's ItemID separated by comma.";
			OM.comment = "Enter any other ores's MetaData (like number after semicolon) separated by comma.";
			ED.comment = "Do you want to be generated ores without hills biome?";

			conf.addCustomCategoryComment("1Coal",
					"Author write how to configure in here. Other ores is same too.");
			conf.addCustomCategoryComment(
					"8other",
					"This category is special. How to configure is written in readme. All value must be separated by comma.");

			parseComma(conf.get("8other", "maxLumpsPerChunk", "0").value,
					conf.get("8other", "maxHeight", "0").value,
					conf.get("8other", "maxBlocksPerLump", "0").value,
					conf.get("8other", "LikeLapisGenerate", "false").value,
					OI.value, OM.value);

			Lumps[0] = CL.getInt(20);
			Height[0] = CH.getInt(128);
			Blocks[0] = CB.getInt(16);
			BlockID[0] = Block.oreCoal.blockID;
			LikeLapis[0] = C2.getBoolean(false);
			isSetMultiple[0] = conf.get("1Coal", "isSetMultiple", 0).getInt(0);

			Lumps[1] = conf.get("2Iron", "maxLumpsPerChunk", 20).getInt(20);
			Height[1] = conf.get("2Iron", "maxHeight", 64).getInt(64);
			Blocks[1] = conf.get("2Iron", "maxBlocksPerLump", 8).getInt(8);
			BlockID[1] = Block.oreIron.blockID;
			LikeLapis[1] = conf.get("2Iron", "LikeLapisGenerate", false)
					.getBoolean(false);
			isSetMultiple[1] = conf.get("2Iron", "isSetMultiple", 0).getInt(0);

			Lumps[2] = conf.get("3Gold", "maxLumpsPerChunk", 2).getInt(2);
			Height[2] = conf.get("3Gold", "maxHeight", 32).getInt(32);
			Blocks[2] = conf.get("3Gold", "maxBlocksPerLump", 8).getInt(8);
			BlockID[2] = Block.oreGold.blockID;
			LikeLapis[2] = conf.get("3Gold", "LikeLapisGenerate", false)
					.getBoolean(false);
			isSetMultiple[2] = conf.get("3Gold", "isSetMultiple", 0).getInt(0);

			Lumps[3] = conf.get("4Diamond", "maxLumpsPerChunk", 1).getInt(1);
			Height[3] = conf.get("4Diamond", "maxHeight", 16).getInt(16);
			Blocks[3] = conf.get("4Diamond", "maxBlocksPerLump", 7).getInt(7);
			BlockID[3] = Block.oreDiamond.blockID;
			LikeLapis[3] = conf.get("4Diamond", "LikeLapisGenerate", false)
					.getBoolean(false);
			isSetMultiple[3] = conf.get("4Diamond", "isSetMultiple", 0).getInt(
					0);

			Lumps[4] = conf.get("7LapisLazuli", "maxLumpsPerChunk", 1)
					.getInt(1);
			Height[4] = conf.get("7LapisLazuli", "maxHeight", 32).getInt(32);
			Blocks[4] = conf.get("7LapisLazuli", "maxBlocksPerLump", 6).getInt(
					6);
			BlockID[4] = Block.oreLapis.blockID;
			LikeLapis[4] = conf.get("7LapisLazuli", "LikeLapisGenerate", true)
					.getBoolean(true);
			isSetMultiple[4] = conf.get("7LapisLazuli", "isSetMultiple", 0)
					.getInt(0);

			Lumps[5] = conf.get("6RedStone", "maxLumpsPerChunk", 8).getInt(8);
			Height[5] = conf.get("6RedStone", "maxHeight", 16).getInt(16);
			Blocks[5] = conf.get("6RedStone", "maxBlocksPerLump", 7).getInt(7);
			BlockID[5] = Block.oreRedstone.blockID;
			LikeLapis[5] = conf.get("6Redstone", "LikeLapisGenerate", false)
					.getBoolean(false);
			isSetMultiple[5] = conf.get("6Redstone", "isSetMultiple", 0)
					.getInt(0);

			Lumps[6] = conf.get("5Emerald", "maxLumpsPerChunk", 6).getInt(6);
			Height[6] = conf.get("5Emerald", "maxHeight", 32).getInt(32);
			Blocks[6] = conf.get("5Emerald", "maxBlocksPerLump", 1).getInt(1);
			BlockID[6] = Block.oreEmerald.blockID;
			LikeLapis[6] = conf.get("5Emerald", "LikeLapisGenerate", false)
					.getBoolean(false);
			ELikeDefault = ED.getBoolean(false);
		} finally {
			conf.save();
		}
		GameRegistry.registerWorldGenerator(this);
	}

	@Mod.Init
	public void load(FMLInitializationEvent event) {
		WgenM = new WorldGenMinable[BlockID.length];
		parseSet();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event) {
		KeyBinding[] myBinding = { new KeyBinding("TimesOreForgeConfigKey",
				bindKey) };

		boolean[] myBindingRepeat = { false };

		KeyBindingRegistry.registerKeyBinding(new KeyboardHandler(myBinding,
				myBindingRepeat));
	}

	private void parseSet() {
		for (int c = 0; c < BlockID.length; c++) {
			WgenM[c] = new WorldGenMinable(BlockID[c], Meta[c], Blocks[c]);
		}
	}

	private void parseGen(World world, Random random, int x, int z) {
		for (int c = 0; c < BlockID.length; c++) {
			if (c == Emerald) {
				if (!ELikeDefault) {
					if (world.getBiomeGenForCoords(x * 16 + 8, z * 16 + 8).biomeName
							.contains("Hills")) {
						genM(world, random, x, z, c);
					}
				} else {
					genM(world, random, x, z, c);
				}
			} else {
				genM(world, random, x, z, c);
			}
		}
	}

	private void genM(World world, Random random, int x, int z, int c) {
		for (int l = 0; l < Lumps[c]; l++) {
			int xa = x * 16 + random.nextInt(16);
			int ya;
			if (LikeLapis[c]) {
				int cache;
				if ((Height[c] % 2) == 1) {
					cache = (Height[c] - 1) / 2;
				} else {
					cache = Height[c] / 2;
				}
				ya = random.nextInt(cache) + random.nextInt(cache);
			} else {
				ya = random.nextInt(Height[c]);
			}
			int za = z * 16 + random.nextInt(16);
			WgenM[c].generate(world, random, xa, ya, za);
		}
	}

	private void parseComma(String lumps, String height, String blocks,
			String likelapis, String blockid, String metadata) {
		String[] cLumps = lumps.split(",");
		String[] cHeight = height.split(",");
		String[] cBlocks = blocks.split(",");
		String[] cLikeLapis = likelapis.split(",");
		String[] cBlockID = blockid.split(",");
		String[] cMetadata = metadata.split(",");
		if ((cLumps.length != cHeight.length || cLumps.length != cBlocks.length
				|| cLumps.length != cLikeLapis.length
				|| cLumps.length != cBlockID.length || cLumps.length != cMetadata.length)
				|| (cLumps.length == 1 && cLumps[0] == "0")) {
			FMLLog.log(Level.WARNING,
					"[TimesOreForge]Setting file is wrong or not generate other ores!");
			Lumps = new int[6];
			Height = new int[6];
			Blocks = new int[6];
			LikeLapis = new boolean[6];
			BlockID = new int[6];
			Meta = new int[6];
			isSetMultiple = new int[6];
			return;
		}
		Lumps = new int[9 + cLumps.length];
		Height = new int[9 + cLumps.length];
		Blocks = new int[9 + cLumps.length];
		LikeLapis = new boolean[9 + cLumps.length];
		BlockID = new int[9 + cLumps.length];
		Meta = new int[9 + cLumps.length];
		isSetMultiple = new int[9 + cLumps.length];
		for (int i = 0; i < cLumps.length; i++) {
			cLumps[i] = cLumps[i].replaceAll(" ", "");
			cHeight[i] = cHeight[i].replaceAll(" ", "");
			cBlocks[i] = cBlocks[i].replaceAll(" ", "");
			cLikeLapis[i] = cLikeLapis[i].replaceAll(" ", "");
			cBlockID[i] = cBlockID[i].replaceAll(" ", "");
			cMetadata[i] = cMetadata[i].replaceAll(" ", "");
			Lumps[i + 9] = Integer.parseInt(cLumps[i]);
			Height[i + 9] = Integer.parseInt(cHeight[i]);
			Blocks[i + 9] = Integer.parseInt(cBlocks[i]);
			BlockID[i + 9] = Integer.parseInt(cBlockID[i]);
			Meta[i + 9] = Integer.parseInt(cMetadata[i]);
			LikeLapis[i + 9] = Boolean.valueOf(cLikeLapis[i]);
		}
		return;
	}

	@Override
	public void generate(Random random, int x, int z, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		parseGen(world, random, x, z);
	}
}
