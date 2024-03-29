package me.helium9.util.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class BoxESPUtil {
    public static void RenderEntityBox(Entity entity, double r, double g, double b, double a){
        AxisAlignedBB bb = ESPUtil.getInterpolatedBB(entity);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(3f);
        glColor4d(r/255, g/255, b/255, (0.3f * a)/255);
        RenderGlobal.renderCustomBoundingBox(bb, true, true);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        disableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    public static void RenderBox(double x, double y, double z, double width, double height, double r, double g, double b, double a){
        AxisAlignedBB bb = ESPUtil.getInterpolatedBB((float) x, (float) y, (float) z, (float) width, (float) height);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(3f);
        glColor4d(r/255, g/255, b/255, (0.2f * a)/255);
        RenderGlobal.renderCustomBoundingBox(bb, true, true);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        disableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void enableCaps(int... caps) {
        for (int cap : caps) glEnable(cap);
    }

    public static void disableCaps(int... caps) {
        for (int cap : caps) glDisable(cap);
    }
}
