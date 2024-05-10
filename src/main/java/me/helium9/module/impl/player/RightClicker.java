package me.helium9.module.impl.player;

import me.helium9.event.impl.render.Event3D;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.util.MathUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

@ModuleInfo(
        name = "RightClicker",
        description = "Automatically clicks for you",
        category = Category.Player,
        excludeArray = "false"
)
public class RightClicker extends Module {

    private final DoubleSetting minCps = new DoubleSetting("Min CPS", 13, 1, 20, 0.1);
    private final DoubleSetting maxCps = new DoubleSetting("Min CPS", 17, 1, 20, 0.1);

    public RightClicker(){
        addSettings(minCps, maxCps);
    }

    @Subscribe
    private final Listener<Event3D> on3D = new Listener<>(e -> {
        if (mc.currentScreen == null) {
            Mouse.poll();

            if (Mouse.isButtonDown(1) && Math.random() * 50 <= minCps.getVal() + (MathUtil.RANDOM.nextDouble() * (maxCps.getVal() - minCps.getVal()))) {
                sendClick(1, true);
                sendClick(1, false);
            }
        }

    });

    private void sendClick(final int button, final boolean state) {
        final Minecraft mc = Minecraft.getMinecraft();
        final int keyBind = button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode();

        KeyBinding.setKeyBindState(button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode(), state);

        if (state) {
            KeyBinding.onTick(keyBind);
        }
    }

}
