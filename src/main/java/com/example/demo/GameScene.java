package com.example.demo;
/**
 *  GameScene.java
 *  The controller class for the whole functions in the board during a game
 *  Includes moves, add, spawns, sketching the boards tiles, when the cell reach the number to win, user's score, checking user lose or not, etc
 *
 */
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

class GameScene {
    private static int HEIGHT = 600;
    public static int n = 4;
    private final static int distanceBetweenCells = 10;
    public static double LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    private TextMaker textMaker = TextMaker.getSingleInstance();
    private Cell[][] cells = new Cell[n][n];
    private Group root;
    private long score = 0;
    private boolean win = false;
    int winValue;
    boolean notContinuing = false;
    boolean doNotPrompt = false;
    private boolean Spawn = true;
    private int[][] oldCells = new int[n][n];
    private int[][] newCells = new int[n][n];
    MediaPlayer mediaPlayer;
    Boolean forceWin = false;
    static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }

    static double getLENGTH() {
        return LENGTH;
    }

    //Spawning random number. Either 2 or 4
    private void randomFillNumber(int turn) {
        Cell[][] emptyCells = new Cell[n][n];
        int a = 0;
        int b = 0;
        int aForBound = 0, bForBound = 0;
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0) {
                    emptyCells[a][b] = cells[i][j];
                    if (b < n - 1) {
                        bForBound = b;
                        b++;

                    } else {
                        aForBound = a;
                        a++;
                        b = 0;
                        if (a == n)
                            break outer;
                    }
                }
            }
        }


        Text text;
        Random random = new Random();
        boolean putTwo = true;
        if (random.nextInt() % 2 == 0)
            putTwo = false;
        int xCell, yCell;
        xCell = random.nextInt(aForBound + 1);
        yCell = random.nextInt(bForBound + 1);
        if (Setting.levelSelected.equals("3072")) {
            if (putTwo) {
                text = textMaker.madeText("3", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
                emptyCells[xCell][yCell].setTextClass(text);
                root.getChildren().add(text);
                emptyCells[xCell][yCell].setColorByNumber(3);
            } else {
                text = textMaker.madeText("6", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
                emptyCells[xCell][yCell].setTextClass(text);
                root.getChildren().add(text);
                emptyCells[xCell][yCell].setColorByNumber(6);
            }
        }else {
            if (putTwo) {
                text = textMaker.madeText("2", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
                emptyCells[xCell][yCell].setTextClass(text);
                root.getChildren().add(text);
                emptyCells[xCell][yCell].setColorByNumber(3);
            } else {
                text = textMaker.madeText("4", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
                emptyCells[xCell][yCell].setTextClass(text);
                root.getChildren().add(text);
                emptyCells[xCell][yCell].setColorByNumber(6);
            }
        }


    }

    //Checks the board whether contains empty cells
    private int  haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
            }
        }
        return -1;
    }

    //To pass the cell to destination index after adding up two cells
    // direct l = left, r = right, u = up, d = down
    private int passDestination(int i, int j, char direct) {
        int coordinate = j;
        if (direct == 'l') {
            for (int k = j - 1; k >= 0; k--) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        coordinate = j;
        if (direct == 'r') {
            for (int k = j + 1; k <= n - 1; k++) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'd') {
            for (int k = i + 1; k <= n - 1; k++) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k - 1;
                    break;

                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'u') {
            for (int k = i - 1; k >= 0; k--) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        return -1;
    }

    //Check the board whether to spawn a new cell or not by comparing the indexes of all previous cells in the board with the indexes of all after cells in the board
    //Saves the previous and after moves into their array. If both arrays are the same, then do not spawn
    private void spawnOrNot(String determine){
        if (Objects.equals(determine, "old")){
            for(int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    oldCells[i][j] = cells[i][j].getNumber();

                }
            }
        }else if(Objects.equals(determine, "new")){
            for(int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    newCells[i][j] = cells[i][j].getNumber();

                }
            }
        }
        boolean checkArray = Arrays.deepEquals(oldCells, newCells);
        if (checkArray){
            Spawn = false;
        }else {
            Spawn = true;
        }

    }
    //Move left function
    private void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    //Move right function
    private void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    //Move up function
    private void moveUp() {
        for (int j = 0; j < n; j++) {
            //for (int i = n - 1; i >= 0; i--) {
            for (int i = 1; i < n; i++) {
                moveVertically(i, j, passDestination(i, j, 'u'), -1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }

    }

    //Move down function
    private void moveDown() {

        for (int j = 0; j < n; j++) {
            for (int i = n - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }

    }

    //Check the horizontal destination is valid or not
    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            if (cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0) {
                return true;
            }
        }
        return false;
    }

    //Controls horizontals moves. Also controls scoring system
    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            // update score by getting score from new cell
            score += cells[i][des + sign].getNumber();
            if (Setting.playEffect){
                soundEffect();
            }
            continueOrNot();
            cells[i][des + sign].setModify(true);
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        }
    }

    //Controls verticals moves. Also controls scoring system
    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            // update score by getting score from new cell
            score += cells[des + sign][j].getNumber();
            if (Setting.playEffect){
                soundEffect();
            }
            continueOrNot();
            cells[des + sign][j].setModify(true);
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        }
    }

    //Check the vertical destination is valid or not
    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0)
            if (cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0) {
                return true;
            }
        return false;
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

    //Determine the cells number to achieve accordingly to the level selected and board selected.
    private void CellToWin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                winValue = cells[i][j].getNumber();
                if (n == 3){
                    if (Setting.levelSelected.equals("Easy") && winValue >= 64){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Normal") && winValue >= 128){
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Hard") && winValue >= 256){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Extreme") && winValue >= 512){
                        win = true;
                        break;
                    }
                    else if (Setting.levelSelected.equals("3072") && winValue >= 192){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Color") && winValue == 2048){
                        forceWin = true;
                        break;
                    }

                }else if (n ==4){
                    if (Setting.levelSelected.equals("Easy") && winValue >= 1024){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Normal") && winValue >= 2048){
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Hard") && winValue >= 4096){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Extreme") && winValue >= 8192){
                        win = true;
                        break;
                    }
                    else if (Setting.levelSelected.equals("3072") && winValue >= 3072){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Color") && winValue == 2048){
                        forceWin = true;
                        break;
                    }
                }else if (n ==5){
                    if (Setting.levelSelected.equals("Easy") && winValue >= 8192){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Normal") && winValue >= 16384){
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Hard") && winValue >= 32768){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Extreme") && winValue >= 65536){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("3072") && winValue >= 24576){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Color") && winValue == 2048){
                        forceWin = true;
                        break;
                    }

                }else if (n ==6){
                    if (Setting.levelSelected.equals("Easy") && winValue >= 524288){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Normal") && winValue >= 1048576){
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Hard") && winValue >= 2097152){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Extreme") && winValue >= 4194304){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("3072") && winValue >= 1572864){
                        win = true;
                        break;
                    }else if (Setting.levelSelected.equals("Color") && winValue == 2048){
                        forceWin = true;
                        break;
                    }
                }

            }
        }
    }

    /**
     *
     */
    //To show prompt and ask user whether to continue or stop the game once they reach the targeted cell's number
    private void continueOrNot(){
        CellToWin();
        if(win && !doNotPrompt){
            ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
            ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "",okButton, noButton);
            alert.setTitle("Congrats!");
            alert.setHeaderText("You have finished the game.\nDo you want to continue?");
            alert.setContentText("Good Luck Have Fun!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                doNotPrompt = true;
            }else if(result.get() == noButton){
                doNotPrompt = true;
                notContinuing = true;
            }


        }

    }

    private void soundEffect(){
        String effect = "sounds/success.mp3";
        Media m = new Media(Paths.get(effect).toUri().toString());
        mediaPlayer = new MediaPlayer(m);
        mediaPlayer.play();
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
                        if (key.getCode() == KeyCode.DOWN) {
                            GameScene.this.moveDown();
                        } else if (key.getCode() == KeyCode.UP) {
                            GameScene.this.moveUp();
                        } else if (key.getCode() == KeyCode.LEFT) {
                            GameScene.this.moveLeft();
                        } else if (key.getCode() == KeyCode.RIGHT){
                            GameScene.this.moveRight();
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
