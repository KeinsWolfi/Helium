package me.helium9.module.impl.motion;

import me.helium9.event.impl.render.EventCamera;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.ChatUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Sprint",
        description = "Test",
        category = Category.Motion,
        excludeArray = "false"
)
public final class Sprint extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Omni");

    public Sprint(){
        addSettings(mode);
        setKey(Keyboard.KEY_R);
    }

    @Override
    public void onEnable(){
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        switch (mode.getCurrentMode()){
            case "Vanilla":
                mc.gameSettings.keyBindSprint.setPressed(true);
                break;
            case "Omni":
                break;
        }
    });
}
