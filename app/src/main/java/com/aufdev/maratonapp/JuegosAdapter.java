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
        TextView score_amigo = (TextView)convertView.findViewById(R.id.scoreamigo);
        TextView score_usuario = (TextView)convertView.findViewById(R.id.scoreuser);
        TextView turno = (TextView)convertView.findViewById(R.id.turno);
        TextView ignorancia = (TextView)convertView.findViewById(R.id.scoreignorancia);

        if(juego.getCurrentUser().equals(juego.getId_p1())){
        vs.setText(juego.getPlayer2());
        score_amigo.setText(String.valueOf(juego.getScorep2()));
        score_usuario.setText(String.valueOf(juego.getScorep1()));
        }else{
                vs.setText(juego.getPlayer1());
                score_amigo.setText(String.valueOf(juego.getScorep1()));
                score_usuario.setText(String.valueOf(juego.getScorep2()));
        }
        ignorancia.setText(""+juego.getScore_ignorancia());
        if((juego.getTurno()&&juego.getCurrentUser().equals(juego.getId_p1())||(!juego.getTurno()&&juego.getCurrentUser().equals(juego.getId_p2())))){
                turno.setText("Tu turno");
        }else{
                turno.setText("Esperando a tu amigo");
        }
        //turno.setText(String.valueOf(juego.getTurno()));

        return convertView;
}
}
