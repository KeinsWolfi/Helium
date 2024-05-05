package me.helium9.module.impl.visual;

import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.render.world.BoxESPUtil;
import me.helium9.util.render.world.RenderDot;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(
        name = "Trail",
        description = "Trail",
        category = Category.Visual,
        excludeArray = "false"
)
public class Trail extends Module{

    private final RGBSetting color = new RGBSetting("Color", 200, 255, 255, 255);
    private final DoubleSetting limit = new DoubleSetting("Limit", 100, 1, 1000, 1);

    private final List<Vec3> points = new ArrayList<>();

    public Trail() {
        this.addSettings(color, limit);
    }

    @Override
    public void onDisable(){
        points.clear();
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(event -> {
        if(mc.thePlayer == null) return;
        points.add(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
        if(points.size() > limit.getVal()) {
            points.remove(0);
        }
    });

    @Subscribe
    private final Listener<Event3D> onRender = new Listener<>(event -> {
        if(mc.thePlayer == null) return;
        for(Vec3 point : points) {
            BoxESPUtil.RenderBox(point.xCoord, point.yCoord, point.zCoord, 0.02, 0.02, color.getR(), color.getG(), color.getB(), color.getA());
        }
    });

}
