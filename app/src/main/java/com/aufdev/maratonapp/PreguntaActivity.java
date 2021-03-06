package com.aufdev.maratonapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import junit.runner.Version;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

import cz.msebera.android.httpclient.Header;
import maraton.Tarjeta;


public class PreguntaActivity extends AppCompatActivity {
    private Tarjeta pregunta;
    private TextView questionTextView;
    private RadioButton opnARadioButton, opnBRadioButton, opnCRadioButton;
    private RadioGroup opnRadioGroup;
    private ProgressDialog progressDialog;
    private String id_user;
    private int  categoria;
    private String id_pregunta;
    private String indice_respuesta;
    private String id_juego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta_layout);
        opnRadioGroup = (RadioGroup) findViewById(R.id.opnRadioGroup);
        opnARadioButton = (RadioButton) findViewById(R.id.opnARadioButton);
        opnBRadioButton = (RadioButton) findViewById(R.id.opnBRadioButton);
        opnCRadioButton = (RadioButton) findViewById(R.id.opnCRadioButton);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data...");
        progressDialog.show();
        id_user = getIntent().getStringExtra("id_user");
        System.out.println("tarjeta-------------------usr-----------"+ id_user);

        id_juego= getIntent().getStringExtra("id_juego");
        categoria = getIntent().getIntExtra("Categoria", 1);
        questionTextView.setText("");
        opnARadioButton.setText("Cargando...");
        opnBRadioButton.setText("Cargando...");
        opnCRadioButton.setText("Cargando...");

        try
        {
            obtainPregunta(categoria);
        }catch(JSONException ex)
        {
            ex.printStackTrace();
        }


    }

    /*
    private void obtainPregunta(int cat) {
        JSONArray arr = CatFileReader.read(this, "PreguntasCat" + cat + ".json");
        Random r = new Random();
        Tarjeta preg = TarjetaJsonAdapter.single(arr.optJSONObject(r.nextInt() % arr.length()));
        pregunta = preg;
    }
    */

    private void obtainPregunta(final int cat) throws JSONException
    {

        Random r = new Random();
        //int categoria = Math.abs(r.nextInt() % 6);//descartar 0
        if(categoria == 0){
            categoria+=1;
        }


        MaratonRestClient client = new MaratonRestClient();
        System.out.println("URL:" + "http://aufdev.com/maraton/questions/json/"+categoria);

        client.get(categoria + "", null, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arr) {
                System.out.println(arr);
                Tarjeta preg = TarjetaJsonAdapter.jsonArrayToTarjeta(arr);
                System.out.println("******************************************************preg"+preg);
                if(preg != null) {
                    id_pregunta=preg.getImagen();
                    progressDialog.dismiss();
                    PreguntaActivity.this.pregunta = preg;
                    questionTextView.setText(PreguntaActivity.this.pregunta.getPregunta());
                    opnARadioButton.setText(PreguntaActivity.this.pregunta.getOpciones()[0]);
                    opnBRadioButton.setText(PreguntaActivity.this.pregunta.getOpciones()[1]);
                    opnCRadioButton.setText(PreguntaActivity.this.pregunta.getOpciones()[2]);
                }else{
                    try {
                        obtainPregunta(cat);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void chkAnswerBtnOnclick(View v) {
        int resp = 0;

        switch (opnRadioGroup.getCheckedRadioButtonId()) {
            case R.id.opnARadioButton:
                resp = 0;
                break;
            case R.id.opnBRadioButton:
                resp = 1;
                break;
            case R.id.opnCRadioButton:
                resp = 2;
                break;
        }
        indice_respuesta=""+(resp+1);

        if (pregunta.accion(resp+1)) {
            Toast.makeText(this, "Correcto!", Toast.LENGTH_LONG).show();
            this.custom_end_activity();
        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_LONG).show();
            this.custom_end_activity();
        }
    }

    //Método que debe utilizarse en lugar de "finish()", ya que actualiza el puntaje en el servidor
    public void custom_end_activity()
    {
        final int puntos = 0;
        MaratonClient.get("questions/validateAnswer/" + id_user + "/" + id_pregunta + "/" + indice_respuesta + "/" + id_juego, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("-------------------------------------Error-----------------------------------------");
                System.out.println(statusCode);
                System.out.println("questions/validateAnswer/" + id_user + "/" + id_pregunta + "/" + indice_respuesta + "/" + id_juego);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("questions/validateAnswer/" + id_user + "/" + id_pregunta + "/" + indice_respuesta + "/" + id_juego);
                System.out.println("------------------------------"+responseString);
                Intent it=new Intent();
                if(responseString.trim().equals("true")){
                    it.putExtra("puntos",1);
                    setResult(RESULT_OK,it);
                }else{
                    it.putExtra("puntos",0);
                    setResult(RESULT_OK,it);
                }

               finish();
            }
        });

        /*
        //Método que debe utilizarse en lugar de "finish()", ya que actualiza el puntaje en el servidor
    public void custom_end_activity()
    {
        int puntos = 0;
        //id_usuario, id_pregunta, #respuesta, id_juego -> query
        MaratonClient client = new MaratonClient();

        //dentro del onSuccess()
            //actualizar puntos
            //;
            //finish(); -> dentro del onSuccess()
    }

         */
    }

    public void skipBtnOnclick(View v) {
        custom_end_activity();
        System.out.println("cllick!!");
    }


     class MaratonRestClient {
        private static final String BASE_URL = "http://aufdev.com/maraton/questions/json/";

        private AsyncHttpClient client = new AsyncHttpClient();

        public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }

        public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.post(getAbsoluteUrl(url), params, responseHandler);
        }

        private String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }
    }
}
