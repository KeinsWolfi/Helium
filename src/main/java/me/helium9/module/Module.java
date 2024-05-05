package me.helium9.module;

import lombok.Getter;
import lombok.Setter;
import me.helium9.HeliumMain;
import me.helium9.module.impl.misc.GUISounds;
import me.helium9.module.impl.misc.ModuleNotifications;
import me.helium9.settings.Setting;
import me.helium9.util.ChatUtil;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public abstract class Module implements Subscriber {
    Random rand = new Random();

    private boolean toggled;

    @Setter
    private String displayName;

    @Setter
    private boolean sounds;

    public final String name, description, excludeArray;
    public final Category category;

    private final List<Setting> sList = new ArrayList<>();


    @Setter
    private int key;

    protected final Minecraft mc = HeliumMain.INSTANCE.getMc();
    protected final FontRenderer fr = mc.fontRendererObj;

    @Getter
    private boolean selectingHotkey = false;

    public Module(){
        ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        Validate.notNull(info, "CONFUSED ANNOTATION EXCEPTION");

        this.name = info.name();
        this.description = info.description();
        this.category = info.category();
        this.excludeArray = info.excludeArray();
    }


    protected void addSetting(Setting setting){
        sList.add(setting);
    }

    protected void addSettings(Setting... settings){
        sList.addAll(Arrays.asList(settings));
    }

    public void onEnable(){
        if(HeliumMain.INSTANCE.getMm().getModule(ModuleNotifications.class).isToggled())
            ChatUtil.addChatMsg(CategoryChatFormatting(this) + this.name + " §aenabled§r.");
        if (HeliumMain.INSTANCE.getMm().getModule(GUISounds.class).isToggled())
            mc.thePlayer.playSound("random.click", 1F, 1F);

        HeliumMain.BUS.subscribe(this);
    }

    public void onDisable(){
        if(HeliumMain.INSTANCE.getMm().getModule(ModuleNotifications.class).isToggled())
            ChatUtil.addChatMsg(CategoryChatFormatting(this) + this.name + " §cdisabled§r.");
        if (HeliumMain.INSTANCE.getMm().getModule(GUISounds.class).isToggled())
            mc.thePlayer.playSound("random.click", 1F, 0.5F);
        HeliumMain.BUS.unsubscribe(this);
    }

    public void onToggle(){

    }

    public void toggle(){
        onToggle();
        if(toggled){
            toggled=false;
            onDisable();
        }else {
            toggled=true;
            onEnable();
        }
    }

    public void setToggled(boolean toggled){
        onToggle();
        if(toggled){
            this.toggled=true;
            onEnable();
        }else {
            this.toggled=false;
            onDisable();
        }
    }

    public String getDisplayName(){
        return displayName == null ? name : displayName;
    }

    public final String CategoryChatFormatting(Module m){
        String colorChar;
        colorChar = "§e";
        if(m.category.equals(Category.Combat)){
            colorChar="§c";
        } else if (m.category.equals(Category.Visual)) {
            colorChar="§b";
        }
        return colorChar;
    }
        // Other methods...

    public void startHotkeySelection() {
            selectingHotkey = true;
    }

    public void stopHotkeySelection() {
            selectingHotkey = false;
    }

    public void setDisplayName(String name, String params) {
        this.displayName = name + " " + EnumChatFormatting.GRAY + params;
    }

}
