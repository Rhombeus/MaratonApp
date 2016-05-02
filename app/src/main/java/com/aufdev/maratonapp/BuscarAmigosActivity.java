package com.aufdev.maratonapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BuscarAmigosActivity extends ListActivity {
    private ListView lista;
    private AmigosAdapter amigosAdapter;
    private Usuario usuario;
    private static ArrayList<Usuario> usuarios ;
    private JSONArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_amigos);

        try{
            getUsers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        usuarios = new ArrayList<Usuario>();
    }

    public ArrayList<Usuario> cargaDatos() {
        try {
            System.out.println("***Array** " + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObjectDos = array.getJSONObject(i);
                JSONObject inner = jsonObjectDos.getJSONObject("users");
                System.out.println("**jsonObject " + inner);
                Usuario us = new Usuario(inner.getString("username"), inner.getString("email"), Integer.parseInt(inner.getString("score")));
                usuarios.add(us);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return usuarios;
    }

    //FALTA AGREGAR USUARIO A LA BASE DE DATOS
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        usuario = amigosAdapter.getItem(position);
        new AlertDialog.Builder(this)
                .setTitle("Agregar Amigo")
                .setMessage("¿Deseas agregar el usuario a tus amigos?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // AGREGAR USUARIO A LA BD
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    //Conexion a servicio
    public void getUsers() throws JSONException {
        MaratonClient.get("users/json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("**JSONOBJ HS** "+response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("**JSONARRAY HS** "+timeline);
                array=timeline;
                amigosAdapter = new AmigosAdapter(BuscarAmigosActivity.this, cargaDatos());
                setListAdapter(amigosAdapter);
            }

        });
    }
}