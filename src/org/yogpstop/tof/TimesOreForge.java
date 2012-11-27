package org.yogpstop.tof;

import java.util.Random;
import java.util.logging.Level;

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
	public static WorldGenMinable[] WgenM;

	public static int RmaxLumps;
	public static int RmaxHeight;
	public static int RmaxBlocks;
	public static boolean RLikeLapis;

	public static int LmaxLumps;
	public static int LmaxHeight;
	public static int LmaxBlocks;
	public static boolean LLikeLapis;

	public static int ImaxLumps;
	public static int ImaxHeight;
	public static int ImaxBlocks;
	public static boolean ILikeLapis;

	public static int GmaxLumps;
	public static int GmaxHeight;
	public static int GmaxBlocks;
	public static boolean GLikeLapis;

	public static int DmaxLumps;
	public static int DmaxHeight;
	public static int DmaxBlocks;
	public static boolean DLikeLapis;

	public static int CmaxLumps;
	public static int CmaxHeight;
	public static int CmaxBlocks;
	public static boolean CLikeLapis;

	public static int EmaxLumps;
	public static int EmaxHeight;
	public static int EmaxBlocks;
	public static boolean ELikeLapis;
	public static boolean ELikeDefault;

	public static int[] anyotheroreLumps;
	public static int[] anyotheroreHeight;
	public static int[] anyotheroreBlocks;
	public static int[] anyotheroreBlockID = new int[1];
	public static int[] anyotheroreMeta;
	public static boolean[] anyotheroreLikeLapis;

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

			Property IL = conf.get("2Iron", "maxLumpsPerChunk", 20);
			Property IH = conf.get("2Iron", "maxHeight", 64);
			Property IB = conf.get("2Iron", "maxBlocksPerLump", 8);
			Property I2 = conf.get("2Iron", "LikeLapisGenerate", false);

			Property GL = conf.get("3Gold", "maxLumpsPerChunk", 2);
			Property GH = conf.get("3Gold", "maxHeight", 32);
			Property GB = conf.get("3Gold", "maxBlocksPerLump", 8);
			Property G2 = conf.get("3Gold", "LikeLapisGenerate", false);

			Property DL = conf.get("4Diamond", "maxLumpsPerChunk", 1);
			Property DH = conf.get("4Diamond", "maxHeight", 16);
			Property DB = conf.get("4Diamond", "maxBlocksPerLump", 7);
			Property D2 = conf.get("4Diamond", "LikeLapisGenerate", false);

			Property EL = conf.get("5Emerald", "maxLumpsPerChunk", 6);
			Property EH = conf.get("5Emerald", "maxHeight", 32);
			Property EB = conf.get("5Emerald", "maxBlocksPerLump", 1);
			Property E2 = conf.get("5Emerald", "LikeLapisGenerate", false);
			Property ED = conf.get("5Emerald", "LikeDefaultOre", false);

			Property RL = conf.get("6RedStone", "maxLumpsPerChunk", 8);
			Property RH = conf.get("6RedStone", "maxHeight", 16);
			Property RB = conf.get("6RedStone", "maxBlocksPerLump", 7);
			Property R2 = conf.get("6Redstone", "LikeLapisGenerate", false);

			Property LL = conf.get("7LapisLazuli", "maxLumpsPerChunk", 1);
			Property LH = conf.get("7LapisLazuli", "maxHeight", 32);
			Property LB = conf.get("7LapisLazuli", "maxBlocksPerLump", 6);
			Property L2 = conf.get("7LapisLazuli", "LikeLapisGenerate", true);

			Property OL = conf.get("8other", "maxLumpsPerChunk", "0");
			Property OH = conf.get("8other", "maxHeight", "0");
			Property OB = conf.get("8other", "maxBlocksPerLump", "0");
			Property OI = conf.get("8other", "ListOfBlockID", "0");
			Property OM = conf.get("8other", "ListOfMetaData", "0");
			Property O2 = conf.get("8other", "LikeLapisGenerate", "false");

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

			CmaxLumps = CL.getInt(20);
			CmaxHeight = CH.getInt(128);
			CmaxBlocks = CB.getInt(16);
			CLikeLapis = C2.getBoolean(false);
			ImaxLumps = IL.getInt(20);
			ImaxHeight = IH.getInt(64);
			ImaxBlocks = IB.getInt(8);
			ILikeLapis = I2.getBoolean(false);
			GmaxLumps = GL.getInt(2);
			GmaxHeight = GH.getInt(32);
			GmaxBlocks = GB.getInt(8);
			GLikeLapis = G2.getBoolean(false);
			DmaxLumps = DL.getInt(1);
			DmaxHeight = DH.getInt(16);
			DmaxBlocks = DB.getInt(7);
			DLikeLapis = D2.getBoolean(false);
			EmaxLumps = EL.getInt(6);
			EmaxHeight = EH.getInt(32);
			EmaxBlocks = EB.getInt(1);
			ELikeLapis = E2.getBoolean(false);
			ELikeDefault = ED.getBoolean(false);
			RmaxLumps = RL.getInt(8);
			RmaxHeight = RH.getInt(16);
			RmaxBlocks = RB.getInt(7);
			RLikeLapis = R2.getBoolean(false);
			LmaxLumps = LL.getInt(1);
			LmaxHeight = LH.getInt(32);
			LmaxBlocks = LB.getInt(6);
			LLikeLapis = L2.getBoolean(true);
			anyotheroreLumps = parseComma(OL.value);
			anyotheroreHeight = parseComma(OH.value);
			anyotheroreBlocks = parseComma(OB.value);
			anyotheroreBlockID = parseComma(OI.value);
			anyotheroreMeta = parseComma(OM.value);
			anyotheroreLikeLapis = parseCommaBool(O2.value);
			if (anyotheroreHeight.length != anyotheroreBlockID.length
					|| anyotheroreBlocks.length != anyotheroreBlockID.length
					|| anyotheroreMeta.length != anyotheroreBlockID.length
					|| anyotheroreLumps.length != anyotheroreBlockID.length
					|| anyotheroreLikeLapis.length != anyotheroreBlockID.length) {
				FMLLog.log(Level.SEVERE, "Config file is wrongly!");
				anyotheroreLumps = new int[1];
				anyotheroreHeight = new int[1];
				anyotheroreBlocks = new int[1];
				anyotheroreBlockID = new int[1];
				anyotheroreMeta = new int[1];
				anyotheroreLikeLapis = new boolean[1];
				anyotheroreLikeLapis[0] = false;
			}
		} finally {
			conf.save();
		}
		GameRegistry.registerWorldGenerator(this);
	}

	@Mod.Init
	public void load(FMLInitializationEvent event) {
		WgenM = new WorldGenMinable[anyotheroreBlockID.length + 7];
		set(net.minecraft.src.Block.oreCoal.blockID, 0, CmaxBlocks, 0);
		set(net.minecraft.src.Block.oreIron.blockID, 0, ImaxBlocks, 1);
		set(net.minecraft.src.Block.oreGold.blockID, 0, GmaxBlocks, 2);
		set(net.minecraft.src.Block.oreDiamond.blockID, 0, DmaxBlocks, 3);
		set(net.minecraft.src.Block.oreLapis.blockID, 0, LmaxBlocks, 4);
		set(net.minecraft.src.Block.oreRedstone.blockID, 0, RmaxBlocks, 5);
		set(net.minecraft.src.Block.oreEmerald.blockID, 0, EmaxBlocks, 6);
		parseSet();
	}

	private void parseSet() {
		for (int c = 0; c < anyotheroreBlockID.length; c++) {
			set(anyotheroreBlockID[c], anyotheroreMeta[c],
					anyotheroreBlocks[c], c + 7);
		}
	}

	private void set(int BlockID, int Meta, int maxBlocks, int count) {
		WgenM[count] = new WorldGenMinable(BlockID, Meta, maxBlocks);
	}

	private static void parseGen(World world, Random random, int i, int j) {
		for (int c = 0; c < anyotheroreBlockID.length; c++) {
			generateMinable(world, random, i, j, anyotheroreLumps[c],
					anyotheroreHeight[c], c + 7, anyotheroreLikeLapis[c]);
		}
	}

	private static void generateMinable(World world, Random random, int i,
			int j, int maxLumps, int maxHeight, int count, boolean twocount) {
		for (int l = 0; l < maxLumps; l++) {
			int ia = i * 16 + random.nextInt(16);
			int ja;
			if (twocount) {
				int cache;
				if ((maxHeight % 2) == 1) {
					cache = (maxHeight - 1) / 2;
				} else {
					cache = maxHeight / 2;
				}
				ja = random.nextInt(cache) + random.nextInt(cache);
			} else {
				ja = random.nextInt(maxHeight);
			}
			int ka = j * 16 + random.nextInt(16);
			WgenM[count].generate(world, random, ia, ja, ka);
		}
	}

	private int[] parseComma(String source) {
		String[] scache = source.split(",");
		int[] cache = new int[scache.length];
		for (int i = 0; i < scache.length; i++) {
			scache[i] = scache[i].replaceAll(" ", "");
			cache[i] = Integer.parseInt(scache[i]);
		}
		return cache;
	}

	private boolean[] parseCommaBool(String source) {
		String[] scache = source.split(",");
		boolean[] cache = new boolean[scache.length];
		for (int i = 0; i < scache.length; i++) {
			scache[i] = scache[i].replaceAll(" ", "");
			cache[i] = Boolean.getBoolean(scache[i]);
		}
		return cache;
	}

	@Override
	public void generate(Random random, int i, int j, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		generateMinable(world, random, i, j, CmaxLumps, CmaxHeight, 0,
				CLikeLapis);
		generateMinable(world, random, i, j, ImaxLumps, ImaxHeight, 1,
				ILikeLapis);
		generateMinable(world, random, i, j, GmaxLumps, GmaxHeight, 2,
				GLikeLapis);
		generateMinable(world, random, i, j, DmaxLumps, DmaxHeight, 3,
				DLikeLapis);
		generateMinable(world, random, i, j, LmaxLumps, LmaxHeight, 4,
				LLikeLapis);
		generateMinable(world, random, i, j, RmaxLumps, RmaxHeight, 5,
				RLikeLapis);
		parseGen(world, random, i, j);
		if (ELikeDefault) {
			generateMinable(world, random, i, j, EmaxLumps, EmaxHeight, 6,
					ELikeLapis);
		} else {
			String biomename = world.getWorldChunkManager().getBiomeGenAt(
					i * 16 + 16, j * 16 + 16).biomeName;
			if (biomename == null) {
				FMLLog.log(Level.WARNING, "BiomeName is null!");
			} else {
				if (biomename.contains("Extreme Hills")
						|| biomename.contains("Extreme Hills Edge")) {
					generateMinable(world, random, i, j, EmaxLumps, EmaxHeight,
							6, ELikeLapis);
				}
			}
		}
	}
}
