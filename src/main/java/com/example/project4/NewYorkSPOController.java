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

public class NewYorkSPOController{

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
    private final Integer maxToppings = 7;
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


    public void initialize(CurrentOrderController controller, ChicagoSPOController controller1, NewYorkSPOController controller2){
        currentOrderController = controller;
        if(currentOrderController != null){
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
        chicagoSPOController = controller1;
        newYorkSPOController = controller2;
        if(chicagoSPOController != null){
            userOrder = chicagoSPOController.getUserOrder();
        }else if(newYorkSPOController != null) {
            userOrder = newYorkSPOController.getUserOrder();
        }else{
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
    @FXML
    private void goToHomeScreen(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();

            mainController.initialize(currentOrderController, chicagoSPOController, this);

            stage = (Stage)mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void flavorChosen(ActionEvent event) {
        availableToppings.getItems().clear();
        selectedToppings.getItems().clear();

        largePizza.setSelected(true);

        if(comboBox.getValue().equalsIgnoreCase("Deluxe")){
            pizza = pizzaFactory.createDeluxe();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);

        }else if(comboBox.getValue().equalsIgnoreCase("BBQ Chicken")){
            pizza = pizzaFactory.createBBQChicken();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);

        }else if(comboBox.getValue().equalsIgnoreCase("Meatzza")){
            pizza = pizzaFactory.createMeatzza();
            availableToppings.setDisable(true);
            addTopping.setDisable(true);
            removeTopping.setDisable(true);

        }else if(comboBox.getValue().equalsIgnoreCase("Build Your Own")){
            pizza = pizzaFactory.createBuildYourOwn();
            availableToppings.setDisable(false);
            addTopping.setDisable(false);
            removeTopping.setDisable(false);

        }
        instantiateFlavorChosen(pizza);
        printPizzaPrice();
    }
    @FXML
    private void addTopping(ActionEvent event){
        String topping = availableToppings.getSelectionModel().getSelectedItem();
        if(topping != null){
            if(selectedToppings.getItems().size() < maxToppings){
                selectedToppings.getItems().add(topping);
                availableToppings.getItems().remove(topping);
                pizza.add(getToppingName(topping));
                printPizzaPrice();
            }else{
                alertError.setContentText("Sorry you cannot have more than 7 toppings on a pizza");
                alertError.show();
            }
        }
    }

    @FXML
    private void removeTopping(ActionEvent event){
        String topping = selectedToppings.getSelectionModel().getSelectedItem();
        if(topping != null){
            availableToppings.getItems().add(topping);
            selectedToppings.getItems().remove(topping);
            pizza.remove(getToppingName(topping));
            printPizzaPrice();
        }
    }
    @FXML
    private void smallPizza(ActionEvent event){
        if(pizza != null){
            pizza.setSize(Size.SMALL);
            printPizzaPrice();
        }
    }
    @FXML
    private void mediumPizza(ActionEvent event){
        if(pizza != null){
            pizza.setSize(Size.MEDIUM);
            printPizzaPrice();
        }
    }
    @FXML
    private void largePizza(ActionEvent event){
        if(pizza != null){
            pizza.setSize(Size.LARGE);
            printPizzaPrice();
        }
    }
    @FXML
    private void addToOrder(ActionEvent event){
        if(pizza != null){
            try{
                FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("CurrentOrder-view.fxml"));
                root = loader.load();
                CurrentOrderController currentOrderController1 = loader.getController();
                userOrder.getPizzaArrayList().add(pizza);
                userOrder.getPizzaArrayListStringed().add(getPizzaInfo(pizza));

                currentOrderController1.initialize(currentOrderController, chicagoSPOController, this);

                stage = (Stage)mainPane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Current Order");
                stage.setResizable(false);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            alertError.setContentText("You didnt make or select a pizza yet");
            alertError.show();
        }
    }
    public Order getUserOrder(){
        return userOrder;
    }

    private void instantiateFlavorChosen(Pizza pizza){
        if(pizza instanceof Deluxe){
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

        for(Topping topping : pizza.getAvailableToppings()){
            availableToppings.getItems().addAll(topping.getToppingText());
        }
        for(Topping topping : pizza.getSelectedToppings()){
            selectedToppings.getItems().addAll(topping.getToppingText());
        }
    }
    private void printPizzaPrice(){
        pizzaPrice.setText("$"+String.format("%.2f",pizza.price()));
    }
    private Topping getToppingName(String topping) {
        topping = topping.replaceAll("\\s", "").toUpperCase();
        return Topping.valueOf(topping);
    }
    private String getPizzaInfo(Pizza pizza){
        String listViewText = "";
        if(pizza instanceof Deluxe){
            listViewText += ("Deluxe (New York Style - "+pizza.getCrust().getCrustText()+"), ");
        } else if (pizza instanceof BBQChicken) {
            listViewText += ("BBQ Chicken (New York Style - "+pizza.getCrust().getCrustText()+"), ");
        } else if (pizza instanceof Meatzza) {
            listViewText += ("Meatzza (New York Style - "+pizza.getCrust().getCrustText()+"), ");
        } else if (pizza instanceof BuildYourOwn) {
            listViewText += ("Build Your Own (New York Style - "+pizza.getCrust().getCrustText()+"), ");
        }

        for(Topping topping : pizza.getSelectedToppings()){
            listViewText += (topping.getToppingText() + (", "));
        }

        listViewText += (pizza.getSize().getSizeText() + (" "));
        listViewText += ("$" + pizza.price());

        return listViewText;
    }

}
