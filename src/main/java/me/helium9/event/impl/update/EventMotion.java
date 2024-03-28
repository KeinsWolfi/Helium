package me.helium9.event.impl.update;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;

@Getter
@Setter
public class EventMotion extends Event {

    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public EventMotion(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public void setRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
