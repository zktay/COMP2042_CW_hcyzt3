module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}