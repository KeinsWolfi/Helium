package me.helium9.util.render;

public class ColorUtil {
    public static float getHue(float seconds) {
        float hue = (System.currentTimeMillis() % (int)(1000*seconds))/(float)(1000f*seconds);
        return hue;
    }
}
