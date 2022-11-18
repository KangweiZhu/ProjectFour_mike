package com.example.project4;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StoreOrdersController {

    @FXML
    private Pane mainPane;
    @FXML
    private ListView<String> storeOrderView;
    @FXML
    private ComboBox<Integer> orderNumberComboBox;
    @FXML
    private TextField orderTotalLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<Integer> numOfOrders = new ArrayList<>();
    private Integer orderNumber;
    private ChicagoSPOController chicagoSPOController;
    private NewYorkSPOController newYorkSPOController;
    private CurrentOrderController currentOrderController;
    private Order userOrder;
    private StoreOrder storeOrdersArrayList;


    public void initialize(CurrentOrderController controller, ChicagoSPOController controller1, NewYorkSPOController controller2){
        currentOrderController = controller;
        if(currentOrderController != null){
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
            updateComboBox();
        }
        chicagoSPOController = controller1;
        newYorkSPOController = controller2;
        if(chicagoSPOController != null){
            userOrder = chicagoSPOController.getUserOrder();
        }else  if(newYorkSPOController != null){
            userOrder = newYorkSPOController.getUserOrder();
        }
    }

    public void transferOrders(Order order){
        storeOrdersArrayList.add(order);
    }

    @FXML
    private void goToHomeScreen(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();

            mainController.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

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
    private void selectOrderNumber(ActionEvent event){
        orderNumber = orderNumberComboBox.getValue();
        setUpStoreOrderView();
    }
    @FXML
    private void cancelOrder(ActionEvent event){
        orderNumber = orderNumberComboBox.getValue();
        if(orderNumber != null){
            storeOrdersArrayList.getOrdersArrayList().removeIf(order -> order.getOrderNumber() == orderNumber);
            if(storeOrdersArrayList.getOrdersArrayList().isEmpty()){
                orderNumberComboBox.getItems().removeAll(orderNumberComboBox.getItems());
                storeOrderView.getItems().clear();
                updateScreenTotals(null);
            }else{
                updateComboBox();
                orderNumber = numOfOrders.get(0);
                orderNumberComboBox.setValue(orderNumber);
                setUpStoreOrderView();
            }
        }
    }
    @FXML
    private void exportStoreOrders(ActionEvent event){
        storeOrdersArrayList.export();
    }

    private void updateScreenTotals(Integer index){
        if(index != null){
            orderTotalLabel.setText("$"+String.format("%.2f", storeOrdersArrayList.getOrdersArrayList().get(index).getOrderTotal()));
        }else{
            orderTotalLabel.setText("");
        }
    }
    private void updateComboBox(){
        numOfOrders.clear();
        for(Order order : storeOrdersArrayList.getOrdersArrayList()){
            numOfOrders.add(order.getOrderNumber());
        }
        orderNumberComboBox.setItems(FXCollections.observableArrayList(numOfOrders));
    }
    private void setUpStoreOrderView(){
        if(orderNumberComboBox.getValue() != null){
            storeOrderView.getItems().clear();
            for(Order order : storeOrdersArrayList.getOrdersArrayList()){
                if(order.getOrderNumber() == orderNumber){
                    storeOrderView.getItems().addAll(order.getPizzaArrayListStringed());
                    updateScreenTotals(storeOrdersArrayList.getOrdersArrayList().indexOf(order));
                }
            }
        }
    }

}
