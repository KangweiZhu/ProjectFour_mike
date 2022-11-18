module com.example.project4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project4 to javafx.fxml;
    exports com.example.project4;
}