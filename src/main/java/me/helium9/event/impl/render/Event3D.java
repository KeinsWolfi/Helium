package me.helium9.event.impl.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;

@Getter
@Setter
@AllArgsConstructor
public class Event3D extends Event {
    private float partialTicks;
}
