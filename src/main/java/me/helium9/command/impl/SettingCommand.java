package me.helium9.command.impl;

import me.helium9.HeliumMain;
import me.helium9.command.Command;
import me.helium9.command.CommandInfo;
import me.helium9.exception.CommandException;
import me.helium9.module.Module;
import me.helium9.settings.Setting;
import me.helium9.settings.impl.BooleanSetting;
import me.helium9.settings.impl.DoubleSetting;
import me.helium9.settings.impl.RGBSetting;
import me.helium9.settings.impl.ModeSetting;
import me.helium9.util.ChatUtil;
import me.helium9.util.notifications.NotificationManager;
import me.helium9.util.notifications.NotificationType;
import net.minecraft.util.EnumChatFormatting;

@CommandInfo(
        name= "setting",
        usage = "#setting <Module> <Setting> <Value>",
        description = "Sets a value of a Module.",
        aliases = {"set", "s", "settings"}
)
public class SettingCommand extends Command {
    @Override
    public void execute(String... args) throws CommandException {
        if(args.length != 3 && args.length!=6){
            ChatUtil.addChatMsg("&9 Usage: &c\"&b" + getUsage() + "&c\"");
            return;
        }

        Module mod = HeliumMain.INSTANCE.getMm().getModule(args[0]);

        if(mod == null) {
            ChatUtil.addChatMsg("&cNo Module was found!");
            return;
        }

        Setting setting = HeliumMain.INSTANCE.getSm().getSetting(mod, args[1]);

        if(setting == null){
            ChatUtil.addChatMsg("Setting not found.");
            return;
        }

        try {
            if(setting instanceof BooleanSetting){
                BooleanSetting bs = (BooleanSetting) setting;

                if(!(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false"))){
                    ChatUtil.addChatMsg("You must either give &atrue&r &4or &cfalse");
                    return;
                }

                NotificationManager.post(NotificationType.INFO, mod.name, "Set " + args[1] + " to " + args[2], 5000);

                bs.setState(args[2].equalsIgnoreCase("true"));
            }

            if(setting instanceof DoubleSetting){
                DoubleSetting ds = (DoubleSetting) setting;

                double chatDouble = Double.parseDouble(args[2]);
                ds.setVal(chatDouble);
                NotificationManager.post(NotificationType.INFO, mod.name, "Set " + args[1] + " to " + args[2], 5000);
                return;
            }

            if(setting instanceof ModeSetting){
                ModeSetting ms = (ModeSetting) setting;

                if(ms.getModes().stream().noneMatch(s -> s.equalsIgnoreCase(args[2]))){
                    ChatUtil.addChatMsg("Mode not found!");
                    return;
                }
                NotificationManager.post(NotificationType.INFO, mod.name, "Set " + args[1] + " to " + args[2], 5000);
                ms.setCurrentMode(args[2]);
            }

            if(setting instanceof RGBSetting){
                RGBSetting rgbs = (RGBSetting) setting;
                int r = Integer.parseInt(args[2]);
                int g = Integer.parseInt(args[3]);
                int b = Integer.parseInt(args[4]);
                int a = Integer.parseInt(args[5]);

                rgbs.setR(r);
                rgbs.setG(g);
                rgbs.setB(b);
                rgbs.setA(a);

                NotificationManager.post(NotificationType.INFO, mod.name, "Set " + args[1] + ".", 5000);
            }

        }catch (NumberFormatException e){
            ChatUtil.addChatMsg("You must pass a number as an argument!/Invalid number!");
            return;
        }

    }
}
