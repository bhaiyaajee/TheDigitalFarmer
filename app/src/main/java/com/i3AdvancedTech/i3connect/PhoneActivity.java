package com.i3AdvancedTech.i3connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneActivity extends AppCompatActivity {
    private EditText PhoneForOtp;
    private Button SendOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_phone);

        PhoneForOtp = (EditText) findViewById(R.id.iPhoneForOtp);
        SendOtpButton = (Button) findViewById(R.id.iSendOtpButton);

        SendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OtpVerificationActivity.class));
            }
        });
    }
}
