package me.helium9.module.impl.player;

import me.helium9.event.impl.render.Event2D;
import me.helium9.event.impl.render.Event3D;
import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.util.PacketUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(
        name = "ChestStealer",
        description = "Steals all items from a chest",
        category = Category.Player,
        excludeArray = "false"
)
public class ChestStealer extends Module {
    private int randomDelay = (int) (Math.random() * 50);
    private final DoubleSetting delay = new DoubleSetting("Delay", 150, 10, 1000, 10);
    private List<Integer> items;
    private long lastTime = System.currentTimeMillis();

    public ChestStealer(){
        items = new ArrayList<>();
        addSettings(delay);
    }

    @Subscribe
    public final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
       if(mc.currentScreen instanceof GuiChest && Display.isVisible()){
           mc.mouseHelper.centerMouse();
           mc.mouseHelper.mouseGrab(false);
           mc.mouseHelper.mouseGrab(true);
       }
    });

    @Subscribe
    public final Listener<Event2D> on2D = new Listener<>(e -> {
        if(!(mc.currentScreen instanceof GuiChest)) return;
        final ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;

        for(int i = 0; i < chest.inventorySlots.size(); i++){
            final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(i);
            if(stack!=null){
                items.add(i);
            }
        }

        items.forEach(s -> {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(s);
            if(System.currentTimeMillis() > (lastTime+delay.getVal()+randomDelay)){
                mc.playerController.windowClick(chest.windowId, s, 0, 1, mc.thePlayer);
                lastTime = System.currentTimeMillis();
            }
            randomDelay = (int) (Math.random() * 50);
        });

        items.clear();


    });

}
