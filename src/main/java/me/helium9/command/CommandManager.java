package me.helium9.command;

import lombok.Getter;
import me.helium9.HeliumMain;
import me.helium9.exception.CommandException;
import me.helium9.util.ChatUtil;
import org.reflections.Reflections;

import java.util.*;

@Getter
public final class CommandManager {

    public static final String COMMAND_PREFIX = HeliumMain.INSTANCE.getCommandprefix();

    private Map<String, Command> commands;

    public CommandManager(){
        this.init();
    }

    private void init(){
        this.commands = new HashMap<>();
        try{
            this.register();
        }catch (CommandException e){
            throw new CommandException("Failed to register Commands :(");
        }
    }

    public boolean handleCommand(final String message){
        if(message.isEmpty()) return false;

        final String[] args = message.substring(1).split(" ");

        if(message.equalsIgnoreCase(COMMAND_PREFIX)){
            ChatUtil.addChatMsg(String.format("Type %shelp to get a list of available commands\n", COMMAND_PREFIX), true);
            return true;
        }

        try {
            this.getCommand(args[0]).orElseThrow(() -> new CommandException(String
                    .format("ERROR: \n Command \"%s\" not found! Use %shelp to see a list of available commands", args[0], COMMAND_PREFIX)))
                    .execute(Arrays.copyOfRange(args, 1, args.length));
        }catch (CommandException ignored){

        }
        return true;
    }

    private void register() throws CommandException {
        final Reflections reflections = new Reflections("me.helium9.command.impl");
        final Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for(Class<? extends Command> command : classes){
            try{
                final Command comm = command.newInstance();
                this.commands.put(comm.getName(), comm);
            }catch (InstantiationException | IllegalAccessException e){
                System.err.println("Failed to init command: " + command.getName() + "!");
            }
        }
    }

    public Optional<Command> getCommand(final String commandName){
        final Command command = this.commands.get(commandName);
        if(command!=null){
            return Optional.of(command);
        }else {
            return Optional.ofNullable(this.commands.values()
                    .stream()
                    .filter(cmd -> cmd.isAlias(commandName))
                    .findFirst()
                    .orElseThrow(() ->
                            new CommandException("No command found.")));
        }
    }

}
