package me.helium9.module.impl.motion;

import me.helium9.event.impl.packet.EventPacket;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.ChatUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(
        name = "NoRotate",
        description = "Prevents you from rotating",
        category = Category.Motion,
        excludeArray = "false"
)
public class NoRotate extends Module {

    private float oldPitch, oldYaw;
    private boolean toRotate = false;

    private final ModeSetting mode = new ModeSetting("Mode", "PacketCancel", "RotateBack");

    public NoRotate() {
        addSettings(mode);
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(p -> {
        if(mode.getCurrentMode().equals("RotateBack") && toRotate){
            mc.thePlayer.rotationPitch = oldPitch;
            mc.thePlayer.rotationYaw = oldYaw;
            toRotate = false;
        }
    });

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(p -> {
        if(p.isInbound() && p.getPacket() instanceof S08PacketPlayerPosLook){
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) p.getPacket();
            if(mode.getCurrentMode().equals("PacketCancel")){
                if(packet.getPitch() != mc.thePlayer.rotationPitch || packet.getYaw() != mc.thePlayer.rotationYaw){
                    //ChatUtil.addChatMsg("Recieved " + p.getPacket().getClass().getName());
                    p.cancel();
                }
            }
            else if(mode.getCurrentMode().equals("RotateBack")){
                oldPitch = mc.thePlayer.prevRotationPitch;
                oldYaw = mc.thePlayer.prevRotationYaw;
                toRotate = true;
            }
        }
    });

}
