package me.helium9.module.impl.visual;

import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.render.EventChestRender;
import me.helium9.event.impl.render.EventEnderChestRender;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.render.world.BlockESPUtil;
import me.helium9.util.render.world.BoxESPUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(
        name = "ChestESP",
        description = "ChestESP",
        category = Category.Visual,
        excludeArray = "false"
)
public class ChestESP extends Module {

    private final RGBSetting color = new RGBSetting("Color", 255, 0, 150, 255);
    private final ModeSetting mode = new ModeSetting("Mode", "ESP", "Chams", "Both");

    public ChestESP(){
        addSettings(color,mode);
    }

    @Subscribe
    public final Listener<Event3D> on3D = new Listener<>(e -> {
        if(!mode.getCurrentMode().equals("ESP")  && !mode.getCurrentMode().equals("Both")) return;
        for(Object o : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
            if(o instanceof TileEntityEnderChest)
                BlockESPUtil.Render1x1Box(((TileEntityEnderChest) o).getPos(), color.getR(), color.getG(), color.getB(), color.getA());
            if (o instanceof TileEntityChest)
                BlockESPUtil.Render1x1Box(((TileEntityChest) o).getPos(), color.getR(), color.getG(), color.getB(), color.getA());
        }
    });

    @Subscribe
    public final Listener<EventChestRender> onChestRender = new Listener<>(e -> {
        if(!mode.getCurrentMode().equals("Chams") && !mode.getCurrentMode().equals("Both")) return;
        GL11.glPushMatrix();
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //GL11.glDisable(GL_TEXTURE_2D);
        //GL11.glColor4d((double) color.getR() /255, (double) color.getG() /255, (double) color.getB() /255, (double) color.getA() /255);

        e.drawChest();

        GL11.glDepthMask(true);
        GL11.glEnable(GL_DEPTH_TEST);
        //GL11.glColor3f(255, 255, 255);
        GL11.glEnable(GL_LIGHTING);
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glPolygonOffset(1.0f, -1000000.0f);
        GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        GL11.glPopMatrix();
    });

    @Subscribe
    public final Listener<EventEnderChestRender> onEnderChestRender = new Listener<>(e -> {
        if(!mode.getCurrentMode().equals("Chams") && !mode.getCurrentMode().equals("Both")) return;
        GL11.glPushMatrix();
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //GL11.glDisable(GL_TEXTURE_2D);
        //GL11.glColor4d((double) color.getR() /255, (double) color.getG() /255, (double) color.getB() /255, (double) color.getA() /255);

        e.drawChest();

        GL11.glDepthMask(true);
        GL11.glEnable(GL_DEPTH_TEST);
        //GL11.glColor3f(255, 255, 255);
        GL11.glEnable(GL_LIGHTING);
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glPolygonOffset(1.0f, -1000000.0f);
        GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        GL11.glPopMatrix();
    });
}
