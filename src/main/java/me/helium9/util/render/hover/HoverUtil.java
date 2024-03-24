package me.helium9.util.render.hover;

public class HoverUtil {

    public static boolean isRectHovered(float x, float y, float width, float height, float mouseX, float mouseY){
        return mouseX >= x && mouseX <= width+x && mouseY <= y + height && mouseY >= y;
    }

}
