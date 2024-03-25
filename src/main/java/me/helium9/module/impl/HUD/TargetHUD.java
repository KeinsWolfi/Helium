package me.helium9.module.impl.HUD;

import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.update.EventAttack;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.render.ColorUtil;
import me.helium9.util.render.RenderUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;


@ModuleInfo(
        name = "TargetHUD",
        description = "Shows your target.",
        category = Category.HUD,
        excludeArray = "false"
)
public class TargetHUD extends Module {
    private EntityLivingBase target;
    private final ModeSetting colorMode = new ModeSetting("ColorMode", "Rainbow", "Health");

    public TargetHUD(){
        addSettings(colorMode);
    }

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        float hue = ColorUtil.getHue(5);
        GlStateManager.scale(2, 2, 1);
        ScaledResolution sr = new ScaledResolution(mc);
        //GlStateManager.scale(2, 2,1);
        if(target != null && target.getDistanceSqToEntity(mc.thePlayer) < 49 && !target.isDead) {
            String targetName = String.valueOf(target.getDisplayName().getUnformattedText());
            switch (colorMode.getCurrentMode()){
                case "Rainbow":
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+3, new Color(Color.HSBtoRGB(hue, 0.5f, 0.5f)));
                    break;
                case "Health":
                    Color healthColor;
                    if(target.getHealth()/target.getMaxHealth() > 0.8){
                        healthColor = new Color(58, 217, 26, 255);
                    } else if (target.getHealth()/target.getMaxHealth() > 0.5) {
                        healthColor = new Color(245, 228, 49, 255);
                    } else if (target.getHealth()/target.getMaxHealth() > 0.25) {
                        healthColor = new Color(242, 132, 29, 255);
                    } else {
                        healthColor = new Color(255, 0, 0, 255);
                    }
                    //Name
                    RenderUtil.outlineRect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, 1, Color.DARK_GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, healthColor);

                    //HP Bar
                    RenderUtil.outlineRect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), fr.getStringWidth(targetName)+6, 2, 1,Color.DARK_GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), fr.getStringWidth(targetName)+6, 2, Color.GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), (fr.getStringWidth(targetName)+6) * (target.getHealth()/target.getMaxHealth()), 2, healthColor);
                    break;
            }
            fr.drawString(targetName, (sr.getScaledWidth()/4) - (fr.getStringWidth(targetName) / 2), (sr.getScaledHeight()-100)/2 - (fr.FONT_HEIGHT/2), -1);
        } else {
            target = null;
        }
        GlStateManager.scale(1/2f, 1/2f,1);
    });
    @Subscribe
    public final Listener<EventAttack> onAttack = new Listener<>(e -> {
        target = e.getTargetEntity();
    });

}
