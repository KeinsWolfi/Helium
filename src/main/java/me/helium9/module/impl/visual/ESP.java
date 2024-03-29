package me.helium9.module.impl.visual;

import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.render.Event3D;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.render.world.BoxESPUtil;
import me.helium9.util.render.world.ESPUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@ModuleInfo(
        name = "ESP",
        description = "Renders a box around Mobs",
        category = Category.Visual,
        excludeArray = "false"
)
public class ESP extends Module {

    private final RGBSetting color = new RGBSetting("Color", 255, 0, 150, 255);
    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", true);

    public ESP(){
        addSettings(color, players, mobs);
    }

    @Subscribe
    public final Listener<Event3D> on3D = new Listener<>(event -> {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != mc.thePlayer && entity instanceof EntityPlayer && players.isState()) {
                BoxESPUtil.RenderEntityBox(entity, color.getR(), color.getG(), color.getB(), color.getA());
            }
            if (entity != mc.thePlayer && entity instanceof EntityLivingBase && mobs.isState()) {
                BoxESPUtil.RenderEntityBox(entity, color.getR(), color.getG(), color.getB(), color.getA());
            }
        }
    });

    /*@Subscribe
    public final Listener<Event2D> on2D = new Listener<>(event -> {
        if(mode.getCurrentMode().equals("3D")) return;
        for (Entity entity : entityPos.keySet()) {

            Vector4f pos = entityPos.get(entity);

            float x = pos.getX(), y = pos.getY(), right = pos.getZ(), bottom = pos.getW();

            if(entity instanceof EntityLivingBase){
                EntityLivingBase renderEntity = (EntityLivingBase) entity;

                //healthbar
                float healthPercent = renderEntity.getHealth()/renderEntity.getMaxHealth();
                Color healthColor = healthPercent > 0.75 ? Color.GREEN : healthPercent > 0.5 ? Color.YELLOW : healthPercent > 0.25 ? Color.ORANGE : Color.RED;

                float height = (bottom - y) + 1;
                Gui.drawRect2(x - 3.5f, y - .5f, 2, height + 1, new Color(0, 0, 0, 180).getRGB());
                Gui.drawRect2(x - 3, y, 1, height, healthColor.getRGB());
                Gui.drawRect2(x - 3, y + (height - (height * healthPercent)), 1, height * healthPercent, healthColor.getRGB());

                GlStateManager.color(1, 1, 1, 1);

                float outlineThickness = .5f;

                Gui.drawRect2(x - .5f, y - outlineThickness, (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x - outlineThickness, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x - .5f, (bottom + 1), (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right + 1, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());


                //top
                Gui.drawRect2(x + 1, y + 1, (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x + 1, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x + 1, (bottom - outlineThickness), (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right - outlineThickness, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());

            }
        }
    });
     */

}
