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
    private Juego juego;
    public static final int SCORE_UPDATE_REQUEST = 0;
    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruleta_layout);
        ruleta = (ImageView)findViewById(R.id.imgRuleta);
        girar = (Button) findViewById(R.id.girarBtn);
        finalizar = (Button) findViewById(R.id.finalizarbtn);

        Intent it = this.getIntent();

        int scorep1 = it.getIntExtra("scorep1", 0);
        int scorep2 = it.getIntExtra("scorep2", 0);
        int score_ignorancia = it.getIntExtra("score_ignorancia", 0);
        String player1 = it.getStringExtra("player1");
        String player2 = it.getStringExtra("player2");
        Boolean turno = it.getBooleanExtra("turno", false);
        id_user = it.getStringExtra(id_user);

        if(!turno){
            girar.setEnabled(false);
            finalizar.setEnabled(false);
        }else{
            girar.setEnabled(true);
            finalizar.setEnabled(true);

        }

        juego = new Juego(player1, player2, scorep1, scorep2, score_ignorancia, turno);
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
                it.putExtra("id_user", id_user);
                startActivityForResult(it, SCORE_UPDATE_REQUEST); //startActivityForResult
                //RuletaActivity.this.finish();
            }
        }, 3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == SCORE_UPDATE_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                //TODO : Checar qué jugador respondió, de manera que se actualice el score del jugador correcto
                //Por el momento, solamente se actualiza el de player1. Se compara el
                int puntos = 0; //En lugar de int, JSONObject y de ahí jalas todo.
                data.getIntExtra("puntos", puntos);
                juego.setScorep1(juego.getScorep1() +  puntos);


            }
        }
    }

    public void finalizarBtn(View v){
        //IR A VISTA DE PERDER O GANAR O SOLO TU SCORE
        finish();
    }
}
