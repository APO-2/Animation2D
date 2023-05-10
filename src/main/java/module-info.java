module com.example.animation2d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.animation2d to javafx.fxml;
    exports com.example.animation2d;
}