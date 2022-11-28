package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.security.Key;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.jar.Manifest;

class GameScene {
    private static int HEIGHT = 600;
    public static int n = 4;
    //public static int n = 5;
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
    static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }

    static double getLENGTH() {
        return LENGTH;
    }

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
        if (putTwo) {
            text = textMaker.madeText("2", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(2);
        } else {
            text = textMaker.madeText("4", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(4);
        }


    }

    private int  haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
                if(cells[i][j].getNumber() == 2048)
                    return 0;
            }
        }
        return -1;
    }

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

    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            if (cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0) {
                return true;
            }
        }
        return false;
    }

    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            // update score by getting score from new cell
            score += cells[i][des + sign].getNumber();
            continueOrNot();
            cells[i][des].setModify(true);
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        }
    }

    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            // update score by getting score from new cell
            score += cells[des + sign][j].getNumber();
            continueOrNot();
            cells[des][j].setModify(true);
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        }
    }

    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0)
            if (cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0) {
                return true;
            }
        return false;
    }



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

    private void CellToWin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                winValue = cells[i][j].getNumber();
                if (winValue >= 2048){
                    win = true;
                    break;
                }
            }
        }
    }
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
    void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot, Scene winGameScene, Group wingameRoot) {



        this.root = root;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell((j) * LENGTH + (j + 1) * distanceBetweenCells,
                        (i) * LENGTH + (i + 1) * distanceBetweenCells, LENGTH, root);
            }

        }

        Text title = new Text();
        root.getChildren().add(title);
        title.setText("2048");
        title.setFont(Font.font(110));
        title.relocate(600, 10);


        Text username = new Text();
        root.getChildren().add(username);
        username.setText("Name:");
        username.setFont(Font.font(30));
        username.relocate(600, 150);

        Text usernameText = new Text();
        root.getChildren().add(usernameText);
        usernameText.setText(Main.usernameEnter);
        usernameText.setFont(Font.font(30));
        usernameText.relocate(690, 150);

        Text text = new Text();
        root.getChildren().add(text);
        text.setText("Score:");
        text.setFont(Font.font(30));
        text.relocate(605, 200);

        Text scoreText = new Text();
        root.getChildren().add(scoreText);
        scoreText.setFont(Font.font(30));
        scoreText.relocate(690, 200);
        scoreText.setText("0");

        randomFillNumber(1);
        randomFillNumber(1);
        spawnOrNot("old");
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key ->{ //changed to key_released to avoid key holding
                Platform.runLater(() -> {
                    if (notContinuing){
                        //Group wingameRoot = main.getWinRoot();
                        //Stage primaryStage = main.getSTAGE();
                        //Scene winGameScene = main.getWinScene();
                        primaryStage.setScene(winGameScene);
                        WinGame.getInstance().winGameShow(winGameScene, wingameRoot, primaryStage, score);
                        root.getChildren().clear();
                        score = 0;
                    }
                    //Check keypress
                    if (key.getCode() == KeyCode.DOWN || key.getCode() == KeyCode.UP || key.getCode() == KeyCode.LEFT || key.getCode() == KeyCode.RIGHT){

                        int haveEmptyCell;
                        if (key.getCode() == KeyCode.DOWN) {
                            GameScene.this.moveDown();
                            spawnOrNot("new");
                        } else if (key.getCode() == KeyCode.UP) {
                            GameScene.this.moveUp();
                            spawnOrNot("new");
                        } else if (key.getCode() == KeyCode.LEFT) {
                            GameScene.this.moveLeft();
                            spawnOrNot("new");
                        } else if (key.getCode() == KeyCode.RIGHT){
                            GameScene.this.moveRight();
                            spawnOrNot("new");
                        }
                        scoreText.setText(score + "");
                        haveEmptyCell = GameScene.this.haveEmptyCell();
                        if (haveEmptyCell == -1) {

                            if (GameScene.this.canNotMove()) {

                                if (win){
                                    primaryStage.setScene(winGameScene);
                                    WinGame.getInstance().winGameShow(winGameScene, wingameRoot, primaryStage, score);
                                }else {
                                    primaryStage.setScene(endGameScene);
                                    EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score);
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
