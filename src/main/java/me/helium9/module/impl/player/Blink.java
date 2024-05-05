package me.helium9.module.impl.player;

import me.helium9.event.impl.packet.EventPacket;
import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.util.ChatUtil;
import me.helium9.util.PacketUtil;
import me.helium9.util.render.world.BoxESPUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;

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

    private int packetCount = 0;
    private int tickCount = 0;

    private double playerPosX;
    private double playerPosY;
    private double playerPosZ;

    public Blink() {
        packetsToSend = new ArrayList<>();
    }

    @Override
    public void onEnable(){
        packetsToSend.clear();
        packetCount = 0;
        tickCount = 0;
        playerPosX = mc.thePlayer.posX;
        playerPosY = mc.thePlayer.posY;
        playerPosZ = mc.thePlayer.posZ;
        super.onEnable();
    }

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(packet -> {
        if(packet.isOutbound()) {
            if(packet.getPacket() instanceof C03PacketPlayer) {
                packetsToSend.add(packet.getPacket());
                packet.cancel();
                packetCount++;
                this.setDisplayName(name, String.valueOf(tickCount));
            }
        }
    });

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(event -> {
        tickCount++;
    });

    @Override
    public void onDisable(){
        for(Packet<?> packet : packetsToSend){
            PacketUtil.sendPacket(packet);
        }
        packetsToSend.clear();
        ChatUtil.addChatMsg(EnumChatFormatting.RED + "Blinked " + packetCount + " packets.");
        this.setDisplayName(name);
        packetCount = 0;
        tickCount = 0;
        super.onDisable();
    }

    @Subscribe
    private final Listener<Event3D> on3D = new Listener<>(event -> {
        BoxESPUtil.RenderBox(playerPosX, playerPosY, playerPosZ, 0.6, 1.8, 200, 0, 100, 255);
    });

}
