package com.example.demo;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class WinGame {
    private static WinGame singleInstance = null;
    private String username;
    private WinGame(){

    }

    public static WinGame getInstance(){
        if(singleInstance == null)
            singleInstance= new WinGame();
        return singleInstance;
    }

    public void winGameShow(Scene winGameScene, Group root, Stage primaryStage,long score, int n) throws IOException {
        username = Main.usernameEnter;
        String varient = null;
        switch (n){
            case 3:
                varient = "3x3";
                break;
            case 4:
                varient = "4x4";
                break;
            case 5:
                varient = "5x5";
                break;
            case 6:
                varient = "6x6";
                break;
        }
        File file = new File("data/score.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(username + ", " + score + ", " + varient + ", " + Setting.levelSelected +", WIN" + "\n");
        bw.close();

        Text text = new Text("YOU WON!");
        text.relocate(200,250);
        text.setFont(Font.font(80));
        root.getChildren().add(text);

        Text scoreText = new Text(score+"");


        String temp = String.valueOf(score);
        //Wrong Approach cuz one length doesnt means one pixel
        int  temp1 = temp.length();
        int scoreAlign = ((900 - temp1) /2) - 100;

        scoreText.setFill(Color.BLACK);

        scoreText.relocate(scoreAlign,350);
        scoreText.setFont(Font.font(70));
        root.getChildren().add(scoreText);

        Button quitButton = new Button("QUIT");
        quitButton.setPrefSize(100,30);
        quitButton.setTextFill(Color.BLACK);
        root.getChildren().add(quitButton);
        quitButton.relocate(100,700);

        Button retryButton = new Button("NEW GAME");
        retryButton.setPrefSize(100,30);
        retryButton.setTextFill(Color.BLACK);
        root.getChildren().add(retryButton);
        retryButton.relocate(250,700);

        Button homeButton = new Button("BACK TO HOME");
        homeButton.setPrefSize(200,30);
        homeButton.setTextFill(Color.BLACK);
        root.getChildren().add(homeButton);
        homeButton.relocate(600,700);


        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setHeaderText("Quit from this page");
                alert.setContentText("Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
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
                if (result.get() == ButtonType.OK){
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    primaryStage.setTitle("ZK 2048");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();


                }
            }
        });



    }
}
