package com.aufdev.maratonapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Button jugar, amigos, buscar, hScore;
    private String username;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        jugar = (Button) findViewById(R.id.jugarBtn);
        amigos = (Button) findViewById(R.id.amigosBtn);
        buscar = (Button) findViewById(R.id.buscarBtn);
        hScore = (Button) findViewById(R.id.hScoreBtn);

        ///----
        username = getIntent().getStringExtra("username");
        System.out.println("***UserName: " + username);
        userid =  getIntent().getStringExtra("userid");

    }

    //Si jugador.juegos = null{bloquear boton, lanzar "toast no tienes juegos"} else{activarlo}
    public void jugarOnClick(View v) {
        /*if(usuario.juego) {
            Intent it = new Intent(this, ruletaActivity.class);
            this.startActivity(it);
        }else{
            Toast.makeText(this, "No tienes juegos :( invita a un amigo a jugar", Toast.LENGTH_LONG).show();
        }*/

        Intent it = new Intent(this, JuegosActivity.class);
        this.startActivity(it);
    }

    public void amigosOnClick(View v) {
        Intent it = new Intent(this, AmigosActivity.class);
        it.putExtra("userid", userid);
        this.startActivity(it);
    }

    public void buscarOnClick(View v) {
        Intent it = new Intent(this, BuscarAmigosActivity.class);
        it.putExtra("username", username);
        System.out.println("***UserName: " + username);
        this.startActivity(it);
    }

    public void hScoreOnClick(View v) {
        Intent it = new Intent(this, HighScoreActivity.class);
        it.putExtra("userid", userid);
        this.startActivity(it);
    }

}
