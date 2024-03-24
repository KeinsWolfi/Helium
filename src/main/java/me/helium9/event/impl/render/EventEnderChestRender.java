package me.helium9.event.impl.render;

import lombok.Getter;
import lombok.Setter;
import me.helium9.event.Event;
import net.minecraft.tileentity.TileEntityEnderChest;

@Getter
@Setter
public class EventEnderChestRender extends Event {
    private final TileEntityEnderChest entity;
    private final Runnable chestRenderer;

    public EventEnderChestRender(TileEntityEnderChest entity, Runnable chestRenderer){
        this.entity = entity;
        this.chestRenderer = chestRenderer;
    }

    public TileEntityEnderChest getEntity() {
        return entity;
    }

    public void drawChest() {
        this.chestRenderer.run();
    }
}
