package com.techelevator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VendingMachine {

    public Map<String, Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(Map<String, Products> productsList) {
        this.productsList = productsList;
    }

    private Map<String, Products> productsList;
    private double balance;

    private double sales;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public void printMenu() {
        File menuFile = new File("vendingmachine.csv");

        try {
            Scanner reader = new Scanner(menuFile);
            while (reader.hasNextLine()) {
                String lineInput = reader.nextLine();
                String[] menu = lineInput.split("\\|");
                System.out.printf("%s  %-20s %4.2f  5 \n", menu[0], menu[1], Double.parseDouble(menu[2]));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    public Map<String, Products> createMap() {
        File menuFile = new File("vendingmachine.csv");
        Map<String, Products> productList = new HashMap<>();
        try {
            Scanner reader = new Scanner(menuFile);
            while (reader.hasNextLine()) {
                String lineInput = reader.nextLine();
                String[] menu = lineInput.split("\\|");
                if (menu[3].equals("Chip")) {
                    Chips chips = new Chips(menu[0], menu[1], Double.valueOf(menu[2]));
                    productList.put(menu[0], chips);
                } else if (menu[3].equals("Candy")) {
                    Candy candy = new Candy(menu[0], menu[1], Double.valueOf(menu[2]));
                    productList.put(menu[0], candy);
                } else if (menu[3].equals("Drink")) {
                    Drinks drink = new Drinks(menu[0], menu[1], Double.valueOf(menu[2]));
                    productList.put(menu[0], drink);
                } else if (menu[3].equals("Gum")) {
                    Gum gum = new Gum(menu[0], menu[1], Double.valueOf(menu[2]));
                    productList.put(menu[0], gum);
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}