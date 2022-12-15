module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;

    opens com.example.game to javafx.fxml;
    exports com.example.game;
    exports com.example.game.unitTesting;
    opens com.example.game.unitTesting to javafx.fxml;
}