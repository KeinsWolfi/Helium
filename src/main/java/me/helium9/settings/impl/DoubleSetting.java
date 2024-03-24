package me.helium9.settings.impl;

import lombok.Getter;
import lombok.Setter;
import me.helium9.settings.Setting;

@Getter
public class DoubleSetting extends Setting {
    @Setter
    private double val, maxVal, minVal, increment, defaultVal;

    public DoubleSetting(String name, double defaultVal, double minVal, double maxVal, double increment){
        this.name = name;
        this.defaultVal = defaultVal;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.increment = increment;
        this.val=defaultVal;
    }

    private double clamp(double val, double min, double max){
        return Math.min(max, Math.max(min, val));
    }
}
