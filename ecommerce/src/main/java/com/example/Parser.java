package com.example;

import java.util.ArrayList;
import java.util.Collections;

public class Parser {
    //constants
    public static final int CORRECT = 1;
    public static final int NOT_ENOUGH_ARGUMENTS = 2;
    public static final int TOO_MANY_ARGUMENTS = 3;
    public static final int WRONG_COMMAND_NAME = 4;

    String[] commands = {"save_product", "purchase_product", "order_product", "get_quantity_of_product",
            "get_average_price", "get_product_profit", "get_fewest_product", "get_most_popular_product",
            "exit"};

    public static int parseCommand(String command, ArrayList<String> resultCommand) {
        int result = CORRECT;
        String[] splittedCommand = command.split(" ");
        switch (splittedCommand[0]) {
            case "save_product", "purchase_product":
                result = checkCommandArguments(splittedCommand, 3);
                break;
            case "order_product":
                result = checkCommandArguments(splittedCommand, 2);
                break;
            case "get_quantity_of_product", "get_average_price", "get_product_profit", "export_orders_report":
                result = checkCommandArguments(splittedCommand, 1);
                break;
            case "get_fewest_product", "get_most_popular_product", "get_orders_report", "exit":
                result = checkCommandArguments(splittedCommand, 0);
                break;
            default:
                result = WRONG_COMMAND_NAME;
                break;
        }

        Collections.addAll(resultCommand, splittedCommand);
        return result;
    }

    private static int checkCommandArguments(String[] splittedCommand, int numberOfArguments) {
        if (splittedCommand.length - 1 == numberOfArguments)
            return CORRECT;
        if (splittedCommand.length - 1 < numberOfArguments)
            return NOT_ENOUGH_ARGUMENTS;
        return TOO_MANY_ARGUMENTS;
    }

}
