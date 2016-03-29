package com.aufdev.maratonapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class HighScoreActivity extends AppCompatActivity {
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_layout);
        lista = (ListView) findViewById(R.id.hScoreListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, generateValues());
        lista.setAdapter(adapter);
    }

    private String[] generateValues() {
        Set<String> usrs = getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getStringSet(AppConstants.GLOBAL_USRS, new HashSet<String>());
        String[] ret = new String[usrs.size()];
        int i = 0;
        for (String s : usrs) {
            ret[i] = s + ":  " + getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getInt(s, 0);
            i++;
        }
        return ret;
    }

    public void meBtnOnclick(View v) {
        Toast.makeText(this, "Metodo no implementado", Toast.LENGTH_LONG).show();
    }

    public void friendsBtnOnclick(View v) {
        Toast.makeText(this, "Metodo no implementado", Toast.LENGTH_LONG).show();
    }

    public void top100BtnOnclick(View v) {
        Toast.makeText(this, "Metodo no implementado", Toast.LENGTH_LONG).show();
    }
}
