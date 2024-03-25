package me.helium9.event.impl.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCamera extends Event3D{
    public EventCamera(float partialTicks) {
        super(partialTicks);
    }
}
