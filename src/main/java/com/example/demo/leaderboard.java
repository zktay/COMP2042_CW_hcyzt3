package com.example.demo;
/**
 *  leaderboard.java
 *  Responsible for the whole score button's page from the index page.
 *  Will be showing top 3 and record of previous players.
 *
 */
import javafx.beans.binding.Bindings;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class leaderboard implements Initializable {
    private Parent root;
    private Stage stage;
    public static Color colorSelected = Color.rgb(189,177,92);
    private ArrayList<Scoring> podiumArray = new ArrayList<Scoring>();
    @FXML
    private TableView leaderboard;
    @FXML
    private BorderPane Pane;
    ObservableList<Scoring> ScoringList;
    @FXML
    private ChoiceBox<String> filterBoard;
    private String[] tilesChoice = {"3x3", "4x4", "5x5", "6x6", "All"};
    private String boardFiltered;
    @FXML
    private Button backButton;
    @FXML
    private MenuItem aboutButton;
    @FXML
    private MenuItem exitGameTab;
    @FXML
    private Circle firstPlace;
    @FXML
    private Text firstPlaceText;
    @FXML
    private Text firstScore;
    @FXML
    private Circle secondPlace;
    @FXML
    private Text secondPlaceText;
    @FXML
    private Text secondScore;
    @FXML
    private Circle thirdPlace;
    @FXML
    private Text thirdPlaceText;
    @FXML
    private Text thirdScore;
    @FXML
    private MenuItem tutorial;
    @FXML
    private TableColumn<Scoring, Void>  index;
    @FXML
    private TableColumn<Scoring, String> difficulty;
    @FXML
    private TableColumn<Scoring, String> result;
    @FXML
    private TableColumn<Scoring, String> score;
    @FXML
    private TableColumn<Scoring, String> username;

    @FXML
    private TableColumn<Scoring, String> variant;
    @FXML
    private TextField textBox;

    @FXML
    private Button searchButton;
    @FXML
    void TutorialButton(ActionEvent event) {
        Main main = new Main();
        main.howToPlay(event);
    }

    @FXML
    void exitGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @FXML
    void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("2048 Game created by ZKTay under Nottingham University of Malaysia");
        alert.setContentText("Contact Us @ hcyzt3@nottingham.edu.my\n© 2022-2023 | ZKTay");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Duration temp = Main.mediaPlayer.getCurrentTime();
        Main.nowPlaying = temp;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }




    //read previous records from score.txt
    private void getScore(){
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            ArrayList<Scoring> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split("\n");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] data = temp.split(", ");
                        if (Objects.equals(data[2], boardFiltered)){
                            Scoring scoringObj = new Scoring();
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }
                    }
                }

            }
            ScoringList = FXCollections.observableArrayList(list);
            leaderboard.setItems(ScoringList);
            //leaderboard.getSortOrder().add(TableColumn<Scoring, score>);

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*leaderboard.getColumns().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                change.next();
                if(change.wasReplaced()) {
                    leaderboard.getColumns().clear();
                    leaderboard.getColumns().addAll();
                }
            }
        });*/
    }

    //Search function to let user search accordingly to specific filter( tiles, level, username, score, etc)
    @FXML
    void searchAction(ActionEvent event) {
        //clear the observableList to remove all previous items
        ArrayList<Scoring> list = new ArrayList<>();
        ScoringList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split("\n");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] data = temp.split(", ");
                        Scoring scoringObj = new Scoring();
                        //Compare the search value and compare with the text file, if match then add the respective to the Observable list.
                        if (data[0].equals(textBox.getText()) && Objects.equals(data[2], boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        } else if (data[1].equals(textBox.getText()) && Objects.equals(data[2], boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        } else if (data[2].equals(textBox.getText()) && Objects.equals(data[2], boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        } else if (data[3].equals(textBox.getText()) && Objects.equals(data[2], boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[4].equals(textBox.getText()) && Objects.equals(data[2], boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[0].equals(textBox.getText()) && Objects.equals("All", boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[1].equals(textBox.getText()) && Objects.equals("All", boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[2].equals(textBox.getText()) && Objects.equals("All", boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[3].equals(textBox.getText()) && Objects.equals("All", boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (data[4].equals(textBox.getText()) && Objects.equals("All", boardFiltered)) {
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }
                        ScoringList = FXCollections.observableArrayList(list);
                        leaderboard.setItems(ScoringList);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Filter the table to show respective score accordingly to the board size (3x3, 4x4, etc)
    @FXML
    private void filterBoard(ActionEvent event){
        ArrayList<Scoring> list = new ArrayList<>();
        textBox.setText("");
        if (ScoringList != null){
            ScoringList.clear();
        }
        boardFiltered = filterBoard.getValue();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split("\n");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        Scoring scoringObj = new Scoring();
                        String[] data = temp.split(", ");
                        if (Objects.equals(data[2], boardFiltered)){
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }else if (Objects.equals("All", boardFiltered)){
                            scoringObj.setUsername(data[0]);
                            scoringObj.setScore(Integer.parseInt(data[1]));
                            scoringObj.setVariant(data[2]);
                            scoringObj.setDifficulty(data[3]);
                            scoringObj.setResult(data[4]);
                            list.add(scoringObj);
                            sort(list);
                        }
                    }
                }

            }
            podium(boardFiltered);
            ScoringList = FXCollections.observableArrayList(list);
            leaderboard.setItems(ScoringList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sorting function to sort the array from highest to lowest
    /*public static void sortOld (ArrayList<String> arr){
        int N = arr.size();
        int E = N-1;
        String temp;
        boolean flag = true;

        while(flag){
            flag=false;

            for(int a = 0 ; a < E ; a++){
                if(Integer.parseInt(arr.get(a).substring(arr.get(a).indexOf(" ")+1)) <
                        Integer.parseInt(arr.get(a+1).substring(arr.get(a+1).indexOf(" ")+1))) {

                    temp=arr.get(a);
                    arr.set(a, arr.get(a+1));
                    arr.set(a+1, temp);

                    flag=true;
                }
            }
            E--;
        }
    }*/

    //Sort the table data read from the text file from highest to lowest using Scoring Object.getScore().
    public static void sort (ArrayList<Scoring> arr){
        int N = arr.size();
        int E = N-1;

        for(int i=0; i<E; i++ ){ //selection sort
            int max = i;
            for(int j=i+1; j<N; j++){
                if (arr.get(j).getScore() > arr.get(max).getScore()){
                    max = j;
                }
            }
            Scoring temp = arr.get(max);
            arr.set(max, arr.get(i));
            arr.set(i, temp);
        }
    }

    //Sort the top 3 user of respective board size and display it into a text field
    void podium(String board){
        podiumArray.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("data/score.txt")))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] arrayLine = line.split("\n");
                for (String temp : arrayLine){
                    for (int i = 0; i < arrayLine.length; i++) {
                        String[] temp1 = temp.split(", ");
                        if (Objects.equals(temp1[2], board)) {
                            Scoring scoringObj = new Scoring();
                            scoringObj.setUsername(temp1[0]);
                            scoringObj.setScore(Integer.parseInt(temp1[1]));
                            scoringObj.setVariant(temp1[2]);
                            scoringObj.setDifficulty(temp1[3]);
                            scoringObj.setResult(temp1[4]);
                            podiumArray.add(scoringObj);
                        }else if (Objects.equals("All", board)){
                            Scoring scoringObj = new Scoring();
                            scoringObj.setUsername(temp1[0]);
                            scoringObj.setScore(Integer.parseInt(temp1[1]));
                            scoringObj.setVariant(temp1[2]);
                            scoringObj.setDifficulty(temp1[3]);
                            scoringObj.setResult(temp1[4]);
                            podiumArray.add(scoringObj);
                        }
                    }
                }
            }
            //String[] topThree = new String[podiumArray.size()];
            ArrayList<Scoring> topThree= new ArrayList<Scoring>();
            sort(podiumArray);
            for (int i = 0; i < podiumArray.size(); i++ ){
                topThree.add(i, podiumArray.get(i));
                //System.out.println(podiumArray.toString());
            }
            firstPlaceText.setText(topThree.get(0).getUsername());
            firstScore.setText(String.valueOf(topThree.get(0).getScore()));
            secondPlaceText.setText(topThree.get(1).getUsername());
            secondScore.setText(String.valueOf(topThree.get(1).getScore()));
            thirdPlaceText.setText(topThree.get(2).getUsername());
            thirdScore.setText(String.valueOf(topThree.get(2).getScore()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Initialize the data and set to respective text field and table.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane.setBackground(new Background(new BackgroundFill(Main.colorSelected, null, null)));
        filterBoard.getItems().addAll(tilesChoice);
        switch (GameScene.n){
            case 3:
                filterBoard.setValue("3x3");
                boardFiltered = "3x3";
                break;
            case 4:
                filterBoard.setValue("4x4");
                boardFiltered = "4x4";
                break;
            case 5:
                filterBoard.setValue("5x5");
                boardFiltered = "5x5";
                break;
            case 6:
                filterBoard.setValue("6x6");
                boardFiltered = "6x6";
                break;
            default:
                filterBoard.setValue("All");
                boardFiltered = "All";
                break;
        }
        getScore();
        podium(boardFiltered);
        //Index
        TableColumn<Scoring, Void> index = new TableColumn<>("Index");
        index.setPrefWidth(60);
        index.setResizable(false);
        index.setReorderable(false);
        leaderboard.getColumns().add(index);

        //Username
        TableColumn<Scoring, Void> username = new TableColumn<>("Username");
        username.setPrefWidth(225);
        username.setResizable(false);
        username.setReorderable(false);
        leaderboard.getColumns().add(username);

        //Score
        TableColumn<Scoring, Void> score = new TableColumn<>("Score");
        score.setPrefWidth(115);
        score.setResizable(false);
        score.setReorderable(false);
        leaderboard.getColumns().add(score);

        //Variant
        TableColumn<Scoring, Void> variant = new TableColumn<>("Variant");
        variant.setPrefWidth(80);
        variant.setResizable(false);
        variant.setReorderable(false);
        leaderboard.getColumns().add(variant);

        //Difficulty
        TableColumn<Scoring, Void> difficulty = new TableColumn<>("Difficulty");
        difficulty.setPrefWidth(75);
        difficulty.setResizable(false);
        difficulty.setReorderable(false);
        leaderboard.getColumns().add(difficulty);

        //Result
        TableColumn<Scoring, Void> result = new TableColumn<>("Result");
        result.setPrefWidth(100);
        result.setResizable(false);
        result.setReorderable(false);
        leaderboard.getColumns().add(result);

        //Data binding
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));
        variant.setCellValueFactory(new PropertyValueFactory<>("Variant"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("Difficulty"));
        result.setCellValueFactory(new PropertyValueFactory<>("Result"));


        index.setCellFactory(col -> {
            TableCell<Scoring, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null ;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell ;
        });
        //leaderboard.setItems();

    }
}
