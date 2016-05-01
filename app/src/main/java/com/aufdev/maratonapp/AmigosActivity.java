package com.aufdev.maratonapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class AmigosActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayAdapter<String> adapter;
    ArrayList<String>friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigos_layout);
        lista = (ListView) findViewById(R.id.amigosListView);
        try {
            getFriends();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, generateValues(getIntent().getIntExtra("Tipo", 1)));*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friends);
        lista.setAdapter(adapter);
    }

    /*private String[] generateValues(int tipo) {
        String[] ret;
        if (tipo == 1) {
            Set<String> usrs = getApplicationContext().getSharedPreferences(AppConstants.currentUser, 0).getStringSet(AppConstants.USR_FRIENDS_KEY, new HashSet<String>());
            ret = new String[usrs.size()];
            int i = 0;
            for (String s : usrs) {
                ret[i] = s + ":  " + getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getInt(s, 0);
                i++;
            }
        } else {
            Set<String> usrs = getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getStringSet(AppConstants.GLOBAL_USRS, new HashSet<String>());
            ret = new String[usrs.size()];
            int i = 0;
            for (String s : usrs) {
                ret[i] = s + ":  " + getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getInt(s, 0);
                i++;
            }
        }
        return ret;
    }*/

    //SERVICIO AMIGOS
    public void getFriends() throws JSONException {
        MaratonClient.get("friends/friends.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray jsonArray = response.getJSONArray("friends");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectDos = jsonArray.getJSONObject(i);
                        friends.add(jsonObjectDos.getString("nombre"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

            }
        });
    }
}
