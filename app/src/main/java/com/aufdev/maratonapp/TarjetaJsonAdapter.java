package com.aufdev.maratonapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
