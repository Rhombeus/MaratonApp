package com.aufdev.maratonapp;

/**
 * Created by Leslie on 03/05/2016.
 */
public class Juego {
    private String id;
    private String player1;
    private String id_p1;
    private String player2;
    private String id_p2;
    private int scorep1;
    private int scorep2;
    private int score_ignorancia;
    private boolean turno;

    public Juego(String player1, String player2, int scorep1, int scorep2, int score_ignorancia, boolean turno, String id_p1, String id_p2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scorep1 = scorep1;
        this.scorep2 = scorep2;
        this.score_ignorancia = score_ignorancia;
        this.turno = turno;
        this.id_p1 = id_p1;
        this.id_p2 = id_p2;
    }

    public Juego(String id_p1, String id_p2, int scorep1, int scorep2, int score_ignorancia, boolean turno) {
        this.id_p1 = id_p1;
        this.id_p2 = id_p2;
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

    public int getScorep1() {
        return scorep1;
    }

    public void setScorep1(int scorep1) {
        this.scorep1 = scorep1;
    }

    public int getScorep2() {
        return scorep2;
    }

    public void setScorep2(int scorep2) {
        this.scorep2 = scorep2;
    }

    public int getScore_ignorancia() {
        return score_ignorancia;
    }

    public void setScore_ignorancia(int score_ignorancia) {
        this.score_ignorancia = score_ignorancia;
    }

    public Boolean getTurno() {
        return turno;
    }

    public void setTurno(Boolean turno) {
        this.turno = turno;
    }

    public String getId_p1() {
        return id_p1;
    }

    public void setId_p1(String id_p1) {
        this.id_p1 = id_p1;
    }

    public String getId_p2() {
        return id_p2;
    }

    public void setId_p2(String id_p2) {
        this.id_p2 = id_p2;
    }
}
