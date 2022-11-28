package com.example.demo;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    public static Color colorSelected = Color.rgb(189,177,92);
    @FXML
    private BorderPane Pane;
    private Parent root;
    @FXML
    private Button backButton;
    @FXML
    private ColorPicker colorButton;
    @FXML
    private Button helpButton;
    @FXML
    private ChoiceBox<String> levelButton;
    private String[] levelChoice = {"Easy","Normal","Hard"};
    @FXML
    private MenuItem tutorial;
    @FXML
    private MenuItem exitGameTab;
    @FXML
    private MenuItem aboutButton;


    @FXML
    void TutorialButton(ActionEvent event) {
        Main main = new Main();
        main.howToPlay(event);
    }

    private HostServices getHostServices() {
        return null;
    }

    @FXML
    void exitGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //root.getChildren().clear();
            System.exit(0);
        }
    }
    @FXML
    void colorSelect(ActionEvent event) {
        colorSelected = colorButton.getValue();
        Pane.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));
        setColorSelected(colorSelected);
    }

    void setColorSelected(Color color){
        colorSelected = color;
    }
    public Color getColorSelected(){
        return colorSelected;
    }

    @FXML
    void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("2048 Game created by ZKTay under Nottingham University of Malaysia");
        alert.setContentText("Contact Us @ hcyzt3@nottingham.edu.my\n© 2022-2023 | ZKTay");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        }
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
        //Main main = new Main();
        //main.setPanelColor(colorSelected);
        //main.indexPanel.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelButton.getItems().addAll(levelChoice);
        Pane.setBackground(new Background(new BackgroundFill(Main.colorSelected, null, null)));
        //colorButton.setValue(Color.rgb(189,177,92));
        colorButton.setValue(colorSelected);
        //colorButton.getItems().addAll(colorChoice);

    }
}
