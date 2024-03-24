package me.helium9.screens.dropdown;

import me.helium9.HeliumMain;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.util.render.RenderUtil;
import me.helium9.util.render.hover.HoverUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame {
    private final List<ModuleButtons> moduleButtons;
    private final List<Module> modules;

    private final Minecraft mc = HeliumMain.INSTANCE.getMc();

    public Category cat;
    public int offset;

    public boolean extended = true;

    public int x, y, width, height;

    public Frame(Category cat, int x, int y, int width, int height){
        this.cat = cat;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        modules = new ArrayList<>();

        modules.addAll(Arrays.asList(HeliumMain.INSTANCE.getMm().getModulesByCat(cat)));

        modules.sort((m1, m2) -> mc.fontRendererObj.getStringWidth(m1.getDisplayName()) - mc.fontRendererObj.getStringWidth(m2.getDisplayName()));

        moduleButtons = new ArrayList<>();

        int offset = height+1;
        for(Module mod : modules){
            moduleButtons.add(new ModuleButtons(mod, this, offset));
            offset+=height+1;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        RenderUtil.rect(x, y, width, height, Color.GRAY);
        fr.drawString(cat.name(), x+5, y+ 4, -1);

        if(extended){
            fr.drawString("-", x + 75, y+4, -1);

            for(ModuleButtons mb : moduleButtons){
                mb.drawScreen(mouseX, mouseY, partialTicks);
            }
        }else{
            fr.drawString("+", x + 75, y+4, -1);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        for(ModuleButtons mb : moduleButtons){
            mb.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if(HoverUtil.isRectHovered(x, y, width, height, mouseX, mouseY) && mouseButton == 1){
            extended=!extended;
        }
    }

}
