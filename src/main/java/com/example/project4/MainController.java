package com.example.project4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Pane mainPane;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ChicagoSPOController chicagoSPOController;
    private NewYorkSPOController newYorkSPOController;
    private CurrentOrderController currentOrderController;
    private Order userOrder;
    private StoreOrder storeOrdersArrayList;



    public void initialize(CurrentOrderController controller, ChicagoSPOController controller1, NewYorkSPOController controller2){
        currentOrderController = controller;
        if(currentOrderController != null){
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
        chicagoSPOController = controller1;
        newYorkSPOController = controller2;
        if(newYorkSPOController != null){
            userOrder = newYorkSPOController.getUserOrder();
        }else if(currentOrderController != null){
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
    }

    @FXML
    private void chicagostyleClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("ChicagoSPO-view.fxml"));
            root = loader.load();
            ChicagoSPOController chicagoSPOController1 = loader.getController();

            chicagoSPOController1.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

            stage = (Stage)mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Chicago Style Pizza Order");
            stage.setResizable(false);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void newyorkstyleClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(NewYorkSPOController.class.getResource("NewYorkSPO-view.fxml"));
            root = loader.load();
            NewYorkSPOController newYorkSPOController1 = loader.getController();

            newYorkSPOController1.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

            stage = (Stage)mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("NewYork Style Pizza Order");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void storeordersClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(CurrentOrderController.class.getResource("StoreOrders-view.fxml"));
            root = loader.load();
            StoreOrdersController storeOrdersController = loader.getController();

            storeOrdersController.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

            stage = (Stage)mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Store Orders");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void currentorderClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(CurrentOrderController.class.getResource("CurrentOrder-view.fxml"));
            root = loader.load();
            CurrentOrderController currentOrderController1 = loader.getController();

            currentOrderController1.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

            stage = (Stage)mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Current Order");
            stage.setResizable(false);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

