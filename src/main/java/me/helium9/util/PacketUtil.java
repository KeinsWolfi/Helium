package me.helium9.util;

import me.helium9.HeliumMain;
import net.minecraft.network.Packet;

public class PacketUtil {

    public static void sendPacket(Packet<?> packet){
        HeliumMain.INSTANCE.getMc().getNetHandler().getNetworkManager().sendPacket(packet);
    }

}
