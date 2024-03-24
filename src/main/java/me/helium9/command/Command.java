package me.helium9.command;

import lombok.Getter;
import me.helium9.HeliumMain;
import me.helium9.exception.CommandException;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Command {

    protected static final Minecraft mc = HeliumMain.INSTANCE.getMc();

    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;

    public Command(){
        final CommandInfo commandInfo = this.getClass().getAnnotation(CommandInfo.class);
        Validate.notNull(commandInfo, "CONFUSED ANNOTATION EXCEPTION");
        this.name = commandInfo.name();
        this.description = commandInfo.description();
        this.usage = commandInfo.usage();
        this.aliases = Arrays.asList(commandInfo.aliases());
    }

    public boolean isAlias(final String str){
        return aliases.stream().anyMatch(s -> s.equalsIgnoreCase(str));
    }

    public abstract void execute(String... args) throws CommandException;

}
