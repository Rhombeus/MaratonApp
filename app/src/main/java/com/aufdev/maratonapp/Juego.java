package com.aufdev.maratonapp;

/**
 * Created by Leslie on 03/05/2016.
 */
public class Juego {
    private String id;
    private String player1;
    private String player2;
    private String scorep1;
    private String scorep2;
    private String score_ignorancia;
    private String turno;

    public Juego(String player1, String player2, String scorep1, String scorep2, String score_ignorancia, String turno) {
        this.player1 = player1;
        this.player2 = player2;
        this.scorep1 = scorep1;
        this.scorep2 = scorep2;
        this.score_ignorancia = score_ignorancia;
        this.turno = turno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getScorep1() {
        return scorep1;
    }

    public void setScorep1(String scorep1) {
        this.scorep1 = scorep1;
    }

    public String getScorep2() {
        return scorep2;
    }

    public void setScorep2(String scorep2) {
        this.scorep2 = scorep2;
    }

    public String getScore_ignorancia() {
        return score_ignorancia;
    }

    public void setScore_ignorancia(String score_ignorancia) {
        this.score_ignorancia = score_ignorancia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
