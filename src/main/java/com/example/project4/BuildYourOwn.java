package com.example.project4;

import java.util.ArrayList;

public class BuildYourOwn extends Pizza{

    public BuildYourOwn(ArrayList<Topping> selectedtoppings, Crust crust, Size size){
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
            return 8.99 + getPriceOfToppings();
        }else if(this.getSize() == Size.MEDIUM){
            return 10.99 + getPriceOfToppings();
        }else if(this.getSize() == Size.LARGE){
            return 12.99 + getPriceOfToppings();
        }
        return 0;
    }

    private double getPriceOfToppings(){
        return (this.getSelectedToppings().size() * 1.59);
    }
}
