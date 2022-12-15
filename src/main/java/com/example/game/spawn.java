package com.example.game;

import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static com.example.game.controller.n;

/**
 * spawn.java
 * Responsible for spawning cells, and detecting have empty cells or not to determine user win or lost the game
 */
public class spawn {
    public Cell[][] cells = new Cell[n][n];
    private int[][] oldCells = new int[n][n];
    private int[][] newCells = new int[n][n];
    private TextMaker textMaker = TextMaker.getSingleInstance();
    boolean Spawn = true;
    Group root;

    /**
     * Spawning random number. Either 2, 4 or 3, 6 in any empty cell
     * @param turn
     *
     */
    void randomFillNumber(int turn) {
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

    /**
     * Checks the board whether contains empty cells to determine the game need to be end or not.
     * @return integer
     *
     */
    int  haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
            }
        }
        return -1;
    }

    /**
     * Check the board whether to spawn a new cell or not by comparing the indexes of all previous cells in the board with the indexes of all after cells in the board
     * aves the previous and after moves into their array. If both arrays are the same, then do not spawn
     * @param determine check the input is "old" or "new" to determine what to do
     *
     */
    void spawnOrNot(String determine){
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

    /**
     * Check cells between cells have same number or not to avoid false "noMoreMoves"
     * @param i x axis
     * @param j y axis
     * @return return boolean
     *
     */
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

    /**
     * If the board are full and no same numbers around the cells then return true to canNotMove.
     * @return boolean
     *
     */

    boolean canNotMove() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (haveSameNumberNearly(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

}
