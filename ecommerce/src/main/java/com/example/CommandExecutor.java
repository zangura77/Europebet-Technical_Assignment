package com.example;

import java.util.ArrayList;

public class CommandExecutor {

    public static void executeCommand(ArrayList<String> parsedCommand, CommandHandler commandHandler) {
        String commandName = parsedCommand.get(0);

        switch (commandName) {
            case "save_product":
                int price = Integer.parseInt(parsedCommand.get(3));
                commandHandler.saveProduct(parsedCommand.get(1), parsedCommand.get(2), price);
                break;
            case "purchase_product":
                int quantity = Integer.parseInt(parsedCommand.get(2));
                price = Integer.parseInt(parsedCommand.get(3));
                commandHandler.purchaseProduct(parsedCommand.get(1), quantity, price);
                break;
            case "order_product":
                quantity = Integer.parseInt(parsedCommand.get(2));
                commandHandler.orderProduct(parsedCommand.get(1), quantity);
                break;
            case "get_quantity_of_product":
                int productQuantity = commandHandler.getProductQuantity(parsedCommand.get(1));
                if (productQuantity == -1) {
                    System.out.println("No such product in catalog");
                } else {
                    System.out.println(productQuantity);
                }
                break;
            case "get_average_price":
                int averagePrice = commandHandler.getAveragePurchasePrice(parsedCommand.get(1));
                if (averagePrice == -1) {
                    System.out.println("No such product in catalog");
                } else {
                    System.out.println(averagePrice);
                }
                break;
            case "get_product_profit":
                int productProfit = commandHandler.getProductProfit(parsedCommand.get(1));
                if (productProfit == -1) {
                    System.out.println("No such product in catalog");
                } else {
                    System.out.println(productProfit);
                }
                break;
            case "get_fewest_product":
                String fewestProductName = commandHandler.getFewestProduct();
                if (fewestProductName.equals("")) {
                    System.out.println("Catalog is empty");
                } else {
                    System.out.println(fewestProductName);
                }
                break;
            case "get_most_popular_product":
                String popularProductName = commandHandler.getMostPopularProduct();
                if (popularProductName.equals("")) {
                    System.out.println("Orders are empty");
                } else {
                    System.out.println(popularProductName);
                }
                break;
            case "get_orders_report":
                String report = commandHandler.getReport();
                System.out.println(report);
                break;
            case "export_orders_report":
                commandHandler.exportReportToCSV(parsedCommand.get(1));
                break;
        }
    }
}
