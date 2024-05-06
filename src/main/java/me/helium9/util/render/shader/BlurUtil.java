package me.helium9.util.render.shader;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class BlurUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private final ResourceLocation resLoc;
    private ShaderGroup shaderGroup;
    private Framebuffer framebuffer;

    private int lastFactor;
    private int lastWidth;
    private int lastHeight;

    public BlurUtil() {
        this.resLoc = new ResourceLocation("helium/shader/blur.json");
    }

    public void init() {
        try {
            this.shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resLoc);
            this.shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            this.framebuffer = shaderGroup.mainFramebuffer;
        } catch (final JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setValues(final int strength) {
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);
    }

    public final void blur(final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;

        setValues(blurStrength);
        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableAlpha();
    }

    public final void blur(final double x, final double y, final double areaWidth, final double areaHeight, final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        scissor(x, y, areaWidth, areaHeight);
        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(mc.timer.renderPartialTicks);
        setValues(blurStrength);
        mc.getFramebuffer().bindFramebuffer(false);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private boolean sizeHasChanged(final int scaleFactor, final int width, final int height) {
        return (lastFactor != scaleFactor || lastWidth != width || lastHeight != height);
    }

    public static void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = new ScaledResolution(mc);
        final double scale = sr.getScaleFactor();

        y = sr.getScaledHeight() - y;

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
    }

}
