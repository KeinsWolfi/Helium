package me.helium9.module.impl.combat;

import me.helium9.HeliumMain;
import me.helium9.event.impl.packet.EventPacket;
import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventAttack;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.PacketUtil;
import me.helium9.util.Timer;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleInfo(
        name = "KillAura",
        description = "Aims at closest entity",
        category = Category.Combat,
        excludeArray = "false"
)
public class KillAura extends Module {

    private Entity target;

    private List<Entity> targets;

    private int index = 0;

    public Timer timer = new Timer();

    private final ModeSetting mode = new ModeSetting("Mode", "Single", "Multi","Legit", "Switch");

    private final ModeSetting sort = new ModeSetting("Sort", "Distance", "Health");

    private final DoubleSetting range = new DoubleSetting("Range", 3, 1, 6, 0.1);

    private final BooleanSetting targetEsp = new BooleanSetting("TargetESP", true);

    private float newYaw, newPitch;

    public KillAura(){
        addSettings(mode, range, sort, targetEsp);
        setKey(Keyboard.KEY_F);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public float[] getRot(Entity e){
        double deltaX = e.posX + (e.posX-e.lastTickPosX) - mc.thePlayer.posX,
                deltaZ = e.posZ + (e.posZ-e.lastTickPosZ) - mc.thePlayer.posZ,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX/deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY/distance));

        if(deltaX < 0 && deltaZ < 0){
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ/deltaX)));
        } else if (deltaX > 0 && deltaZ <0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ/deltaX)));
        }
        return new float[]{yaw,pitch};
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(e ->{

        if (e.isPost()) return;

        this.setDisplayName(name + " " + EnumChatFormatting.GRAY + mode.getCurrentMode() + " " + range.getVal());

        targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getVal() && entity != mc.thePlayer).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> !entity.isDead && ((EntityLivingBase) entity).deathTime <= 0).collect(Collectors.toList());

        switch (sort.getCurrentMode()){
            case "Distance":
                targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));
                break;
            case "Health":
                targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getHealth()));
        }


        if(!targets.isEmpty()) {
            target = targets.get(0);
            if(target.isDead || ((EntityLivingBase) target).deathTime > 0) return;

            float randomYaw = (getRand().nextFloat()-0.5f)*3;
            float randomPitch = (getRand().nextFloat()-0.5f)*3;
            int randomDelay = getRand().nextInt(50) - 25;
            switch (mode.getCurrentMode()){
                case "Single":
                    newYaw = getRot(target)[0] + randomYaw;
                    newPitch = getRot(target)[1] + randomPitch;
                    PacketUtil.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(newYaw, newPitch, mc.thePlayer.onGround));
                    mc.thePlayer.rotationYawHead = newYaw;
                    mc.thePlayer.rotationPitchHead = newPitch;
                    if(timer.hasTimeElapsed(100 + randomDelay, true)) {
                        mc.thePlayer.swingItem();

                        if(target.getDistanceToEntity(mc.thePlayer) < 6) {
                            EventAttack eventAttack = new EventAttack((EntityLivingBase) target);
                            HeliumMain.BUS.post(eventAttack);
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                        }
                    }
                    break;
                case "Multi":
                    if(timer.hasTimeElapsed(100, true)) {
                        for (Entity entity : targets) {
                            mc.thePlayer.swingItem();
                            EventAttack eventAttack = new EventAttack((EntityLivingBase) target);
                            HeliumMain.BUS.post(eventAttack);
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                        }
                    }
                    break;
                case "Legit":
                    newYaw = getRot(target)[0] + randomYaw;
                    newPitch = getRot(target)[1] + randomPitch;
                    mc.thePlayer.rotationYaw = newYaw;
                    mc.thePlayer.rotationPitch = newPitch;
                    if(timer.hasTimeElapsed(100 + randomDelay, true)) {
                        mc.thePlayer.swingItem();
                        if(target.getDistanceToEntity(mc.thePlayer) < 6 && mc.pointedEntity != null) {
                            EventAttack eventAttack = new EventAttack((EntityLivingBase) target);
                            HeliumMain.BUS.post(eventAttack);
                            mc.playerController.attackEntity(mc.thePlayer, target);
                        }
                    }
                    break;
                case "Switch":
                    if(index >= targets.size()) index = 0;
                    target = targets.get(index);
                    newYaw = getRot(target)[0] + randomYaw;
                    newPitch = getRot(target)[1] + randomPitch;
                    PacketUtil.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(newYaw, newPitch, mc.thePlayer.onGround));
                    mc.thePlayer.rotationYawHead = newYaw;
                    mc.thePlayer.rotationPitchHead = newPitch;
                    if(timer.hasTimeElapsed(100 + randomDelay, true)) {
                        mc.thePlayer.swingItem();
                        index++;

                        if(target.getDistanceToEntity(mc.thePlayer) < 6) {
                            EventAttack eventAttack = new EventAttack((EntityLivingBase) target);
                            HeliumMain.BUS.post(eventAttack);
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                        }
                    }
                    break;
            }

        }
        else {
            target = null;
        }
    });

    @Subscribe
    public final Listener<Event3D> on3D = new Listener<>(e -> {
        if(targetEsp.isState() && mode.getCurrentMode().equals("Single")){
            if(target != null){
                target.drawESP(255, 0, 0, 255);
            }
        } else if (targetEsp.isState() && mode.getCurrentMode().equals("Multi")){
            if(!targets.isEmpty()){
                for(Entity entity : targets){
                    entity.drawESP(255, 0, 0, 255);
                }
            }
        }
    });

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(packet -> {
        if(packet.getPacket() instanceof C03PacketPlayer){
            if(packet.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                C03PacketPlayer.C06PacketPlayerPosLook packetPlayerPosLook = (C03PacketPlayer.C06PacketPlayerPosLook) packet.getPacket();
                Vec3 pos = new Vec3(packetPlayerPosLook.getPositionX(), packetPlayerPosLook.getPositionY(), packetPlayerPosLook.getPositionZ());
                packet.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(pos.xCoord, pos.yCoord, pos.zCoord, newYaw, newPitch, packetPlayerPosLook.isOnGround()));
            }
            if(packet.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
                C03PacketPlayer.C05PacketPlayerLook packetPlayerLook = (C03PacketPlayer.C05PacketPlayerLook) packet.getPacket();
                packet.setPacket(new C03PacketPlayer.C05PacketPlayerLook(newYaw, newPitch, packetPlayerLook.isOnGround()));
            }
        }
    });
}
