package me.helium9.event.impl.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventLook {

    private float yaw, pitch;

    public EventLook(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

}
