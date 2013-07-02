package org.yogpstop.tof;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

public class GuiOre extends GuiScreen {
	public int ore;
	private GuiButton maxLumpsPerChunk;
	private GuiButton maxBlocksPerLump;
	private GuiButton maxHeight;
	private GuiButton blocks;
	private GuiButton density;
	private GuiButton lapis;
	private GuiButton biome;
	private GuiButton baseBlock;
	private GuiButton[] moveB = new GuiButton[18];

	public GuiOre(int OreID) {
		super();
		this.ore = OreID;
	}

	@Override
	public void initGui() {
		addButton();
		getValue();
	}

	private void getValue() {
		byte dIndex = Default.get(TimesOreForge.setting.get(this.ore).baseBlock), dBlocks = Default.blocks(dIndex);

		this.lapis.displayString = StatCollector.translateToLocal("tof.likelapis")
				+ ": "
				+ (TimesOreForge.setting.get(this.ore).likeLapis == true ? StatCollector.translateToLocal("options.on") : StatCollector
						.translateToLocal("options.off"));
		if (TimesOreForge.setting.get(this.ore).height > 255) {
			TimesOreForge.setting.get(this.ore).height = 255;
		}
		if (TimesOreForge.setting.get(this.ore).height < 1) {
			TimesOreForge.setting.get(this.ore).height = 1;
		}
		if (TimesOreForge.setting.get(this.ore).getBlocks() < 0) {
			TimesOreForge.setting.get(this.ore).setBlocks(0);
		}
		if (TimesOreForge.setting.get(this.ore).lumps < 0) {
			TimesOreForge.setting.get(this.ore).lumps = 0;
		}
		if (TimesOreForge.setting.get(this.ore).isSetMultiple == 1 || TimesOreForge.setting.get(this.ore).isSetMultiple == 2) {
			TimesOreForge.setting.get(this.ore).setBlocks(dBlocks);
		}
		this.maxHeight.displayString = StatCollector.translateToLocal("tof.maxheight") + ": " + Integer.toString(TimesOreForge.setting.get(this.ore).height);
		int blocks1 = TimesOreForge.setting.get(this.ore).lumps * TimesOreForge.setting.get(this.ore).getBlocks();
		float density1 = (float) blocks1 / (float) TimesOreForge.setting.get(this.ore).height;
		int dblocksa = 0;
		float ddensitya = 0;
		byte Index;
		if ((Index = Default.get(TimesOreForge.setting.get(this.ore).blockID)) != -1 && TimesOreForge.setting.get(this.ore).meta == 0) {
			dblocksa = Default.blocks(Index) * Default.lumps(Index);
			ddensitya = (float) dblocksa / (float) Default.height(Index);
		}
		String cache2 = Float.toString(density1);
		this.density.displayString = StatCollector.translateToLocal("tof.density") + ": " + (cache2.length() > 10 ? cache2.substring(0, 9) : cache2);
		this.blocks.displayString = StatCollector.translateToLocal("tof.blocksperchunk") + ": " + Integer.toString(blocks1);

		int dblocks = Default.lumps(dIndex) * dBlocks;
		float ddensity = (float) dblocks / (float) Default.height(dIndex);
		this.baseBlock.displayString = Block.blocksList[TimesOreForge.setting.get(this.ore).baseBlock].getLocalizedName()
				+ StatCollector.translateToLocal("tof.base");

		switch (TimesOreForge.setting.get(this.ore).isSetMultiple) {
		case 0:
			this.maxLumpsPerChunk.displayString = StatCollector.translateToLocal("tof.lumps") + ": "
					+ Integer.toString(TimesOreForge.setting.get(this.ore).lumps);
			this.maxBlocksPerLump.displayString = StatCollector.translateToLocal("tof.blocks") + ": "
					+ Integer.toString(TimesOreForge.setting.get(this.ore).getBlocks());
			for (int i = 6; i <= 11; i++) {
				this.moveB[i].enabled = true;
			}
			break;
		case 1:
			Float densityratiocache = density1 / ddensity;
			densityratiocache += ddensitya / ddensity;
			String cache1 = Float.toString(densityratiocache);
			this.maxLumpsPerChunk.displayString = StatCollector.translateToLocal("tof.ratio") + ": " + (cache1.length() > 7 ? cache1.substring(0, 6) : cache1);
			this.maxBlocksPerLump.displayString = StatCollector.translateToLocal("tof.densitybase");
			for (int i = 6; i <= 11; i++) {
				this.moveB[i].enabled = false;
			}
			break;
		case 2:
			Float blockratiocache = (float) blocks1 / (float) dblocks;
			blockratiocache += (float) dblocksa / (float) dblocks;
			String cache3 = Float.toString(blockratiocache);
			this.maxLumpsPerChunk.displayString = StatCollector.translateToLocal("tof.ratio") + ": " + (cache3.length() > 7 ? cache3.substring(0, 6) : cache3);
			this.maxBlocksPerLump.displayString = StatCollector.translateToLocal("tof.blocksbase");
			for (int i = 6; i <= 11; i++) {
				this.moveB[i].enabled = false;
			}
			break;
		}
	}

	private void addButton() {
		int[] pos = { -170, -152, -138, 22, 32, 46 };
		int[] size = { 18, 14, 10, 10, 14, 18 };
		String[] moveb = { new String("<<<"), new String("<<"), new String("<"), new String(">"), new String(">>"), new String(">>>") };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				this.moveB[i * 6 + j] = new GuiButton(10 * i + j, this.width / 2 + pos[j], 20 * i + 20, size[j], 20, moveb[j]);
				this.buttonList.add(this.moveB[i * 6 + j]);
			}
		}

		this.maxLumpsPerChunk = new GuiButton(6, this.width / 2 - 128, 20, 150, 20, "");
		this.maxLumpsPerChunk.enabled = false;
		this.buttonList.add(this.maxLumpsPerChunk);

		this.buttonList.add(new GuiButton(8, this.width / 2 + 80, 20, 100, 20, StatCollector.translateToLocal("tof.toggle")));

		this.maxBlocksPerLump = new GuiButton(16, this.width / 2 - 128, 40, 150, 20, "");
		this.maxBlocksPerLump.enabled = false;
		this.buttonList.add(this.maxBlocksPerLump);

		this.maxHeight = new GuiButton(26, this.width / 2 - 128, 60, 150, 20, "");
		this.maxHeight.enabled = false;
		this.buttonList.add(this.maxHeight);

		this.lapis = new GuiButton(31, this.width / 2 - 180, 80, 170, 20, "");
		this.buttonList.add(this.lapis);

		this.biome = new GuiButton(32, this.width / 2 + 10, 80, 170, 20, StatCollector.translateToLocal("tof.selectbiome"));
		this.buttonList.add(this.biome);

		this.density = new GuiButton(33, this.width / 2 - 180, 100, 170, 20, "");
		this.density.enabled = false;
		this.buttonList.add(this.density);

		this.blocks = new GuiButton(34, this.width / 2 + 10, 100, 170, 20, "");
		this.blocks.enabled = false;
		this.buttonList.add(this.blocks);

		this.baseBlock = new GuiButton(35, this.width / 2 + 80, 40, 100, 20, "");
		this.buttonList.add(this.baseBlock);

		this.buttonList.add(new GuiButton(-1, this.width / 2 - 125, this.height - 26, 120, 20, StatCollector.translateToLocal("gui.done")));

		this.buttonList.add(new GuiButton(-2, this.width / 2 + 5, this.height - 26, 120, 20, StatCollector.translateToLocal("generator.default")));
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		String title = TimesOreForge.getname(TimesOreForge.setting.get(this.ore).blockID, TimesOreForge.setting.get(this.ore).meta) + " "
				+ StatCollector.translateToLocal("options.title");
		this.fontRenderer.drawStringWithShadow(title, (this.width - this.fontRenderer.getStringWidth(title)) / 2, 4, 0xFFFFFF);
		this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("tof.chunkinfo"), 40, 130, 0xFFFFFF);
		this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("tof.ratioinfo"), 40, 150, 0xFFFFFF);
		this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("tof.ratioinfo2"), 40, 170, 0xFFFFFF);
		this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("tof.baseblockinfo"), 40, 190, 0xFFFFFF);
		super.drawScreen(i, j, k);
	}

	@Override
	public void actionPerformed(GuiButton par1) {
		switch (par1.id) {
		case -1:
			TimesOreForge.setting.get(this.ore).closeGui();
			Minecraft.getMinecraft().displayGuiScreen(new GuiSetting());
			break;
		case -2:
			TimesOreForge.setting.get(this.ore).allBiome = true;
			TimesOreForge.setting.get(this.ore).biomes.clear();
			if (Default.get(TimesOreForge.setting.get(this.ore).blockID) != -1) {
				TimesOreForge.setting.get(this.ore).baseBlock = TimesOreForge.setting.get(this.ore).blockID;
			}
			if (TimesOreForge.setting.get(this.ore).baseBlock == Block.oreEmerald.blockID) {
				TimesOreForge.setting.get(this.ore).allBiome = false;
				TimesOreForge.setting.get(this.ore).biomes.add(BiomeGenBase.extremeHills.biomeID);
				TimesOreForge.setting.get(this.ore).biomes.add(BiomeGenBase.extremeHillsEdge.biomeID);
			}
			byte index = Default.get(TimesOreForge.setting.get(this.ore).baseBlock);
			TimesOreForge.setting.get(this.ore).height = Default.height(index);
			TimesOreForge.setting.get(this.ore).setBlocks(Default.blocks(index));
			TimesOreForge.setting.get(this.ore).lumps = 0;
			TimesOreForge.setting.get(this.ore).likeLapis = (TimesOreForge.setting.get(this.ore).blockID == Block.oreLapis.blockID ? true : false);
			getValue();
			break;
		case 8:
			TimesOreForge.setting.get(this.ore).isSetMultiple++;
			if (TimesOreForge.setting.get(this.ore).isSetMultiple >= 3) {
				TimesOreForge.setting.get(this.ore).isSetMultiple = 0;
			}
			getValue();
			break;
		case 20:
			TimesOreForge.setting.get(this.ore).height -= 24;
		case 21:
			TimesOreForge.setting.get(this.ore).height -= 7;
		case 22:
			TimesOreForge.setting.get(this.ore).height -= 1;
			getValue();
			break;
		case 25:
			TimesOreForge.setting.get(this.ore).height += 24;
		case 24:
			TimesOreForge.setting.get(this.ore).height += 7;
		case 23:
			TimesOreForge.setting.get(this.ore).height += 1;
			getValue();
			break;
		case 10:
			TimesOreForge.setting.get(this.ore).shiftBlocks(-4);
		case 11:
			TimesOreForge.setting.get(this.ore).shiftBlocks(-3);
		case 12:
			TimesOreForge.setting.get(this.ore).shiftBlocks(-1);
			getValue();
			break;
		case 15:
			TimesOreForge.setting.get(this.ore).shiftBlocks(4);
		case 14:
			TimesOreForge.setting.get(this.ore).shiftBlocks(3);
		case 13:
			TimesOreForge.setting.get(this.ore).shiftBlocks(1);
			getValue();
			break;
		case 0:
			TimesOreForge.setting.get(this.ore).lumps -= 12;
		case 1:
			TimesOreForge.setting.get(this.ore).lumps -= 3;
		case 2:
			TimesOreForge.setting.get(this.ore).lumps--;
			getValue();
			break;
		case 5:
			TimesOreForge.setting.get(this.ore).lumps += 12;
		case 4:
			TimesOreForge.setting.get(this.ore).lumps += 3;
		case 3:
			TimesOreForge.setting.get(this.ore).lumps++;
			getValue();
			break;
		case 31:
			TimesOreForge.setting.get(this.ore).likeLapis = (TimesOreForge.setting.get(this.ore).likeLapis == true ? false : true);
			getValue();
			break;
		case 32:
			TimesOreForge.setting.get(this.ore).closeGui();
			Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(this.ore));
			break;
		case 35:
			byte i = Default.get(TimesOreForge.setting.get(this.ore).baseBlock);
			if (i == 8 || i == -1) {
				TimesOreForge.setting.get(this.ore).baseBlock = Default.id((byte) 0);
			} else {
				TimesOreForge.setting.get(this.ore).baseBlock = Default.id(++i);
			}
			getValue();
			break;
		}
	}
}
