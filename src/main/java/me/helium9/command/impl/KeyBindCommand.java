package me.helium9.command.impl;

import me.helium9.HeliumMain;
import me.helium9.command.Command;
import me.helium9.command.CommandInfo;
import me.helium9.exception.CommandException;
import me.helium9.module.Module;
import me.helium9.module.ModuleManager;
import me.helium9.util.ChatUtil;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

@CommandInfo(
        name = "keybind",
        usage = "#keybind",
        description = "Returns all Keybinds.",
        aliases = {"key","binds","keybinds"}
)
public class KeyBindCommand extends Command {

    @Override
    public void execute(String... args) throws CommandException {
        ChatUtil.addChatMsg("Here are all Modules.");
        for(Module mod : HeliumMain.INSTANCE.getMm().getModules().values()){
            if(mod.getKey() != Keyboard.KEY_NONE) {
                ChatUtil.addChatMsg(EnumChatFormatting.YELLOW + "  " + mod.getName() + EnumChatFormatting.GRAY + " - " + Keyboard.getKeyName(mod.getKey()));
            }
        }
    }
}
