package com.aufdev.maratonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leslie on 03/05/2016.
 */
public class JuegosAdapter extends ArrayAdapter<Juego> {
private final Context context;

public JuegosAdapter(Context context, List<Juego> list){

        super(context, 0 , list);
        this.context = context;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        Juego juego = getItem(position);
        if(convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.juegoslayout, parent, false);
        }

        TextView vs = (TextView)convertView.findViewById(R.id.oponenteTV);
        vs.setText(juego.getPlayer1());

        TextView score_amigo = (TextView)convertView.findViewById(R.id.scoreamigo);
        score_amigo.setText(juego.getScorep2());

        TextView score_usuario = (TextView)convertView.findViewById(R.id.scoreuser);
        score_usuario.setText(juego.getScorep1());

        TextView turno = (TextView)convertView.findViewById(R.id.turno);
        turno.setText(juego.getTurno());

        return convertView;
        }
        }
