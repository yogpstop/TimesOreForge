package org.yogpstop.tof;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiYesNoOnAdd extends GuiScreen {
    private String message1;
    private String message2;

    private String buttonText1;
    private String buttonText2;

    private int oreId;
    private int meta;

    public GuiYesNoOnAdd(String par2Str, String par3Str, int par4, int par5) {
        this.message1 = par2Str;
        this.message2 = par3Str;
        StringTranslate var5 = StringTranslate.getInstance();
        this.buttonText1 = var5.translateKey("gui.yes");
        this.buttonText2 = var5.translateKey("gui.no");
        oreId = par4;
        meta = par5;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.add(new GuiSmallButton(0, this.width / 2 - 155, this.height / 6 + 96, this.buttonText1));
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.buttonText2));
    }

    protected void actionPerformed(GuiButton par1) {
        if (par1.id == 0) {
            TimesOreForge.setting.add(new SettingObject((short) oreId, meta));
        }
        Minecraft.getMinecraft().displayGuiScreen(new GuiSetting());
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 16777215);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
