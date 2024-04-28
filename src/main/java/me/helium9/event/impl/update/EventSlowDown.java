package me.helium9.event.impl.update;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.entity.Entity;

@Getter
@Setter
public class EventSlowDown extends Event {

    public enum type {
        COBWEB,
        USE,
        SOUL_SAND
    };
    private type type;
    private Entity entity;

    public EventSlowDown(type type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }

}
