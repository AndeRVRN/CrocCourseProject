package ru.ander.nc.core;

public class Main {
    public static void main(String[] args) {
        CommandHandler commandHandler = null;
        try {
            commandHandler = new CommandHandler();

            if (args.length > 0) {
                commandHandler.defineCommand(args);
            }
            commandHandler.requestCommandFromUser();
        } finally {
            if (commandHandler != null) {
                commandHandler.dispose();
            }
        }
    }


}
