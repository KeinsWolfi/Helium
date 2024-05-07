package me.helium9.command.impl;

import me.helium9.HeliumMain;
import me.helium9.command.Command;
import me.helium9.command.CommandInfo;
import me.helium9.exception.CommandException;
import me.helium9.module.Module;
import me.helium9.screens.dropdown.Frame;
import me.helium9.screens.dropdown.ModuleButtons;
import me.helium9.util.notifications.NotificationManager;
import me.helium9.util.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

@CommandInfo(
        name = "bind",
        usage = "#bind",
        description = "Binds a module to a key.",
        aliases = {"binds"}
)
public class BindCommand extends Command {
    @Override
    public void execute(String... args) throws CommandException {
        if(args.length > 2) {
            throw new CommandException("Invalid arguments! Usage: #bind <module> <key>");
        }

        Module module = HeliumMain.INSTANCE.getMm().getModule(args[0]);

        if(module == null) {
            throw new CommandException("Module not found!");
        }

        String key = args[1].toUpperCase();

        if(key.length() > 1) {
            if(args[1].equalsIgnoreCase("rshift") || args[1].equalsIgnoreCase("rcontrol") || args[1].equalsIgnoreCase("ralt")) {
                key = key.substring(1);
            } else {
                throw new CommandException("Invalid key!");
            }
        }

        int keyCode = Keyboard.getKeyIndex(key);

        module.setKey(keyCode);

        NotificationManager.post(NotificationType.INFO, "Bound", "Bound " + key + " to " + module.getName() + ".", 5000);

    }
}
