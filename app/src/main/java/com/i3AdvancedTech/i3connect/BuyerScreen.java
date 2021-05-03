package com.i3AdvancedTech.i3connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class BuyerScreen extends AppCompatActivity {

    private TextView Welcome;
    private Button SignOut;
    private ImageView CartLogo;
    private ImageView Savings , MarketInfo , AccountHistory , Share , Profile;

    //Firebase Auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("FFFFB300")));
        setContentView(R.layout.activity_buyer_screen);

        Welcome= (TextView)findViewById(R.id.iHelloFarmer);
        SignOut= (Button)findViewById(R.id.iSignOut);
        CartLogo= (ImageView)findViewById(R.id.iCartLogo);
        Savings= (ImageView)findViewById(R.id.iWalletLogo);
        MarketInfo= (ImageView)findViewById(R.id.iMarketLogo);
        AccountHistory= (ImageView)findViewById(R.id.iHistoryLogo);
        Share= (ImageView)findViewById(R.id.iShareLogo);
        Profile= (ImageView)findViewById(R.id.iProfileLogo);

        final FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();

        CartLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BuyerScreen.this, BuyCrop.class));


            }
        });

        Savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BuyerScreen.this, BuyerHistory.class));


            }
        });

        AccountHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyerScreen.this, BuyerHistory.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BuyerScreen.this, ShowProfile.class));


            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(BuyerScreen.this,MainActivity.class));
                finish();


            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "*Referral Message*");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey There !Join me on the Digital Farmer App and Buy genuine crops");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        MarketInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + "9521207962");
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                startActivity(i);

            }
        });

    }
}
