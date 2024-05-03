package me.helium9.module.impl.player;

import me.helium9.event.impl.packet.EventPacket;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.util.ChatUtil;
import me.helium9.util.PacketUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import tv.twitch.chat.Chat;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(
        name = "Blink",
        description = "Blink",
        category = Category.Player,
        excludeArray = "false"
)
public class Blink extends Module {

    private List<Packet<?>> packetsToSend;

    public Blink() {
        packetsToSend = new ArrayList<>();
    }

    @Override
    public void onEnable(){
        packetsToSend.clear();
        super.onEnable();
    }

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(packet -> {
        if(packet.isOutbound()) {
            if(packet.getPacket() instanceof C03PacketPlayer) {
                packetsToSend.add(packet.getPacket());
                ChatUtil.addChatMsg("x: " + ((C03PacketPlayer) packet.getPacket()).getPositionX() + " y: " + ((C03PacketPlayer) packet.getPacket()).getPositionY() + " z: " + ((C03PacketPlayer) packet.getPacket()).getPositionZ() + " yaw: " + ((C03PacketPlayer) packet.getPacket()).getYaw() + " pitch: " + ((C03PacketPlayer) packet.getPacket()).getPitch() + " onGround: " + ((C03PacketPlayer) packet.getPacket()).isOnGround());
                packet.cancel();
            }
        }
    });

    @Override
    public void onDisable(){
        for(Packet<?> packet : packetsToSend){
            PacketUtil.sendPacket(packet);
        }
        packetsToSend.clear();
        super.onDisable();
    }

}
