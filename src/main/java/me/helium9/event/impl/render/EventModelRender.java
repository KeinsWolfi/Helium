package me.helium9.event.impl.render;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.entity.EntityLivingBase;

@Getter
@Setter
public class EventModelRender extends Event {

    private final EntityLivingBase entity;
    private final Runnable modelRenderer;
    private final Runnable layerRenderer;

    public EventModelRender(EntityLivingBase entity, Runnable modelRenderer, Runnable layerRenderer) {
        this.entity = entity;
        this.modelRenderer = modelRenderer;
        this.layerRenderer = layerRenderer;
    }

    public EntityLivingBase getEntity(){
        return entity;
    }

    public void drawModel(){
        this.modelRenderer.run();
    }

    public void drawLayers(){
        this.layerRenderer.run();
    }
}
