package me.helium9.module.impl.world;

import me.helium9.HeliumMain;
import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.module.impl.motion.Sprint;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.PacketUtil;
import me.helium9.util.notifications.NotificationManager;
import me.helium9.util.notifications.NotificationType;
import me.helium9.util.player.EnumFacingOffset;
import me.helium9.util.render.world.BlockESPUtil;
import me.helium9.util.render.world.BoxESPUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(
        name = "Scaffold",
        description = "Automatically places blocks under you",
        category = Category.World,
        excludeArray = "false"
)
public class Scaffold extends Module {

    private final BooleanSetting tower = new BooleanSetting("Tower", false);
    private final BooleanSetting towerMove = new BooleanSetting("TowerMove", true);
    private final ModeSetting rotationMode = new ModeSetting("RotationMode", "NCP", "Vanilla", "Legit");

    private int currentSlot;

    private float yaw, pitch, prevYaw, prevPitch, camPitch;
    private int ticksOnAir = 0;

    private boolean blockPlaced = false;
    private boolean sprintClass;

    private List<Vec3> possibilities = new ArrayList<>();
    private Vec3 blockToPlace;
    private BlockPos blockFace;

    private EnumFacingOffset enumFacing;

    public Scaffold() {
        setKey(Keyboard.KEY_V);
        addSettings(tower, rotationMode, towerMove);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        currentSlot = mc.thePlayer.inventory.currentItem;
        yaw = prevYaw = mc.thePlayer.rotationYaw;
        pitch = prevPitch = mc.thePlayer.rotationPitch;
        sprintClass = HeliumMain.INSTANCE.getMm().getModule(Sprint.class).isToggled();
        HeliumMain.INSTANCE.getMm().getModule(Sprint.class).setToggled(false);
        getBlocksAndSelect();
    }

    public void getBlocksAndSelect(){

        int itemSlot = -1;

        for(int i = 0; i <= 8; i++){
            if(mc.thePlayer.inventory.getStackInSlot(i) == null) continue;
            if(!(mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) continue;
            if(!(((ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem()).getBlock().isFullBlock())) continue;
            Block block = ((ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem()).getBlock();
            if(block instanceof BlockFalling) continue;
            itemSlot = i;
        }

        if(itemSlot == -1) {
            NotificationManager.post(NotificationType.ERROR, "BlockError", "No Blocks found in Hotbar!", 3000);
            this.toggle();
            return;
        }

        if(itemSlot == mc.thePlayer.inventory.currentItem) return;

        mc.thePlayer.inventory.currentItem = itemSlot;
        PacketUtil.sendPacket(new C09PacketHeldItemChange(itemSlot));
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(e -> {
        if(e.isPre()) {

            getBlocksAndSelect();

            mc.thePlayer.setSprinting(false);

            if(tower.isState() && mc.gameSettings.keyBindJump.isKeyDown() && (!(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) || towerMove.isState())) {
                mc.thePlayer.motionY = 0.42f;
            }

            if(getBlockRelativeToPlayer(0, -1, 0) instanceof BlockAir){
                ticksOnAir++;
                blockPlaced = false;
            }
            else{
                blockPlaced = true;
                ticksOnAir = 0;
            }

            mc.thePlayer.setSprinting(false);

            e.setYaw(yaw);
            e.setPitch(pitch);

            if(!blockPlaced){
                switch (rotationMode.getCurrentMode()) {
                    case "NCP":
                        mc.thePlayer.renderYawOffset = yaw;
                        mc.thePlayer.rotationYawHead = yaw;
                        mc.thePlayer.rotationPitch = pitch;
                        break;
                    case "Vanilla":
                        mc.thePlayer.renderYawOffset = yaw;
                        mc.thePlayer.rotationYawHead = yaw;
                        mc.thePlayer.rotationPitchHead = pitch;
                        break;
                    case "Legit":
                        if (enumFacing != null) {
                            switch (enumFacing.getEnumFacing()) {
                                case NORTH:
                                    mc.thePlayer.rotationYaw = 180;
                                    mc.thePlayer.rotationPitch = calcRotations()[1];
                                    break;
                                case SOUTH:
                                    mc.thePlayer.rotationYaw = 0;
                                    pitch = calcRotations()[1];
                                    break;
                                case EAST:
                                    mc.thePlayer.rotationYaw = -90;
                                    mc.thePlayer.rotationPitch = calcRotations()[1];
                                    break;
                                case WEST:
                                    mc.thePlayer.rotationYaw = 90;
                                    mc.thePlayer.rotationPitch = calcRotations()[1];
                                    break;
                                case UP:
                                    mc.thePlayer.rotationPitch = 0;
                                    mc.thePlayer.rotationYaw = calcRotations()[1];
                                    break;
                            }
                        }
                        break;
                }
            }

            prevPitch = pitch;
            prevYaw = yaw;

            possibilities = getPlacePossibilities();

            if (possibilities.isEmpty()) return;

            possibilities.sort(Comparator.comparingDouble(vec3 -> mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord + 1, vec3.zCoord)));

            blockToPlace = possibilities.get(0);

            enumFacing = getEnumFacing(blockToPlace);
            if (enumFacing == null) return;

            BlockPos pos = new BlockPos(blockToPlace.xCoord, blockToPlace.yCoord, blockToPlace.zCoord);

            blockFace = pos.add(enumFacing.getOffset().xCoord, enumFacing.getOffset().yCoord, enumFacing.getOffset().zCoord);
            if (blockFace == null) return;

        } else {
            if(!blockPlaced && ticksOnAir > 1){
                placeBlock();
            }
        }

    });

    @Subscribe
    private final Listener<Event3D> on3D = new Listener<>(e -> {
        if(enumFacing == null || blockFace == null) return;

        calcRotations();

        if(blockToPlace != null){
            BlockESPUtil.Render1x1Box(new BlockPos(blockToPlace), 200, 0, 50, 255);
        }
    });

    @Override
    public void onDisable() {
        mc.thePlayer.inventory.currentItem = currentSlot;
        PacketUtil.sendPacket(new C09PacketHeldItemChange(currentSlot));
        HeliumMain.INSTANCE.getMm().getModule(Sprint.class).setToggled(sprintClass);
        super.onDisable();
    }

    public void placeBlock(){

        if(mc.thePlayer.getHeldItem() == null || possibilities.isEmpty() || blockToPlace == null || enumFacing == null || blockFace == null) return;

        MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTraceCustom(mc.playerController.getBlockReachDistance(), mc.timer.renderPartialTicks, yaw, pitch);

        if(movingObjectPosition == null) return;

        Vec3 hitVec = movingObjectPosition.hitVec;
        ItemStack item = mc.thePlayer.getHeldItem();

        //hitVec.yCoord = Math.random() + blockFace.getY();
        //hitVec.zCoord = Math.random() + blockFace.getZ();
        //hitVec.xCoord = Math.random() + blockFace.getX();

        if(!lookingAtBlock(blockFace, yaw, pitch, enumFacing.getEnumFacing(), false)) return;

        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, item, blockFace, enumFacing.getEnumFacing(), hitVec);
        PacketUtil.sendPacket(new C0APacketAnimation());

    }

    private List<Vec3> getPlacePossibilities() {
        final List<Vec3> possibilities = new ArrayList<>();
        final int range = (int) Math.ceil(3);

        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    final Block block = getBlockRelativeToPlayer(x, y, z);

                    if (!(block instanceof BlockAir)) {
                        for (int x2 = -1; x2 <= 1; x2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x + x2, mc.thePlayer.posY + y, mc.thePlayer.posZ + z));

                        for (int y2 = -1; y2 <= 1; y2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y + y2, mc.thePlayer.posZ + z));

                        for (int z2 = -1; z2 <= 1; z2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z + z2));
                    }
                }
            }
        }

        possibilities.removeIf(vec3 -> !(mc.theWorld.getBlockState(new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord)).getBlock() instanceof BlockAir) || (mc.thePlayer.posX == vec3.xCoord && mc.thePlayer.posY + 1 == vec3.yCoord && mc.thePlayer.posZ == vec3.zCoord));

        return possibilities;
    }

    private Block getBlockRelativeToPlayer(double offsetX, int offsetY, int offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ)).getBlock();
    }

    private EnumFacingOffset getEnumFacing(final Vec3 position) {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (!(mc.theWorld.getBlockState(new BlockPos(position.xCoord + x2, position.yCoord, position.zCoord)).getBlock() instanceof BlockAir)) {
                if (x2 > 0) {
                    return new EnumFacingOffset(EnumFacing.WEST, new Vec3(x2, 0, 0));
                } else {
                    return new EnumFacingOffset(EnumFacing.EAST, new Vec3(x2, 0, 0));
                }
            }
        }

        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (!(mc.theWorld.getBlockState(new BlockPos(position.xCoord, position.yCoord + y2, position.zCoord)).getBlock() instanceof BlockAir)) {
                if (y2 < 0) {
                    return new EnumFacingOffset(EnumFacing.UP, new Vec3(0, y2, 0));
                }
            }
        }

        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (!(mc.theWorld.getBlockState(new BlockPos(position.xCoord, position.yCoord, position.zCoord + z2)).getBlock() instanceof BlockAir)) {
                if (z2 < 0) {
                    return new EnumFacingOffset(EnumFacing.SOUTH, new Vec3(0, 0, z2));
                } else {
                    return new EnumFacingOffset(EnumFacing.NORTH, new Vec3(0, 0, z2));
                }
            }
        }

        return null;
    }

    public float[] calcRotations(){
        float[] rotations = getDirectionToBlock(blockFace.getX(), blockFace.getY(), blockFace.getZ(), enumFacing.getEnumFacing());

        yaw = rotations[0];
        pitch = rotations[1];

        return new float[]{yaw, pitch};
    }

    public float[] getDirectionToBlock(final double x, final double y, final double z, final EnumFacing enumfacing) {
        final EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = x + 0.5D;
        var4.posY = y + 0.5D;
        var4.posZ = z + 0.5D;
        var4.posX += (double) enumfacing.getDirectionVec().getX() * 0.5D;
        var4.posY += (double) enumfacing.getDirectionVec().getY() * 0.5D;
        var4.posZ += (double) enumfacing.getDirectionVec().getZ() * 0.5D;
        return getRotations(var4.posX, var4.posY, var4.posZ);
    }

    public float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + (double) player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public boolean lookingAtBlock(final BlockPos blockFace, final float yaw, final float pitch, final EnumFacing enumFacing, final boolean strict) {
        final MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTraceCustom(mc.playerController.getBlockReachDistance(), mc.timer.renderPartialTicks, yaw, pitch);
        if (movingObjectPosition == null) return false;
        final Vec3 hitVec = movingObjectPosition.hitVec;
        if (hitVec == null) return false;
        if ((hitVec.xCoord - blockFace.getX()) > 1.0 || (hitVec.xCoord - blockFace.getX()) < 0.0) return false;
        if ((hitVec.yCoord - blockFace.getY()) > 1.0 || (hitVec.yCoord - blockFace.getY()) < 0.0) return false;
        return !((hitVec.zCoord - blockFace.getZ()) > 1.0) && !((hitVec.zCoord - blockFace.getZ()) < 0.0) && (movingObjectPosition.sideHit == enumFacing || !strict);
    }

}
