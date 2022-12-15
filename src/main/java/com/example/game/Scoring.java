package com.example.game;

/**
 * Scoring.java
 * Setting Scoring object to keep value passed from leaderboard.
 * Every object contains, username, score, variant, difficulty, and result.
 */
public class Scoring {

    //private int index;
    private String username;
    private int score;
    private String variant;
    private String difficulty;
    private String result;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
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
