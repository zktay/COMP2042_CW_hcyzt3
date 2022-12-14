package com.example.demo;
/**
 * GameScene.java
 * The controller class for the whole functions in the board during a game
 * Includes moves, add, spawns, sketching the boards tiles, when the cell reach the number to win, user's score, checking user lose or not, etc.
 */

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

class GameScene extends controller{
    private static int HEIGHT = 600;
    //public static int n = 4;
    private final static int distanceBetweenCells = 10;
    public static double LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    //private TextMaker textMaker = TextMaker.getSingleInstance();
    /*private Cell[][] cells = new Cell[n][n];*/
    //private Group root;
    /*long score = 0;*/
    //boolean win = false;
    int winValue;
    //boolean notContinuing = false;
    //boolean doNotPrompt = false;
    //private boolean Spawn = true;
    /*private int[][] oldCells = new int[n][n];
    private int[][] newCells = new int[n][n];*/
    MediaPlayer mediaPlayer;
    Boolean forceWin = false;
    //controller c = new controller();
    static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }


    static double getLENGTH() {
        return LENGTH;
    }

    //Check cells between cells have same number or not to avoid false "noMoreMoves"
    private boolean haveSameNumberNearly(int i, int j) {
        if (i < n - 1 && j < n - 1) {
            if (cells[i + 1][j].getNumber() == cells[i][j].getNumber()){
                return true;
            }
            if (cells[i][j + 1].getNumber() == cells[i][j].getNumber()){
                return true;
            }

        }
        return false;
    }

    //If the board are full and no same numbers around the cells then return true to canNotMove.
    private boolean canNotMove() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (haveSameNumberNearly(i, j)) {

                    return false;

                }
            }
        }
        return true;
    }

    /**
     * @param gameScene
     * @param root
     * @param primaryStage
     * @param endGameScene
     * @param endGameRoot
     * @param winGameScene
     * @param wingameRoot
     */
    //To Controls user's input, and the whole scene of the game/board
    void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot, Scene winGameScene, Group wingameRoot) {
        gameScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.root = root;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell((j) * LENGTH + (j + 1) * distanceBetweenCells + 145,
                        (i) * LENGTH + (i + 1) * distanceBetweenCells + 140, LENGTH, root);
            }

        }

        Text title = new Text();
        root.getChildren().add(title);
        title.setFill(Color.web("#776e65"));
        title.setText("2048");
        title.setFont(Font.font("",FontWeight.BOLD, FontPosture.REGULAR, 80));
        title.relocate(150, 0);


        Text username = new Text();
        root.getChildren().add(username);
        username.setText("Name:");
        username.setFill(Color.web("#776e65"));
        username.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        //username.relocate(600, 150);
        username.relocate(155, 105);

        Text usernameText = new Text();
        root.getChildren().add(usernameText);
        usernameText.setText(Main.usernameEnter);
        usernameText.setFill(Color.web("#776e65"));
        usernameText.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        //usernameText.relocate(690, 150);
        usernameText.relocate(245, 105);

        Text text = new Text();
        root.getChildren().add(text);
        text.setText("Score:");
        text.setFill(Color.web("#776e65"));
        text.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        text.relocate(450, 105);

        Text scoreText = new Text();
        root.getChildren().add(scoreText);
        scoreText.setFill(Color.web("#776e65"));
        scoreText.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        scoreText.relocate(530, 105);
        scoreText.setText("0");

        Button homeButton = new Button("HOME");
        homeButton.setFocusTraversable(false);
        //homeButton.setId("homeButton");
        homeButton.setPrefSize(60,30);
        homeButton.setTextFill(Color.BLACK);
        root.getChildren().add(homeButton);
        homeButton.relocate(675,110);

        randomFillNumber(1);
        randomFillNumber(1);
        spawnOrNot("old");

        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("HOME");
                alert.setContentText("Any unsaved progress will lost!\nBack to Main Menu?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Duration temp = Main.mediaPlayer.getCurrentTime();
                    Main.nowPlaying = temp;
                    primaryStage.setTitle("ZK 2048");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();


                }
            }
        });

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key ->{ //changed to key_released to avoid key holding
                Platform.runLater(() -> {
                    if (key.getCode() == KeyCode.ESCAPE){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("HOME");
                        alert.setContentText("Any unsaved progress will lost!\nBack to Main Menu?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            Parent root1 = null;
                            try {
                                root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            primaryStage.setTitle("ZK 2048");
                            primaryStage.setScene(new Scene(root1));
                            primaryStage.show();
                        }
                    }
                    //Check keypress
                    if (key.getCode() == KeyCode.DOWN || key.getCode() == KeyCode.UP || key.getCode() == KeyCode.LEFT || key.getCode() == KeyCode.RIGHT){
                        int haveEmptyCell;
                        switch (key.getCode()){
                            case DOWN -> GameScene.this.moveDown();
                            case UP -> GameScene.this.moveUp();
                            case LEFT -> GameScene.this.moveLeft();
                            case RIGHT -> GameScene.this.moveRight();
                        }

                        if (notContinuing || forceWin){
                            primaryStage.setScene(winGameScene);
                            try {
                                WinGame.getInstance().winGameShow(winGameScene, wingameRoot, primaryStage, score, n);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            root.getChildren().clear();
                            score = 0;
                        }
                        spawnOrNot("new");
                        scoreText.setText(score + "");
                        haveEmptyCell = GameScene.this.haveEmptyCell();
                        if (haveEmptyCell == -1) {

                            if (GameScene.this.canNotMove()) {
                                //Pass to winGameScene if the user wins the game
                                if (win){
                                    primaryStage.setScene(winGameScene);
                                    try {
                                        WinGame.getInstance().winGameShow(winGameScene, wingameRoot, primaryStage, score, n);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                //Pass to winGameScene if the user lose the game
                                }else {
                                    primaryStage.setScene(endGameScene);
                                    try {
                                        EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score, n);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                root.getChildren().clear();
                                score = 0;
                            }
                        } else if(haveEmptyCell == 1)
                            if (Spawn){
                                GameScene.this.randomFillNumber(2);
                                spawnOrNot("old");
                            }

                    }
                });
            });
    }
}
