package com.example.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etMo_no, etEmail, etUsername, etPassword;
    Button btnRegister;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        setTitle("Registration Activity");

        preferences=PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        editor=preferences.edit();

        etName = findViewById(R.id.etRegisterName);
        etMo_no = findViewById(R.id.etRegisterMobileno);
        etEmail = findViewById(R.id.etRegisterEmail);
        etUsername = findViewById(R.id.etRegisterusername);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegisterRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("please enter your Name");
                } else if (etMo_no.getText().toString().isEmpty()) {
                    etMo_no.setError("please enter your Mobile No");
                } else if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("please enter your Email");
                } else if (etMo_no.getText().toString().length() != 10) {
                    etMo_no.setError("please enter your Username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("please enter your Password");
                } else if (!etName.getText().toString().matches(".*[A-Z].*")) {
                    etName.setError("please enter one uppercase letter");
                } else if (!etMo_no.getText().toString().matches(".*[0-9].*")) {
                    etMo_no.setError("please enter 10 digit no");

                }
                else
                {
                    progressDialog=new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("please wait...");
                    progressDialog.setMessage("Registration is in process");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();





//                    userRegisterDetails();
                    
                    }

                }
            });

        }

    private void userRegisterDetails() {

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("name",etName.getText().toString());
        params.put("mobile_no",etMo_no.getText().toString());
        params.put("Email",etEmail.getText().toString());
        params.put("Username",etUsername.getText().toString());
        params.put("Password",etPassword.getText().toString());

        client.post("http://192.168.40.209:80/fittnessAPI/userregister.php",params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status=response.getString(Integer.parseInt("Success"));
                    if(status.equals("1"))

                    {
                        Toast.makeText(RegistrationActivity.this, "Registration Successfully done", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Already data present", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(RegistrationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        }

        );
    }
}

