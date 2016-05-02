package com.aufdev.maratonapp;

/**
 * Created by Leslie on 01/05/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class highscoreAdapter extends ArrayAdapter<HighScore> {
    private final Context context;

    public highscoreAdapter(Context context, List<HighScore> list){

        super(context, 0 , list);
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HighScore highScore = getItem(position); //creo el objeto
        if(convertView == null) { //convertView renglon que voy a crear
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.scorelayout, parent, false); //crear renglon
        }

        TextView nombre = (TextView)convertView.findViewById(R.id.nombre_jugador);
        nombre.setText(highScore.getPlayer());

        TextView score = (TextView)convertView.findViewById(R.id.score_text);
        score.setText(Integer.toString(highScore.getScore()));

        return convertView;
    }

}
