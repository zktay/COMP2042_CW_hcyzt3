package com.example.game.unitTesting;

import com.example.game.Cell;
import com.example.game.spawn;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class spawnTest {
    spawn s = new spawn();
    int n = 1;
    private Cell[][] cells = new Cell[n][n];
    private int[][] oldCells = new int[n][n];
    private int[][] newCells = new int[n][n];

    @Test
    public void spawnOrNot() {
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                newCells[i][j] = 1;
                oldCells[i][j] = 1;
            }
        }
        boolean checkArray = Arrays.deepEquals(newCells, oldCells);
        assertEquals(true, checkArray);
    }

}