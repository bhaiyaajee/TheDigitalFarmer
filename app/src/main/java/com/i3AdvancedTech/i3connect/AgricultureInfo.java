package com.i3AdvancedTech.i3connect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AgricultureInfo extends AppCompatActivity {

    TextView Wheat, Rice, Pulse, Sugar, GovtMsp, GovtSchemes;

    FirebaseFirestore db;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture_info);

        Wheat=findViewById(R.id.iMspWheat);
        Rice=findViewById(R.id.iMspRice);
        Pulse=findViewById(R.id.iMspPulse);
        Sugar=findViewById(R.id.iMspSugarcane);
        GovtMsp=findViewById(R.id.iGovtMsp);
        GovtSchemes=findViewById(R.id.iGovtSchemes);

        db=FirebaseFirestore.getInstance();
        documentReference= db.collection("MSPs").document("trsoOvrO6PQJC0DkoVrA");

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists())
                {
                    Wheat.setText("MSP for Wheat = Rs. "+value.getString("Wheat")+" /kg");
                    Rice.setText("MSP for  Rice = Rs. "+value.getString("Rice")+" /kg");
                    Pulse.setText("MSP for Pulse = Rs. "+value.getString("Pulse")+" /kg");
                    Sugar.setText("MSP for SugarCane = Rs. "+value.getString("Sugar")+" /kg");
                }
            }
        });

        GovtSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://agricoop.nic.in/hi/programmes-schemes-listing");
                Intent webIntent1 = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent1);
            }
        });

        GovtMsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://pib.gov.in/PressReleasePage.aspx?PRID=1628348");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });


    }
}
