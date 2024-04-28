package me.helium9.module.impl.motion;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.event.impl.update.EventSlowDown;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.util.EnumChatFormatting;

@ModuleInfo(
        name = "NoSlow",
        description = "NoSlow",
        category = Category.Motion,
        excludeArray = "false"
)
public class NoSlow extends Module {

    private final BooleanSetting cobweb = new BooleanSetting("Cobweb", true);
    public static final BooleanSetting use = new BooleanSetting("Use", true);
    private final BooleanSetting soulSand = new BooleanSetting("SoulSand", true);

    public NoSlow(){
        this.addSettings(cobweb, use, soulSand);
        this.setDisplayName(this.name);
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(e -> {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name + EnumChatFormatting.GRAY);
        if(mc.thePlayer.isInWeb()){
            if(cobweb.isState()){
                stringBuilder.append("[WEB]");
            }
        }
        if (mc.thePlayer.isUsingItem()) {
            if(use.isState()){
                stringBuilder.append("[USE]");
            }
        }
        this.setDisplayName(stringBuilder.toString());
    });

    @Subscribe
    private final Listener<EventSlowDown> onSlowDown = new Listener<>(e -> {
        if(e.getEntity() != mc.thePlayer) return;
        if(e.getType().equals(EventSlowDown.type.COBWEB) && cobweb.isState()){
            e.cancel();
        }
        if(e.getType().equals(EventSlowDown.type.USE) && use.isState()){
            e.cancel();
        }
        if(e.getType().equals(EventSlowDown.type.SOUL_SAND) && soulSand.isState()){
            e.cancel();
        }
    });
}
