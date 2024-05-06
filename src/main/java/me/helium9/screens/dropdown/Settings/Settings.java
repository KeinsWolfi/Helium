package me.helium9.screens.dropdown.Settings;

import me.helium9.settings.Setting;

public class Settings {

    public int x, y, width, height;

    public SettingsBase base;

    public Settings(SettingsBase base, int x, int y, int width, int height, int offset, Setting setting){
        this.base = base;
        this.x = base.x;
        this.y = base.y + offset;
        this.width = base.width;
        this.height = base.height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

}
