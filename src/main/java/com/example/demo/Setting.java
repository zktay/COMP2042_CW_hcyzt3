package com.example.demo;
/**
 * Setting.java
 * To control the setting page.
 * Allow user to choose the background color of the game, level of the game, size of the board, background music, audio effects.
 */

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Setting implements Initializable {
    public static Color colorSelected = Color.rgb(189, 177, 92);
    public static String levelSelected = "Normal";
    public static boolean playMusic = true;
    public static boolean playEffect = true;
    @FXML
    public CheckBox effectButton;
    @FXML
    public CheckBox musicButton;
    String determine;
    @FXML
    ChoiceBox<String> levelButton;
    @FXML
    ChoiceBox<String> tilesButton;
    private Stage stage;
    private Scene scene;
    @FXML
    private BorderPane Pane;
    private Parent root;
    @FXML
    private Button backButton;
    @FXML
    private ColorPicker colorButton;
    @FXML
    private Button helpButton;
    private final String[] levelChoice = {"Easy", "Normal", "Hard", "Extreme", "3072", "Color"};
    @FXML
    private MenuItem tutorial;
    @FXML
    private MenuItem exitGameTab;
    @FXML
    private MenuItem aboutButton;
    private final String[] tilesChoice = {"3x3", "4x4", "5x5", "6x6"};

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
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Retrieve color selected from the color picker
    @FXML
    void colorSelect(ActionEvent event) {
        colorSelected = colorButton.getValue();
        Pane.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));
        setColorSelected(colorSelected);
    }

    public Color getColorSelected() {
        return colorSelected;
    }

    void setColorSelected(Color color) {
        colorSelected = color;
    }

    @FXML
    void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("2048 Game created by ZKTay under Nottingham University of Malaysia");
        alert.setContentText("Contact Us @ hcyzt3@nottingham.edu.my\n© 2022-2023 | ZKTay");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        }
    }

    //Retrieve the level selected by the user from the level choice box
    @FXML
    void levelSelect(ActionEvent event) {
        levelSelected = levelButton.getValue();
    }

    //Direct user back to the main page after setting the game
    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        Duration temp = Main.mediaPlayer.getCurrentTime();
        Main.nowPlaying = temp;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void effectToggle(ActionEvent event) {
        if (effectButton.isSelected()) {
            playEffect = true;
        } else if (!effectButton.isSelected()) {
            playEffect = false;

        }
    }

    @FXML
    void musicToggle(ActionEvent event) {
        if (musicButton.isSelected()) {
            playMusic = true;
            Main main = new Main();
            main.intro();
        } else if (!musicButton.isSelected()) {
            playMusic = false;
            Main main = new Main();
            main.stopMusic();
        }
    }

    //To pass the size of the board to gamescene, the gap between the cells, and the overall size of the board
    @FXML
    void tilesSelect(ActionEvent event) {
        determine = tilesButton.getValue();
        if (Objects.equals(determine, "3x3")) {
            GameScene.n = 3;
            GameScene.LENGTH = (600 - ((4) * 10)) / (double) 3;
        } else if (Objects.equals(determine, "4x4")) {
            GameScene.n = 4;
            GameScene.LENGTH = (600 - ((5) * 10)) / (double) 4;
        } else if (Objects.equals(determine, "5x5")) {
            GameScene.n = 5;
            GameScene.LENGTH = (600 - ((6) * 10)) / (double) 5;
        } else if (Objects.equals(determine, "6x6")) {
            GameScene.n = 6;
            GameScene.LENGTH = (600 - ((7) * 10)) / (double) 6;
        }
    }

    //Initialize the levelchoice, colorChoice.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicButton.setSelected(playMusic);
        if (playEffect) {
            effectButton.setSelected(true);
        } else {
            effectButton.setSelected(false);
        }

        levelButton.getItems().addAll(levelChoice);
        tilesButton.getItems().addAll(tilesChoice);
        Pane.setBackground(new Background(new BackgroundFill(Main.colorSelected, null, null)));
        colorButton.setValue(colorSelected);
        if (levelSelected.equals("Easy")) {
            levelButton.setValue("Easy");
        } else if (levelSelected.equals("Normal")) {
            levelButton.setValue("Normal");
        } else if (levelSelected.equals("Hard")) {
            levelButton.setValue("Hard");
        } else if (levelSelected.equals("Extreme")) {
            levelButton.setValue("Extreme");
        } else if (levelSelected.equals("3072")) {
            levelButton.setValue("3072");
        } else if (levelSelected.equals("Color")) {
            levelButton.setValue("Color");
        }

        switch (GameScene.n) {
            case 3:
                tilesButton.setValue("3x3");
                break;
            case 4:
                tilesButton.setValue("4x4");
                break;
            case 5:
                tilesButton.setValue("5x5");
                break;
            case 6:
                tilesButton.setValue("6x6");
                break;
        }
    }


}
