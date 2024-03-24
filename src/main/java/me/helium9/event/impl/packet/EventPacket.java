package me.helium9.event.impl.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public final class EventPacket extends Event {

    private Packet<?> packet;

}
