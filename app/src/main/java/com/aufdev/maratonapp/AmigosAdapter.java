package com.aufdev.maratonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Leslie on 01/05/2016.
 */
public class AmigosAdapter extends ArrayAdapter<Usuario> {
private final Context context;

public AmigosAdapter(Context context, List<Usuario> list){

        super(context, 0 , list);
        this.context = context;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        Usuario usuario = getItem(position); //creo el objeto
        if(convertView == null) { //convertView renglon que voy a crear
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.buscaramigoslayout, parent, false); //crear renglon
        }

        TextView nombre = (TextView)convertView.findViewById(R.id.nombre);
        nombre.setText(usuario.getNombre());

        TextView correo = (TextView)convertView.findViewById(R.id.correo);
        correo.setText(usuario.getCorreo());

        TextView score = (TextView)convertView.findViewById(R.id.score);
        score.setText(Integer.toString(usuario.getScore()));

        TextView id = (TextView)convertView.findViewById(R.id.id_usuario);
        id.setText(usuario.getId());

        return convertView;
        }
}
