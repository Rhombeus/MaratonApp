package com.aufdev.maratonapp;
/**
 * Created by Leslie on 01/05/2016.
 */
public class Usuario {
    private String nombre;
    private String correo;
    private int score;

    public Usuario(String nombre, String correo, int score) {
        this.nombre = nombre;
        this.correo = correo;
        this.score = score;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
