package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CurrentOrderController {

    @FXML
    private Pane mainPane;
    @FXML
    private ListView<String> currentOrderView;
    @FXML
    private TextField ordernumberLabel;
    @FXML
    private TextField salestaxLabel;
    @FXML
    private TextField subtotalLabel;
    @FXML
    private TextField ordertotalLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ArrayList<String> currentOrderText;
    private ChicagoSPOController chicagoSPOController;
    private NewYorkSPOController newYorkSPOController;
    private Order userOrder;
    private CurrentOrderController currentOrderController;
    private StoreOrder storeOrdersArrayList;
    private Alert alertError = new Alert(Alert.AlertType.ERROR);

    public void initialize(CurrentOrderController controller, ChicagoSPOController controller1, NewYorkSPOController controller2) {
        currentOrderController = controller;
        if(currentOrderController != null){
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }else{
            storeOrdersArrayList = new StoreOrder();
        }
        chicagoSPOController = controller1;
        newYorkSPOController = controller2;
        if(newYorkSPOController != null){
            userOrder = newYorkSPOController.getUserOrder();

            if(userOrder != null){
                currentOrderView.getItems().addAll(userOrder.getPizzaArrayListStringed());
                updateScreenTotals();
            }
        }else if(chicagoSPOController != null){
            userOrder = chicagoSPOController.getUserOrder();
            if(userOrder != null){
                currentOrderView.getItems().addAll(userOrder.getPizzaArrayListStringed());
                updateScreenTotals();
            }
        }
    }

    @FXML
    private void goToHomeScreen(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();

            mainController.initialize(this, chicagoSPOController, newYorkSPOController);

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
    private void removePizza(ActionEvent event){
        String pizza = currentOrderView.getSelectionModel().getSelectedItem();
        if(pizza != null){
            int index = userOrder.getPizzaArrayListStringed().indexOf(pizza);
            userOrder.getPizzaArrayListStringed().remove(pizza);
            userOrder.getPizzaArrayList().remove(index);
            currentOrderView.getItems().remove(pizza);
            updateScreenTotals();
        }
    }
    @FXML
    private void placeOrder(ActionEvent event){
        if(userOrder != null && !userOrder.getPizzaArrayList().isEmpty()){
            try{
                FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("StoreOrders-view.fxml"));
                root = loader.load();
                StoreOrdersController storeOrdersController = loader.getController();
                storeOrdersArrayList.getOrdersArrayList().add(userOrder);


                chicagoSPOController = null;
                newYorkSPOController = null;
                storeOrdersController.initialize(this, null, null);

                stage = (Stage)mainPane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Store Orders");
                stage.setResizable(false);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            alertError.setContentText("Your current order is empty");
            alertError.show();
        }
    }
    @FXML
    private void clearOrder(ActionEvent event){
        currentOrderView.getItems().clear();
        userOrder.getPizzaArrayList().clear();
        userOrder.getPizzaArrayListStringed().clear();
        updateScreenTotals();
    }
    public Order getUserOrder(){
        return userOrder;
    }
    public StoreOrder getStoreOrdersArrayList(){
        return storeOrdersArrayList;
    }
    private void updateScreenTotals(){
        ordernumberLabel.setText(String.valueOf(userOrder.getOrderNumber()));
        subtotalLabel.setText("$"+String.format("%.2f",userOrder.getSubTotal()));
        salestaxLabel.setText("$"+String.format("%.2f",userOrder.getTaxRate()*100));
        ordertotalLabel.setText("$"+String.format("%.2f",userOrder.getOrderTotal()));
    }
}
