package me.helium9.module.impl.visual;

import me.helium9.event.impl.input.EventMouse;
import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.util.render.world.BoxESPUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Freecam",
        description = "Allows you to move outside of your Player.",
        category = Category.Visual,
        excludeArray = "false"
)
public class Freecam extends Module {

    public static EntityOtherPlayerMP en = null;
    private final double toRad = 0.017453292519943295D;
    private int[] lcc = new int[]{Integer.MAX_VALUE, 0};
    private final float[] sAng = new float[]{0.0F, 0.0F};

    private final DoubleSetting speed = new DoubleSetting("Speed", 1, 0.5, 10, 0.5);

    public Freecam(){
        addSettings(speed);
        setKey(Keyboard.KEY_U);
    }

    @Override
    public void onEnable(){
        en = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        en.copyLocationAndAnglesFrom(mc.thePlayer);
        this.sAng[0] = en.rotationYawHead = mc.thePlayer.rotationYawHead;
        this.sAng[1] = mc.thePlayer.rotationPitch;
        en.setVelocity(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        en.setInvisible(true);
        en.inventory = mc.thePlayer.inventory;
        mc.theWorld.addEntityToWorld(-8008, en);
        mc.setRenderViewEntity(en);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(en!=null){
            mc.setRenderViewEntity(mc.thePlayer);
            mc.thePlayer.rotationYaw = mc.thePlayer.rotationYawHead = this.sAng[0];
            mc.thePlayer.rotationPitch = this.sAng[1];
            mc.theWorld.removeEntity(en);
            en = null;
        }
        this.lcc = new int[]{Integer.MAX_VALUE, 0};

        int x = mc.thePlayer.chunkCoordX;
        int z = mc.thePlayer.chunkCoordZ;
        super.onDisable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        mc.thePlayer.setSprinting(false);
        mc.thePlayer.moveForward = 0.0F;
        mc.thePlayer.moveStrafing = 0.0F;
        en.rotationYaw = en.rotationYawHead = mc.thePlayer.rotationYaw;
        en.rotationPitch = mc.thePlayer.rotationPitch;
        double s = speed.getVal() * 0.2;
        EntityOtherPlayerMP var10000;
        double rad;
        double dx;
        double dz;
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX -= dx;
            var10000 = en;
            var10000.posZ -= dz;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            rad = (double)(en.rotationYawHead - 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            rad = (double)(en.rotationYawHead + 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            var10000 = en;
            var10000.posY += 0.93D * s;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            var10000 = en;
            var10000.posY -= 0.93D * s;
        }

        mc.thePlayer.setSneaking(false);
        if (this.lcc[0] != Integer.MAX_VALUE && (this.lcc[0] != en.chunkCoordX || this.lcc[1] != en.chunkCoordZ)) {
            int x = en.chunkCoordX;
            int z = en.chunkCoordZ;
            mc.theWorld.markBlockRangeForRenderUpdate(x * 16, 0, z * 16, x * 16 + 15, 256, z * 16 + 15);
        }

        this.lcc[0] = en.chunkCoordX;
        this.lcc[1] = en.chunkCoordZ;
    });

    @Subscribe
    public final Listener<Event3D> on3D = new Listener<>(e -> {
        mc.thePlayer.renderArmPitch = mc.thePlayer.prevRenderArmPitch = 700.0F;
        BoxESPUtil.RenderEntityBox(mc.thePlayer, 0, 255, 100, 255);
    });

    @Subscribe
    public final Listener<EventMouse> onMouse = new Listener<>(e -> {
        e.cancel();
    });

}
