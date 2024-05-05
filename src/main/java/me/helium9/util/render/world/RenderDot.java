package me.helium9.util.render.world;

import me.helium9.HeliumMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import static me.helium9.util.render.world.BoxESPUtil.disableCaps;
import static me.helium9.util.render.world.BoxESPUtil.enableCaps;
import static org.lwjgl.opengl.GL11.*;

public class RenderDot {
    public static void renderDot(double x, double y, double z, double radius, double r, double g, double b, double a){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        Minecraft mc = HeliumMain.INSTANCE.getMc();

        //x = x - mc.getRenderManager().viewerPosX;
        //y = y - mc.getRenderManager().viewerPosY;
        //z = z - mc.getRenderManager().viewerPosZ;

        glTranslated(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glPointSize((float) radius);
        glColor4d(r/255, g/255, b/255, a/255);
        glBegin(GL_POINTS);
        glVertex3d(x, y, z);
        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        disableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        glColor4f(1, 1, 1, 1);
    }

    public static void renderLine(double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd, double width, double r, double g, double b, double a){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        enableCaps(GL_BLEND, GL_LINE_SMOOTH, GL_POLYGON_SMOOTH, GL_POINT_SMOOTH);

        //xStart = xStart - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        //yStart = yStart - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        //zStart = zStart - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        //xEnd = xEnd - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        //yEnd = yEnd - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        //zEnd = zEnd - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        glTranslated(-Minecraft.getMinecraft().getRenderManager().viewerPosX, -Minecraft.getMinecraft().getRenderManager().viewerPosY, -Minecraft.getMinecraft().getRenderManager().viewerPosZ);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth((float) width);
        glColor4d(r/255, g/255, b/255, a/255);
        glBegin(GL_LINES);
        glVertex3d(xStart, yStart, zStart);
        glVertex3d(xEnd, yEnd, zEnd);
        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        disableCaps(GL_BLEND, GL_LINE_SMOOTH, GL_POLYGON_SMOOTH, GL_POINT_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        glColor4f(1, 1, 1, 1);
        glLineWidth(1f);
    }

}
