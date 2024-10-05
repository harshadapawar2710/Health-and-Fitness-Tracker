package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConformRegisterMobileNoActivity extends AppCompatActivity {

    EditText etConformRegisterMobileNo;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conform_register_mobile_no);

        etConformRegisterMobileNo=findViewById(R.id.etConformRegisterMobileNo);
        btnVerify=findViewById(R.id.acbtnConformRegisterMobileNoVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etConformRegisterMobileNo.getText().toString().isEmpty())
                {
                    etConformRegisterMobileNo.setError("Please Enter Mobile No");
                }
                else if(etConformRegisterMobileNo.getText().toString().length()!=10)
                {
                    etConformRegisterMobileNo.setError("Please Enter Valid Mobile Number");
                }else
                {

                }
            }
        });

    }
}