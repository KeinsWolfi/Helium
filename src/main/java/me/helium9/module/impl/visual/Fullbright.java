package me.helium9.module.impl.visual;

import me.helium9.event.impl.update.EventUpdate;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name="FullBright",
        description = "Makes things bright",
        category = Category.Visual,
        excludeArray = "false"
)
public final class Fullbright extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Gamma", "NightVision");
    private final DoubleSetting gamma = new DoubleSetting("Brightness", 10, 1, 100, 0.1);

    private float oldGamma;

    public Fullbright(){
        addSettings(mode, gamma);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        oldGamma=mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = oldGamma;
        mc.thePlayer.removePotionEffect(Potion.nightVision.id);
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e ->{
        this.setDisplayName(name + " " + EnumChatFormatting.GRAY + mode.getCurrentMode());

        switch (mode.getCurrentMode()){
            case "Gamma":
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                int biomeID = mc.theWorld.getBiomeGenForCoords(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)).biomeID;
                boolean inDesert = biomeID == 2 || biomeID == 17;

                mc.gameSettings.gammaSetting = inDesert ? oldGamma : (float) gamma.getVal();

                break;
            case "NightVision":
                mc.gameSettings.gammaSetting = oldGamma;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 0));
                break;
        }

    });
}
