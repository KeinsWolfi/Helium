package me.helium9.module.impl.combat;

import me.helium9.HeliumMain;
import me.helium9.event.impl.update.EventAttack;
import me.helium9.event.impl.update.EventMotion;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.Timer;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;
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

    public Timer timer = new Timer();

    private final ModeSetting mode = new ModeSetting("Mode", "Single", "Multi","Legit");

    private final ModeSetting sort = new ModeSetting("Sort", "Distance", "Health");

    private final DoubleSetting range = new DoubleSetting("Range", 3, 1, 6, 0.1);

    public KillAura(){
        addSettings(mode, range, sort);
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

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getVal() &&  entity != mc.thePlayer).collect(Collectors.toList());

        switch (sort.getCurrentMode()){
            case "Distance":
                targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));
                break;
            case "Health":
                targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getHealth()));
        }


        if(!targets.isEmpty()) {
            Entity target = targets.get(0);
            if(target.isDead) return;
            switch (mode.getCurrentMode()){
                case "Single":
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, getRot(target)[0], getRot(target)[1], mc.thePlayer.onGround));
                    if(timer.hasTimeElapsed(100, true)) {
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
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                        }
                    }
                    break;
                case "Legit":
                    mc.thePlayer.rotationYaw = getRot(target)[0];
                    mc.thePlayer.rotationPitch = getRot(target)[1];
                    if(timer.hasTimeElapsed(100, true)) {
                        mc.thePlayer.swingItem();
                        if(target.getDistanceToEntity(mc.thePlayer) < 6) {
                            EventAttack eventAttack = new EventAttack((EntityLivingBase) target);
                            HeliumMain.BUS.post(eventAttack);
                            mc.playerController.attackEntity(mc.thePlayer, target);
                            //mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                        }
                    }
            }

        }
    });

}
