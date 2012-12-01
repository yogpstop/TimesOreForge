package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StatCollector;

public class GuiConstantOre extends GuiScreen {
	private GuiScreen parent;
	private int ore;
	private GuiButton maxLumpsPerChunk;
	private GuiButton maxBlocksPerLump;
	private GuiButton maxHeight;
	private GuiButton Blocks;
	private GuiButton Density;
	private GuiButton Lapis;
	private GuiButton Biome;
	private GuiButton[] moveB = new GuiButton[18];

	public GuiConstantOre(GuiScreen parentA, int OreID) {
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
		Lapis.displayString = (TimesOreForge.LikeLapis[ore] == true ? "LikeLapisGenerate: true"
				: "LikeLapisGenerate: false");
		if (TimesOreForge.Height[ore] > 255) {
			TimesOreForge.Height[ore] = 255;
		}
		if (TimesOreForge.Height[ore] < 1) {
			TimesOreForge.Height[ore] = 1;
		}
		if (TimesOreForge.Blocks[ore] < 0) {
			TimesOreForge.Blocks[ore] = 0;
		}
		if (TimesOreForge.Lumps[ore] < 0) {
			TimesOreForge.Lumps[ore] = 0;
		}
		maxHeight.displayString = "maxHeight: "
				+ Integer.toString(TimesOreForge.Height[ore]);
		int blocks = TimesOreForge.Lumps[ore] * TimesOreForge.Blocks[ore];
		float density = (float) blocks / (float) TimesOreForge.Height[ore];
		int dblocks = TimesOreForge.DLumps[ore] * TimesOreForge.DBlocks[ore];
		float ddensity = (float) dblocks / (float) TimesOreForge.DHeight[ore];
		String cache2 = Float.toString(density);
		Density.displayString = "Density: "
				+ (cache2.length() > 10 ? cache2.substring(0, 9) : cache2);
		Blocks.displayString = "Blocks: " + Integer.toString(blocks);
		switch (TimesOreForge.isSetMultiple[ore]) {
		case 0:
			maxLumpsPerChunk.displayString = "maxLumpsPerChunk: "
					+ Integer.toString(TimesOreForge.Lumps[ore]);
			maxBlocksPerLump.displayString = "maxBlocksPerLump: "
					+ Integer.toString(TimesOreForge.Blocks[ore]);
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = true;
			}
			break;
		case 1:
			TimesOreForge.Blocks[ore] = TimesOreForge.DBlocks[ore];
			String cache1 = Float.toString(density / ddensity + 1);
			maxLumpsPerChunk.displayString = "GenerationCoefficient: "
					+ (cache1.length() > 7 ? cache1.substring(0, 6) : cache1);
			maxBlocksPerLump.displayString = "DensityBase";
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = false;
			}
			break;
		case 2:
			TimesOreForge.Blocks[ore] = TimesOreForge.DBlocks[ore];
			String cache3 = Float.toString((float) blocks / (float) dblocks + 1);
			maxLumpsPerChunk.displayString = "GenerationCoefficient: "
					+ (cache3.length() > 7 ? cache3.substring(0, 6) : cache3);
			maxBlocksPerLump.displayString = "BlocksBase";
			for (int i = 6; i <= 11; i++) {
				moveB[i].enabled = false;
			}
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void addButton() {
		int[] pos = { -170, -152, -138, 42, 52, 66 };
		int[] size = { 18, 14, 10, 10, 14, 18 };
		String[] moveb = { new String("<<<"), new String("<<"),
				new String("<"), new String(">"), new String(">>"),
				new String(">>>") };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				moveB[i * 6 + j] = new GuiButton(10 * i + j, this.width / 2
						+ pos[j], 30 * i + 40, size[j], 20, moveb[j]);
				controlList.add(moveB[i * 6 + j]);
			}
		}

		maxLumpsPerChunk = new GuiButton(6, this.width / 2 - 128, 40, 170, 20,
				"");
		maxLumpsPerChunk.enabled = false;
		controlList.add(maxLumpsPerChunk);

		controlList.add(new GuiButton(8, this.width / 2 + 100, 55, 80, 20,
				"Toggle"));

		maxBlocksPerLump = new GuiButton(16, this.width / 2 - 128, 70, 170, 20,
				"");
		maxBlocksPerLump.enabled = false;
		controlList.add(maxBlocksPerLump);

		maxHeight = new GuiButton(26, this.width / 2 - 128, 100, 170, 20, "");
		maxHeight.enabled = false;
		controlList.add(maxHeight);

		Lapis = new GuiButton(31, this.width / 2 - 180, 130, 170, 20, "");
		controlList.add(Lapis);

		Biome = new GuiButton(32, this.width / 2 + 10, 130, 170, 20,
		"SelectBiome");
		controlList.add(Biome);

		Density = new GuiButton(33, this.width / 2 - 180, 160, 170, 20, "");
		Density.enabled = false;
		controlList.add(Density);

		Blocks = new GuiButton(34, this.width / 2 + 10, 160, 170, 20, "");
		Blocks.enabled = false;
		controlList.add(Blocks);

		controlList.add(new GuiButton(-1, this.width / 2 - 125, 200, 250, 20,
				StatCollector.translateToLocal("gui.done")));
		controlList.add(new GuiButton(-2, this.width / 2 - 125, 225, 250, 20,
				"Reset"));
	}

	@Override
	public void drawScreen(int i, int j, float k) {
		drawDefaultBackground();
		String title = StatCollector.translateToLocal(TimesOreForge.ores[ore])
				+ " " + StatCollector.translateToLocal("options.title");
		fontRenderer.drawStringWithShadow(title,
				(this.width - fontRenderer.getStringWidth(title)) / 2, 15,
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
			TimesOreForge.Height[ore] = TimesOreForge.DHeight[ore];
			TimesOreForge.Blocks[ore] = TimesOreForge.DBlocks[ore];
			TimesOreForge.Lumps[ore] = TimesOreForge.DLumps[ore];
			TimesOreForge.LikeLapis[ore] = (ore == TimesOreForge.Lapis ? true
					: false);
			getValue();
			break;
		case 8:
			TimesOreForge.isSetMultiple[ore]++;
			if (TimesOreForge.isSetMultiple[ore] >= 3) {
				TimesOreForge.isSetMultiple[ore] = 0;
			}
			getValue();
			break;
		case 20:
			TimesOreForge.Height[ore] -= 24;
		case 21:
			TimesOreForge.Height[ore] -= 7;
		case 22:
			TimesOreForge.Height[ore] -= 1;
			getValue();
			break;
		case 25:
			TimesOreForge.Height[ore] += 24;
		case 24:
			TimesOreForge.Height[ore] += 7;
		case 23:
			TimesOreForge.Height[ore] += 1;
			getValue();
			break;
		case 10:
			TimesOreForge.Blocks[ore] -= 4;
		case 11:
			TimesOreForge.Blocks[ore] -= 3;
		case 12:
			TimesOreForge.Blocks[ore] -= 1;
			getValue();
			break;
		case 15:
			TimesOreForge.Blocks[ore] += 4;
		case 14:
			TimesOreForge.Blocks[ore] += 3;
		case 13:
			TimesOreForge.Blocks[ore] += 1;
			getValue();
			break;
		case 0:
			TimesOreForge.Lumps[ore] -= 12;
		case 1:
			TimesOreForge.Lumps[ore] -= 3;
		case 2:
			TimesOreForge.Lumps[ore]--;
			getValue();
			break;
		case 5:
			TimesOreForge.Lumps[ore] += 12;
		case 4:
			TimesOreForge.Lumps[ore] += 3;
		case 3:
			TimesOreForge.Lumps[ore]++;
			getValue();
			break;
		case 31:
			TimesOreForge.LikeLapis[ore] = (TimesOreForge.LikeLapis[ore] == true ? false
					: true);
			getValue();
			break;
		case 32:
			Minecraft.getMinecraft().displayGuiScreen(new GuiBiomeSelect(this));
			break;
		}
	}
}
