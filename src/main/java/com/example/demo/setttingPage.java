package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class setttingPage implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> colorButton;
    private String[] colorChoice = {"Default","Blue","Pink"};
    @FXML
    private Button helpButton;
    @FXML
    private ChoiceBox<String> levelButton;
    private String[] levelChoice = {"Easy","Normal","Hard"};

    @FXML
    void colorSelect(ActionEvent event) {
        String colorSelected = colorButton.getValue();
        System.out.println(colorSelected);
        colorButton.getItems().addAll(colorChoice);

    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void levelSelect(MouseEvent event) {
        String levelSelected = levelButton.getValue();
        System.out.println(levelSelected);
        levelButton.getItems().addAll(levelChoice);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelButton.getItems().addAll(levelChoice);
        colorButton.getItems().addAll(colorChoice);
    }
}
