package com.aufdev.maratonapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Button jugar, amigos, buscar, hScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        jugar = (Button) findViewById(R.id.jugarBtn);
        amigos = (Button) findViewById(R.id.amigosBtn);
        buscar = (Button) findViewById(R.id.buscarBtn);
        hScore = (Button) findViewById(R.id.hScoreBtn);
    }

    public void jugarOnClick(View v) {
        Intent it = new Intent(this, RuletaActivity.class);
        this.startActivity(it);

    }

    public void amigosOnClick(View v) {
        Intent it = new Intent(this, AmigosActivity.class);
        it.putExtra("Tipo", 1);
        this.startActivity(it);
    }

    public void buscarOnClick(View v) {
        Intent it = new Intent(this, AmigosActivity.class);
        it.putExtra("Tipo", 2);
        this.startActivity(it);
    }

    public void hScoreOnClick(View v) {
        Intent it = new Intent(this, HighScoreActivity.class);
        this.startActivity(it);
    }
}