package me.helium9.screens.dropdown;

import me.helium9.HeliumMain;
import me.helium9.module.Module;
import me.helium9.settings.Setting;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.render.RenderUtil;
import me.helium9.util.render.hover.HoverUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ModuleButtons {

    public Module mod;
    public Frame parent;
    public int offset;

    public ModuleButtons(Module mod, Frame parent, int offset){
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        RenderUtil.rect(parent.x,offset+35, parent.width, parent.height, Color.DARK_GRAY);
        RenderUtil.rect(parent.x,offset+34, parent.width, 1, Color.LIGHT_GRAY);

        if(mod.isToggled()) {
            fr.drawString(mod.getName(), parent.x + 5, offset + 39, new Color(0,220,0,255).getRGB());
        }else{
            fr.drawString(mod.getName(), parent.x + 5, offset + 39, Color.WHITE.getRGB());
        }

        if(mod.getKey()!=0) {
            fr.drawString(Keyboard.getKeyName(mod.getKey()), parent.x + 75, offset + 39, Color.WHITE.getRGB());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(HoverUtil.isRectHovered(parent.x, offset+35, parent.width, parent.height, mouseX, mouseY) && mouseButton == 0){
            mod.toggle();
        }
        if(HoverUtil.isRectHovered(parent.x, offset+35, parent.width, parent.height, mouseX, mouseY) && mouseButton == 1){
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
        }
    }

}
