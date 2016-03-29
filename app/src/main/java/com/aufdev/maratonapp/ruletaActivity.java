package com.aufdev.maratonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import maraton.Dado;

public class RuletaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruleta_layout);
    }

    public void girarBtnOnclick(View v) {
        int categoria = Dado.getInstance().accion();
        Toast.makeText(this, "Categoria: " + categoria, Toast.LENGTH_LONG).show();
        Intent it = new Intent(this, PreguntaActivity.class);
        it.putExtra("Categoria", categoria);
        startActivity(it);
    }
}
