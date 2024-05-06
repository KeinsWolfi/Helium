package me.helium9;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import lombok.Getter;
import me.helium9.command.CommandManager;
import me.helium9.event.impl.input.EventKey;
import me.helium9.module.ModuleManager;
import me.helium9.module.impl.misc.ModuleNotifications;
import me.helium9.screens.altmgr.AltManager;
import me.helium9.screens.dropdown.DropDownGui;
import me.helium9.screens.rise.ClickGui.ClickGui;
import me.helium9.settings.SettingManager;
import me.helium9.util.render.shader.BlurUtil;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.opengl.Display;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Getter
public enum HeliumMain implements Subscriber {
    INSTANCE;

    private final Minecraft mc = Minecraft.getMinecraft();

    public static final EventBus BUS = EventManager.builder()
            .setName("root/Helium")
            .setSuperListeners()
            .build();

    private String
            name = "Helium9Client",
            version = "a0.0.1",
            commandprefix = "#",
            clientPrefix = "§b[H§3el§1iu§9m]§r ",
            authors = "Helium9";


    private DropDownGui gui;
    //private ClickGui gui;

    private ModuleManager mm;
    private CommandManager cm;
    private SettingManager sm;

    private AltManager am;

    private BlurUtil blurUtil;

    public final void init(){
        BUS.subscribe(this);
        Display.setTitle(name + "  |  " + version);
        try {

            InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("helium/Icon1.png")).getInputStream();
            InputStream inputStream1 = mc.getResourceManager().getResource(new ResourceLocation("helium/Icon2.png")).getInputStream();
            Display.setIcon(new ByteBuffer[]{mc.readImageToBuffer(inputStream), mc.readImageToBuffer(inputStream1)});
            Minecraft.logger.info("Icon loaded");

        } catch (Exception e) {
            e.printStackTrace();
        }

        mm = new ModuleManager();
        cm = new CommandManager();
        sm= new SettingManager();

        gui = new DropDownGui();
        //gui = new ClickGui();

        am = new AltManager();

        blurUtil = new BlurUtil();

        mm.getModule(ModuleNotifications.class).setToggled(true);
    }

    public final void shutdown(){
        BUS.unsubscribe(this);
    }

    @Subscribe
    private final Listener<EventKey> listener = new Listener<>( e -> {
        if (this.mm != null){
            mm.getModules().values().forEach(m -> {
                if(m.getKey() == e.getKey()){
                    m.toggle();
                }
            });
        }
    });
}
