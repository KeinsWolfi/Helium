package me.helium9.module.impl.world;

import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

@ModuleInfo(
        name = "GetEntities",
        description = "Gets entities in specified range. Default is 16 Blocks",
        category = Category.World,
        excludeArray = "true"
)
public class GetEntities extends Module {

    private final DoubleSetting range = new DoubleSetting("Range", 16, 1, 32, 0.1);

    public GetEntities(){
        addSettings(range);
    }

    @Override
    public void onEnable() {
        List<Entity> entities = mc.theWorld.getLoadedEntityList();
        for (Entity entity : entities) {
            if(entity.getPosition().distanceSq(mc.thePlayer.getPosition().getX(), mc.thePlayer.getPosition().getY(), mc.thePlayer.getPosition().getZ()) < range.getVal()* range.getVal()) {
                if(entity instanceof EntityLivingBase){
                    ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + String.valueOf(entity.getCommandSenderName()) + EnumChatFormatting.GRAY + " is at position x: " + Math.floor(entity.posX) + " y: " + Math.floor(entity.posY) + " z: " + Math.floor(entity.posZ) + EnumChatFormatting.WHITE + " and has " + EnumChatFormatting.RED + ((EntityLivingBase) entity).getHealth() + "HP.");
                }else {
                    ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + String.valueOf(entity.getCommandSenderName()) + EnumChatFormatting.GRAY + " is at position x: " + Math.floor(entity.posX) + " y: " + Math.floor(entity.posY) + " z: " + Math.floor(entity.posZ));
                }
            }
        }
        this.toggle();
    }

    @Override
    public void onDisable(){

    }
}
