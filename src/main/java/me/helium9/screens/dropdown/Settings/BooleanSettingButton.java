package me.helium9.screens.dropdown.Settings;

import me.helium9.settings.Setting;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class BooleanSettingButton extends Settings{

    private BooleanSetting setting;

    public BooleanSettingButton(SettingsBase base, int x, int y, int width, int height, int offset, Setting setting) {
        super(base, x, y, width, height, offset, setting);
        this.setting = (BooleanSetting) setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        RenderUtil.rect(x, y, width, height, Color.GRAY);
        if(this.setting.isState()) {
            fr.drawString(this.setting.getName(), x + 5, y + 4, Color.green.getRGB());
        }else {
            fr.drawString(this.setting.getName(), x + 5, y + 4, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
