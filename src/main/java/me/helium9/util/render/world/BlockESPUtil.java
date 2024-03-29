package me.helium9.util.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESPUtil {
    public static void Render1x1Box(BlockPos blockPos, double r, double g, double b, double a){

        BoxESPUtil.RenderBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 1, r, g, b, a);

    }
}
