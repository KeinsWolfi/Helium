package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo(
        name = "Fly",
        description = "Fly???",
        category = Category.Motion,
        excludeArray = "false"
)
public class Fly extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Motion", "Vanilla");
    private final DoubleSetting speed = new DoubleSetting("Speed", 0.5, 0.1, 5, 0.1);

    public Fly(){
        addSettings(mode,speed);
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
                    player.setVelocity(player.motionX, speed.getVal(), player.motionZ);
                }
                else {
                    player.setVelocity(player.motionX, 0, player.motionZ);
                }
                if(mc.gameSettings.keyBindForward.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians) * speed.getVal();
                    double motionZ = Math.cos(yawRadians) * speed.getVal();

                    mc.thePlayer.setVelocity(motionX, mc.thePlayer.motionY, motionZ);
                }
                if(mc.gameSettings.keyBindBack.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians) * speed.getVal();
                    double motionZ = Math.cos(yawRadians) * speed.getVal();

                    mc.thePlayer.setVelocity(-motionX, mc.thePlayer.motionY, -motionZ);
                }
                if(mc.gameSettings.keyBindLeft.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians - Math.PI/2) * speed.getVal();
                    double motionZ = Math.cos(yawRadians - Math.PI/2) * speed.getVal();

                    mc.thePlayer.setVelocity(motionX, mc.thePlayer.motionY, motionZ);
                }
                if(mc.gameSettings.keyBindRight.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians + Math.PI/2) * speed.getVal();
                    double motionZ = Math.cos(yawRadians + Math.PI/2) * speed.getVal();

                    mc.thePlayer.setVelocity(motionX, mc.thePlayer.motionY, motionZ);
                }
                if(mc.gameSettings.keyBindLeft.isKeyDown() && mc.gameSettings.keyBindForward.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians - Math.PI/4) * speed.getVal();
                    double motionZ = Math.cos(yawRadians - Math.PI/4) * speed.getVal();

                    mc.thePlayer.setVelocity(motionX, mc.thePlayer.motionY, motionZ);
                }
                if(mc.gameSettings.keyBindRight.isKeyDown() && mc.gameSettings.keyBindForward.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians + Math.PI/4) * speed.getVal();
                    double motionZ = Math.cos(yawRadians + Math.PI/4) * speed.getVal();

                    mc.thePlayer.setVelocity(motionX, mc.thePlayer.motionY, motionZ);
                }
                if(mc.gameSettings.keyBindLeft.isKeyDown() && mc.gameSettings.keyBindBack.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians + Math.PI/4) * speed.getVal();
                    double motionZ = Math.cos(yawRadians + Math.PI/4) * speed.getVal();

                    mc.thePlayer.setVelocity(-motionX, mc.thePlayer.motionY, -motionZ);
                }
                if(mc.gameSettings.keyBindRight.isKeyDown() && mc.gameSettings.keyBindBack.isKeyDown()){
                    double yawRadians = Math.toRadians(mc.thePlayer.rotationYaw);

                    double motionX = -Math.sin(yawRadians - Math.PI/4) * speed.getVal();
                    double motionZ = Math.cos(yawRadians - Math.PI/4) * speed.getVal();

                    mc.thePlayer.setVelocity(-motionX, mc.thePlayer.motionY, -motionZ);
                }
                if(mc.gameSettings.keyBindSneak.isKeyDown()){
                    player.setVelocity(player.motionX, -speed.getVal(), player.motionZ);
                }
                if(!mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()){
                    mc.thePlayer.setVelocity(0, 0, 0);
                }
                break;
        }
    });
}
