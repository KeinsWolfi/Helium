package me.helium9.util;

import me.helium9.HeliumMain;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ChatUtil {

    public static String fix(String string){
        return string.replace("&", "§")
                .replace(">>", "»")
                .replace("<<", "«")
                .replace("->", "→")
                .replace("<-", "←");
    }

    public static void addChatMsg(final String msg){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(fix(HeliumMain.INSTANCE.getClientPrefix()) + fix(msg)));
    }

    public static void addChatMsg(final String msg, final boolean prefix){
        if(prefix){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(fix(HeliumMain.INSTANCE.getClientPrefix()) + fix(msg)));
        }else{
            addChatMsg(msg);
        }
    }

}
