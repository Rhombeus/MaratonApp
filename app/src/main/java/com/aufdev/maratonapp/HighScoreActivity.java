package com.aufdev.maratonapp;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HighScoreActivity extends ListActivity{
    private ListView lista;
    private highscoreAdapter highscoreAdapter;
    private HighScore highScore;
    private static ArrayList<HighScore> puntajes ;
    private JSONArray array;
    private Button top100;
    private Button friends;
    private Button me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_layout);
        top100 = (Button)findViewById(R.id.top100Btn);
        friends = (Button)findViewById(R.id.friendsBtn);
        me = (Button)findViewById(R.id.meBtn);

        try{
            getTopHighScore();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("***Array** " + array);
        puntajes = new ArrayList<HighScore>();
    }

    public ArrayList<HighScore> cargaDatos(String nombreServicio) {
        AssetManager assetManager = this.getAssets();
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if(nombreServicio.equals("tophighscore")){
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObjectDos = array.getJSONObject(i);
                    JSONObject inner = jsonObjectDos.getJSONObject("users");
                    System.out.println("**jsonObject "+inner);
                    HighScore hs = new HighScore(inner.getString("username"), Integer.parseInt(inner.getString("score")));
                    puntajes.add(hs);
                }
            }else {
                //HARDCODEADO
                InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(nombreServicio+".json"));
                bufferedReader = new BufferedReader(inputStreamReader);
                while (bufferedReader.ready()) {
                    stringBuffer.append(bufferedReader.readLine());
                }
                JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("highscores");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectDos = jsonArray.getJSONObject(i);
                    System.out.println("jsonObject "+jsonArray.getJSONObject(i));
                    HighScore hs = new HighScore(jsonObjectDos.getString("nombre"), jsonObjectDos.getInt("score"));
                    puntajes.add(hs);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return puntajes;
    }


    //FALTA OBTENER LISTA DE AMIGOS A PARTIR DEL USUARIO
    public void getFriendsHighScore() throws JSONException {
        MaratonClient.get("friends/friendshighscore.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("highscores");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectDos = jsonArray.getJSONObject(i);
                        HighScore hs = new HighScore(jsonObjectDos.getString("nombre"), jsonObjectDos.getInt("score"));
                        puntajes.add(hs);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            }
        });
    }
    //FALTA OBTENER PUNTAJE DEL USUARIO
    public void getHighScore() throws JSONException {
        MaratonClient.get("users/mehighscore.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("highscores");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectDos = jsonArray.getJSONObject(i);
                        HighScore hs = new HighScore(jsonObjectDos.getString("nombre"), jsonObjectDos.getInt("score"));
                        puntajes.add(hs);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            }
        });
    }


    public void meBtnOnclick(View v) throws JSONException {
        top100.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        friends.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        me.setBackgroundColor(getResources().getColor(R.color.colorMarino));
        puntajes.clear();
        highscoreAdapter = new highscoreAdapter(this, cargaDatos("mehighscore"));
        //getHighScore();
        //highscoreAdapter = new highscoreAdapter(this, puntajes);
        highscoreAdapter.notifyDataSetChanged();
        setListAdapter(highscoreAdapter);
        System.out.println("Click MEEE!!");
    }

    public void friendsBtnOnclick(View v) throws JSONException{
        top100.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        friends.setBackgroundColor(getResources().getColor(R.color.colorMarino));
        me.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        puntajes.clear();
        //getFriendsHighScore();
        //highscoreAdapter = new highscoreAdapter(this, puntajes);
        highscoreAdapter = new highscoreAdapter(this, cargaDatos("friendshighscore"));
        highscoreAdapter.notifyDataSetChanged();
        setListAdapter(highscoreAdapter);
        System.out.println("Click Friends!!");
    }


    //TOP 100 LISTO!!
    //Conexion a servicio TOP100
    public void getTopHighScore() throws JSONException {
        MaratonClient.get("users/highscore", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("**JSONARRAY HS** " + timeline);
                puntajes.clear();
                array = timeline;
                highscoreAdapter = new highscoreAdapter(HighScoreActivity.this, cargaDatos("tophighscore"));
                setListAdapter(highscoreAdapter);

            }

        });
    }

    public void top100BtnOnclick(View v) throws JSONException{
        top100.setBackgroundColor(getResources().getColor(R.color.colorMarino));
        friends.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        me.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        try{
            getTopHighScore();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}