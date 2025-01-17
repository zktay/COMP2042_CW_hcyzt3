package com.example.game;


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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Ze Tay-modified
 * GameScene.java
 * The controller class for the whole functions in the board during a game
 * Includes moves, add, spawns, sketching the boards tiles, when the cell reach the number to win, user's score, checking user lose or not, etc.
 */
public class GameScene extends controller{

    public static double getLENGTH() {
        return LENGTH;
    }

    /**
     * To Controls user's input, and the whole scene of the game/board
     * @param gameScene Get Scene from Main
     * @param root
     * @param primaryStage
     * @param endGameScene Pass scene to endGame.java
     * @param endGameRoot
     *
     */

    void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot) {
        gameScene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
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
        username.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        //username.relocate(600, 150);
        username.relocate(155, 123);

        Text usernameText = new Text();
        root.getChildren().add(usernameText);
        usernameText.setText(Main.usernameEnter);
        usernameText.setFill(Color.web("#776e65"));
        usernameText.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        //usernameText.relocate(690, 150);
        usernameText.relocate(215, 123);

        Text text = new Text();
        root.getChildren().add(text);
        text.setText("Score:");
        text.setFill(Color.web("#776e65"));
        text.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        text.relocate(450, 123);

        Text scoreText = new Text();
        root.getChildren().add(scoreText);
        scoreText.setFill(Color.web("#776e65"));
        scoreText.setFont(Font.font("",FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        scoreText.relocate(505, 124);
        scoreText.setText("0");

        Button homeButton = new Button("HOME");
        homeButton.setFocusTraversable(false);
        //homeButton.setId("homeButton");
        homeButton.setPrefSize(60,30);
        homeButton.setTextFill(Color.BLACK);
        root.getChildren().add(homeButton);
        homeButton.relocate(675,115);

        randomFillNumber();
        randomFillNumber();
        spawnOrNot("old");

        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * handle "home" button clicked during the game
             * @param event trigger then button whenever any event happens
             *
             */
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("HOME");
                alert.setContentText("Any unsaved progress will lost!\nBack to Main Menu?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/index.fxml")));
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
                                root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/index.fxml")));
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
                            primaryStage.setScene(endGameScene);
                            try {
                                EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score, n, "WIN");
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
                                    primaryStage.setScene(endGameScene);
                                    try {
                                        EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score, n, "WIN");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                //Pass to winGameScene if the user lose the game
                                }else {
                                    primaryStage.setScene(endGameScene);
                                    try {
                                        EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score, n, "LOSE");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                root.getChildren().clear();
                                score = 0;
                            }
                        } else if(haveEmptyCell == 1)
                            if (Spawn){
                                GameScene.this.randomFillNumber();
                                spawnOrNot("old");
                            }

                    }
                });
            });
    }
}
