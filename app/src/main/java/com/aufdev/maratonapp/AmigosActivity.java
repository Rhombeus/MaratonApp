package com.aufdev.maratonapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class AmigosActivity extends ListActivity {
    private ListView lista;
    private AmigosAdapter amigosAdapter;
    private Usuario usuario;
    private static ArrayList<Usuario> usuarios ;
    private JSONArray array;

    private ProgressDialog progressDialog;
    private String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigos_layout);
        id_user = getIntent().getStringExtra("userid");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data...");

        try{
            getFriends();
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
                Usuario us = new Usuario(inner.getString("username"), inner.getString("email"), Integer.parseInt(inner.getString("score")), inner.getString("id"));
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
                .setMessage("¿Deseas iniciar una partida?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // AGREGAR USUARIO A LA BD
                        progressDialog.show();
                        addGame(usuario.getId());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    //Conexion a servicio
    public void getFriends() throws JSONException {
        MaratonClient.get("friends/json/" + id_user, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("**JSONOBJ HS** " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("**JSONARRAY HS** " + timeline);
                array = timeline;
                amigosAdapter = new AmigosAdapter(AmigosActivity.this, cargaDatos());
                setListAdapter(amigosAdapter);
            }

        });
    }

    //Agregar juego
    public void addGame(String id_friend){
        RequestParams params=new RequestParams();
        //params.add("data[Game][id]","");
        params.add("data[Game][player1]", id_user);
        params.add("data[Game][player2]", id_friend);//usuario actual
        params.add("data[Game][p1Score]", "0");
        params.add("data[Game][p2Score]", "0");
        params.add("data[Game][ingoranciaScore]", "0");
        params.add("data[Game][p1turn]", "0");
        params.add("data[Game][p1turn]", "1");

        System.out.println("**Usuario:" + id_user);
        System.out.println("**Amigo:" + id_friend);
        MaratonClient.post("games/add", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("ST: "+statusCode);
                if(statusCode == 302){
                    onSuccess(202, headers, responseString);
                    return;
                }
                System.out.println("Error");
                Toast.makeText(AmigosActivity.this, "No se ha podido agregar a la lista de juegos", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                Toast.makeText(AmigosActivity.this, "Se ha agregado a tu lista juegos", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                AmigosActivity.this.finish();
            }
        });
    }
}
