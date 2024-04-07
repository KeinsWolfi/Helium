package me.helium9.module.impl.player;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;

@ModuleInfo(
        name = "FastPlace",
        description = "Allows you to place blocks faster",
        category = Category.Player,
        excludeArray = "False"
)
public class FastPlace extends Module {
    public FastPlace() {

    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
