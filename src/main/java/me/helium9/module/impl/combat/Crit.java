package me.helium9.module.impl.combat;

import me.helium9.event.impl.update.EventAttack;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "Crit",
        description = "Automatically crits when hitting an entity",
        category = Category.Combat,
        excludeArray = "false"
)
public class Crit extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet");

    public Crit() {
        addSettings(mode);
    }

    /*
    @Subscribe
    public final Listener<EventMotion> onMotion = new Listener<>(event -> {
        switch (mode.getCurrentMode()){
            case "Packet":
                if (mc.thePlayer.onGround) {
                    for (double offset : new double[]{0.006253453, 0.002253453, 0.001253453}) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                    }
                }
        }
    });
    */

    @Subscribe
    public final Listener<EventAttack> onAttack = new Listener<>(event -> {
        switch (mode.getCurrentMode()){
            case "Packet":
                if (mc.thePlayer.onGround) {
                    for (double offset : new double[]{0.006253453, 0.002253453, 0.001253453}) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                    }
                }
        }
    });
}
