package net.minecraft.client.renderer.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.ShadersRender;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal>
{
    private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random RANDOM = new Random(31100L);
    FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer(16);

    public void renderTileEntityAt(TileEntityEndPortal tileEntity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!Config.isShaders() || !ShadersRender.renderEndPortal(tileEntity, x, y, z, partialTicks, destroyStage, 0.75F))
        {
            float viewerX = (float)this.rendererDispatcher.entityX;
            float viewerY = (float)this.rendererDispatcher.entityY;
            float viewerZ = (float)this.rendererDispatcher.entityZ;
            GlStateManager.disableLighting();
            RANDOM.setSeed(31100L);
            float portalSize = 0.75F;

            for (int layer = 0; layer < 16; ++layer)
            {
                GlStateManager.pushMatrix();
                float layerSize = (float)(16 - layer);
                float layerOffset = 0.0625F;
                float scaleFactor = 1.0F / (layerSize + 1.0F);

                if (layer == 0)
                {
                    this.bindTexture(END_SKY_TEXTURE);
                    scaleFactor = 0.1F;
                    layerSize = 65.0F;
                    layerOffset = 0.125F;
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                }

                if (layer >= 1)
                {
                    this.bindTexture(END_PORTAL_TEXTURE);
                }

                if (layer == 1)
                {
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(1, 1);
                    layerOffset = 0.5F;
                }

                float viewerYOffset = (float)(-(y + (double)portalSize));
                float startViewY = viewerYOffset + (float)ActiveRenderInfo.getPosition().yCoord;
                float endViewY = viewerYOffset + layerSize + (float)ActiveRenderInfo.getPosition().yCoord;
                float interpolation = startViewY / endViewY;
                interpolation = (float)(y + (double)portalSize) + interpolation;
                GlStateManager.translate(viewerX, interpolation, viewerZ);
                GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
                GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
                GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
                GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
                GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9473, this.getFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9473, this.getFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
                GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9473, this.getFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
                GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, this.getFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
                GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
                GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
                GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
                GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
                GlStateManager.scale(layerOffset, layerOffset, layerOffset);
                GlStateManager.translate(0.5F, 0.5F, 0.0F);
                GlStateManager.rotate((float)(layer * layer * 4321 + layer * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translate(-0.5F, -0.5F, 0.0F);
                GlStateManager.translate(-viewerX, -viewerZ, -viewerY);
                startViewY = viewerYOffset + (float)ActiveRenderInfo.getPosition().yCoord;
                GlStateManager.translate((float)ActiveRenderInfo.getPosition().xCoord * layerSize / startViewY, (float)ActiveRenderInfo.getPosition().zCoord * layerSize / startViewY, -viewerY);
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
                float red = (RANDOM.nextFloat() * 0.5F + 0.1F) * scaleFactor;
                float green = (RANDOM.nextFloat() * 0.5F + 0.4F) * scaleFactor;
                float blue = (RANDOM.nextFloat() * 0.5F + 0.5F) * scaleFactor;

                if (layer == 0)
                {
                    red = green = blue = 1.0F * scaleFactor;
                }

                worldRenderer.func_181662_b(x, y + (double)portalSize, z).func_181666_a(red, green, blue, 1.0F).func_181675_d();
                worldRenderer.func_181662_b(x, y + (double)portalSize, z + 1.0D).func_181666_a(red, green, blue, 1.0F).func_181675_d();
                worldRenderer.func_181662_b(x + 1.0D, y + (double)portalSize, z + 1.0D).func_181666_a(red, green, blue, 1.0F).func_181675_d();
                worldRenderer.func_181662_b(x + 1.0D, y + (double)portalSize, z).func_181666_a(red, green, blue, 1.0F).func_181675_d();
                tessellator.draw();
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                this.bindTexture(END_SKY_TEXTURE);
            }

            GlStateManager.disableBlend();
            GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.enableLighting();
        }
    }

    private FloatBuffer getFloatBuffer(float a, float b, float c, float d)
    {
        this.floatBuffer.clear();
        this.floatBuffer.put(a).put(b).put(c).put(d);
        this.floatBuffer.flip();
        return this.floatBuffer;
    }
}