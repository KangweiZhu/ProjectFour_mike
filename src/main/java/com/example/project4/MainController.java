package com.example.project4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class is the controller class for the Main-view.fxml file.
 * It provides the service for displaying the javafx main page of this java program.
 *
 * @author Michael Israel, Kangwei Zhu
 */
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

    /**
     * Default constructor of MainController class
     */
    public MainController() {
    }

    /**
     * This method is used for sharing datas between each view.
     * It is called to initialize controllers after its root element has been completely processed.
     *
     * @param currentOrderController The controller for currentOrder view.
     * @param chicagoSPOController    The controller for chicagoSPO view.
     * @param newYorkSPOController   The controller for newYorkSPO view.
     */
    public void initialize(CurrentOrderController currentOrderController, ChicagoSPOController chicagoSPOController,
                           NewYorkSPOController newYorkSPOController) {
        this.currentOrderController = currentOrderController;
        if (currentOrderController != null) {
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
        this.chicagoSPOController = chicagoSPOController;
        this.newYorkSPOController = newYorkSPOController;
        if (newYorkSPOController != null) {
            userOrder = newYorkSPOController.getUserOrder();
        } else if (currentOrderController != null) {
            storeOrdersArrayList = currentOrderController.getStoreOrdersArrayList();
        }
    }

    /**
     * After the chicagoStyle imageButton is clicked, it will try to jump to a new page which people can select/add
     * ChicagoStyle pizza.
     */
    @FXML
    private void chicagostyleClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(ChicagoSPOController.class.getResource("ChicagoSPO-view.fxml"));
            root = loader.load();
            ChicagoSPOController chicagoSPOController = loader.getController();
            chicagoSPOController.initialize(currentOrderController, this.chicagoSPOController, newYorkSPOController);
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Chicago Style Pizza Order");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * After the newYorkStyle imageButton is clicked, it will try to jump to a new page which people can select/add
     * newYorkStyle pizza.
     */
    @FXML
    private void newyorkstyleClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(NewYorkSPOController.class.getResource("NewYorkSPO-view.fxml"));
            root = loader.load();
            NewYorkSPOController newYorkSPOController = loader.getController();
            newYorkSPOController.initialize(currentOrderController, chicagoSPOController, this.newYorkSPOController);
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("NewYork Style Pizza Order");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * After the storeOrder imageButton is clicked, it will try to jump to a new page which people can check the store
     * order.
     */
    @FXML
    private void storeordersClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(CurrentOrderController.class.getResource("StoreOrders-view.fxml"));
            root = loader.load();
            StoreOrdersController storeOrdersController = loader.getController();
            storeOrdersController.initialize(currentOrderController, chicagoSPOController, newYorkSPOController);
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Store Orders");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * After the currentOrder imageButton is clicked, it will try to jump to a new page which people can view the
     * current order.
     */
    @FXML
    private void currentorderClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(CurrentOrderController.class.getResource("CurrentOrder-view.fxml"));
            root = loader.load();
            CurrentOrderController currentOrderController = loader.getController();
            currentOrderController.initialize(this.currentOrderController, chicagoSPOController, newYorkSPOController);
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Current Order");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

