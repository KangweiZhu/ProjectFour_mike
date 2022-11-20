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
 * This class is the controller class for the ChicagoSPO-view.fxml.
 * It provides the services that buy or add a Chicago Style pizza into the cart.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class ChicagoSPOController {
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
    private PizzaFactory pizzaFactory = new ChicagoPizza();
    private Pizza pizza;
    private ChicagoSPOController chicagoSPOController;
    private NewYorkSPOController newYorkSPOController;
    private Order userOrder;
    private CurrentOrderController currentOrderController;
    private StoreOrder storeOrdersArrayList;
    private Alert alertError = new Alert(Alert.AlertType.ERROR);

    /**
     * Default constructor of ChicagoSPOController class
     */
    public ChicagoSPOController() {
    }

    /**
     * This method is used for sharing datas between each view.
     * It is called to initialize controllers after its root element has been completely processed.
     *
     * @param currentOrderController The controller for currentOrder view.
     * @param chicagoSPOController   The controller for chcagoSPO view.
     * @param newYorkSPOController   The controller for newYorkSPO view.
     */
    public void initialize(CurrentOrderController currentOrderController, ChicagoSPOController chicagoSPOController,
                           NewYorkSPOController newYorkSPOController) {
        this.currentOrderController = currentOrderController;
        imageView.setImage(new Image(ChicagoSPOController.class.getResource("Photos/ChicagoStyle-pizza.png").
                toString()));
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
     * After the back button is click, it will jump to the previous page, which is the main page.
     *
     * @param event
     */
    @FXML
    private void goToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();
            mainController.initialize(currentOrderController, this, newYorkSPOController);
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
     * This method provide the service of selecthing topping for pizza.
     *
     * @param event An event that represent some type of action.
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
     * This method provides services for add a topping to a buildYourOwn pizza.
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
     * This method provides services that remove a topping from a buildYourOwn pizza
     *
     * @param event An event that represents some type of action.
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
     * This method shows the price of small size pizza.
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
     * This method shows the price of medium size pizza.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void mediumPizza(ActionEvent event) {
        if (pizza != null) {
            pizza.setSize(Size.MEDIUM);
            printPizzaPrice();
        }
    }

    /**
     * This method shows the price of large size pizza.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void largePizza(ActionEvent event) {
        if (pizza != null) {
            pizza.setSize(Size.LARGE);
            printPizzaPrice();
        }
    }

    /**
     * This method provides the service that add the selected pizza into the cart.
     *
     * @param event An event that represent some type of action.
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
                currentOrderController.initialize(this.currentOrderController, this, newYorkSPOController);
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
     * Get the user's order.
     *
     * @return Order type userOrder.
     */
    public Order getUserOrder() {
        return userOrder;
    }

    /**
     * Setting up the image of each selected flavor pizza and display pizza's basic info on the page.
     *
     * @param pizza The pizza that is selected.
     */
    private void instantiateFlavorChosen(Pizza pizza) {
        if (pizza instanceof Deluxe) {
            imageView.setImage(new Image(ChicagoSPOController.class.getResource("Photos/csDELUXE.jpg").
                    toString()));
        } else if (pizza instanceof BBQChicken) {
            Image bbqchickenImage = new Image(ChicagoSPOController.class.getResource("Photos/csBBQ.jpg").
                    toString());
            imageView.setImage(bbqchickenImage);
        } else if (pizza instanceof Meatzza) {
            Image meatzzaImage = new Image(ChicagoSPOController.class.getResource("Photos/csMEATZZA.jpg").
                    toString());
            imageView.setImage(meatzzaImage);
        } else if (pizza instanceof BuildYourOwn) {
            Image buildYOImage = new Image(ChicagoSPOController.class.getResource("Photos/CREATEYOUROWN.jpg").
                    toString());
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
     * This method prints the price of pizza.
     */
    private void printPizzaPrice() {
        pizzaPrice.setText("$" + String.format("%.2f", pizza.price()));
    }

    /**
     * Get the name of the topping.
     *
     * @param topping The Topping that we want to know its name.
     * @return The String type name of that topping.
     */
    private Topping getToppingName(String topping) {
        topping = topping.replaceAll("\\s", "").toUpperCase();
        return Topping.valueOf(topping);
    }

    /**
     * Get the detail about the pizza.
     *
     * @param pizza The pizza that we want to know its detail.
     * @return The String type info about that pizza.
     */
    private String getPizzaInfo(Pizza pizza) {
        String listViewText = "";
        if (pizza instanceof Deluxe) {
            listViewText += ("Deluxe (Chicago Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof BBQChicken) {
            listViewText += ("BBQ Chicken (Chicago Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof Meatzza) {
            listViewText += ("Meatzza (Chicago Style - " + pizza.getCrust().getCrustText() + "), ");
        } else if (pizza instanceof BuildYourOwn) {
            listViewText += ("Build Your Own (Chicago Style - " + pizza.getCrust().getCrustText() + "), ");
        }
        for (Topping topping : pizza.getSelectedToppings()) {
            listViewText += (topping.getToppingText() + (", "));
        }
        listViewText += (pizza.getSize().getSizeText() + (" "));
        listViewText += ("$" + pizza.price());
        return listViewText;
    }

}
