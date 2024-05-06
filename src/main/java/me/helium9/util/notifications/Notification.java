package me.helium9.util.notifications;

import lombok.Getter;
import lombok.Setter;
import me.helium9.HeliumMain;
import me.helium9.util.Timer;
import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Animations.EaseInOutSine;
import me.helium9.util.render.RenderUtil;
import me.helium9.util.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

@Getter
@Setter
public class Notification {

    private static Minecraft mc = HeliumMain.INSTANCE.getMc();

    private final NotificationType type;
    private final String title, description;
    private final int duration;
    private final Timer timer;
    private final Animation animation;


    public Notification(NotificationType type, String title, String description, int durationMS) {
        this.description = description;
        this.title = title;
        this.type = type;
        this.duration = durationMS;
        this.timer = new Timer();
        this.animation = new EaseInOutSine(250, 1);
    }

    public void draw(float x, float y, float width, float height, Color backgroundColor) {
        FontRenderer fr = mc.fontRendererObj;

        RoundedUtil.drawRoundedRect(x, y, width, height, 4, backgroundColor);
        RoundedUtil.drawRoundedOutline(x, y, width, height, 4, 1, new Color(16, 16, 16).getRGB());

        //RenderUtil.rect(x, y, width, height, backgroundColor);

        fr.drawString(title, x + 5, y + 4, getColor(this.type).getRGB());
        if(this.type == NotificationType.ENABLE || this.type == NotificationType.DISABLE){
            fr.drawString(description, x + 5, y + 14, getColor(this.type).getRGB());
        } else fr.drawString(description, x + 5, y + 14, -1);
    }

    public Color getColor(NotificationType type) {
        switch (type) {
            case WARNING:
                return new Color(255, 100, 0);
            case ERROR:
                return new Color(255, 0, 0);
            case ENABLE:
                return new Color(0, 255, 0);
            case DISABLE:
                return new Color(255, 50, 0);
            default:
                return new Color(100, 100, 200);
        }
    }

}
