package com.example.project4;

import java.util.ArrayList;

public class Meatzza extends Pizza{

    public Meatzza(ArrayList<Topping> selectedtoppings, Crust crust, Size size){
        super(selectedtoppings, crust, size);
    }

    @Override
    public boolean add(Object obj) {
        if(obj instanceof Topping){
            if(! getAvailableToppings().contains(obj)){
                return false;
            }else{
                this.getSelectedToppings().add((Topping)obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        if(obj instanceof Topping){
            if(getAvailableToppings().contains(obj)){
                return false;
            }else{
                this.getSelectedToppings().remove((Topping)obj);
                return false;
            }
        }
        return false;
    }

    @Override
    public double price() {
        if(this.getSize() == Size.SMALL){
            return 15.99;
        }else if(this.getSize() == Size.MEDIUM){
            return 17.99;
        }else if(this.getSize() == Size.LARGE){
            return 19.99;
        }
        return 0;
    }
}
