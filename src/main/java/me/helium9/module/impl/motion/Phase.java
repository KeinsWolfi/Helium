package me.helium9.module.impl.motion;

import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "Phase",
        description = "Phase",
        category = Category.Motion,
        excludeArray = "false"
)
public class Phase extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Down");

    public Phase() {
        this.addSettings(mode);
    }

    @Override
    public void onEnable() {
        mc.thePlayer.noClip = true;
        if(mode.getCurrentMode().equals("Down")){
            double newY = mc.thePlayer.posY - 3;
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, newY, mc.thePlayer.posZ, mc.thePlayer.onGround));
            mc.thePlayer.setPosition(mc.thePlayer.posX, newY, mc.thePlayer.posZ);
            ChatUtil.addChatMsg("Down (new pos:" + (newY) + ")");
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.noClip = false;
    }
}
