package com.aufdev.maratonapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Leslie on 05/05/2016.
 */
public class JuegoActivity extends ListActivity {
    private ListView lista;
    private JuegosAdapter juegosAdapter;
    private Juego juego;
    private static ArrayList<Juego> juegos;
    private JSONArray array;

    private String id_user;

    //dfsdf
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);
        id_user = getIntent().getStringExtra("userid");
        System.out.println("JuegosActivity--------usr---"+id_user);
        try {
            getJuegos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        juegos = new ArrayList<Juego>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargaDatos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cargaDatos();
    }

    public ArrayList<Juego> cargaDatos() {
        try {
            System.out.println("***Array** " + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObjectDos = array.getJSONObject(i);
                JSONObject innerGame = jsonObjectDos.getJSONObject("Game");
                JSONObject innerP1 = jsonObjectDos.getJSONObject("player1");
                JSONObject innerP2 = jsonObjectDos.getJSONObject("player2");
                Juego j = new Juego(innerGame.getString("id"),innerP1.getString("username"), innerP2.getString("username"), Integer.parseInt(innerGame.getString("p1score")),
                        Integer.parseInt(innerGame.getString("p2score")), Integer.parseInt(innerGame.getString("ingoranciaScore")), Boolean.valueOf(innerGame.getString("p1turn")),
                        innerP1.getString("id"), innerP2.getString("id"),id_user);

                juegos.add(j);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return juegos;
    }

    //FALTA AGREGAR USUARIO A LA BASE DE DATOS
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        juego = juegosAdapter.getItem(position);
        Intent it = new Intent(this, ruletaActivity.class);
        //  public Juego(String player1, String player2, String scorep1, String scorep2, String score_ignorancia, String turno)
        it.putExtra("scorep1", juego.getScorep1());
        it.putExtra("scorep2", juego.getScorep2());
        it.putExtra("player1", juego.getId_p1());
        it.putExtra("player2", juego.getId_p2());
        it.putExtra("score_ignorancia", juego.getScore_ignorancia());
        it.putExtra("turno", juego.getTurno());
        it.putExtra("id_user", id_user);
        it.putExtra("id_juego",juego.getId());

        if((juego.getTurno()&&id_user.equals(juego.getId_p1())||(!juego.getTurno()&&id_user.equals(juego.getId_p2())))){
            startActivityForResult(it, ruletaActivity.SCORE_UPDATE_REQUEST);
        }else{
            Toast.makeText(JuegoActivity.this, "Aún no es tu turno", Toast.LENGTH_LONG).show();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == ruletaActivity.SCORE_UPDATE_REQUEST)
            {
                int puntos = 0;
                data.getIntExtra("PUNTOS", puntos);
                juego.setScorep1(juego.getScorep1() + puntos);
            }
        }
    }

    //Conexion a servicio
    public void getJuegos() throws JSONException {
        MaratonClient.get("games/json/" + id_user, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("JA-ST: " + statusCode);
                System.out.println("JA-Error");
                Toast.makeText(JuegoActivity.this, "No tienes juegos :(", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("**JSONOBJ HS** " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("**JSONARRAY HS** " + timeline);
                array = timeline;
                juegosAdapter = new JuegosAdapter(JuegoActivity.this, cargaDatos());
                setListAdapter(juegosAdapter);
            }

        });
    }
}