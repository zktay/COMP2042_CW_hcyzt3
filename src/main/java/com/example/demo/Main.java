package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main extends Application implements Initializable {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double height = screenBounds.getHeight();
    double width = screenBounds.getWidth()/2;*/
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


    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            setStage(primaryStage);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pics/Icon.png")));
            primaryStage.getIcons().add(image);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            //WIDTH = screenBounds.getMaxX();
            //HEIGHT = screenBounds.getMaxY();
            //System.out.println(WIDTH);
            //System.out.println(HEIGHT);

            //Below are fxml files
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));

            primaryStage.setTitle("ZK 2048");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e){
            System.out.println(e);
        }

    }


    @FXML
    void exitGame(ActionEvent event) {
        Controller con = new Controller();
        con.exitGame(event);
    }

    @FXML
    private void username(ActionEvent event) {
        usernameEnter = usernameField.getText();
    }

    @FXML
    public void startGame(ActionEvent event) {
        if (usernameField != null){
            username(event);
        }
        if (!Objects.equals(usernameEnter, "")) {
            Stage stage = getSTAGE();
            //Group menuRoot = new Group();
            //Scene menuScene = new Scene(menuRoot, WIDTH, HEIGHT);
            //Group accountRoot = new Group();
            //Scene accountScene = new Scene(accountRoot, WIDTH, HEIGHT, Color.rgb(150, 20, 100, 0.2));
            //Group getAccountRoot = new Group();
            //Scene getAccountScene = new Scene(getAccountRoot, WIDTH, HEIGHT, Color.rgb(200, 20, 100, 0.2));
            Group endgameRoot = new Group();
            Group wingameRoot = new Group();
            Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, Color.rgb(194, 70, 65));
            Scene winGameScene = new Scene(wingameRoot, WIDTH, HEIGHT, Color.rgb(52, 165, 111));
            //Group rankRoot = new Group();
            //Scene rankScene = new Scene(rankRoot, WIDTH, HEIGHT, Color.rgb(250, 50, 120, 0.3));
            //BackgroundFill background_fill = new BackgroundFill(Color.rgb(120, 100, 100), CornerRadii.EMPTY, Insets.EMPTY);
            //Background background = new Background(background_fill);


            /*Rectangle backgroundOfMenu = new Rectangle(240, 120, Color.rgb(120, 120, 120, 0.2));
            backgroundOfMenu.setX(WIDTH / 2 - 120);
            backgroundOfMenu.setY(180);
            menuRoot.getChildren().add(backgroundOfMenu);*/

            /*Rectangle backgroundOfMenuForPlay = new Rectangle(240, 140, Color.rgb(120, 20, 100, 0.2));
            backgroundOfMenuForPlay.setX(WIDTH / 2 - 120);
            backgroundOfMenuForPlay.setY(180);
            accountRoot.getChildren().add(backgroundOfMenuForPlay);*/

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
            stage.show();
        }else{

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Enter Username");
            alert.setHeaderText("Please enter your username to start the game.");
            //alert.setContentText("Enter ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

            }
        }
    }

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
    @FXML
    void setting(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("setting.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("ZK 2048");
        stage.setScene(new Scene(root));
        stage.show();

        //inSetting = true;
    }

    /*@FXML
    void TutorialButton(ActionEvent event) {
        getHostServices().showDocument("https://www.youtube.com/watch?v=-rqRWzSP2iM");
    }*/

    @FXML
    public void help(ActionEvent event) {
        Controller con = new Controller();
        con.help(event);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Controller c = new Controller();
        if (c.getColorSelected() != null){
            colorSelected = c.getColorSelected();
        }
        indexPanel.setBackground(new Background(new BackgroundFill(colorSelected, null, null)));

    }
}
