package me.helium9.screens.dropdown;

import me.helium9.module.Module;
import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Animations.EaseInOutSine;
import me.helium9.util.render.Animation.Direction;
import me.helium9.util.render.RenderUtil;
import me.helium9.util.render.hover.HoverUtil;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class ModuleButtons implements Subscriber{
    private final Animation hoverAnimation = new EaseInOutSine(200, 1);

    public Module mod;
    public Frame parent;
    public int offset;
    public int height;

    private String keyName;

    private boolean extended = false;

    public ModuleButtons(Module mod, Frame parent, int offset){
        this.mod = mod;
        this.parent = parent;
        this.height = 15;
        keyName = Keyboard.getKeyName(mod.getKey());
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        boolean hovered = HoverUtil.isRectHovered(parent.x, parent.y + offset + height, parent.width, height, mouseX, mouseY);

        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDuration(hovered ? 400 : 250);

        RenderUtil.rect(parent.x,parent.y + offset + height, parent.width, height, new Color(Color.HSBtoRGB(0, 0, (0.25f) + Math.min(0.4f, hoverAnimation.getOutput().floatValue()))));
        RenderUtil.rect(parent.x,parent.y + offset + height-1, parent.width, 1, Color.LIGHT_GRAY);

        if(mod.isToggled()) {
            fr.drawString(mod.getName(), parent.x + 5, parent.y + offset + height + 4, new Color(0,220,0,255).getRGB());
        }else{
            fr.drawString(mod.getName(), parent.x + 5, parent.y + offset + height + 4, Color.WHITE.getRGB());
        }

        if(mod.getKey()!=0) {
            fr.drawString(keyName, parent.x + 83 - fr.getStringWidth(keyName), parent.y + offset + height + 4, Color.WHITE.getRGB());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(HoverUtil.isRectHovered(parent.x, parent.y + offset + height, parent.width, height, mouseX, mouseY) && mouseButton == 0){
            mod.toggle();
        }
        if(HoverUtil.isRectHovered(parent.x, parent.y + offset + height, parent.width, height, mouseX, mouseY) && mouseButton == 1){
            if(extended) {
                extended = false;
                DropDownGui.extendedModule = null;
                height = 15;
            }
            else {
                extended = true;
                DropDownGui.extendedModule = this.mod;
                height = 15 + mod.getSList().size() * 15;
            }
        }
    }
}
