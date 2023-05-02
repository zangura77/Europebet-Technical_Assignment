package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Product> currentCatalog;
    private final HashMap<String, ArrayList<Product>> purchasedProducts;
    private final HashMap<String, ArrayList<Product>> soldProducts;

    public CommandHandler() {
        this.currentCatalog = new HashMap<>();
        this.purchasedProducts = new HashMap<>();
        this.soldProducts = new HashMap<>();
    }

    public void saveProduct(String id, String name, int price) {
        if (currentCatalog.containsKey(id)) {
            currentCatalog.get(id).setName(name);
            currentCatalog.get(id).setPrice(price);
        } else {
            Product addedProduct = new Product(id, name, price);
            currentCatalog.put(id, addedProduct);
        }
    }

    public void purchaseProduct(String id, int quantity, int price) {
        String productName = currentCatalog.get(id).getName();
        int productQuantity = currentCatalog.get(id).getQuantity();
        if (!purchasedProducts.containsKey(id)) {
            purchasedProducts.put(id, new ArrayList<>());
        }
        purchasedProducts.get(id).add(new Product(id, productName, price, quantity));
        currentCatalog.get(id).setQuantity(productQuantity + quantity);
    }

    public void orderProduct(String id, int quantity) {
        String productName = currentCatalog.get(id).getName();
        int productQuantity = currentCatalog.get(id).getQuantity();
        int productPrice = currentCatalog.get(id).getPrice();
        if (!soldProducts.containsKey(id)) {
            soldProducts.put(id, new ArrayList<>());
        }
        soldProducts.get(id).add(new Product(id, productName, productPrice, quantity));
        currentCatalog.get(id).setQuantity(productQuantity - quantity);
    }

    public int getProductQuantity(String id) {
        if (currentCatalog.containsKey(id)) {
            return currentCatalog.get(id).getQuantity();
        } else {
            return -1;
        }
    }

    public int getAveragePurchasePrice(String id) {
        return getAveragePriceOf(id, purchasedProducts);
    }

    private int getAverageOrderPrice(String id) {
        return getAveragePriceOf(id, soldProducts);
    }

    private int getAveragePriceOf(String id, HashMap<String, ArrayList<Product>> map) {
        ArrayList<Product> productsList = map.get(id);
        int sumOfPrices = productsList.stream()
                .mapToInt(product -> product.getPrice() * product.getQuantity())
                .sum();
        int sumOfQuantities = productsList.stream().mapToInt(Product::getQuantity).sum();

        return sumOfPrices / sumOfQuantities;
    }

    public int getProductProfit(String id) {
        int profitPerUnit = getAverageOrderPrice(id) - getAveragePurchasePrice(id);
        ArrayList<Product> soldProductsList = soldProducts.get(id);
        int sumOfOrderedProductQuantities = soldProductsList
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();
        return sumOfOrderedProductQuantities * profitPerUnit;
    }

    public String getFewestProduct() {
        return currentCatalog.entrySet()
                .stream()
                .min(Comparator.comparingInt(element -> element.getValue().getQuantity()))
                .map(element -> element.getValue().getName())
                .orElse("");
    }

    public String getMostPopularProduct() {
        return soldProducts.entrySet()
                .stream()
                .max(Comparator.comparingInt(element -> element.getValue()
                        .stream()
                        .mapToInt(Product::getQuantity)
                        .sum()))
                .map(element -> element.getValue().get(0).getName())
                .orElse("");
    }

    public String getReport(){
        String result = "product_ID, product_name, quantity, price, cost_of_goods_sold\n";
        for (String prodId : soldProducts.keySet()){
            ArrayList<Product> soldProductsList = soldProducts.get(prodId);
            for (Product product : soldProductsList){
                result = result.concat(String.format("%s, %s, %d, %d, %d\n",
                        prodId, product.getName(), product.getQuantity(),
                        product.getPrice(), computeCostOfGoodsSold(product)));
            }
        }
        return result;
    }

    private int computeCostOfGoodsSold(Product product){
        return getAveragePurchasePrice(product.getId()) * product.getQuantity();
    }

    public void exportReportToCSV(String filepath){
        String report = getReport();
        File file = new File(filepath);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))){
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
