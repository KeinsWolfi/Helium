package me.helium9.exception;

import me.helium9.util.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

public class CommandException extends IllegalArgumentException{
    public CommandException(String message) {
        super(message);
        ChatUtil.addChatMsg(EnumChatFormatting.RED + message, false);
    }
}
