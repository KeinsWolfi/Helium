package me.helium9.module.impl.misc;

import me.helium9.HeliumMain;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import net.minecraft.client.Minecraft;

@ModuleInfo(
        name = "Panic",
        description = "Turns off all modules. !!CANNOT UNDO!!",
        category = Category.Misc,
        excludeArray = "true"
)
public class Panic extends Module {

    public Panic(){

    }

    @Override
    public void onEnable() {
        if(HeliumMain.INSTANCE.getMm().getModule(ModuleNotifications.class).isToggled())
            HeliumMain.INSTANCE.getMm().getModule(ModuleNotifications.class).toggle();
        this.toggle();
        super.onEnable();
        for(Category cat : Category.values()) {
            for (Module m : HeliumMain.INSTANCE.getMm().getModulesByCat(cat)) {
                if(m.isToggled()){
                    m.toggle();
                }
            }
        }
    }
}
