package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventKeepSprint;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.zero.alpine.event.Cancellable;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "KeepSprint",
        category = Category.Motion,
        description = "Stops sprint reset after hitting",
        excludeArray = "false"
)
public class KeepSprint extends Module {

    public KeepSprint() {

    }

    @Subscribe
    private final Listener<EventKeepSprint> onKeepSprint = new Listener<>(eventKeepSprint -> {
        eventKeepSprint.cancel();
    });
}
