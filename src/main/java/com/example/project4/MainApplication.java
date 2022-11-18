package com.example.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main-view.fxml"));
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Main-view.fxml"));
            Scene scene1 = new Scene(root);
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.setScene(scene1);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch();
    }
}