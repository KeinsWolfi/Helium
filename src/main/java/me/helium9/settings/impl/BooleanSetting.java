package me.helium9.settings.impl;

import lombok.Getter;
import lombok.Setter;
import me.helium9.settings.Setting;

@Getter
@Setter
public class BooleanSetting extends Setting {
    private boolean state;

    public BooleanSetting(String name, boolean state){
        this.name = name;
        this.state = state;
    }

    public boolean isEnabled(){
        return state;
    }

    public void toggle(){
        setState(!isEnabled());
    }
}
