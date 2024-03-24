package me.helium9.module.impl.motion;

import me.helium9.event.impl.packet.EventPacket;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

@ModuleInfo(
        name = "NoFall",
        description = "Makes fall down edge not ouch",
        category = Category.Motion,
        excludeArray = "false"
)
public class NoFall extends Module {

    public NoFall(){

    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        if(mc.thePlayer.fallDistance>3 && e.isPre()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    });
}
