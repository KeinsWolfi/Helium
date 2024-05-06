package me.helium9.event.impl.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;

@Getter
@Setter
public class EventCamera extends Event {

    private float partialTicks;

    public EventCamera(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
