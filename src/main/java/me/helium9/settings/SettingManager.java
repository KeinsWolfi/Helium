package me.helium9.settings;

import me.helium9.module.Module;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;

import java.util.List;

public class SettingManager {
    public List<Setting> settingList;

    public BooleanSetting getBooleanSetting(Module mod, String settingName){
        for(Setting setting : mod.getSList()){
            if(setting instanceof BooleanSetting){
                BooleanSetting bs = (BooleanSetting) setting;

                if(bs.getName().equals(settingName)) return bs;
            }
        }
        return null;
    }

    public ModeSetting getModeSetting(Module mod, String settingName){
        for(Setting setting : mod.getSList()){
            if(setting instanceof ModeSetting){
                ModeSetting ms = (ModeSetting) setting;

                if(ms.getName().equals(settingName)) return ms;
            }
        }
        return null;
    }

    public DoubleSetting getDoubleSetting(Module mod, String settingName){
        for(Setting setting : mod.getSList()){
            if(setting instanceof DoubleSetting){
                DoubleSetting ds = (DoubleSetting) setting;

                if(ds.getName().equals(settingName)) return ds;
            }
        }
        return null;
    }

    public Setting getSetting(Module mod, String settingName){
        for(Setting setting : mod.getSList()){
            if(setting.getName().equalsIgnoreCase(settingName)) return setting;
        }
        return null;
    }

}
