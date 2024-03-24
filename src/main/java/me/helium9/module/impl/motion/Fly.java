package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo(
        name = "Fly",
        description = "Fly???",
        category = Category.Motion,
        excludeArray = "false"
)
public class Fly extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Motion");

    public Fly(){
        addSettings(mode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(mode.getCurrentMode().equals("Vanilla")){
            mc.thePlayer.capabilities.allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.capabilities.allowFlying = false;
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        EntityPlayerSP player = mc.thePlayer;
        switch (mode.getCurrentMode()){
            case "Vanilla":
                mc.thePlayer.capabilities.allowFlying = true;
                break;
            case "Motion":
                if(mc.gameSettings.keyBindJump.isKeyDown()){
                    player.setVelocity(player.motionX, 1, player.motionZ);
                }
                if(mc.gameSettings.keyBindLeft.isKeyDown()){
                    player.setVelocity(player.motionX, player.motionY+1, player.motionZ);
                }
        }
    });
}
