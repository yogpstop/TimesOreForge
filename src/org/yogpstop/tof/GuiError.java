package org.yogpstop.tof;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.StringTranslate;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiError extends GuiScreen
{
    private GuiScreen parentScreen;

    private String message1;

    private String message2;

    private String buttonText1;

    public GuiError(GuiScreen par1GuiScreen, String par2Str, String par3Str)
    {
        this.parentScreen = par1GuiScreen;
        this.message1 = par2Str;
        this.message2 = par3Str;
        StringTranslate var5 = StringTranslate.getInstance();
        this.buttonText1 = var5.translateKey("gui.done");
    }

    @SuppressWarnings("unchecked")
	public void initGui()
    {
        this.controlList.add(new GuiSmallButton(0, this.width / 2 - 75, this.height / 6 + 96, this.buttonText1));
    }

    protected void actionPerformed(GuiButton par1GuiButton)
    {
        Minecraft.getMinecraft().displayGuiScreen(parentScreen);
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 16777215);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
