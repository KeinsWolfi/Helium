package me.helium9.module.impl.combat;

import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;

@ModuleInfo(
        name = "Reach",
        description = "Allows you to reach further",
        category = Category.Combat,
        excludeArray = "false"
)
public class Reach extends Module {

    public final DoubleSetting reach = new DoubleSetting("Reach", 3.1, 3.0, 6.0, 0.01);

    public Reach(){
        addSettings(reach);
    }

}
