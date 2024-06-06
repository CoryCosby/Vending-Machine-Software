package com.techelevator.model;

public class Gum extends Products implements Vendable{
    public Gum(String itemNumber, String itemName, double itemCost){
        super(itemNumber, itemName, itemCost);
    }
    @Override
    public void sound(){
        System.out.println("Chew Chew, Pop!");
    }
}
