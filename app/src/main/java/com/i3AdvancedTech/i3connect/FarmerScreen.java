package com.i3AdvancedTech.i3connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FarmerScreen extends AppCompatActivity {

    private TextView Welcome;
    private Button SignOut;
    private ImageView AddCrop;
    private ImageView CropBiddingSummary , WeatherInfo , AccountHistory , HelpCentre , Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_screen);
        getSupportActionBar().hide();

        Welcome= (TextView)findViewById(R.id.iHelloFarmer);
        SignOut= (Button)findViewById(R.id.iSignOut);
        AddCrop= (ImageView)findViewById(R.id.iAddCropLogo);
        CropBiddingSummary= (ImageView)findViewById(R.id.iBiddingLogo);
        WeatherInfo= (ImageView)findViewById(R.id.iWeatherLogo);
        AccountHistory= (ImageView)findViewById(R.id.iHistoryLogo);
        HelpCentre= (ImageView)findViewById(R.id.iHelpCentreLogo);
        Profile= (ImageView)findViewById(R.id.iProfileLogo);


        final FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();

        AddCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FarmerScreen.this, AddCrop.class));


            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                finish();
                startActivity(new Intent(FarmerScreen.this,MainActivity.class));


            }
        });

        CropBiddingSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(FarmerScreen.this, "Your Crops ",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),SellCrop.class));


            }
        });

        WeatherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FarmerScreen.this, AgricultureInfo.class));


            }
        });

        AccountHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FarmerScreen.this, FarmerHistory.class));

                /*Toast.makeText(FarmerScreen.this, "Account History to Arrive Soon",
                        Toast.LENGTH_SHORT).show(); */

            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerScreen.this, ShowProfile.class));


            }
        });

        HelpCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "*Calling Message*");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey There ! Bid for my genuine crops at the Digital Farmer App. You never know , your price can match my expectations ! ");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);


            }
        });
    }
}
