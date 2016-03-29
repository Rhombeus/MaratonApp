package com.aufdev.maratonapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

public class AmigosActivity extends AppCompatActivity {
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigos_layout);
        lista = (ListView) findViewById(R.id.amigosListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, generateValues(getIntent().getIntExtra("Tipo", 1)));
        lista.setAdapter(adapter);
    }

    private String[] generateValues(int tipo) {
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
    }
}
