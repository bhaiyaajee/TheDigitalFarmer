package com.i3AdvancedTech.i3connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HeartActivity extends AppCompatActivity {
    private Button FarmerSelection;
    private Button BuyerSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        FarmerSelection= (Button)findViewById(R.id.iFarmer);
        BuyerSelection= (Button)findViewById(R.id.iBuyer);

        final FirebaseAuth mAuth ;
        mAuth= FirebaseAuth.getInstance();

        FarmerSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HeartActivity.this, FarmerScreen.class));


            }
        });


        BuyerSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HeartActivity.this, BuyerScreen.class));


            }
        });
    }
}
