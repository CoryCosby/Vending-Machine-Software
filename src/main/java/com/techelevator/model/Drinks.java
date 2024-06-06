package com.techelevator.model;

public class Drinks extends Products implements Vendable{

    public Drinks(String itemNumber, String itemName, double itemCost){
        super(itemNumber,itemName,itemCost);
    }
    @Override
    public void sound(){
        System.out.println("Glug Glug, Chug Chug!");
    }
}
