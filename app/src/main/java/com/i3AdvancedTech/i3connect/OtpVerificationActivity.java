package com.i3AdvancedTech.i3connect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OtpVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();;
        setContentView(R.layout.activity_otp_verification);
    }
}
