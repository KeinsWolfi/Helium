package me.helium9.module.impl.visual;

import me.helium9.HeliumMain;
import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.screens.dropdown.DropDownGui;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "ClickGUI",
        description = "Main Gui",
        category = Category.Visual,
        excludeArray = "true"
)
public class ClickGUI extends Module {

    public ClickGUI(){
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable(){
        mc.displayGuiScreen(null);
        super.onDisable();
    }

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e ->{
        mc.displayGuiScreen(HeliumMain.INSTANCE.getGui());
    });
}
