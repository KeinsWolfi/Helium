package me.helium9.module.impl.HUD;

import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.update.EventAttack;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.render.ColorUtil;
import me.helium9.util.render.RenderUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


@ModuleInfo(
        name = "TargetHUD",
        description = "Shows your target.",
        category = Category.HUD,
        excludeArray = "false"
)
public class TargetHUD extends Module {
    private EntityLivingBase target;
    private final ModeSetting colorMode = new ModeSetting("ColorMode", "Standard" ,"Health", "Rainbow");
    private final DoubleSetting scale = new DoubleSetting("Scale", 2, 1, 10, 1);

    public TargetHUD(){
        addSettings(colorMode, scale);
    }

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        float hue = ColorUtil.getHue(5);
        GlStateManager.scale(2, 2, 1);
        ScaledResolution sr = new ScaledResolution(mc);
        if(target != null && target.getDistanceSqToEntity(mc.thePlayer) < 100 && !target.isDead) {
            String targetName = String.valueOf(target.getDisplayName().getUnformattedText());
            Color healthColor = getHealthColor();
            float targetHealth = target.getHealth();
            switch (colorMode.getCurrentMode()){
                /*case "Rainbow":
                    RenderUtil.outlineRect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, 1, Color.DARK_GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, new Color(Color.HSBtoRGB(hue, 0.5f, 0.6f)));
                    GuiInventory.drawEntityOnScreen(50, 100, 25, 0, 0, target);
                    fr.drawString(targetName, (sr.getScaledWidth()/4) - (fr.getStringWidth(targetName) / 2), (sr.getScaledHeight()-100)/2 - (fr.FONT_HEIGHT/2), -1);
                    break;
                case "Health":
                    //Name
                    RenderUtil.outlineRect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, 1, Color.DARK_GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f - (fr.FONT_HEIGHT/2f), fr.getStringWidth(targetName)+6, fr.FONT_HEIGHT+2, healthColor);

                    //HP Bar
                    RenderUtil.outlineRect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), fr.getStringWidth(targetName)+6, 2, 1,Color.DARK_GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), fr.getStringWidth(targetName)+6, 2, Color.GRAY);
                    RenderUtil.rect((sr.getScaledWidth() / 4f) - (fr.getStringWidth(targetName) / 2f) -3, (sr.getScaledHeight()-103)/2f + (fr.FONT_HEIGHT + 2), (fr.getStringWidth(targetName)+6) * (targetHealth /target.getMaxHealth()), 2, healthColor);
                    //GuiInventory.drawEntityOnScreen(50, 100, 25, 0, 0, target);
                    fr.drawString(targetName, (sr.getScaledWidth()/4) - (fr.getStringWidth(targetName) / 2), (sr.getScaledHeight()-100)/2 - (fr.FONT_HEIGHT/2), -1);
                    break;
                */

                case "Standard":
                    RenderUtil.outlineRect((sr.getScaledWidth() / (2f * scale.getVal()) ) - 50, (sr.getScaledHeight() / (1f * scale.getVal()) - 70), 100, 50, 1, Color.DARK_GRAY);
                    RenderUtil.rect( (sr.getScaledWidth() / (2f * scale.getVal()) ) - 50, (sr.getScaledHeight() / (1f * scale.getVal()) - 70), 100, 50, Color.GRAY);
                    fr.drawStringWithShadow(targetName, (float) (sr.getScaledWidth() / (2f * scale.getVal()) ) - 45, (float) (sr.getScaledHeight() / (1f * scale.getVal()) - 35), -1);
                    GuiInventory.drawEntityOnScreen((int) (sr.getScaledWidth() / (2f * scale.getVal()) ) - 36, (int) (sr.getScaledHeight() / (1f * scale.getVal()) - 38), 16, -10, -10, target);
                    RenderUtil.outlineRect((sr.getScaledWidth() / (2f * scale.getVal()) ) - 45, (sr.getScaledHeight() / (1f * scale.getVal()) - 25), 90, 3, 1,Color.DARK_GRAY);
                    RenderUtil.rect( (sr.getScaledWidth() / (2f * scale.getVal()) ) - 45, (sr.getScaledHeight() / (1f * scale.getVal()) - 25), 90, 3, Color.LIGHT_GRAY);
                    RenderUtil.rect( (sr.getScaledWidth() / (2f * scale.getVal()) ) - 45, (sr.getScaledHeight() / (1f * scale.getVal()) - 25), 90 * (targetHealth /target.getMaxHealth()), 3, healthColor);
                    fr.drawStringWithShadow(String.format("%.2f", targetHealth) + EnumChatFormatting.WHITE + " / " + target.getMaxHealth(), (float) (sr.getScaledWidth() / (2f * scale.getVal()) ) - 25, (float) (sr.getScaledHeight() / (1f * scale.getVal()) - 65), healthColor.getRGB());
                    RenderUtil.outlineRect((sr.getScaledWidth() / (2f * scale.getVal()) ) - 27, (sr.getScaledHeight() / (1f * scale.getVal()) - 54), 75, 15, 1, Color.DARK_GRAY);
                    RenderUtil.rect( (sr.getScaledWidth() / (2f * scale.getVal()) ) - 27, (sr.getScaledHeight() / (1f * scale.getVal()) - 54), 75, 15, Color.LIGHT_GRAY);
                    GlStateManager.scale(1f/2, 1f/2, 1);
                    GlStateManager.scale(1.5, 1.5, 1);
                    drawArmor(sr, target, (int) (sr.getScaledWidth() / (2f * 1.5) ) - 34, (int) (sr.getScaledHeight() / (1f * 1.5) - 70));
                    GlStateManager.scale(1/1.5, 1/1.5, 1);
                    GlStateManager.scale(2, 2, 1);
                    break;
            }
        } else {
            target = null;
        }
        GlStateManager.scale(1/2f, 1/2f,1);
    });

    @NotNull
    private Color getHealthColor() {
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
        return healthColor;
    }

    private void drawArmor(ScaledResolution sr, EntityLivingBase target, int x, int y) {
        for (int i = 0; i < 4; i++) {
            ItemStack stack = target.getCurrentArmor(i);
            if (stack != null) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
            }
            x += 20;
        }
        ItemStack stack = target.getHeldItem();
        if(stack != null) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        } else if (target instanceof EntityEnderman) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(((EntityEnderman) target).getHeldBlockState().getBlock()), x, y);
        }
    }

    @Subscribe
    public final Listener<EventAttack> onAttack = new Listener<>(e -> {
        target = e.getTargetEntity();
    });

}
