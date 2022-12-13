package com.example.demo;
/**
 *  Main.java
 *  The main file that will be run once the program have started.
 *  Responsible for setting the required value for the game, such as Username, Number of tiles, level, and even background color of the game.
 *
 */
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
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Main extends Application implements Initializable {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    private Stage stage;
    private Scene scene;
    private Scene winScene;
    private Group winRoot;
    private Parent root;
    private Group gameRoot = new Group();
    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
    private static Scanner input= new Scanner(System.in);
    @FXML
    private Button exitButton;
    public static Color colorSelected = Color.rgb(189,177,92);
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
    @FXML
    public TextField usernameField;
    public static String usernameEnter;
    public static MediaPlayer mediaPlayer;
    public static boolean mediaPlaying;
    public static Duration nowPlaying;



    public static Stage STAGE;

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }
    public void setGameRoot(Group gameRoot) {
        this.gameRoot = gameRoot;
    }
    public void setStage(Stage primaryStage){
        STAGE = primaryStage;
    }
    public Stage getSTAGE() {
        return STAGE;
    }
    public void setWinScene(Scene winSceneParam){
        winScene = winSceneParam;
    }
    public Scene getWinScene(){
        return winScene;
    }
    public void setWinRoot(Group winRootParam){
        winRoot = winRootParam;
    }
    public Group getWinRoot(){
        return winRoot;
    }

    //Initial startup to load the index page of the game.
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            setStage(primaryStage);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pics/Icon.png")));
            primaryStage.getIcons().add(image);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
            primaryStage.setTitle("ZK 2048");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e){
            System.out.println(e);
        }

    }


    @FXML
    void exitGame(ActionEvent event) {
        Setting con = new Setting();
        con.exitGame(event);
    }

    @FXML
    private void username(ActionEvent event) {
        usernameEnter = usernameField.getText();
    }

    //Control and pass the required variables when the user start the game by clicking the STARTGAME button
    @FXML
    public void startGame(ActionEvent event) {
        if (usernameField != null){
            username(event);
        }
        if (!usernameEnter.isBlank()) {
            Stage stage = getSTAGE();
            Group endgameRoot = new Group();
            Group wingameRoot = new Group();
            Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, Color.rgb(194, 70, 65));
            Scene winGameScene = new Scene(wingameRoot, WIDTH, HEIGHT, Color.rgb(52, 165, 111));

            //Whole window*
            Group gameRoot = new Group();
            setGameRoot(gameRoot);
            Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, colorSelected);

            setGameScene(gameScene);
            stage.setScene(gameScene);
            setWinScene(winGameScene);
            setWinRoot(winRoot);
            GameScene game = new GameScene();
            game.game(gameScene, gameRoot, stage, endGameScene, endgameRoot, winGameScene, wingameRoot);
            stage.setTitle("ZK 2048");
            stage.setResizable(false);
            stage.show();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Enter Username");
            alert.setHeaderText("Please enter your username to start the game.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

            }
        }
    }

    //Direct user to leaderboard page controlled by Leaderboard's class
    @FXML
    void viewScore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("leaderboard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void howToPlay(ActionEvent event) {
        ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "", okButton, noButton);

        alert.setTitle("Redirect");
        alert.setHeaderText("You are about to redirect to third-party website.");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton){
            getHostServices().showDocument("https://www.youtube.com/watch?v=-rqRWzSP2iM");
        }
    }

    //To redirect user to setting page that allows user to set the size of the board, color of the board and also the difficulty of the game
    @FXML
    void setting(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("setting.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void help(ActionEvent event) {
        Setting con = new Setting();
        con.help(event);
    }

    public void intro(){
        if (Setting.playMusic){
            String effect = "sounds/song.mp3";
            Media m = new Media(Paths.get(effect).toUri().toString());
            mediaPlayer = new MediaPlayer(m);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            if (nowPlaying == null){
                mediaPlayer.play();
            }else{
                mediaPlayer.seek(nowPlaying);
                mediaPlayer.play();
            }
            mediaPlaying = true;
        }

    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Initialize the background color of the game
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Setting c = new Setting();
        if (Setting.playMusic){
            if (!mediaPlaying){
                intro();
            }
        }

        if (c.getColorSelected() != null){
            colorSelected = c.getColorSelected();
        }
        indexPanel.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));

    }
}
