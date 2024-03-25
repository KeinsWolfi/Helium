package me.helium9.util.render;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class RenderUtil {

    public static void rect(double x, double y, double width, double height, Color color){
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }
    public static void outlineRect(double x, double y, double width, double height, double outlineWidth, Color color){
        //left
        Gui.drawRect(x-outlineWidth, y-outlineWidth, x + outlineWidth, y + height + outlineWidth, color.getRGB());
        //top
        Gui.drawRect(x, y-outlineWidth, x + width, y + outlineWidth, color.getRGB());
        //right
        Gui.drawRect(x+width+outlineWidth, y-outlineWidth, x + outlineWidth, y + height + outlineWidth, color.getRGB());
        //bottom
        Gui.drawRect(x, y+outlineWidth+height, x + width, y + outlineWidth, color.getRGB());
    }

}
