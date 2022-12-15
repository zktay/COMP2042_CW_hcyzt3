package com.example.game;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static com.example.game.Main.mediaPlayer;

/**
 * WinGame.java
 * To sketch the winGame scene of the 2048 game when the user reached the targeted value of the cell.
 * Allows user to restart, exit, or back to home.
 */
public class WinGame {
    private static WinGame singleInstance = null;
    private String username;

    private WinGame() {

    }

    public static WinGame getInstance() {
        if (singleInstance == null)
            singleInstance = new WinGame();
        return singleInstance;
    }

    /**
     * sketching the winGameScene, get the board size from main's class. Combine username, score, board size and level, and result and write it into a file
     * @param winGameScene winGame Scene
     * @param root
     * @param primaryStage
     * @param score score archived by the user
     * @param n the board size
     * @throws IOException
     *
     */
    public void winGameShow(Scene winGameScene, Group root, Stage primaryStage, long score, int n) throws IOException {
        winGameScene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
        if (Setting.playEffect) {
            String effect = "sounds/cat.mp3";
            Media m = new Media(Paths.get(effect).toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(m);
            mediaPlayer.play();
        }
        username = Main.usernameEnter;
        String variant = switch (n) {
            case 3 -> "3x3";
            case 4 -> "4x4";
            case 5 -> "5x5";
            case 6 -> "6x6";
            default -> null;
        };
        File file = new File("data/score.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(username + ", " + score + ", " + variant + ", " + Setting.levelSelected + ", WIN" + "\n");
        bw.close();

        Text text = new Text("YOU WON!");
        text.relocate(240, 250);
        text.setFont(Font.font(80));
        root.getChildren().add(text);

        Text scoreText = new Text("Score: " + score);


        String temp = String.valueOf(score);
        //Wrong Approach cuz one length doesnt means one pixel
        int temp1 = temp.length();
        int scoreAlign = ((900 - temp1) / 2) - 200;

        scoreText.setFill(Color.BLACK);

        scoreText.relocate(scoreAlign, 350);
        scoreText.setFont(Font.font(70));
        root.getChildren().add(scoreText);

        Button quitButton = new Button("QUIT");
        quitButton.setPrefSize(100, 30);
        quitButton.setTextFill(Color.BLACK);
        root.getChildren().add(quitButton);
        quitButton.relocate(100, 700);

        Button retryButton = new Button("NEW GAME");
        retryButton.setPrefSize(100, 30);
        retryButton.setTextFill(Color.BLACK);
        root.getChildren().add(retryButton);
        retryButton.relocate(250, 700);

        Button homeButton = new Button("BACK TO HOME");
        homeButton.setPrefSize(200, 30);
        homeButton.setTextFill(Color.BLACK);
        root.getChildren().add(homeButton);
        homeButton.relocate(600, 700);

        //Actions of those buttons, "QUIT", "RESTART", "BACK TO HOME"
        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setHeaderText("Quit from this page");
                alert.setContentText("Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //root.getChildren().clear();
                    System.exit(0);
                }
            }
        });

        retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main main = new Main();
                Stage stage = main.getSTAGE();
                try {
                    mediaPlayer.stop();
                    main.startGame(actionEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("HOME");
                alert.setContentText("Any unsaved progress will lost!\nBack to Main Menu?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Parent root = null;
                    try {
                        mediaPlayer.stop();
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/index.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    primaryStage.setTitle("ZK 2048");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
            }
        });


    }
}