package com.example.project4;

import java.util.ArrayList;

public class NewYorkPizza implements PizzaFactory {

    @Override
    public Pizza createDeluxe() {
        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();
        Crust crust = Crust.BROOKLYN;
        Size size = Size.LARGE;
        selectedToppings.add(Topping.SAUSAGE);
        selectedToppings.add(Topping.PEPPERONI);
        selectedToppings.add(Topping.GREENPEPPERS);
        selectedToppings.add(Topping.ONIONS);
        selectedToppings.add(Topping.MUSHROOMS);
        Deluxe pizza = new Deluxe(selectedToppings, crust, size);
        return pizza;
    }

    @Override
    public Pizza createBBQChicken() {
        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();
        Crust crust = Crust.THIN;
        Size size = Size.LARGE;
        selectedToppings.add(Topping.BBQCHICKEN);
        selectedToppings.add(Topping.GREENPEPPERS);
        selectedToppings.add(Topping.PROVOLONE);
        selectedToppings.add(Topping.CHEDDAR);
        BBQChicken pizza = new BBQChicken(selectedToppings, crust, size);
        return pizza;
    }

    @Override
    public Pizza createMeatzza() {
        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();
        Crust crust = Crust.HANDTOSSED;
        Size size = Size.LARGE;
        selectedToppings.add(Topping.SAUSAGE);
        selectedToppings.add(Topping.PEPPERONI);
        selectedToppings.add(Topping.BEEF);
        selectedToppings.add(Topping.HAM);
        Meatzza pizza = new Meatzza(selectedToppings, crust, size);
        return pizza;
    }

    @Override
    public Pizza createBuildYourOwn() {
        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();
        Crust crust = Crust.HANDTOSSED;
        Size size = Size.LARGE;
        BuildYourOwn pizza = new BuildYourOwn(selectedToppings, crust, size);
        return pizza;
    }
}
