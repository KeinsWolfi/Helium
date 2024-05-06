package me.helium9.module.impl.HUD;

import me.helium9.event.impl.render.Event2D;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.util.render.ColorUtil;
import me.helium9.util.render.RoundedUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

@ModuleInfo(
        name = "BPSDisplay",
        description = "BPSDisplay",
        category = Category.HUD,
        excludeArray = "true"
)
public class BPSDisplay extends Module {

    private final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);

    public BPSDisplay(){
        addSettings(rainbow);
    }

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        ScaledResolution sr = new ScaledResolution(mc);

        float hue = ColorUtil.getHue(5);
        String bpsText = EnumChatFormatting.GRAY + "BPS: §r" + String.format("%.2f", getBPS());
        if(rainbow.isState()) {
            RoundedUtil.drawRoundedRect(2, sr.getScaledHeight()-(6 + fr.FONT_HEIGHT), fr.getStringWidth(bpsText) + 2, fr.FONT_HEIGHT + 2, 2, new Color(16, 16, 16, 200));
            fr.drawString(bpsText, 4, sr.getScaledHeight()-(4 + fr.FONT_HEIGHT), new Color(Color.HSBtoRGB(hue, 0.5f, 0.6f)).getRGB());
        }
        else {
            fr.drawString(bpsText, sr.getScaledWidth(), sr.getScaledHeight(), Color.gray.getRGB());
        }
    });

    public double getBPS() {
        if(mc.thePlayer == null) return 0;
        return Math.sqrt(Math.pow(mc.thePlayer.posX - mc.thePlayer.prevPosX, 2) + Math.pow(mc.thePlayer.posZ - mc.thePlayer.prevPosZ, 2)) * 20;
    }
}
