package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Main extends Application {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double height = screenBounds.getHeight();
    double width = screenBounds.getWidth()/2;*/
    private Group gameRoot = new Group();
    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
    private static Scanner input= new Scanner(System.in);
    @FXML
    private Button exitButton;
    @FXML
    private Button scoreButton;
    @FXML
    private Button startButton;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        //Below are fxml files
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("ZK 2048");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
    void startGame(ActionEvent event) {
        Stage stage = getSTAGE();
        Group menuRoot = new Group();
        Scene menuScene = new Scene(menuRoot, WIDTH, HEIGHT);
        Group accountRoot = new Group();
        Scene accountScene = new Scene(accountRoot, WIDTH, HEIGHT, Color.rgb(150, 20, 100, 0.2));
        Group getAccountRoot = new Group();
        Scene getAccountScene = new Scene(getAccountRoot, WIDTH, HEIGHT, Color.rgb(200, 20, 100, 0.2));
        Group endgameRoot = new Group();
        Group wingameRoot = new Group();
        Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, Color.rgb(194, 70, 65));
        Scene winGameScene = new Scene(wingameRoot, WIDTH, HEIGHT, Color.rgb(52, 165, 111));
        Group rankRoot = new Group();
        Scene rankScene = new Scene(rankRoot, WIDTH, HEIGHT, Color.rgb(250, 50, 120, 0.3));
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(120, 100, 100), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);


        Rectangle backgroundOfMenu = new Rectangle(240, 120, Color.rgb(120, 120, 120, 0.2));
        backgroundOfMenu.setX(WIDTH / 2 - 120);
        backgroundOfMenu.setY(180);
        menuRoot.getChildren().add(backgroundOfMenu);

        Rectangle backgroundOfMenuForPlay = new Rectangle(240, 140, Color.rgb(120, 20, 100, 0.2));
        backgroundOfMenuForPlay.setX(WIDTH / 2 - 120);
        backgroundOfMenuForPlay.setY(180);
        accountRoot.getChildren().add(backgroundOfMenuForPlay);

        Group gameRoot = new Group();
        setGameRoot(gameRoot);
        Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
        setGameScene(gameScene);
        stage.setScene(gameScene);
        GameScene game = new GameScene();
        game.game(gameScene, gameRoot, stage, endGameScene, endgameRoot, winGameScene, wingameRoot);
        stage.setTitle("ZK 2048");
        stage.show();
    }

    @FXML
    void viewScore(ActionEvent event) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
