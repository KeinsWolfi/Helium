package me.helium9.module.impl.combat;

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
        name = "LeftClicker",
        description = "Automatically clicks for you",
        category = Category.Combat,
        excludeArray = "false"
)
public class LeftClicker extends Module {

    private final DoubleSetting minCps = new DoubleSetting("Min CPS", 8, 1, 20, 0.1);
    private final DoubleSetting maxCps = new DoubleSetting("Min CPS", 12, 1, 20, 0.1);

    public LeftClicker(){
        addSettings(minCps, maxCps);
    }

    @Subscribe
    private final Listener<Event3D> on3D = new Listener<>(e -> {
        if (Mouse.isButtonDown(0) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            mc.gameSettings.keyBindAttack.setPressed(true);
            return;
        }

        if (mc.currentScreen == null && !mc.thePlayer.isBlocking()) {
            Mouse.poll();

            if (Mouse.isButtonDown(0) && Math.random() * 50 <= minCps.getVal() + (MathUtil.RANDOM.nextDouble() * (maxCps.getVal() - minCps.getVal()))) {
                sendClick(0, true);
                sendClick(0, false);
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
