package com.example.project4;

import java.util.ArrayList;

public class Order implements Customizable {

    private ArrayList<Pizza> pizzaArrayList;
    private ArrayList<String> pizzaArrayListStringed;
    private static int nextOrderNumber = 0;
    private int orderNumber = 0;

    public Order() {
        this.pizzaArrayList = new ArrayList<>();
        this.pizzaArrayListStringed = new ArrayList<>();
        orderNumber = nextOrderNumber;
        nextOrderNumber++;
    }
    public Order(ArrayList<Pizza> pizzaArrayList, ArrayList<String> pizzaArrayListStringed) {
        this.pizzaArrayList = pizzaArrayList;
        this.pizzaArrayListStringed = pizzaArrayListStringed;
        orderNumber = nextOrderNumber;
        nextOrderNumber++;
    }

    @Override
    public boolean add(Object obj) {
        if(obj instanceof Pizza){
            return pizzaArrayList.add((Pizza) obj);
        }else if(obj instanceof String) {
            return pizzaArrayListStringed.add((String) obj);
        }else{
            return false;
        }
    }

    @Override
    public boolean remove(Object obj) {
        if(obj instanceof Pizza){
            return pizzaArrayList.remove((Pizza) obj);
        }else if(obj instanceof String) {
            return pizzaArrayListStringed.remove((String) obj);
        }else{
            return false;
        }
    }
    public ArrayList<Pizza> getPizzaArrayList() {
        return pizzaArrayList;
    }
    public ArrayList<String> getPizzaArrayListStringed() {
        return pizzaArrayListStringed;
    }
    public int getOrderNumber() {
        return orderNumber;
    }
    public double getSubTotal(){
        double subTotal = 0;
        for(Pizza pizza : this.getPizzaArrayList()){
            subTotal += pizza.price();
        }
        return subTotal;
    }
    public double getOrderTotal(){
        return getSubTotal() + (getSubTotal()*(getTaxRate()));
    }
    public double getTaxRate(){
        return .006625;
    }

}
