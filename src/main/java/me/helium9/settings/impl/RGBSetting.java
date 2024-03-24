package me.helium9.settings.impl;

import lombok.Getter;
import lombok.Setter;
import me.helium9.settings.Setting;

@Setter
@Getter
public class RGBSetting extends Setting {
    private int r,g,b,a;

    public RGBSetting(String name, int r, int g, int b, int a){
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
