package me.helium9.module.impl.motion;

import me.helium9.event.impl.packet.EventPacket;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@ModuleInfo(
        name = "Velocity",
        description = "Modifies your velocity",
        category = Category.Motion,
        excludeArray = "false"

)
public class Velocity extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet");
    private final DoubleSetting horizontal = new DoubleSetting("Horizontal", 90, 0, 100, 1);
    private final DoubleSetting vertical = new DoubleSetting("Vertical", 100, 0, 100, 1);

    public Velocity() {
        addSettings(mode, horizontal, vertical);
    }

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(e -> {
        if(!mode.getCurrentMode().equals("Packet")) return;

        final Packet<?> p = e.getPacket();


        if (p instanceof S12PacketEntityVelocity){

            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;

            if(packet.getEntityID() == mc.thePlayer.getEntityId()){
                float modHorizontal = (float) (horizontal.getVal() / 100);
                float modVertical = (float) (vertical.getVal() / 100);

                if(modHorizontal == 0 && modVertical == 0) {
                    e.cancel();
                    return;
                }

                packet.motionX *= modHorizontal;
                packet.motionY *= modVertical;
                packet.motionZ *= modHorizontal;

                e.setPacket(packet);
            }
        }

    });

}
