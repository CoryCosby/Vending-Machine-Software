package com.techelevator.model;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VendingMachine {

    public Map<String, Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(Map<String, Products> productsList) {
        this.productsList = productsList;
    }

    private Map<String, Products> productsList;
    private BigDecimal balance = new BigDecimal(0);

    private double sales;

    public BigDecimal getBalance() {
        return balance;
    }

    public void feedMoney() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a whole dollar amount. (No decimal spaces)");

        BigDecimal money = scan.nextBigDecimal();

        while(money.scale() > 0) {
            System.out.println("Please enter a whole dollar amount");

            money = scan.nextBigDecimal();
        }

        this.balance = this.balance.add(money);

        File log = new File("log.txt");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(log, true));
            writer.printf("%s Feed Money: $%4.2f $%4.2f\n", getDate(), Double.valueOf(money.toString()), Double.valueOf(balance.toString()));

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeChange() {
        BigDecimal quarter = new BigDecimal(0);
        BigDecimal dime = new BigDecimal(0);
        BigDecimal nickel = new BigDecimal(0);
        double balancePlaceHolder = Double.valueOf(balance.toString());


        if (balance.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
            quarter = (balance.divide(BigDecimal.valueOf(0.25)));
            quarter = quarter.setScale(0, RoundingMode.DOWN);
            this.balance = this.balance.subtract(quarter.multiply(BigDecimal.valueOf(0.25)));
        }

        if (balance.compareTo(BigDecimal.valueOf(0.10)) >= 0) {
            dime = (balance.divide(BigDecimal.valueOf(0.10), RoundingMode.DOWN));
            dime = dime.setScale(0, RoundingMode.DOWN);
            this.balance = this.balance.subtract(dime.multiply(BigDecimal.valueOf(0.10)));
        }

        if (balance.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
            nickel = (balance.divide(BigDecimal.valueOf(0.05), RoundingMode.DOWN));
            nickel = nickel.setScale(0, RoundingMode.DOWN);
            this.balance = this.balance.subtract(nickel.multiply(BigDecimal.valueOf(0.05)));
        }


        System.out.println("Your change is $" +
                "" + balancePlaceHolder + "\n" + quarter + " quarters\n" + dime + " dimes\n" + nickel + " nickels\n");

        File log = new File("log.txt");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(log, true));
            writer.printf("%s Give Change: $%4.2f $%4.2f\n", getDate(), balancePlaceHolder, Double.valueOf(balance.toString()));

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public void printMenu() {
        //Set<String> keys = getProductsList().keySet();
        String[] keys = getProductsList().keySet().toArray(new String[0]);
        Arrays.sort(keys);

        System.out.println("*********************************");
        System.out.println("          PURCHASE MENU");
        System.out.println("*********************************");

        for (String key : keys) {
            if (productsList.get(key).getInventoryCount() == 0) {
                System.out.printf("%s  %-20s %4.2f  %s \n", productsList.get(key).getItemNumber(), productsList.get(key).getItemName(), productsList.get(key).getItemCost(), "SOLD OUT");
            } else {
                System.out.printf("%s  %-20s %4.2f  %d \n", productsList.get(key).getItemNumber(), productsList.get(key).getItemName(), productsList.get(key).getItemCost(), productsList.get(key).getInventoryCount());
            }
        }
        System.out.println();
    }

    public void makePurchase() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        boolean madePurchase = false;

        while (!productsList.containsKey(input)) {
            System.out.println("Make Your selection from the below items:");
            printMenu();
            input = scan.nextLine();

            if (!productsList.containsKey(input)) {
                System.out.println("Please enter a valid option.");
            }
            if (productsList.containsKey(input) && productsList.get(input).getInventoryCount() == 0) {
                System.out.println("Sorry this item is SOLD OUT.");
                input = "";
            }
        }

        if (Double.valueOf(balance.toString()) >= productsList.get(input).getItemCost() && productsList.containsKey(input)) {
            productsList.get(input).setInventoryCount(1);
            balance = balance.subtract(BigDecimal.valueOf(productsList.get(input).getItemCost()));
            productsList.get(input).sound();
            trackSales(input);
            madePurchase = true;
        } else {
            System.out.println("Insufficient Funds. Enter 1 to add funds or 2 to make another selection.");

            String feedOrBuy = "";

            while (!feedOrBuy.equals("1") || !feedOrBuy.equals("2")) {
                feedOrBuy = scan.nextLine();
                if (feedOrBuy.equals("1")) {
                    feedMoney();
                    break;
                } else if (feedOrBuy.equals("2")){
                    break;
                }
            }
        }

        System.out.println("\nYour new balance is $" + balance);

        File log = new File("log.txt");

        if (madePurchase) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(log, true));
                writer.printf("%s %s %s $%4.2f $%4.2f\n", getDate(), productsList.get(input).getItemName(), productsList.get(input).getItemNumber(), productsList.get(input).getItemCost(), Double.valueOf(balance.toString()));

                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void trackSales(String input) {
        sales += productsList.get(input).getItemCost();

        File salesReport = new File("salesReport.txt");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(salesReport));

            String[] keys = getProductsList().keySet().toArray(new String[0]);
            Arrays.sort(keys);
            for(String key : keys) {
                int temp = (productsList.get(key).getInventoryCount() - 5) * -1;
                writer.printf("%s|%d\n", productsList.get(key).getItemName(), temp);
            }
            writer.println("****************************************");
            writer.printf("There has been $%4.2f in sales.", getSales());


            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDate() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String reportName = String.format("%tm/%td/%tY %tI:%tM:%tS %Tp", date, date, date, time, time, time, time);
        return reportName;
    }
}