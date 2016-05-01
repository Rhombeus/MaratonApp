package com.aufdev.maratonapp;

import android.app.ProgressDialog;
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

        questionTextView.setText("");
        opnARadioButton.setText("Cargando...");
        opnBRadioButton.setText("Cargando...");
        opnCRadioButton.setText("Cargando...");

        try
        {
            obtainPregunta(getIntent().getIntExtra("Categoria", 1));
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
        int categoria = Math.abs(r.nextInt() % 6);//descartar 0
        if(categoria == 0){
            categoria+=1;
        }


        MaratonRestClient client = new MaratonRestClient();
        System.out.println("URL:" + "http://aufdev.com/maraton/questions/json/"+categoria);

        client.get(categoria + "", null, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arr) {
                Tarjeta preg = TarjetaJsonAdapter.jsonArrayToTarjeta(arr);
                if(preg != null) {
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
        if (pregunta.accion(resp)) {
            Toast.makeText(this, "Correcto!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void skipBtnOnclick(View v) {
        finish();
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
