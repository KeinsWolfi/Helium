package me.helium9.util.render;

import java.awt.*;

public class ColorUtil {
    public static float getHue(float seconds) {
        float hue = (System.currentTimeMillis() % (int)(1000*seconds))/(float)(1000f*seconds);
        return hue;
    }

    public static Color getRainbowColor(float seconds, float saturation, float brightness) {
        return Color.getHSBColor(getHue(seconds), saturation, brightness);
    }

    public static Color getColor(float hue, float saturation, float brightness) {
        return Color.getHSBColor(hue, saturation, brightness);
    }

}
