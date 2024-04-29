package me.helium9.module.impl.motion;

import me.helium9.event.impl.update.EventMotion;
import me.helium9.event.impl.update.EventSlowDown;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

@ModuleInfo(
        name = "NoSlow",
        description = "NoSlow",
        category = Category.Motion,
        excludeArray = "false"
)
public class NoSlow extends Module {

    private final BooleanSetting cobweb = new BooleanSetting("Cobweb", true);
    public static final BooleanSetting use = new BooleanSetting("Use", true);
    private final BooleanSetting soulSand = new BooleanSetting("SoulSand", true);

    public NoSlow(){
        this.addSettings(cobweb, use, soulSand);
        this.setDisplayName(this.name);
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(e -> {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name + EnumChatFormatting.GRAY);
        if(cobweb.isState()){
            if(mc.thePlayer.isInWeb()){
                stringBuilder.append(" [WEB]");
            }
        }
        if(use.isState()){
            if (mc.thePlayer.isUsingItem()) {
                stringBuilder.append(" [USE]");
            }
        }
        if(soulSand.isState()){
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);
            if(mc.theWorld.getBlock(pos).equals(Blocks.soul_sand)){
                stringBuilder.append(" [SOUL]");
            }
        }
        this.setDisplayName(stringBuilder.toString());
    });

    @Subscribe
    private final Listener<EventSlowDown> onSlowDown = new Listener<>(e -> {
        if(e.getEntity() != mc.thePlayer) return;
        if(e.getType().equals(EventSlowDown.type.COBWEB) && cobweb.isState()){
            e.cancel();
        }
        if(e.getType().equals(EventSlowDown.type.USE) && use.isState()){
            e.cancel();
        }
        if(e.getType().equals(EventSlowDown.type.SOUL_SAND) && soulSand.isState()){
            e.cancel();
        }
    });
}
