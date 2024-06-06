package com.techelevator.model;

public class Candy extends Products implements Vendable{

    public Candy(String itemNumber, String itemName, double itemCost){
        super(itemNumber,itemName,itemCost);
    }
    @Override
    public void sound(){
        System.out.println("Munch Munch, Mmm Mmm Good!");
    }
}
