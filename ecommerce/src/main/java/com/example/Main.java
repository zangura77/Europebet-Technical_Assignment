package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String command = sc.nextLine();
            ArrayList<String> parsedCommand = new ArrayList<>();
            int errorCode = Parser.parseCommand(command, parsedCommand);
            String commandName = parsedCommand.get(0);

            if (errorCode == Parser.NOT_ENOUGH_ARGUMENTS) {
                System.out.println("Not enough arguments");
                continue;
            } else if (errorCode == Parser.TOO_MANY_ARGUMENTS) {
                System.out.println("Too many arguments");
                continue;
            } else if (errorCode == Parser.WRONG_COMMAND_NAME) {
                System.out.println("Wrong command name");
                continue;
            }

            if (commandName.equals("exit")) {
                break;
            }

            CommandExecutor.executeCommand(parsedCommand, commandHandler);
        }
    }
}