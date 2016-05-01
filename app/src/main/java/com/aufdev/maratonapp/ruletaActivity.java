package com.aufdev.maratonapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import maraton.Dado;

public class ruletaActivity extends AppCompatActivity {
    private ImageView ruleta;
    private Button girar;
    private Button finalizar;
    private int arr[] = {400, 335, 270, 205, 140, 75};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruleta_layout);
        ruleta = (ImageView)findViewById(R.id.imgRuleta);
        girar = (Button) findViewById(R.id.girarBtn);
        finalizar = (Button) findViewById(R.id.finalizarbtn);
    }

    public void girarBtnOnclick(View v) {
        girar.setEnabled(false);
        finalizar.setEnabled(false);
        //ruleta = null;
        //ruleta.setVisibility(View.VISIBLE);
        //ruleta = (ImageView)findViewById(R.id.imgRuleta);
        Random r = new Random();
        int rand_num = Math.abs(r.nextInt() % 6);
        ruleta.animate()
                .rotation(360 + arr[rand_num]).setInterpolator(new AccelerateDecelerateInterpolator())
                .start();


        //final int categoria = Dado.getInstance().accion();
        final int categoria = rand_num+1;
        System.out.println("****Categoria: "+categoria);
        //Toast.makeText(this, "Categoria: " + categoria, Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ruleta.clearAnimation();
                girar.setEnabled(true);
                finalizar.setEnabled(true);
                Intent it = new Intent(ruletaActivity.this, PreguntaActivity.class);
                it.putExtra("Categoria", categoria);
                startActivity(it);
                //RuletaActivity.this.finish();
            }
        }, 3000);

    }

    public void finalizarBtn(View v){
        //IR A VISTA DE PERDER O GANAR O SOLO TU SCORE
        finish();
    }
}
