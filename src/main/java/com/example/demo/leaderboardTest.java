package com.example.demo;

import javafx.collections.FXCollections;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

public class leaderboardTest {
    leaderboard l = new leaderboard();
    ArrayList<Scoring> list = new ArrayList<>();
    Scoring scoringObj = new Scoring();
    String searchThings;

    private void SettingObj(){

    }
    @Test
    public void getScore(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals("Username", scoringObj.getUsername());
    }
    @Test
    public void searchByUsername(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals("Username", scoringObj.getUsername());
    }
    @Test
    public void searchByScore(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals(5, scoringObj.getScore());
    }

    @Test
    public void searchByVariant(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals("4x4", scoringObj.getVariant());
    }

    @Test
    public void searchByDifficulty(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals("Hard", scoringObj.getDifficulty());
    }

    @Test
    public void searchByResult(){
        scoringObj.setUsername("Username");
        scoringObj.setScore(Integer.parseInt("5"));
        scoringObj.setVariant("4x4");
        scoringObj.setDifficulty("Hard");
        scoringObj.setResult("Win");
        list.add(scoringObj);
        l.ScoringList = FXCollections.observableArrayList(list);
        assertEquals("Win", scoringObj.getResult());
    }



}