package com.aufdev.maratonapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.Random;

import maraton.Tarjeta;

public class PreguntaActivity extends AppCompatActivity {
    private Tarjeta pregunta;
    private TextView questionTextView;
    private RadioButton opnARadioButton, opnBRadioButton, opnCRadioButton;
    private RadioGroup opnRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta_layout);
        opnRadioGroup = (RadioGroup) findViewById(R.id.opnRadioGroup);
        opnARadioButton = (RadioButton) findViewById(R.id.opnARadioButton);
        opnBRadioButton = (RadioButton) findViewById(R.id.opnBRadioButton);
        opnCRadioButton = (RadioButton) findViewById(R.id.opnCRadioButton);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        obtainPregunta(getIntent().getIntExtra("Categoria", 1));
        questionTextView.setText(pregunta.getPregunta());
        opnARadioButton.setText(pregunta.getOpciones()[0]);
        opnBRadioButton.setText(pregunta.getOpciones()[1]);
        opnCRadioButton.setText(pregunta.getOpciones()[2]);


    }

    private void obtainPregunta(int cat) {
        JSONArray arr = CatFileReader.read(this, "PreguntasCat" + cat + ".json");
        Random r = new Random();
        Tarjeta preg = TarjetaJsonAdapter.single(arr.optJSONObject(r.nextInt() % arr.length()));
        pregunta = preg;
    }

    public void chkAnswerBtnOnclick(View v) {
        int resp = 0;
        switch (opnRadioGroup.getCheckedRadioButtonId()) {
            case R.id.opnARadioButton:
                resp = 1;
                break;
            case R.id.opnBRadioButton:
                resp = 0;
                break;
            case R.id.opnCRadioButton:
                resp = 2;
                break;
        }
        if (pregunta.accion(resp)) {
            Toast.makeText(this, "Correcto!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Nope", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void skipBtnOnclick(View v) {
        finish();
    }
}
