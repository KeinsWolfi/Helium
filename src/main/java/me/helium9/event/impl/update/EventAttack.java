package me.helium9.event.impl.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.entity.EntityLivingBase;

@Getter
@Setter
@AllArgsConstructor
public class EventAttack extends Event {

    private final EntityLivingBase targetEntity;

    public EntityLivingBase getTargetEntity() {
        return targetEntity;
    }

}
