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

/**
 * This controller class is the controller for currentOrder view.
 * It provides services for displaying all infos about the current order in the cart.
 *
 * @author Michael Israel, Kangwei Zhu
 */
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
    private StoreOrders storeOrdersArrayList;
    private Alert alertError = new Alert(Alert.AlertType.ERROR);

    /**
     * Default Constructor for currentOrderController class.
     */
    public CurrentOrderController() {
    }

    /**
     * It is called to initialize all the controllers after the root elements is processed.
     * It is used for sharing the datas between different controllers.
     *
     * @param currentOrderController The controller for currentOrder view.
     * @param chicagoSPOController   The controller for chicagoSPO view.
     * @param newYorkSPOController   The controller for newYorkSPO view.
     */
    public void initialize(CurrentOrderController currentOrderController, ChicagoSPOController chicagoSPOController,
                           NewYorkSPOController newYorkSPOController) {
        this.currentOrderController = currentOrderController;
        if (this.currentOrderController != null) {
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        } else {
            storeOrdersArrayList = new StoreOrders();
        }
        this.chicagoSPOController = chicagoSPOController;
        this.newYorkSPOController = newYorkSPOController;
        if (this.newYorkSPOController != null) {
            userOrder = newYorkSPOController.getUserOrder();
            if (userOrder != null) {
                currentOrderView.getItems().addAll(userOrder.getPizzaArrayListStringed());
                updateScreenTotals();
            }
        } else if (this.chicagoSPOController != null) {
            userOrder = chicagoSPOController.getUserOrder();
            if (userOrder != null) {
                currentOrderView.getItems().addAll(userOrder.getPizzaArrayListStringed());
                updateScreenTotals();
            }
        }
    }

    /**
     * When the back button is clicked, it will jump to the previous page.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void goToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();
            mainController.initialize(this, chicagoSPOController, newYorkSPOController);
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
     * Provide services for removing the pizza from current cart.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void removePizza(ActionEvent event) {
        String pizza = currentOrderView.getSelectionModel().getSelectedItem();
        if (pizza != null) {
            int index = userOrder.getPizzaArrayListStringed().indexOf(pizza);
            userOrder.getPizzaArrayListStringed().remove(pizza);
            userOrder.getPizzaArrayList().remove(index);
            currentOrderView.getItems().remove(pizza);
            updateScreenTotals();
        }
    }

    /**
     * Provide services for placing the pizza in the cart as a order.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void placeOrder(ActionEvent event) {
        if (userOrder != null && !userOrder.getPizzaArrayList().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("StoreOrders-view.fxml"));
                root = loader.load();
                StoreOrdersController storeOrdersController = loader.getController();
                storeOrdersArrayList.getOrdersArrayList().add(userOrder);
                chicagoSPOController = null;
                newYorkSPOController = null;
                storeOrdersController.initialize(this, null, null);
                stage = (Stage) mainPane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Store Orders");
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alertError.setContentText("Your current order is empty");
            alertError.show();
        }
    }

    /**
     * Clear the current order cart.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void clearOrder(ActionEvent event) {
        if (userOrder == null) {
            alertError.setContentText("No order made right now");
            alertError.show();
        } else {
            currentOrderView.getItems().clear();
            userOrder.getPizzaArrayList().clear();
            userOrder.getPizzaArrayListStringed().clear();
            ordernumberLabel.clear();
            subtotalLabel.clear();
            salestaxLabel.clear();
            ordertotalLabel.clear();
        }
    }

    /**
     * Get the user order.
     *
     * @return The order type user order.
     */
    public Order getUserOrder() {
        return userOrder;
    }

    /**
     * Get the storeOrder ArrayList
     *
     * @return The storeOrderArrayList.
     */
    public StoreOrders getStoreOrdersArrayList() {
        return storeOrdersArrayList;
    }

    /**
     * Update the total displayed in the screen
     */
    private void updateScreenTotals() {
        ordernumberLabel.setText(String.valueOf(userOrder.getOrderNumber()));
        subtotalLabel.setText("$" + String.format("%.2f", userOrder.getSubTotal()));
        salestaxLabel.setText("$" + String.format("%.2f", userOrder.getTaxRate() * userOrder.getOrderTotal()));
        ordertotalLabel.setText("$" + String.format("%.2f", userOrder.getOrderTotal()));
    }
}
