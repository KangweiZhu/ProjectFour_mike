package com.example.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the entry of this whole javafx project.
 * It will load and display the main view.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class MainApplication extends Application {
    /**
     * Default constructor for MainApplication class.
     */
    public MainApplication() {
    }

    /**
     * The entry of the javafx project.
     *
     * @param args The commandLine arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * This method is override from the Application class. It will be automatically called after a JavaFx application is
     * launched. It will place the UI Controller in a scene. And display the scene in a stage.
     *
     * @param stage The stage that we put the scene into it.
     */
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main-view.fxml"));
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Main-view.fxml"));
            Scene scene1 = new Scene(root);
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.setScene(scene1);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}