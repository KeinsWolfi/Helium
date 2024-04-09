package me.helium9.screens.dropdown;

import me.helium9.HeliumMain;
import me.helium9.event.impl.input.EventKey;
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
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
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

        boolean hovered = HoverUtil.isRectHovered(parent.x, offset+35, parent.width, parent.height, mouseX, mouseY);

        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDuration(hovered ? 400 : 250);

        RenderUtil.rect(parent.x,offset+35, parent.width, parent.height, new Color(Color.HSBtoRGB(0, 0, (0.25f) + Math.min(0.4f, hoverAnimation.getOutput().floatValue()))));
        RenderUtil.rect(parent.x,offset+34, parent.width, 1, Color.LIGHT_GRAY);

        if(mod.isToggled()) {
            fr.drawString(mod.getName(), parent.x + 5, offset + 39, new Color(0,220,0,255).getRGB());
        }else{
            fr.drawString(mod.getName(), parent.x + 5, offset + 39, Color.WHITE.getRGB());
        }

        if(mod.getKey()!=0) {
            fr.drawString(keyName, parent.x + 83 - fr.getStringWidth(keyName), offset + 39, Color.WHITE.getRGB());
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
            //not implemented correctly yet
            //hotkeySelect();
        }
    }

    private void hotkeySelect() {
        // Start the hotkey selection process
        mod.startHotkeySelection();

        // Prompt the user to press a key
        ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + "Press a key to set as the new hotkey for " + mod.getName() + ", or press ESC to cancel.");

        // Listen for a key press event
        HeliumMain.BUS.subscribe(new Listener<EventKey>(event -> {
            if (mod.isSelectingHotkey()) {
                keyName = "...";
                if (event.getKey() == Keyboard.KEY_ESCAPE) {
                    // If the ESC key is pressed, cancel the hotkey change
                    ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + "Hotkey change for " + mod.getName() + " cancelled.");
                } else {
                    // If any other key is pressed, update the module's key to the pressed key
                    mod.setKey(event.getKey());
                    ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + "New hotkey for " + mod.getName() + " set to " + Keyboard.getKeyName(event.getKey()) + ".");
                }

                // Stop the hotkey selection process
                mod.stopHotkeySelection();

                // Unsubscribe this listener after a key is pressed
                HeliumMain.BUS.unsubscribe(this);
                keyName = Keyboard.getKeyName(mod.getKey());
            }
        }));
    }
}
