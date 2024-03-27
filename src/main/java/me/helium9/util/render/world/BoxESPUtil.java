package me.helium9.util.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class BoxESPUtil {
    public static void RenderEntityBox(Entity entity, double r, double g, double b, double a){
        RenderBox(entity.posX - entity.width / 2, entity.posY, entity.posZ - entity.width / 2, entity.width, entity.height, r, g, b, a);
    }
    public static void RenderBox(double x, double y, double z, double width, double height, double r, double g, double b, double a){

        double xF = x- Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double yF = y- Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double zF = z- Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d(r/255, g/255, b/255, a/255);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        RenderGlobal.func_181561_a(new AxisAlignedBB(xF, yF, zF, xF+width, yF+height, zF+width));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3d(255, 255, 255);
    }
}
