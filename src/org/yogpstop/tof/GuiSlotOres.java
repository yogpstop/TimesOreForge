package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

public class GuiSlotOres extends GuiSlot {
    private GuiSetting parent;
    public int currentore = 0;

    public GuiSlotOres(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6, GuiSetting parents) {
        super(par1Minecraft, par2, par3, par4, par5, par6);

        parent = parents;
    }

    @Override
    protected int getSize() {
        return TimesOreForge.setting.size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2) {
        currentore = var1;
    }

    @Override
    protected boolean isSelected(int var1) {
        return var1 == currentore;
    }

    @Override
    protected void drawBackground() {
        this.parent.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        String name = TimesOreForge.getname(TimesOreForge.setting.get(var1).blockID, TimesOreForge.setting.get(var1).meta);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(name,
                (this.parent.width * 3 / 5 - Minecraft.getMinecraft().fontRenderer.getStringWidth(name)) / 2, var3 + 1, 0xFFFFFF);
    }

}
