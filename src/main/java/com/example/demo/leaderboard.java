package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class leaderboard implements Initializable {
    private Parent root;
    private Stage stage;
    public static Color colorSelected = Color.rgb(189,177,92);
    //ArrayList<String> podiumArray = new ArrayList<String>();
    String[][] podiumArray;
    @FXML
    private TableView leaderboard;
    @FXML
    private BorderPane Pane;
    ObservableList<Scoring> Scoring;
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
    private TableColumn  index;
    @FXML
    private TableColumn difficulty;
    @FXML
    private TableColumn result;
    @FXML
    private TableColumn score;
    @FXML
    private TableColumn username;

    @FXML
    private TableColumn variant;
    @FXML
    private TextField textBox;

    @FXML
    private Button searchButton;
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
        getScore();
        podium();
        index.setCellValueFactory(new PropertyValueFactory<>("Index"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));
        variant.setCellValueFactory(new PropertyValueFactory<>("Variant"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("Difficulty"));
        result.setCellValueFactory(new PropertyValueFactory<>("Result"));
        //leaderboard.setItems();
        leaderboard.getColumns().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                change.next();
                if(change.wasReplaced()) {
                    leaderboard.getColumns().clear();
                    leaderboard.getColumns().addAll();
                }
            }
        });
    }

    private void getScore(){
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            Scoring = FXCollections.observableArrayList();
            String line;
            int counter = 1;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split(";");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] data = temp.split(", ");
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                    }
                }
                counter ++;
            }

            leaderboard.setItems(Scoring);
            //leaderboard.getSortOrder().add(TableColumn<Scoring, score>);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchAction(ActionEvent event) {
        //clear the observableList to remove all previous items
        Scoring.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            String line;
            int counter = 1;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split(";");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] data = temp.split(", ");
                        //Compare the search value and compare with the text file, if match then add the respective to the Observable list.
                        if (data[0].equals(textBox.getText())) {
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                            leaderboard.setItems(Scoring);
                            //resetting the index
                            counter ++;
                        } else if (data[1].equals(textBox.getText())) {
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                            leaderboard.setItems(Scoring);
                            counter ++;
                        } else if (data[2].equals(textBox.getText())) {
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                            leaderboard.setItems(Scoring);
                            counter ++;
                        } else if (data[3].equals(textBox.getText())) {
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                            leaderboard.setItems(Scoring);
                            counter ++;
                        }else if (data[4].equals(textBox.getText())) {
                            Scoring.add(new Scoring(counter, data[0], Integer.parseInt(data[1]), data[2], data[3], data[4]));
                            leaderboard.setItems(Scoring);
                            counter ++;

                        }

                    }


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String[][] bubbleSort(String arr[][]) {
        int n = arr.length;
        int tempAge = 0;
        String tempName = "";
        int []result = new int[n];
        String myName[] = new String [n];
        for (int i = 0; i < n; i++)
        {
            result[i] = Integer.parseInt(arr[i][1]);
            myName[i] = arr[i][0];
        }
        boolean sorted = false;
        while (!sorted){
            sorted = true;
            for(int i = 1; i < n; i++) {
                if(result[i-1] > result[i]) {
                    tempAge = result[i-1];
                    result[i-1] = result[i];
                    result[i] = tempAge;
                    tempName = myName[i-1];
                    myName[i-1] = myName[i];
                    myName[i] = tempName;
                    sorted = false;
                }
            }
        }
        for (int i = 0; i < arr.length; i++)
        {
            arr[i][0] = myName[i];
            arr[i][1] = Integer.toString(result[i]);
        }
        return arr;
    }

    void podium(){
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split(";");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] temp1 = temp.split(", ");
                        podiumArray = new String[Integer.parseInt(temp1[0])][Integer.parseInt(temp1[1])];
                        //podiumArray = new String[]{Arrays.toString(podiumArray)};
                        System.out.println(Arrays.toString(podiumArray));
                        System.out.println(Arrays.deepToString(podiumArray));
                        bubbleSort(podiumArray);
                        //https://stackoverflow.com/questions/60841514/2d-bubble-sort-java-program-that-have-string-and-integer-in-array
                        //podiumArray.add();

                        //String data = temp1[0] + ", " +temp1[1];
                        //podiumArray.add(data);
                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
