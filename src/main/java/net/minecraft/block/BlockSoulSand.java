package net.minecraft.block;

import me.helium9.HeliumMain;
import me.helium9.event.impl.update.EventSlowDown;
import me.zero.alpine.event.EventPhase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSoulSand extends Block
{
    public BlockSoulSand()
    {
        super(Material.sand, MapColor.brownColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        float f = 0.125F;
        return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - f), (double)(pos.getZ() + 1));
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {

        EventSlowDown event = new EventSlowDown(EventSlowDown.type.SOUL_SAND, entityIn);
        event.setEventPhase(EventPhase.PRE);
        HeliumMain.BUS.post(event);

        if(!event.isCancelled()) {
            entityIn.motionX *= 0.4D;
            entityIn.motionZ *= 0.4D;
        }
    }
}
