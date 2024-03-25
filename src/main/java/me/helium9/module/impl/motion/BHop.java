package me.helium9.module.impl.motion;

import me.helium9.HeliumMain;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "BHop",
        description = "Bunnyhop",
        category = Category.Motion,
        excludeArray = "false"
)
public final class BHop extends Module {
    private boolean jumped = false;
    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Always", "Boost", "LowHop");
    private final DoubleSetting boostSpeed = new DoubleSetting("BoostSpeed", 2 , 2, 5, 1);

    public BHop(){
        addSettings(mode, boostSpeed);
        setKey(Keyboard.KEY_B);
    }

    @Override
    public void onEnable(){
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(mode.getCurrentMode().equals("Vanilla")){
            mc.gameSettings.keyBindJump.setPressed(false);
        }
        super.onDisable();
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e ->{
        this.setDisplayName(name + " " + EnumChatFormatting.GRAY + mode.getCurrentMode());

        switch (mode.getCurrentMode()){
            case ("Vanilla"):
                if (mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) return;
                if(!mc.thePlayer.onGround) return;
                mc.thePlayer.jump();
                break;
            case ("Always"):
                if(!mc.thePlayer.onGround) return;
                mc.thePlayer.jump();
                break;
            case ("Boost"):
                if (mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) return;
                if(!mc.thePlayer.onGround) return;
                int x;
                for(x=0; x<boostSpeed.getVal(); x++){
                    mc.thePlayer.jump();
                }
                break;
            case "LowHop":
                if (mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) return;
                if(mc.thePlayer.onGround){
                    mc.thePlayer.jump();
                    jumped = true;
                }else if(!mc.thePlayer.capabilities.isFlying && !HeliumMain.INSTANCE.getMm().getModule(Fly.class).isToggled()){
                    if(jumped) jumped = false;
                    if(mc.thePlayer.isCollidedHorizontally) return;
                    mc.thePlayer.setVelocity(mc.thePlayer.motionX, mc.thePlayer.motionY-0.1, mc.thePlayer.motionZ);
                }
                break;
        }
    });
}
