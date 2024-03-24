package me.helium9.event.impl.render;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.tileentity.TileEntityChest;

@Getter
@Setter
public class EventChestRender extends Event {

    private final TileEntityChest entity;
    private final Runnable chestRenderer;

    public EventChestRender(TileEntityChest entity, Runnable chestRenderer){
        this.entity = entity;
        this.chestRenderer = chestRenderer;
    }

    public TileEntityChest getEntity() {
        return entity;
    }

    public void drawChest() {
        this.chestRenderer.run();
    }

}
