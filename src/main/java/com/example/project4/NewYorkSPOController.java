package com.example.project4;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The controller class for NewYorkSPO view fxml file. It provides services for the transaction of New York Styled Pizza
 *
 * @author Michael Israel, Kangwei Zhu.
 */
public class NewYorkSPOController {
    private final Integer MAXTOPPINGS = 7;
    @FXML
    private Pane mainPane;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField crustType;
    @FXML
    private ListView<String> availableToppings;
    @FXML
    private ListView<String> selectedToppings;
    @FXML
    private TextField pizzaPrice;
    @FXML
    private RadioButton largePizza;
    @FXML
    private Button addTopping;
    @FXML
    private Button removeTopping;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private PizzaFactory pizzaFactory = new NewYorkPizza();
    private Pizza pizza;
    private ChicagoSPOController chicagoSPOController;
    private NewYorkSPOController newYorkSPOController;
    private Order userOrder;
    private CurrentOrderController currentOrderController;
    private StoreOrder storeOrdersArrayList;
    private Alert alertError = new Alert(Alert.AlertType.ERROR);

    /**
     * The default constructor for NewYorkSPOController class.
     */
    public NewYorkSPOController() {
    }

    /**
     * It is used to initialize controllers after its root element is fully processed.
     * It will share values between diffferent controllers.
     *
     * @param currentOrderController The Controller for currentOrder view.
     * @param chicagoSPOController   The Controller for chicagoSPO view.
     * @param newYorkSPOController   The Controller for newYorkSPO view.
     */
    public void initialize(CurrentOrderController currentOrderController, ChicagoSPOController chicagoSPOController,
                           NewYorkSPOController newYorkSPOController) {
        this.currentOrderController = currentOrderController;
        if (this.currentOrderController != null) {
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
        this.chicagoSPOController = chicagoSPOController;
        this.newYorkSPOController = newYorkSPOController;
        if (this.chicagoSPOController != null) {
            userOrder = chicagoSPOController.getUserOrder();
        } else if (this.newYorkSPOController != null) {
            userOrder = newYorkSPOController.getUserOrder();
        } else {
            userOrder = new Order();
        }
        comboBox.setItems(FXCollections.observableArrayList(
                "Deluxe",
                "BBQ Chicken",
                "Meatzza",
                "Build Your Own"
        ));
        comboBox.setPromptText("Choose Flavor:");
    }

    /**
     * When click the back button, it will jump to the home screen.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void goToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();
            mainController.initialize(currentOrderController, chicagoSPOController, this);
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setting up the Topping listView and calls the function that instantiate the Flavor chosen.
     *
     * @param event An event that represent some type of action
     */
    @FXML
    private void flavorChosen(ActionEvent event) {
        availableToppings.getItems().clear();
        selectedToppings.getItems().clear();
        largePizza.setSelected(true);
        if (comboBox.getValue().equalsIgnoreCase("Deluxe")) {
            pizza = pizzaFactory.createDeluxe();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);
        } else if (comboBox.getValue().equalsIgnoreCase("BBQ Chicken")) {
            pizza = pizzaFactory.createBBQChicken();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);
        } else if (comboBox.getValue().equalsIgnoreCase("Meatzza")) {
            pizza = pizzaFactory.createMeatzza();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);
        } else if (comboBox.getValue().equalsIgnoreCase("Build Your Own")) {
            pizza = pizzaFactory.createBuildYourOwn();
            availableToppings.setDisable(false);
            addTopping.setDisable(false);
            removeTopping.setDisable(false);
        }
        instantiateFlavorChosen(pizza);
        printPizzaPrice();
    }

    /**
     * Add a topping to a pizza.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void addTopping(ActionEvent event) {
        String topping = availableToppings.getSelectionModel().getSelectedItem();
        if (topping != null) {
            if (selectedToppings.getItems().size() < MAXTOPPINGS) {
                selectedToppings.getItems().add(topping);
                availableToppings.getItems().remove(topping);
                pizza.add(getToppingName(topping));
                printPizzaPrice();
            } else {
                alertError.setContentText("Sorry you cannot have more than 7 toppings on a pizza");
                alertError.show();
            }
        }
    }

    /**
     * Try to remove a topping of one pizza.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void removeTopping(ActionEvent event) {
        String topping = selectedToppings.getSelectionModel().getSelectedItem();
        if (topping != null) {
            availableToppings.getItems().add(topping);
            selectedToppings.getItems().remove(topping);
            pizza.remove(getToppingName(topping));
            printPizzaPrice();
        }
    }

    /**
     * Set the size of pizza to small size and call the method printPizzaPrice().
     *
     * @param event An event that represents some type of action.
     */
    @FXML
    private void smallPizza(ActionEvent event) {
        if (pizza != null) {
            pizza.setSize(Size.SMALL);
            printPizzaPrice();
        }
    }

    /**
     * Set the size of pizza to medium size and call the method printPizzaPrice().
     *
     * @param event An event that represents some type of action.
     */
    @FXML
    private void mediumPizza(ActionEvent event) {
        if (pizza != null) {
            pizza.setSize(Size.MEDIUM);
            printPizzaPrice();
        }
    }

    /**
     * Set the size of pizza to large size and call the method printPizzaPrice().
     *
     * @param event An event that represents some type of action.
     */
    @FXML
    private void largePizza(ActionEvent event) {
        if (pizza != null) {
            pizza.setSize(Size.LARGE);
            printPizzaPrice();
        }
    }

    /**
     * Add the current pizza into the cart.
     *
     * @param event An event that represents some type of action.
     */
    @FXML
    private void addToOrder(ActionEvent event) {
        if (pizza != null) {
            try {
                FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("CurrentOrder-view.fxml"));
                root = loader.load();
                CurrentOrderController currentOrderController = loader.getController();
                userOrder.getPizzaArrayList().add(pizza);
                userOrder.getPizzaArrayListStringed().add(getPizzaInfo(pizza));
                currentOrderController.initialize(this.currentOrderController, chicagoSPOController, this);
                stage = (Stage) mainPane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Current Order");
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alertError.setContentText("You didnt make or select a pizza yet");
            alertError.show();
        }
    }

    /**
     * Get the current user order.
     *
     * @return The order type user order.
     */
    public Order getUserOrder() {
        return userOrder;
    }

    /**
     * Provide the service that instantiate the Flavor Chosen.
     *
     * @param pizza
     */
    private void instantiateFlavorChosen(Pizza pizza) {
        if (pizza instanceof Deluxe) {
            Image deluxeImage = new Image(ChicagoSPOController.class.getResource("Photos/nyDeluxe.jpg").toString());
            imageView.setImage(deluxeImage);
        } else if (pizza instanceof BBQChicken) {
            Image bbqchickenImage = new Image(ChicagoSPOController.class.getResource("Photos/nyBBQ.png").toString());
            imageView.setImage(bbqchickenImage);
        } else if (pizza instanceof Meatzza) {
            Image meatzzaImage = new Image(ChicagoSPOController.class.getResource("Photos/nyMEATZZA.png").toString());
            imageView.setImage(meatzzaImage);
        } else if (pizza instanceof BuildYourOwn) {
            Image buildYOImage = new Image(ChicagoSPOController.class.getResource("Photos/CREATEYOUROWN.jpg").toString());
            imageView.setImage(buildYOImage);
        }
        crustType.setText(pizza.getCrust().getCrustText());
        for (Topping topping : pizza.getAvailableToppings()) {
            availableToppings.getItems().addAll(topping.getToppingText());
        }
        for (Topping topping : pizza.getSelectedToppings()) {
            selectedToppings.getItems().addAll(topping.getToppingText());
        }
    }

    /**
     * Print the price of current pizza.
     */
    private void printPizzaPrice() {
        pizzaPrice.setText("$" + String.format("%.2f", pizza.price()));
    }

    /**
     * Get the name of the topping.
     *
     * @param topping The topping that we want to retrieve its name.
     * @return String type name of that topping.
     */
    private Topping getToppingName(String topping) {
        topping = topping.replaceAll("\\s", "").toUpperCase();
        return Topping.valueOf(topping);
    }

    /**
     * Get the information of pizza.
     *
     * @param pizza The pizza that we want to print its information.
     * @return The String typed information of that pizza.
     */
    private String getPizzaInfo(Pizza pizza) {
        String listViewText = "";
        if (pizza instanceof Deluxe) {
            listViewText += ("Deluxe (New York Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof BBQChicken) {
            listViewText += ("BBQ Chicken (New York Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof Meatzza) {
            listViewText += ("Meatzza (New York Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof BuildYourOwn) {
            listViewText += ("Build Your Own (New York Style - " + pizza.getCrust().getCrustText() + "), ");
        }
        for (Topping topping : pizza.getSelectedToppings()) {
            listViewText += (topping.getToppingText() + (", "));
        }
        listViewText += (pizza.getSize().getSizeText() + (" "));
        listViewText += ("$" + pizza.price());
        return listViewText;
    }

}
