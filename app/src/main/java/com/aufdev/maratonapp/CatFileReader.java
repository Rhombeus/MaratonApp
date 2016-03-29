package com.aufdev.maratonapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Alejandro on 29/03/2016.
 */
public class CatFileReader {
    public static JSONArray read(Activity caller, String assetFileName) {
        BufferedReader reader = null;
        JSONArray ret = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(caller.getAssets().open(assetFileName)));

            // do reading, usually loop until end of file reading
            String mLine;
            String jsonArr = "";
            while ((mLine = reader.readLine()) != null) {
                //System.out.println(mLine);
                jsonArr += mLine;
            }
            ret = new JSONArray(jsonArr);
        } catch (IOException e) {
            //log the exception
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        return ret;
    }
}
