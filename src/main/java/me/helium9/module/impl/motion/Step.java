package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
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
    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vulcan");

    private int ticksInAir = 0;

    public Step(){
        addSettings(stepHeight, mode);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight=0.6f;
        ticksInAir = 0;
        super.onDisable();
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        this.setDisplayName(this.name + EnumChatFormatting.GRAY + " " + stepHeight.getVal());
        if(mode.getCurrentMode().equalsIgnoreCase("vanilla")) {
            mc.thePlayer.stepHeight = (float) stepHeight.getVal();
        } else if(mode.getCurrentMode().equalsIgnoreCase("vulcan")){
            mc.thePlayer.stepHeight = 0.6f;
            if(mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWeb() && mc.thePlayer.isCollidedHorizontally){
                mc.thePlayer.motionY = 0.4f;
                if(!mc.thePlayer.onGround){
                    ticksInAir++;
                }
                if(ticksInAir >= 2){
                    mc.thePlayer.motionY = -0.25;
                    ticksInAir = 0;
                }
            }
        }
    });

}
