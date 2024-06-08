package com.techelevator.model;

public abstract class Products {

    private String itemNumber;
    private String itemName;
    private double itemCost;
    private int inventoryCount=5;

    public String getItemNumber(){
        return itemNumber;
    }
    public String getItemName(){
        return itemName;
    }
    public double getItemCost(){
        return itemCost;
    }
    public int getInventoryCount(){
        return inventoryCount;
    }
    public void setInventoryCount(int inventoryCount){
        this.inventoryCount -= inventoryCount;
    }

    public abstract void sound();

    public Products(String itemNumber, String itemName, double itemCost){
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.inventoryCount = 5;

    }

}
