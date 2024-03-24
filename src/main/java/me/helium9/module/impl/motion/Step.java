package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.util.EnumChatFormatting;

@ModuleInfo(
        name = "Step",
        description = "Lets your player step higher.",
        category = Category.Motion,
        excludeArray = "false"
)
public class Step extends Module {

    private final DoubleSetting stepHeight = new DoubleSetting("Height", 1, 0.5, 10, 0.5);

    public Step(){
        addSettings(stepHeight);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight=0.6f;
        super.onDisable();
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        this.setDisplayName(this.name + EnumChatFormatting.GRAY + " " + stepHeight.getVal());
        mc.thePlayer.stepHeight = (float) stepHeight.getVal();
    });

}
