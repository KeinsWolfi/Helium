package me.helium9.screens.dropdown.Settings;

import me.helium9.module.Module;
import me.helium9.screens.dropdown.ModuleButtons;
import me.helium9.settings.Setting;
import me.helium9.settings.impl.BooleanSetting;

import java.util.List;

public class SettingsBase {

    private ModuleButtons parentButton;
    private Module parentModule;

    public int x, y, width, height;

    private List<Settings> settingsButtons;

    public SettingsBase(ModuleButtons parentButton, Module parentModule) {
        this.parentButton = parentButton;
        this.parentModule = parentModule;
        this.x = parentButton.parent.x + parentButton.parent.width;
        this.y = parentButton.parent.y + parentButton.offset + parentButton.parent.height;
        this.width = parentButton.parent.width;
        this.height = parentButton.parent.height;

        int offset = 0;
        for(Setting s : this.parentModule.getSList()){
            if(s instanceof BooleanSetting){
                settingsButtons.add(new BooleanSettingButton(this, x, y, width, height, offset, s));
                offset += height + 1;
            }
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Settings s : settingsButtons){
            s.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

}
