package me.helium9.module.impl.misc;

import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.util.notifications.Notification;
import me.helium9.util.notifications.NotificationManager;
import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Direction;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

@ModuleInfo(
        name = "Notifications",
        description = "Displays a notification on module toggle",
        category = Category.Misc,
        excludeArray = "true"
)
public class ModuleNotifications extends Module {

    public ModuleNotifications(){

    }

    public void render(){

        ScaledResolution sr = new ScaledResolution(mc);

        float yOffset = 0;

        for(Notification notification : NotificationManager.getNotifications()){
            Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimer().hasTimeElapsed((long) notification.getDuration()) ? Direction.BACKWARDS : Direction.FORWARDS);

            if(animation.getDirection().backwards() && animation.isDone()) {
                NotificationManager.getNotifications().remove(notification);
                continue;
            }

            float x, y;

            animation.setDuration(250);
            int actualOffset = 8;

            int notificationHeight = 28;
            int notificationWidth = (int) Math.max(fr.getStringWidth(notification.getTitle()), fr.getStringWidth(notification.getDescription())) + 35;

            x = sr.getScaledWidth() - (notificationWidth + 5) * (float) animation.getOutput().floatValue();
            y = sr.getScaledHeight() - (notificationHeight + 5) * (float) animation.getOutput().floatValue() - yOffset;

            notification.draw(x, y, notificationWidth, notificationHeight, new Color(64, 64, 64, 210));

            yOffset += notificationHeight + 5;

        }
    }

}
