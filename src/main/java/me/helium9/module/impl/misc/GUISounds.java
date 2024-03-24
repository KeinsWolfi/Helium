package me.helium9.module.impl.misc;

import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;

@ModuleInfo(
        name = "GUISounds",
        description = "Adds sounds to various GUI/Module elements",
        category = Category.Misc,
        excludeArray = "false"
)
public class GUISounds extends Module {

    public GUISounds(){

    }

    @Override
    public void onEnable() {
        super.setSounds(true);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.setSounds(false);
        super.onDisable();
    }
}
