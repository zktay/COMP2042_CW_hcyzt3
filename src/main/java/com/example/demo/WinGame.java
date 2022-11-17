package com.example.demo;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Optional;


public class WinGame {
    private static WinGame singleInstance = null;
    private WinGame(){

    }

    public static WinGame getInstance(){
        if(singleInstance == null)
            singleInstance= new WinGame();
        return singleInstance;
    }

    public void winGameShow(Scene winGameScene, Group root, Stage primaryStage,long score){
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


        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("HOME");
                alert.setContentText("Any unsaved progress will lost!\nBack to Main Menu?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Main start = new Main();
                    /*start.start(Stage primaryStage);*/

                    //root.getChildren().();

                }
            }
        });



    }
}
