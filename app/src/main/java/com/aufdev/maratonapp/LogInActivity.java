package com.aufdev.maratonapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {


    String m_androidId;
    private EditText uName, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        uName = (EditText) findViewById(R.id.uNameEditText);
        pwd = (EditText) findViewById(R.id.pwdEditText);
        System.out.println(m_androidId);
    }

    public void loginOnclick(View view) {
        if (chckCredentials()) {
            AppConstants.currentUser = uName.getText().toString();
            Intent it = new Intent(this, MainMenuActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(this, "Usuario o Contraseña Invalidos", Toast.LENGTH_LONG).show();
        }
    }

    public void registerOnclick(View v) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }


    public void fPwdOnClick(View v) {
        Toast.makeText(this, "Accion no implementada", Toast.LENGTH_LONG).show();
    }

    public boolean chckCredentials() {
        String hash = getApplicationContext().getSharedPreferences(uName.getText().toString(), 0).getString(AppConstants.USR_PASS_KEY, null);
        PasswordEncryptor e = PasswordEncryptor.getInstance();
        return PasswordEncryptor.getInstance().check(pwd.getText().toString(), hash);
         /* MaratonClient.get("cake/view_workshops", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                try {
                    JSONObject firstEvent = (JSONObject) timeline.get(0);
                    JSONObject nom = firstEvent.getJSONObject("Workshop");
                    String n=nom.getString("nombre");
                    System.out.println(firstEvent);
                    System.out.println(n);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }


}
