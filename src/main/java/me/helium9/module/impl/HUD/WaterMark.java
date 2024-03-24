package me.helium9.module.impl.HUD;

import me.helium9.HeliumMain;
import me.helium9.event.impl.render.Event2D;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.render.ColorUtil;
import me.helium9.util.render.RenderUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

@ModuleInfo(
        name = "WaterMark",
        description = "Watermark :D",
        category = Category.HUD,
        excludeArray = "true"
)
public class WaterMark extends Module {

    private final BooleanSetting text = new BooleanSetting("Text", true);
    private final BooleanSetting ver = new BooleanSetting("Version", true);
    private final BooleanSetting fps = new BooleanSetting("FPS", true);

    private final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    private final DoubleSetting x = new DoubleSetting("x", 4, 0, new ScaledResolution(mc).getScaledWidth(), 1);
    private final DoubleSetting y = new DoubleSetting("y", 4, 0, new ScaledResolution(mc).getScaledHeight(), 1);
    private final DoubleSetting scale = new DoubleSetting("scale", 2, 1, 10, 0.1);
    private final RGBSetting color = new RGBSetting("Color", 0,0,0,255);

    public WaterMark(){
        addSettings(x,y,scale,color, text, ver, fps);
    }

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        float hue = ColorUtil.getHue(5);
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        String finalText = "";

        if(text.isState()){
            finalText += "H" + EnumChatFormatting.GRAY + "elium";
        }
        if(ver.isState()){
            finalText += " " + EnumChatFormatting.GRAY + HeliumMain.INSTANCE.getVersion();
        }
        if(fps.isState()){
            finalText += " " + EnumChatFormatting.GRAY + Minecraft.getDebugFPS();
        }

        GlStateManager.scale(scale.getVal(), scale.getVal(),1);
        RenderUtil.rect(x.getVal()-2, y.getVal()-2, fr.getStringWidth(finalText)+3, fr.FONT_HEIGHT+2, new Color(100, 100, 100 ,200));
        if(rainbow.isState()){
            fr.drawString(finalText, x.getVal(), y.getVal(), new Color(Color.HSBtoRGB(hue, 1, 0.8f)).getRGB());
        }else {
            fr.drawString(finalText, x.getVal(), y.getVal(), new Color(color.getR(), color.getG(), color.getB(), color.getA()).getRGB());
        }
        GlStateManager.scale(1/scale.getVal(), 1/scale.getVal(),1);
    });

}
