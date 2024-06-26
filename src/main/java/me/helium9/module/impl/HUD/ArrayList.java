package me.helium9.module.impl.HUD;

import me.helium9.HeliumMain;
import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.util.render.ColorUtil;
import me.helium9.util.render.RenderUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

@ModuleInfo(
        name = "ArrayList",
        description = "Arraylist",
        category = Category.HUD,
        excludeArray = "true"
)
public class ArrayList extends Module {

    private final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);

    public ArrayList(){
        addSettings(rainbow);
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
    });

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        float hue = ColorUtil.getHue(5);

        ScaledResolution sr = new ScaledResolution(mc);
        java.util.ArrayList<Module> enabledMods = new java.util.ArrayList<Module>();
        for(Module m : HeliumMain.INSTANCE.getMm().getModules().values()) {
            if (m.isToggled() && m.getExcludeArray().equalsIgnoreCase("false"))
                enabledMods.add(m);
        }

        enabledMods.sort((m1, m2) -> mc.fontRendererObj.getStringWidth(m2.getDisplayName()) - mc.fontRendererObj.getStringWidth(m1.getDisplayName()));
        float offset = 0;
        int y = 2;
        for(Module m : enabledMods) {
            RenderUtil.rect(sr.getScaledWidth()-(mc.fontRendererObj.getStringWidth(m.getDisplayName())+4), y-2, mc.fontRendererObj.getStringWidth(m.getDisplayName())+4, mc.fontRendererObj.FONT_HEIGHT+2, new Color(40, 40, 40, 200));
            if(rainbow.isState()) {
                RenderUtil.rect(sr.getScaledWidth() - (mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 6), y - 2, 2, mc.fontRendererObj.FONT_HEIGHT + 2, new Color(Color.HSBtoRGB(hue + offset, 0.5f, 0.8f)));
                mc.fontRendererObj.drawString(m.getDisplayName(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 2, y, new Color(Color.HSBtoRGB(hue + offset, 0.7f, 1)).getRGB());
            }else {
                RenderUtil.rect(sr.getScaledWidth() - (mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 6), y - 2, 2, mc.fontRendererObj.FONT_HEIGHT + 2, Color.YELLOW);
                mc.fontRendererObj.drawString(EnumChatFormatting.YELLOW + m.getDisplayName(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 2, y, -1);
            }
            y += 11;
            offset+=0.02f;
        }
    });

}
