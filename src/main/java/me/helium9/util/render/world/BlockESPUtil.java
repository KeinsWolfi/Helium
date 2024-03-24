package me.helium9.util.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESPUtil {
    public static void Render1x1Box(BlockPos blockPos, double r, double g, double b, double a){

        double x = blockPos.getX()- Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y = blockPos.getY()- Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z = blockPos.getZ()- Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d(r/255, g/255, b/255, a/255);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x+1, y+1, z+1));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3d(255, 255, 255);

    }
}
