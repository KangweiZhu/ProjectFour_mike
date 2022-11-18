package com.example.project4;

/**
 * This is the interface that contains two method(add/remove toppings of one pizza) that need to be implemented.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public interface Customizable {
    /**
     * This method is for adding a topping to a pizza.
     *
     * @param obj The Topping that are going to be added.
     * @return true if successfully added, otherwise false.
     */
    boolean add(Object obj);

    /**
     * This method is for removing a topping from a pizza.
     *
     * @param obj The Topping that are going to be removed.
     * @return true if successfully removed, otherwise false.
     */
    boolean remove(Object obj);
}
