package me.helium9.event.impl.packet;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.network.Packet;

@Getter
@Setter
public class EventSendPacket extends Event {

    private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
    }

}
