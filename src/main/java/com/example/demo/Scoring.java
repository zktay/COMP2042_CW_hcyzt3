package com.example.demo;

public class Scoring {

    private int index;
    private String username;
    private String score;
    private String variant;
    private String difficulty;
    private String result;



    public Scoring(int index, String username, String score, String variant, String difficulty, String result){
        this.index = index;
        this.username = username;
        this.score = score;
        this.variant = variant;
        this.difficulty = difficulty;
        this.result = result;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
