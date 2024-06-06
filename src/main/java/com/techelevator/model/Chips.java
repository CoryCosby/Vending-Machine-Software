package com.techelevator.model;

public class Chips extends Products implements Vendable{

    public Chips(String itemNumber, String itemName, double itemCost){
        super(itemNumber,itemName,itemCost);
    }
    @Override
    public void sound(){
        System.out.println("Crunch Crunch, It's Yummy!");
    }
}
