import com.example.project4.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This is the unit testing class. For testing the BuildYourOwn class.
 *
 * @author Kangwei Zhu, Michael Israel
 */
public class BuildYourOwnTest {
    /**
     * Default constructor of BuildYourOwnTest class
     */
    public BuildYourOwnTest() {
    }

    /**
     * This method will test if for Chicago Build your pizza, the crust is pan.
     * Correct situation.
     */
    @Test
    public void test_if_crust_is_pan_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        String expectedCrustName = "Pan";
        String crustName = pizza.getCrust().getCrustText();
        assertTrue(crustName.equals(expectedCrustName));
    }

    /**
     * This method will test if the crust that the build your own using is Pan
     * Wrong situation.
     */
    @Test
    public void test_if_crust_is_pan_false() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        String expectedCrustName = "Deep Dish";
        String crustName = pizza.getCrust().getCrustText();
        assertFalse(crustName.equals(expectedCrustName));
    }

    /**
     * This method will test if the price of small size chicago build your own pizza is $8.99
     * True situation
     */
    @Test
    public void test_if_small_size_price_eightNineNine_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 8.99;
        pizza.setSize(Size.SMALL);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of medium size chicago build your own pizza is $10.99
     * True condition
     */
    @Test
    public void test_if_small_size_price_tenNineNine_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 10.99;
        pizza.setSize(Size.MEDIUM);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of large size chicago build your own pizza is $12.99
     * True condition.
     */
    @Test
    public void test_if_small_size_price_twNineNine_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 12.99;
        pizza.setSize(Size.LARGE);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of small size chicago build your own pizza is $8.99
     * False situation
     */
    @Test
    public void test_if_small_size_price_eightNineNine_False() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 9.99;
        pizza.setSize(Size.SMALL);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test if the price of medium size chicago build your own pizza is $10.99
     * False condition
     */
    @Test
    public void test_if_small_size_price_tenNineNine_False() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 101.99;
        pizza.setSize(Size.MEDIUM);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test if the price of large size chicago build your own pizza is $12.99
     * False condition.
     */
    @Test
    public void test_if_small_size_price_twNineNine_False() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        double expectPrice = 122.99;
        pizza.setSize(Size.LARGE);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test the chicago style build your own pizza can add at most 7 Toppings
     */
    @Test
    public void test_chi_at_most_seven_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        boolean res = pizza.add(Topping.GREENPEPPERS);
        assertTrue(res);
    }

    /**
     * This method will test the chicago style build your own pizza can add at most 7 Toppings
     * False condition
     */
    @Test
    public void test_chi_at_most_seven_False() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        boolean res = pizza.add(Topping.SPINACH);
        assertFalse(res);
    }

    /**
     * This method will test if each topping worth $1.59.
     * True conditions.
     */
    @Test
    public void test_price_of_Topping_added_True() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        double price = pizza.price();
        double expectedPrice = 24.12;
        assertTrue(price == expectedPrice);
    }

    /**
     * This method will test if each topping worth $1.59.
     * False conditions.
     */
    @Test
    public void test_price_of_Topping_added_False() {
        ChicagoPizza chicagoPizza = new ChicagoPizza();
        BuildYourOwn pizza = (BuildYourOwn) chicagoPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        double price = pizza.price();
        double expectedPrice = 124.12;
        assertFalse(price == expectedPrice);
    }

    /**
     * This method will test if for NY Build your pizza, the crust is pan.
     * Correct situation.
     */
    @Test
    public void test_if_crust_is_pan_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        String expectedCrustName = "Hand Tossed";
        String crustName = pizza.getCrust().getCrustText();
        assertTrue(crustName.equals(expectedCrustName));
    }

    /**
     * This method will test if the crust that the NY build your own using is Pan
     * Wrong situation.
     */
    @Test
    public void test_if_crust_is_pan_false_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        String expectedCrustName = "Deep Dish";
        String crustName = pizza.getCrust().getCrustText();
        assertFalse(crustName.equals(expectedCrustName));
    }

    /**
     * This method will test if the price of small size NY build your own pizza is $8.99
     * True situation
     */
    @Test
    public void test_if_small_size_price_eightNineNine_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 8.99;
        pizza.setSize(Size.SMALL);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of medium size NY build your own pizza is $10.99
     * True condition
     */
    @Test
    public void test_if_small_size_price_tenNineNine_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 10.99;
        pizza.setSize(Size.MEDIUM);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of large size NY build your own pizza is $12.99
     * True condition.
     */
    @Test
    public void test_if_small_size_price_twNineNine_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 12.99;
        pizza.setSize(Size.LARGE);
        double price = pizza.price();
        assertTrue(expectPrice == price);
    }

    /**
     * This method will test if the price of small size NY build your own pizza is $8.99
     * False situation
     */
    @Test
    public void test_if_small_size_price_eightNineNine_False_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 9.99;
        pizza.setSize(Size.SMALL);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test if the price of medium size NY build your own pizza is $10.99
     * False condition
     */
    @Test
    public void test_if_small_size_price_tenNineNine_False_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 101.99;
        pizza.setSize(Size.MEDIUM);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test if the price of large size NY build your own pizza is $12.99
     * False condition.
     */
    @Test
    public void test_if_small_size_price_twNineNine_False_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        double expectPrice = 122.99;
        pizza.setSize(Size.LARGE);
        double price = pizza.price();
        assertFalse(expectPrice == price);
    }

    /**
     * This method will test the NY style build your own pizza can add at most 7 Toppings
     */
    @Test
    public void test_chi_at_most_seven_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        boolean res = pizza.add(Topping.GREENPEPPERS);
        assertTrue(res);
    }

    /**
     * This method will test the NY style build your own pizza can add at most 7 Toppings
     * False condition
     */
    @Test
    public void test_chi_at_most_seven_False_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        boolean res = pizza.add(Topping.SPINACH);
        assertFalse(res);
    }

    /**
     * This method will test if each NY buildyourown pizza topping worth $1.59.
     * True conditions.
     */
    @Test
    public void test_price_of_Topping_added_True_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        double price = pizza.price();
        double expectedPrice = 24.12;
        assertTrue(price == expectedPrice);
    }

    /**
     * This method will test if each NY buildYourOwn pizza topping worth $1.59.
     * False conditions.
     */
    @Test
    public void test_price_of_Topping_added_False_NY() {
        NYPizza nyPizza = new NYPizza();
        BuildYourOwn pizza = (BuildYourOwn) nyPizza.createBuildYourOwn();
        pizza.add(Topping.HAM);
        pizza.add(Topping.BEEF);
        pizza.add(Topping.SAUSAGE);
        pizza.add(Topping.CHEDDAR);
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.ONIONS);
        pizza.add(Topping.GREENPEPPERS);
        double price = pizza.price();
        double expectedPrice = 124.12;
        assertFalse(price == expectedPrice);
    }
}
