package org.yogpstop.tof;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

public class GuiOre extends GuiScreen {
	private GuiScreen parent;
	public int ore;
	private GuiButton maxLumpsPerChunk;
	private GuiButton maxBlocksPerLump;
	private GuiButton maxHeight;
	private GuiButton Blocks;
	private GuiButton Density;
	private GuiButton Lapis;
	private GuiButton Biome;
	private GuiButton BaseBlock;
	private GuiButton[] moveB = new GuiButton[18];

	public GuiOre(GuiScreen parentA, int OreID) {
		super();
		this.parent = parentA;
		this.ore = OreID;
	}

	@Override
	public void initGui() {
		addButton();
		getValue();
	}

	private void getValue() {
		Lapis.displayString = StatCollector.translateToLocal("tof.likelapis")
				+ ": "
				+ (TimesOreForge.setting.get(ore).LikeLapis == true ? StatCollector
						.translateToLocal("options.on") : StatCollector
						.translateToLocal("options.off"));
		if (TimesOreForge.setting.get(ore).Height > 255) {
			TimesOreForge.setting.get(ore).Height = 255;
		}
		if (TimesOreForge.setting.get(ore).Height < 1) {
			TimesOreForge.setting.get(ore).Height = 1;
		}
		if (TimesOreForge.setting.get(ore).Blocks < 0) {
			TimesOreForge.setting.get(ore).Blocks = 0;
		}
		if (TimesOreForge.setting.get(ore).Lumps < 0) {
			TimesOreForge.setting.get(ore).Lumps = 0;
		}
		if (TimesOreForge.setting.get(ore).isSetMultiple == 1
				|| TimesOreForge.setting.get(ore).isSetMultiple == 2) {
			TimesOreForge.setting.get(ore).Blocks = TimesOreForge.DBlocks
					.get(TimesOreForge.setting.get(ore).BaseBlock);
		}
		maxHeight.displayString = StatCollector
				.translateToLocal("tof.maxheight")
				+ ": "
				+ Integer.toString(TimesOreForge.setting.get(ore).Height);
		int blocks = TimesOreForge.setting.get(ore).Lumps
				* TimesOreForge.setting.get(ore).Blocks;
		float density = (float) blocks
				/ (float) TimesOreForge.setting.get(ore).Height;
		int dblocksa = 0;
		float ddensitya = 0;
		if (TimesOreForge.DLumps
				.containsKey(TimesOreForge.setting.get(ore).BlockID)
				&& TimesOreForge.setting.get(ore).Meta == 0) {
			dblocksa = TimesOreForge.DBlocks
					.get(TimesOreForge.setting.get(ore).BlockID)
					* TimesOreForge.DLumps
							.get(TimesOreForge.setting.get(ore).BlockID);
			ddensitya = (float) dblocksa
					/ (float) TimesOreForge.DHeight.get(TimesOreForge.setting
							.get(ore).BlockID);
		}
		String cache2 = Float.toString(density);
		Density.displayString = StatCollector.translateToLocal("tof.density")
				+ ": "
				+ (cache2.length() > 10 ? cache2.substring(0, 9) : cache2);
		Blocks.displayString = StatCollector
				.translateToLocal("tof.blocksperchunk")
				+ ": "
				+ Integer.toString(blocks);

		int dblocks = TimesOreForge.DLumps
				.get(TimesOreForge.setting.get(ore).BaseBlock)
				* TimesOreForge.DBlocks
						.get(TimesOreForge.setting.get(ore).BaseBlock);
		float ddensity = (float) dblocks
				/ (float) TimesOreForge.DHeight.get(TimesOreForge.setting
						.get(ore).BaseBlock);
		BaseBlock.displayString = Block.blocksList[TimesOreForge.setting
				.get(ore).BaseBlock].translateBlockName()
				+ StatCollector.translateToLocal("tof.base");

		switch (TimesOreForge.setting.get(ore).isSetMultiple) {
		case 0:
			maxLumpsPerChunk.displayString = StatCollector
					.translateToLocal("tof.lumps")
					+ ": "
					+ Integer.toString(TimesOreForge.setting.get(ore).Lumps);
			maxBlocksPerLump.displayString = StatCollector
					.translateToLocal("tof.blocks")
					+ ": "
					+ Integer.toString(TimesOreForge.setting.get(ore).Blocks);
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = true;
			}
			break;
		case 1:
			Float densityratiocache = density / ddensity;
			densityratiocache += ddensitya / ddensity;
			String cache1 = Float.toString(densityratiocache);
			maxLumpsPerChunk.displayString = StatCollector
					.translateToLocal("tof.ratio")
					+ ": "
					+ (cache1.length() > 7 ? cache1.substring(0, 6) : cache1);
			maxBlocksPerLump.displayString = StatCollector
					.translateToLocal("tof.densitybase");
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = false;
			}
			break;
		case 2:
			Float blockratiocache = (float) blocks / (float) dblocks;
			blockratiocache += (float) dblocksa / (float) dblocks;
			String cache3 = Float.toString(blockratiocache);
			maxLumpsPerChunk.displayString = StatCollector
					.translateToLocal("tof.ratio")
					+ ": "
					+ (cache3.length() > 7 ? cache3.substring(0, 6) : cache3);
			maxBlocksPerLump.displayString = StatCollector
					.translateToLocal("tof.blocksbase");
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = false;
			}
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void addButton() {
		int[] pos = { -170, -152, -138, 22, 32, 46 };
		int[] size = { 18, 14, 10, 10, 14, 18 };
		String[] moveb = { new String("<<<"), new String("<<"),
				new String("<"), new String(">"), new String(">>"),
				new String(">>>") };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				moveB[i * 6 + j] = new GuiButton(10 * i + j, this.width / 2
						+ pos[j], 20 * i + 20, size[j], 20, moveb[j]);
				controlList.add(moveB[i * 6 + j]);
			}
		}

		maxLumpsPerChunk = new GuiButton(6, this.width / 2 - 128, 20, 150, 20,
				"");
		maxLumpsPerChunk.enabled = false;
		controlList.add(maxLumpsPerChunk);

		controlList.add(new GuiButton(8, this.width / 2 + 80, 20, 100, 20,
				StatCollector.translateToLocal("tof.toggle")));

		maxBlocksPerLump = new GuiButton(16, this.width / 2 - 128, 40, 150, 20,
				"");
		maxBlocksPerLump.enabled = false;
		controlList.add(maxBlocksPerLump);

		maxHeight = new GuiButton(26, this.width / 2 - 128, 60, 150, 20, "");
		maxHeight.enabled = false;
		controlList.add(maxHeight);

		Lapis = new GuiButton(31, this.width / 2 - 180, 80, 170, 20, "");
		controlList.add(Lapis);

		Biome = new GuiButton(32, this.width / 2 + 10, 80, 170, 20,
				StatCollector.translateToLocal("tof.selectbiome"));
		controlList.add(Biome);

		Density = new GuiButton(33, this.width / 2 - 180, 100, 170, 20, "");
		Density.enabled = false;
		controlList.add(Density);

		Blocks = new GuiButton(34, this.width / 2 + 10, 100, 170, 20, "");
		Blocks.enabled = false;
		controlList.add(Blocks);

		BaseBlock = new GuiButton(35, this.width / 2 + 80, 40, 100, 20, "");
		controlList.add(BaseBlock);

		controlList.add(new GuiButton(-1, this.width / 2 - 125,
				this.height - 26, 120, 20, StatCollector
						.translateToLocal("gui.done")));

		controlList.add(new GuiButton(-2, this.width / 2 + 5, this.height - 26,
				120, 20, StatCollector.translateToLocal("generator.default")));
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		String title = TimesOreForge.getname(
				TimesOreForge.setting.get(ore).BlockID,
				TimesOreForge.setting.get(ore).Meta)
				+ " " + StatCollector.translateToLocal("options.title");
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 4,
				0xFFFFFF);
		fontRenderer.drawStringWithShadow(
				StatCollector.translateToLocal("tof.chunkinfo"), 40, 130,
				0xFFFFFF);
		fontRenderer.drawStringWithShadow(
				StatCollector.translateToLocal("tof.ratioinfo"), 40, 150,
				0xFFFFFF);
		fontRenderer.drawStringWithShadow(
				StatCollector.translateToLocal("tof.ratioinfo2"), 40, 170,
				0xFFFFFF);
		fontRenderer.drawStringWithShadow(
				StatCollector.translateToLocal("tof.baseblockinfo"), 40, 190,
				0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			Minecraft.getMinecraft().displayGuiScreen(parent);
			break;
		case -2:
			TimesOreForge.setting.get(ore).AllBiome = true;
			TimesOreForge.setting.get(ore).Biome = new ArrayList<Integer>();
			if (TimesOreForge.DHeight.containsKey(TimesOreForge.setting
					.get(ore).BlockID)) {
				TimesOreForge.setting.get(ore).BaseBlock = TimesOreForge.setting
						.get(ore).BlockID;
			}
			if (TimesOreForge.setting.get(ore).BaseBlock == Block.oreEmerald.blockID) {
				TimesOreForge.setting.get(ore).AllBiome = false;
				TimesOreForge.setting.get(ore).Biome
						.add(BiomeGenBase.extremeHills.biomeID);
				TimesOreForge.setting.get(ore).Biome
						.add(BiomeGenBase.extremeHillsEdge.biomeID);
			}
			TimesOreForge.setting.get(ore).Height = TimesOreForge.DHeight
					.get(TimesOreForge.setting.get(ore).BaseBlock);
			TimesOreForge.setting.get(ore).Blocks = TimesOreForge.DBlocks
					.get(TimesOreForge.setting.get(ore).BaseBlock);
			TimesOreForge.setting.get(ore).Lumps = 0;
			TimesOreForge.setting.get(ore).LikeLapis = (TimesOreForge.setting
					.get(ore).BlockID == Block.oreLapis.blockID ? true : false);
			getValue();
			break;
		case 8:
			TimesOreForge.setting.get(ore).isSetMultiple++;
			if (TimesOreForge.setting.get(ore).isSetMultiple >= 3) {
				TimesOreForge.setting.get(ore).isSetMultiple = 0;
			}
			getValue();
			break;
		case 20:
			TimesOreForge.setting.get(ore).Height -= 24;
		case 21:
			TimesOreForge.setting.get(ore).Height -= 7;
		case 22:
			TimesOreForge.setting.get(ore).Height -= 1;
			getValue();
			break;
		case 25:
			TimesOreForge.setting.get(ore).Height += 24;
		case 24:
			TimesOreForge.setting.get(ore).Height += 7;
		case 23:
			TimesOreForge.setting.get(ore).Height += 1;
			getValue();
			break;
		case 10:
			TimesOreForge.setting.get(ore).Blocks -= 4;
		case 11:
			TimesOreForge.setting.get(ore).Blocks -= 3;
		case 12:
			TimesOreForge.setting.get(ore).Blocks -= 1;
			getValue();
			break;
		case 15:
			TimesOreForge.setting.get(ore).Blocks += 4;
		case 14:
			TimesOreForge.setting.get(ore).Blocks += 3;
		case 13:
			TimesOreForge.setting.get(ore).Blocks += 1;
			getValue();
			break;
		case 0:
			TimesOreForge.setting.get(ore).Lumps -= 12;
		case 1:
			TimesOreForge.setting.get(ore).Lumps -= 3;
		case 2:
			TimesOreForge.setting.get(ore).Lumps--;
			getValue();
			break;
		case 5:
			TimesOreForge.setting.get(ore).Lumps += 12;
		case 4:
			TimesOreForge.setting.get(ore).Lumps += 3;
		case 3:
			TimesOreForge.setting.get(ore).Lumps++;
			getValue();
			break;
		case 31:
			TimesOreForge.setting.get(ore).LikeLapis = (TimesOreForge.setting
					.get(ore).LikeLapis == true ? false : true);
			getValue();
			break;
		case 32:
			Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(this));
			break;
		case 35:
			for (int i = 0; i < 9; i++) {
				if (TimesOreForge.DBlockID[i] == TimesOreForge.setting.get(ore).BaseBlock) {
					if (i == 8) {
						TimesOreForge.setting.get(ore).BaseBlock = TimesOreForge.DBlockID[0];
						break;
					} else {
						TimesOreForge.setting.get(ore).BaseBlock = TimesOreForge.DBlockID[++i];
						break;
					}
				}
			}
			getValue();
			break;
		}
	}
}
