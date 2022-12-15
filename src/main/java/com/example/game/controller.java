package com.example.game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * controller.java
 * responsible for passing cells to destination, receive user input and process it accordingly
 */
public class controller extends spawn{
    private static int HEIGHT = 600;
    final static int distanceBetweenCells = 10;
    public static int n = 4;
    public static double LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;

    int winValue;
    boolean doNotPrompt = false;
    boolean win = false;
    MediaPlayer mediaPlayer;
    Boolean forceWin = false;
    boolean notContinuing = false;
    long score = 0;


    /**
     * Set N to change the board's size and the length of the cell to make sure the board remain same size.
     * @param number n value to be pass in
     *
     */
    public static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }


    /**
     * Passing one cell to either down, up, left or right including its number to the destination
     * @param i x axis
     * @param j y axis
     * @param direct direction "left, right, up or down"
     * @return
     *
     *
     */
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


    /**
     * Will be called if the user click "left" arrow key. Left function
     */
    void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }
    /**
     * Will be called if the user click "right" arrow key. Right function
     */
    void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * Will be called if the user click "up" arrow key. Up function
     */
    void moveUp() {
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

    /**
     * Will be called if the user click "down" arrow key. Down function
     */
    void moveDown() {
        for (int j = 0; j < n; j++) {
            for (int i = n - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }

        }

    }


    /**
     * Responsible passing two cells and add them together.
     * Playing sound effect when two cells are added together.
     * Controls horizontal moves. Also controls scoring system.
     * @param i x axis
     * @param j y axis
     * @param des destination
     * @param sign sign
     *
     */
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

    /**
     * Responsible passing two cells and add them together.
     * Playing sound effect when two cells are added together.
     * Controls verticals moves. Also controls scoring system.
     * @param i x axis
     * @param j y axis
     * @param des destination
     * @param sign sign
     *
     */
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

    /**
     * Prompt to ask user whether to continue the game after the user reaching the targeted cell.
     */
    void continueOrNot(){
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

    boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0){
            //return true if the criteria met
            return cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0;
        }
        return false;
    }

    boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            //return true if the criteria met
            return cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0;
        }
        return false;
    }

    /**
     * Determine the cell's value to win the game depends on the game's level and size of the board.
     */
    void CellToWin() {
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
                    else if (Setting.levelSelected.equals("3072") && winValue >= 192) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Color") && winValue >= 2048) {
                        forceWin = true;
                        break;
                    }

                }else if (n ==4){
                    if (Setting.levelSelected.equals("Easy") && winValue >= 1024) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Normal") && winValue >= 2048) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Hard") && winValue >= 4096) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Extreme") && winValue >= 8192) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("3072") && winValue >= 3072) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Color") && winValue >= 2048) {
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
                    }else if (Setting.levelSelected.equals("3072") && winValue >= 24576) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Color") && winValue >= 2048) {
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
                    }else if (Setting.levelSelected.equals("3072") && winValue >= 1572864) {
                        win = true;
                        break;
                    } else if (Setting.levelSelected.equals("Color") && winValue >= 2048) {
                        forceWin = true;
                        break;
                    }
                }

            }
        }
    }

    /**
     * play the sound effect when two cells are added together.
     */
    private void soundEffect(){
        String effect = "sounds/success.mp3";
        Media m = new Media(Paths.get(effect).toUri().toString());
        mediaPlayer = new MediaPlayer(m);
        mediaPlayer.play();
    }
}
