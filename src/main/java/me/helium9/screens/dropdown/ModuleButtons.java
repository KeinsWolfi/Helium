package me.helium9.screens.dropdown;

import me.helium9.HeliumMain;
import me.helium9.module.Module;
import me.helium9.settings.Setting;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Animations.EaseInOutSine;
import me.helium9.util.render.Animation.Direction;
import me.helium9.util.render.RenderUtil;
import me.helium9.util.render.hover.HoverUtil;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class ModuleButtons implements Subscriber{
    private final Animation hoverAnimation = new EaseInOutSine(200, 1);

    public Module mod;
    public Frame parent;
    public int offset;

    private String keyName;

    public ModuleButtons(Module mod, Frame parent, int offset){
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        keyName = Keyboard.getKeyName(mod.getKey());
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        boolean hovered = HoverUtil.isRectHovered(parent.x, parent.y + offset + parent.height, parent.width, parent.height, mouseX, mouseY);

        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDuration(hovered ? 400 : 250);

        RenderUtil.rect(parent.x,parent.y + offset + parent.height, parent.width, parent.height, new Color(Color.HSBtoRGB(0, 0, (0.25f) + Math.min(0.4f, hoverAnimation.getOutput().floatValue()))));
        RenderUtil.rect(parent.x,parent.y + offset + parent.height-1, parent.width, 1, Color.LIGHT_GRAY);

        if(mod.isToggled()) {
            fr.drawString(mod.getName(), parent.x + 5, parent.y + offset + parent.height + 4, new Color(0,220,0,255).getRGB());
        }else{
            fr.drawString(mod.getName(), parent.x + 5, parent.y + offset + parent.height + 4, Color.WHITE.getRGB());
        }

        if(mod.getKey()!=0) {
            fr.drawString(keyName, parent.x + 83 - fr.getStringWidth(keyName), parent.y + offset + parent.height + 4, Color.WHITE.getRGB());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(HoverUtil.isRectHovered(parent.x, parent.y + offset + parent.height, parent.width, parent.height, mouseX, mouseY) && mouseButton == 0){
            mod.toggle();
        }
        if(HoverUtil.isRectHovered(parent.x, parent.y + offset + parent.height, parent.width, parent.height, mouseX, mouseY) && mouseButton == 1){
            for(Setting s : mod.getSList()) {
                ChatUtil.addChatMsg( EnumChatFormatting.YELLOW + s.name);
                if(s instanceof ModeSetting) {
                    for(String modeName : HeliumMain.INSTANCE.getSm().getModeSetting(mod, s.getName()).getModes()){
                        ChatUtil.addChatMsg("  " + EnumChatFormatting.GRAY + modeName);
                    }
                } else if (s instanceof DoubleSetting) {
                    ChatUtil.addChatMsg(EnumChatFormatting.GRAY + "  Min: " + EnumChatFormatting.GRAY + ((DoubleSetting) s).getMinVal());
                    ChatUtil.addChatMsg(EnumChatFormatting.GRAY + "  Max: " + EnumChatFormatting.GRAY + ((DoubleSetting) s).getMaxVal());
                } else if (s instanceof RGBSetting) {
                    ChatUtil.addChatMsg(EnumChatFormatting.GRAY + "  RGBA Setting.");
                } else if (s instanceof BooleanSetting) {
                    ChatUtil.addChatMsg(EnumChatFormatting.GRAY + "  Bool setting. (true/false)");
                }
            }
            DropDownGui.extendedModule = this.mod;
        }
    }
}
