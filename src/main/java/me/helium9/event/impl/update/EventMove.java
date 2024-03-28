package me.helium9.event.impl.update;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;

@Getter
@Setter
public class EventMove extends Event{

    private double x, y, z;

    public EventMove(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
