package me.helium9.command.impl;

import me.helium9.HeliumMain;
import me.helium9.command.Command;
import me.helium9.command.CommandInfo;
import me.helium9.command.CommandManager;
import me.helium9.exception.CommandException;
import me.helium9.util.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

@CommandInfo(
        name="help",
        usage= "#help <command>",
        description = "Help command if you didnt notice",
        aliases = {"hlp", "h", "hlep"}
)
public final class HelpCommand extends Command {
    @Override
    public void execute(String... args) throws CommandException {
        if(args.length>0){
            final Command command = HeliumMain.INSTANCE.getCm().getCommand(args[0])
                    .orElseThrow(()->
                        new CommandException(String.format(EnumChatFormatting.RED + "ERROR: Command \"%s\" not found!\n",args[0] )));
            ChatUtil.addChatMsg("Usage of " + EnumChatFormatting.YELLOW + command.getName() + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.GRAY + command.getUsage() + EnumChatFormatting.WHITE);
            return;
        }
        HeliumMain.INSTANCE.getCm().getCommands().values().stream().forEach(command -> ChatUtil.addChatMsg(String.format(EnumChatFormatting.YELLOW + "%s " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.GRAY + "%s", " " + command.getName(), command.getDescription()), true));
    }
}
