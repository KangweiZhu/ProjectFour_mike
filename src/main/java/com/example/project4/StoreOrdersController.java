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

/**
 * The controller class for StoreOrderController view.
 * It will provide services for viewing all the orders that this store has.
 *
 * @author Michael Israel, Kangwei Zhu.
 */
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

    /**
     * Default constructor of StoreOrderController class.
     */
    public StoreOrdersController() {
    }

    /**
     * It is called to initialize all the controllers after the root element is loaded.
     * It is used for sharing the data between controllers.
     *
     * @param currentOrderController The controller for the currentOrder View.
     * @param chicagoSPOController   The controller for the chicagoSPO view.
     * @param newYorkSPOController   The controller for the newYorkSPO view.
     */
    public void initialize(CurrentOrderController currentOrderController, ChicagoSPOController chicagoSPOController,
                           NewYorkSPOController newYorkSPOController) {
        this.currentOrderController = currentOrderController;
        if (currentOrderController != null) {
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
            updateComboBox();
        }
        this.chicagoSPOController = chicagoSPOController;
        this.newYorkSPOController = newYorkSPOController;
        if (chicagoSPOController != null) {
            userOrder = chicagoSPOController.getUserOrder();
        } else if (newYorkSPOController != null) {
            userOrder = newYorkSPOController.getUserOrder();
        }
    }

    /**
     * It will add an order to the current orderArrayList.
     *
     * @param order The order that are going to be added.
     */
    public void transferOrders(Order order) {
        storeOrdersArrayList.add(order);
    }

    /**
     * When the back button is clicked, it will go back to home screen.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void goToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("Main-view.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();

            mainController.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);

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
     * Provide services when clicking the checkBox to view the orders.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void selectOrderNumber(ActionEvent event) {
        orderNumber = orderNumberComboBox.getValue();
        setUpStoreOrderView();
    }

    /**
     * Provide services for cancel(remove) one order.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void cancelOrder(ActionEvent event) {
        orderNumber = orderNumberComboBox.getValue();
        if (orderNumber != null) {
            storeOrdersArrayList.getOrdersArrayList().removeIf(order -> order.getOrderNumber() == orderNumber);
            if (storeOrdersArrayList.getOrdersArrayList().isEmpty()) {
                orderNumberComboBox.getItems().removeAll(orderNumberComboBox.getItems());
                storeOrderView.getItems().clear();
                updateScreenTotals(null);
            } else {
                updateComboBox();
                orderNumber = numOfOrders.get(0);
                orderNumberComboBox.setValue(orderNumber);
                setUpStoreOrderView();
            }
        }
    }

    /**
     * Provide services for exporting the storeOrders into a textFile.
     *
     * @param event An event that represent some type of action.
     */
    @FXML
    private void exportStoreOrders(ActionEvent event) {
        storeOrdersArrayList.export();
    }

    /**
     * Provides the service for displaying the total orders.
     *
     * @param index The total number that going to be updated.
     */
    private void updateScreenTotals(Integer index) {
        if (index != null) {
            orderTotalLabel.setText("$" + String.format("%.2f", storeOrdersArrayList.getOrdersArrayList().get(index).getOrderTotal()));
        } else {
            orderTotalLabel.setText("");
        }
    }

    /**
     * Provide services for update the comboBox.
     */
    private void updateComboBox() {
        numOfOrders.clear();
        for (Order order : storeOrdersArrayList.getOrdersArrayList()) {
            numOfOrders.add(order.getOrderNumber());
        }
        orderNumberComboBox.setItems(FXCollections.observableArrayList(numOfOrders));
    }

    /**
     * Provide services for setting up storeOrder view.
     */
    private void setUpStoreOrderView() {
        if (orderNumberComboBox.getValue() != null) {
            storeOrderView.getItems().clear();
            for (Order order : storeOrdersArrayList.getOrdersArrayList()) {
                if (order.getOrderNumber() == orderNumber) {
                    storeOrderView.getItems().addAll(order.getPizzaArrayListStringed());
                    updateScreenTotals(storeOrdersArrayList.getOrdersArrayList().indexOf(order));
                }
            }
        }
    }

}
