package com.example.project4;

import java.util.ArrayList;

/**
 * This class extends from the Pizza class. It presents the real world build your own pizza meal plan.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class BuildYourOwn extends Pizza {
    /**
     * Default constructor of BuildYourOwn class.
     */
    public BuildYourOwn() {
    }

    /**
     * Parameterized constructor of BuilderYourOwn class.
     * Setting up basic attributes of BuilderYourOwn Pizza.
     *
     * @param selectedtoppings The ArrayList that stores the toppings that are selected.
     * @param crust            The Crust that are used for making this pizza.
     * @param size             The size of this pizza.
     */
    public BuildYourOwn(ArrayList<Topping> selectedtoppings, Crust crust, Size size) {
        super(selectedtoppings, crust, size);
    }

    /**
     * This method overrides from
     * @param obj The Topping that are going to be added.
     * @return
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Topping) {
            if (!getAvailableToppings().contains(obj)) {
                return false;
            } else {
                this.getSelectedToppings().add((Topping) obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Topping) {
            if (getAvailableToppings().contains(obj)) {
                return false;
            } else {
                this.getSelectedToppings().remove((Topping) obj);
                return false;
            }
        }
        return false;
    }

    @Override
    public double price() {
        if (this.getSize() == Size.SMALL) {
            return 8.99 + getPriceOfToppings();
        } else if (this.getSize() == Size.MEDIUM) {
            return 10.99 + getPriceOfToppings();
        } else if (this.getSize() == Size.LARGE) {
            return 12.99 + getPriceOfToppings();
        }
        return 0;
    }

    private double getPriceOfToppings() {
        return (this.getSelectedToppings().size() * 1.59);
    }
}
