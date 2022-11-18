package com.example.project4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StoreOrder implements Customizable {

    private ArrayList<Order> ordersArrayList;

    public StoreOrder() {
        this.ordersArrayList = new ArrayList<>();
    }

    public StoreOrder(ArrayList<Order> ordersArrayList) {
        this.ordersArrayList = ordersArrayList;
    }

    @Override
    public boolean add(Object obj) {
        if(obj instanceof Order){
            return this.ordersArrayList.add((Order) obj);
        }else{
            return false;
        }
    }

    @Override
    public boolean remove(Object obj) {
        if(obj instanceof Order){
            return this.ordersArrayList.remove((Order) obj);
        }else{
            return false;
        }
    }
    public ArrayList<Order> getOrdersArrayList() {
        return ordersArrayList;
    }
    public void export(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("exportedStoreOrders.txt"));
            writer.write("Store Orders\n\n");
            for(Order order : this.ordersArrayList){
                writer.write("\nOrder Number : "+order.getOrderNumber()+"\n");
                for(String string : order.getPizzaArrayListStringed()){
                    writer.write(string);
                }
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}