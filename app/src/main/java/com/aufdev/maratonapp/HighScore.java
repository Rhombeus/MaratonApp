package com.aufdev.maratonapp;

/**
 * Created by Leslie on 01/05/2016.
 */
public class HighScore {
    private String player;
    private int score;

    public HighScore(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
