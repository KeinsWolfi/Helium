package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventMotion;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.player.MovementUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "Strafe",
        description = "Strafe",
        category = Category.Motion,
        excludeArray = "false"
)
public class Strafe extends Module {


    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Ground");

    public Strafe() {
    }

    @Subscribe
    public final Listener<EventMotion> onMotion = new Listener<>(e -> {
        switch (mode.getCurrentMode()){
            case "Vanilla":
                MovementUtil.strafe();
                break;
        }
    });
}
