package com.example.game;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
/**
 * @author Ze Tay-modified
 * Main.java
 * The main file will be run the program have started.
 * Responsible for setting the required value for the game, such as Username, Number of tiles, level, and even background color of the game, window size and etc.
 * Controlling the main page (index page) of the program including accessing to Setting page, Leaderboard page, Exit the program, and also starting the game.
 */
public class Main extends Application implements Initializable {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    public static Color colorSelected = Color.rgb(189, 177, 92);
    public static String usernameEnter;
    public static MediaPlayer mediaPlayer;
    public static boolean mediaPlaying;
    public static Duration nowPlaying;
    public static Stage STAGE;
    private static final Scanner input = new Scanner(System.in);
    @FXML
    public TextField usernameField;
    private Stage stage;
    private Parent root;
    private Group gameRoot = new Group();
    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
    @FXML
    private Button exitButton;
    @FXML
    private BorderPane indexPanel;
    @FXML
    private Button scoreButton;
    @FXML
    private Button startButton;
    @FXML
    private Button settingButton;
    @FXML
    private MenuItem tutorial;
    @FXML
    private MenuItem exitGameTab;
    @FXML
    private MenuItem helpTab;

    public static void main(String[] args) {
        launch(args);
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setGameRoot(Group gameRoot) {
        this.gameRoot = gameRoot;
    }

    public void setStage(Stage primaryStage) {
        STAGE = primaryStage;
    }

    public Stage getSTAGE() {
        return STAGE;
    }

    //Initial startup to load the index page of the game.
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            setStage(primaryStage);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pics/Icon.png")));
            primaryStage.getIcons().add(image);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/index.fxml")));
            primaryStage.setTitle("ZK 2048");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    /**
     * Get username keyed-in in the username text field before starting the game.
     * @param event trigger when any event happens in the username input field
     *
     */
    @FXML
    private void username(ActionEvent event) {
        usernameEnter = usernameField.getText();
    }

    /**
     * Control and pass the required variables when the user start the game by clicking the STARTGAME button
     * @param event trigger when any event happens to the start button
     *
     */
    @FXML
    public void startGame(ActionEvent event) {
        if (usernameField != null) {
            username(event);
        }
        if (!usernameEnter.isBlank()) {
            Stage stage = getSTAGE();
            Group endgameRoot = new Group();
            Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT);


            //Whole window*
            Group gameRoot = new Group();
            setGameRoot(gameRoot);
            Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, colorSelected);

            setGameScene(gameScene);
            stage.setScene(gameScene);
            GameScene game = new GameScene();
            game.game(gameScene, gameRoot, stage, endGameScene, endgameRoot);
            stage.setTitle("ZK 2048");
            stage.setResizable(false);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Enter Username");
            alert.setHeaderText("Please enter your username to start the game.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

            }
        }
    }

    /**
     * Direct user to leaderboard page controlled by Leaderboard's class
     * @param event direct user to leaderboard on key click
     * @throws IOException to handle fxml file not found
     *
     */

    @FXML
    void viewScore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/leaderboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * To redirect user to setting page that allows user to set the size of the board, color of the board and also the difficulty of the game
     * @param event direct user to setting page on key click
     * @throws IOException to handle fxml file not found
     *
     */
    @FXML
    void setting(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/setting.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * MenuBar "Help" button
     * @param event show help on key click
     *
     */
    @FXML
    void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("2048 Game created by ZKTay under Nottingham University of Malaysia");
        alert.setContentText("Contact Us @ hcyzt3@nottingham.edu.my\n© 2022-2023 | ZKTay");
        Optional<ButtonType> result = alert.showAndWait();
        result.get();
    }
    /**
     * MenuBar "Exit Game" button
     * @param event prompt exit confirmation on key click
     *
     */
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
    /**
     * MenuBar "How to Play" button
     * @param event how redirect confirmation on key click
     *
     */
    @FXML
    void howToPlay(ActionEvent event) {
        ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "", okButton, noButton);

        alert.setTitle("Redirect");
        alert.setHeaderText("You are about to redirect to third-party website.");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            getHostServices().showDocument("https://www.youtube.com/watch?v=-rqRWzSP2iM");
        }
    }

    /**
     * Load and play the background music
     */
    public void intro() {
        if (Setting.playMusic) {
            String effect = "sounds/song.mp3";
            Media m = new Media(Paths.get(effect).toUri().toString());
            mediaPlayer = new MediaPlayer(m);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            if (nowPlaying == null) {
                mediaPlayer.setVolume(0.3);
                mediaPlayer.play();
            } else {
                mediaPlayer.setVolume(0.3);
                mediaPlayer.seek(nowPlaying);
                mediaPlayer.play();
            }
            mediaPlaying = true;
        }

    }

    /**
     * Stop the background music from paying
     */
    public void stopMusic() {
        mediaPlayer.stop();
    }

    /**
     * Initialize the background color of the game
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Setting c = new Setting();
        if (Setting.playMusic) {
            if (!mediaPlaying) {
                intro();
            }
        }

        if (c.getColorSelected() != null) {
            colorSelected = c.getColorSelected();
        }
        indexPanel.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));

    }
}
