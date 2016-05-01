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
        //lista = (ListView) findViewById(R.id.hScoreListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, generateValues());
        //lista.setAdapter(adapter);
        try{
            getTopHighScore();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("***Array** " + array);
        puntajes = new ArrayList<HighScore>();
        if(array != null) {
            highscoreAdapter = new highscoreAdapter(this, cargaDatos("tophighscore"));
            setListAdapter(highscoreAdapter);
        }
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
                //Llamar a servicio obtenerTopHighScore (recibir json)
                InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(nombreServicio+".json"));
                bufferedReader = new BufferedReader(inputStreamReader);
                while (bufferedReader.ready()) {
                    stringBuffer.append(bufferedReader.readLine());
                }
                JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("highscores");


                //EL BUENO
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectDos = jsonArray.getJSONObject(i);
                    System.out.println("jsonObject "+jsonArray.getJSONObject(i));
                    HighScore hs = new HighScore(jsonObjectDos.getString("nombre"), jsonObjectDos.getInt("score"));
                    puntajes.add(hs);
                }
                /*for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObjectDos = array.getJSONObject(i);
                    System.out.println("jsonObject");
                    HighScore hs = new HighScore(jsonObjectDos.getString("username"), Integer.parseInt(jsonObjectDos.getString("score")));
                    puntajes.add(hs);
                }*/
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return puntajes;
    }


    //Conexion a servicio
    public void getTopHighScore() throws JSONException {
        MaratonClient.get("users/highscore", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("**JSONOBJ HS** "+response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("**JSONARRAY HS** "+timeline);
                array=timeline;

            }

        });
    }
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
    //  Conexión con el servidor
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

    //Acción Botones

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

    public void top100BtnOnclick(View v) throws JSONException{
        top100.setBackgroundColor(getResources().getColor(R.color.colorMarino));
        friends.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        me.setBackgroundColor(getResources().getColor(R.color.colorCielo));
        //getTopHighScore();
        //highscoreAdapter = new highscoreAdapter(this, puntajes);
        try{
            getTopHighScore();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(array != null) {
            if(puntajes != null) {
                puntajes.clear();
            }
            System.out.println("***ArrayTop** "+array);
            highscoreAdapter = new highscoreAdapter(this, cargaDatos("tophighscore"));
            highscoreAdapter.notifyDataSetChanged();
            setListAdapter(highscoreAdapter);
            System.out.println("Click HS!!");
        }
    }
}