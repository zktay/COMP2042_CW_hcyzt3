package com.example.demo;

import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static com.example.demo.controller.n;

public class spawn {
    Cell[][] cells = new Cell[n][n];
    private int[][] oldCells = new int[n][n];
    private int[][] newCells = new int[n][n];
    private TextMaker textMaker = TextMaker.getSingleInstance();
    boolean Spawn = true;
    Group root;

    //Spawning random number. Either 2, 4 or 3, 6
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

    //Checks the board whether contains empty cells
    int  haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
            }
        }
        return -1;
    }

    //Check the board whether to spawn a new cell or not by comparing the indexes of all previous cells in the board with the indexes of all after cells in the board
    //Saves the previous and after moves into their array. If both arrays are the same, then do not spawn
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

}
