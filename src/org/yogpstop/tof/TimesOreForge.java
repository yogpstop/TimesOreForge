package org.yogpstop.tof;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "TimesOreForge", name = "TimesOreForge", version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class TimesOreForge implements IWorldGenerator {
	public int EmaxLumps;
	public int EmaxHeight;
	public int EmaxBlocks;
	public boolean ELikeLapis;
	public boolean ELikeDefault;
	public WorldGenMinable WgenE;

	public int[] Lumps;
	public int[] Height;
	public int[] Blocks;
	public int[] BlockID;
	public int[] Meta;
	public boolean[] LikeLapis;
	public WorldGenMinable[] WgenM;

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

			Lumps[1] = conf.get("2Iron", "maxLumpsPerChunk", 20).getInt(20);
			Height[1] = conf.get("2Iron", "maxHeight", 64).getInt(64);
			Blocks[1] = conf.get("2Iron", "maxBlocksPerLump", 8).getInt(8);
			BlockID[1] = Block.oreIron.blockID;
			LikeLapis[1] = conf.get("2Iron", "LikeLapisGenerate", false)
					.getBoolean(false);

			Lumps[2] = conf.get("3Gold", "maxLumpsPerChunk", 2).getInt(2);
			Height[2] = conf.get("3Gold", "maxHeight", 32).getInt(32);
			Blocks[2] = conf.get("3Gold", "maxBlocksPerLump", 8).getInt(8);
			BlockID[2] = Block.oreGold.blockID;
			LikeLapis[2] = conf.get("3Gold", "LikeLapisGenerate", false)
					.getBoolean(false);

			Lumps[3] = conf.get("4Diamond", "maxLumpsPerChunk", 1).getInt(1);
			Height[3] = conf.get("4Diamond", "maxHeight", 16).getInt(16);
			Blocks[3] = conf.get("4Diamond", "maxBlocksPerLump", 7).getInt(7);
			BlockID[3] = Block.oreDiamond.blockID;
			LikeLapis[3] = conf.get("4Diamond", "LikeLapisGenerate", false)
					.getBoolean(false);

			Lumps[4] = conf.get("7LapisLazuli", "maxLumpsPerChunk", 1)
					.getInt(1);
			Height[4] = conf.get("7LapisLazuli", "maxHeight", 32).getInt(32);
			Blocks[4] = conf.get("7LapisLazuli", "maxBlocksPerLump", 6).getInt(
					6);
			BlockID[4] = Block.oreLapis.blockID;
			LikeLapis[4] = conf.get("7LapisLazuli", "LikeLapisGenerate", true)
					.getBoolean(true);

			Lumps[5] = conf.get("6RedStone", "maxLumpsPerChunk", 8).getInt(8);
			Height[5] = conf.get("6RedStone", "maxHeight", 16).getInt(16);
			Blocks[5] = conf.get("6RedStone", "maxBlocksPerLump", 7).getInt(7);
			BlockID[5] = Block.oreRedstone.blockID;
			LikeLapis[5] = conf.get("6Redstone", "LikeLapisGenerate", false)
					.getBoolean(false);

			EmaxLumps = conf.get("5Emerald", "maxLumpsPerChunk", 6).getInt(6);
			EmaxHeight = conf.get("5Emerald", "maxHeight", 32).getInt(32);
			EmaxBlocks = conf.get("5Emerald", "maxBlocksPerLump", 1).getInt(1);
			ELikeLapis = conf.get("5Emerald", "LikeLapisGenerate", false)
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
		WgenE = new WorldGenMinable(Block.oreEmerald.blockID, 0, EmaxBlocks);
		parseSet();
	}

	private void parseSet() {
		for (int c = 0; c < BlockID.length; c++) {
			WgenM[c] = new WorldGenMinable(BlockID[c], Meta[c], Blocks[c]);
		}
	}

	private void parseGen(World world, Random random, int x, int z) {
		for (int c = 0; c < BlockID.length; c++) {
			genM(world, random, x, z, c);
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

	private void generateEmerald(World world, Random random, int x, int z) {
		for (int l = 0; l < EmaxLumps; l++) {
			int xa = x * 16 + random.nextInt(16);
			int ya;
			if (ELikeLapis) {
				int cache;
				if ((EmaxHeight % 2) == 1) {
					cache = (EmaxHeight - 1) / 2;
				} else {
					cache = EmaxHeight / 2;
				}
				ya = random.nextInt(cache) + random.nextInt(cache);
			} else {
				ya = random.nextInt(EmaxHeight);
			}
			int za = z * 16 + random.nextInt(16);
			WgenE.generate(world, random, xa, ya, za);
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
			return;
		}
		Lumps = new int[6 + cLumps.length];
		Height = new int[6 + cLumps.length];
		Blocks = new int[6 + cLumps.length];
		LikeLapis = new boolean[6 + cLumps.length];
		BlockID = new int[6 + cLumps.length];
		Meta = new int[6 + cLumps.length];
		for (int i = 0; i < cLumps.length; i++) {
			cLumps[i] = cLumps[i].replaceAll(" ", "");
			cHeight[i] = cHeight[i].replaceAll(" ", "");
			cBlocks[i] = cBlocks[i].replaceAll(" ", "");
			cLikeLapis[i] = cLikeLapis[i].replaceAll(" ", "");
			cBlockID[i] = cBlockID[i].replaceAll(" ", "");
			cMetadata[i] = cMetadata[i].replaceAll(" ", "");
			Lumps[i + 6] = Integer.parseInt(cLumps[i]);
			Height[i + 6] = Integer.parseInt(cHeight[i]);
			Blocks[i + 6] = Integer.parseInt(cBlocks[i]);
			BlockID[i + 6] = Integer.parseInt(cBlockID[i]);
			Meta[i + 6] = Integer.parseInt(cMetadata[i]);
			LikeLapis[i + 6] = Boolean.valueOf(cLikeLapis[i]);
		}
		return;
	}

	@Override
	public void generate(Random random, int x, int z, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		parseGen(world, random, x, z);

		if (ELikeDefault) {
			generateEmerald(world, random, x, z);
		} else {
			String biomename = world.getWorldChunkManager().getBiomeGenAt(
					x * 16 + 8, z * 16 + 8).biomeName;
			if (biomename == null) {
				FMLLog.log(Level.WARNING, "BiomeName is null!");
			} else {
				if (biomename.contains("Hills")) {
					generateEmerald(world, random, x, z);
				}
			}
		}
	}
}
