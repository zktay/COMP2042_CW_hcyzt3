package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ObservableFaceArray;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class leaderboard implements Initializable {
    private Parent root;
    private Stage stage;
    public static Color colorSelected = Color.rgb(189,177,92);
    @FXML
    private TableView<String> leaderboard;
    @FXML
    private BorderPane Pane;

    @FXML
    private Button backButton;

    @FXML
    private MenuItem aboutButton;

    @FXML
    private MenuItem exitGameTab;

    @FXML
    private Circle firstPlace;

    @FXML
    private Circle secondPlace;

    @FXML
    private Circle thirdPlace;

    @FXML
    private MenuItem tutorial;
    @FXML
    private TableColumn<leaderboard, Integer> index;
    @FXML
    private TableColumn<leaderboard, String> difficulty;
    @FXML
    private TableColumn<leaderboard, String> result;

    @FXML
    private TableColumn<leaderboard, String> score;
    @FXML
    private TableColumn<leaderboard, String> username;

    @FXML
    private TableColumn<leaderboard, String> varient;
    @FXML
    void TutorialButton(ActionEvent event) {

    }

    @FXML
    void exitGame(ActionEvent event) {

    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane.setBackground(new Background(new BackgroundFill(Main.colorSelected, null, null)));
        //leaderboard.setItems();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {

            String line;
            while ((line = br.readLine()) != null){
                String[] array1 = line.split("; ");
                for (String a : array1){
                    for (int i = 0; i < array1.length; i++) {
                        String[] temp1 = a.split(", ");
                        leaderboard.getItems().add(temp1[1]);
                    }
                }
            //    System.out.println(Arrays.toString(array));
            //leaderboard.getItems().add(String.valueOf(new Scoring(1,array[0],array[1],array[2],array[3],array[4])));
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
