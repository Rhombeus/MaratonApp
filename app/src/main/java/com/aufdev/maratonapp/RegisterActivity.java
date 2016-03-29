package com.aufdev.maratonapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private EditText emailEditText, emailConfirmEditText, usrNameEditText, passEditText, confirmPassEditText;
    private CheckBox termsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        emailConfirmEditText = (EditText) findViewById(R.id.emailConfirmEditText);
        usrNameEditText = (EditText) findViewById(R.id.usrNameEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        confirmPassEditText = (EditText) findViewById(R.id.confirmPassEditText);
        termsCheckBox = (CheckBox) findViewById(R.id.termsCheckBox);
    }

    public void cancelRegOnclick(View v) {
        finish();
    }

    public void registrarseOnclick(View v) {
        if (validateFields()) {
            SharedPreferences.Editor usrEditor = getApplicationContext().getSharedPreferences(usrNameEditText.getText().toString(), 0).edit();
            usrEditor.putString(AppConstants.USR_NAME_KEY, usrNameEditText.getText().toString());
            usrEditor.putString(AppConstants.USR_MAIL_KEY, emailEditText.getText().toString());
            usrEditor.putString(AppConstants.USR_PASS_KEY, PasswordEncryptor.getInstance().encrypt(passEditText.getText().toString()));
            usrEditor.putInt(AppConstants.USR_SCORE_KEY, 0);
            usrEditor.commit();
            SharedPreferences.Editor usrListEditor = getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).edit();
            Set<String> usrs = getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getStringSet(AppConstants.GLOBAL_USRS, new HashSet<String>());
            usrs.add(usrNameEditText.getText().toString());
            usrListEditor.putStringSet(AppConstants.GLOBAL_USRS, usrs);
            usrListEditor.putInt(usrNameEditText.getText().toString(), 0);
            usrListEditor.commit();
            Toast.makeText(this, "Se ha registrado exitosamente", Toast.LENGTH_LONG).show();
            this.finish();

        }

    }

    public boolean validateFields() {
        boolean ret = false;
        if (EMAIL_REGEX.matcher(emailEditText.getText().toString()).matches()) {
            if (emailConfirmEditText.getText().toString().equals(emailEditText.getText().toString())) {
                if (passEditText.getText().length() >= 5) {
                    if (confirmPassEditText.getText().toString().equals(passEditText.getText().toString())) {
                        Set<String> usrs = getApplicationContext().getSharedPreferences(AppConstants.GLOBAL, 0).getStringSet(AppConstants.GLOBAL_USRS, new HashSet<String>());
                        System.out.println(usrs);
                        if (!usrs.contains(usrNameEditText.getText().toString())) {
                            if (termsCheckBox.isChecked()) {
                                ret = true;
                            } else {
                                Toast.makeText(this, "Para continuar es necesario aeptar los terminos y condiciones", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "El nombre de usuario ya esta tomado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "La contraseña debe tener al menos 5 caracteres", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Las direcciones de correo no coinciden", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese una dirección de correo válida", Toast.LENGTH_LONG).show();
        }
        return ret;
    }
}
