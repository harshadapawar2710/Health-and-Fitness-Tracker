package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    EditText etusername, etPassword;

    CheckBox cbShowHidePassword;

    Button btnlogin;
    TextView tvNewUser;
    
    ProgressDialog progressDialog;

    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setTitle("Login activity");
        etusername = findViewById(R.id.etLoginusername);
        etPassword = findViewById(R.id.etLoginpassword);
        cbShowHidePassword = findViewById(R.id.cbLoginShowHidePassword);
        btnlogin = findViewById(R.id.btnLoginLogin);
        tvNewUser = findViewById(R.id.tvloginnewuser);

        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("please enter your username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("please enter your password");
                } else if (etusername.getText().toString().length() < 8) {
                    etusername.setError("please enter Atlist 8 character");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("please enter 8 character password");
                } else if (!etusername.getText().toString().matches(".*[A-Z].*")) {
                    etusername.setError("please use one uppercase letter");

                } else if (!etusername.getText().toString().matches(".*[a-z].*")) {
                    etusername.setError("please use one lowercase letter");

                } else if (!etusername.getText().toString().matches(".*[0-9].*")) {
                    etusername.setError("please use one no.");

                } else if (!etusername.getText().toString().matches(".*[@,$,#,*].*")) {
                    etusername.setError("please use one special symbol");

                } else {
                    progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("please wait...");
                    progressDialog.setMessage("Login is in process");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();
                    userLogin();
                    
                    Toast.makeText(LoginActivity.this, "login successfully done", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
          }
      });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

    private void userLogin() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("password",etPassword.getText().toString());

        client.post("http://192.168.198.209:80/fittnessAPI/userlogin.php",params,new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(LoginActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
