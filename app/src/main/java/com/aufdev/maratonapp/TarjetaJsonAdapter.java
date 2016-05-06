package com.aufdev.maratonapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import maraton.Tarjeta;
import maraton.TarjetaFactory;

/**
 * Created by Alejandro on 29/03/2016.
 */
public class TarjetaJsonAdapter {
    public static Tarjeta single(JSONObject js) {
        Tarjeta ret = null;
        try {
            JSONArray arr = js.getJSONArray("op");
            TarjetaFactory tarjetaFactory = new TarjetaFactory();
            String[] resp = new String[arr.length()];
            JSONObject temp;
            for (int i = 0; i < arr.length(); i++) {
                temp = (JSONObject) arr.get(i);
                resp[i] = temp.getString("resp");
            }
            ret = (Tarjeta) tarjetaFactory.crearItem(js.getString("imagen"), js.getString("cat"), js.getString("preg"), resp, js.getInt("corr"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Tarjeta jsonArrayToTarjeta(JSONArray arr)
    {
        Tarjeta ret = null;
        try {
            Random r = new Random();

            JSONObject pregunta = arr.getJSONObject(Math.abs(r.nextInt() % arr.length()));
            String id_pregunta=pregunta.getJSONObject("Question").getString("id");
            String texto = pregunta.getJSONObject("Question").getString("question_text");
            String categoria = pregunta.getJSONObject("Category").getString("description");
            int respuestaCorrecta = pregunta.getJSONObject("Question").getInt("correct");

            JSONArray opcionesArr = pregunta.getJSONArray("respuestas");
            String[] opciones = new String[opcionesArr.length()];

            JSONObject temp;

            for (int i = 0; i < opcionesArr.length(); i++)
            {
                temp = (JSONObject) opcionesArr.get(i);
                opciones[i] = temp.getString("answer_text");
            }

            TarjetaFactory tarjetaFactory = new TarjetaFactory();
            ret = (Tarjeta) tarjetaFactory.crearItem(id_pregunta, categoria, texto, opciones, respuestaCorrecta);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
