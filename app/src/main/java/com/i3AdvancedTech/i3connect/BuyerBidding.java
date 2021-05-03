package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class BuyerBidding extends AppCompatActivity
{
    private static final String TAG = "Bidding";

    private TextView HintAddCrop ;
    private TextView CropName, CropQuantity ,CropPrice;
    private EditText NewCropPrice;
    private Button PlaceYourBid;
    private ImageView BiddingSuccess;
    private ProgressDialog progress;

    FirebaseFirestore db;
    FirebaseFirestore db2;
    FirebaseAuth mAuth;
    String Farmer_Id, Buyer_Id;
    String DocId;

    String Farmer_Name;
    float Bid_Price;
    double lastPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_bidding);

        getSupportActionBar().setTitle("Place Bid");

        //Binding UI Elements
        HintAddCrop=(TextView)findViewById(R.id.iHelloAddCrop);
        CropName=(TextView) findViewById(R.id.iCName);
        CropQuantity=(TextView) findViewById(R.id.iCQty);
        NewCropPrice=(EditText) findViewById(R.id.iNewCropPrice);
        CropPrice=(TextView) findViewById(R.id.iCPrice);
        PlaceYourBid=(Button) findViewById(R.id.iPlaceBid);
        BiddingSuccess=findViewById(R.id.iBiddingLogo);

        BiddingSuccess.setVisibility(View.INVISIBLE);



        //Getting Instance of Databases
        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        DocId =getIntent().getExtras().get("Doc_Id").toString();

        final DocumentReference documentReference = db.collection("Farmers").document(DocId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                DocumentReference UserReference = db2.collection("Users").document(value.getString("Farmer_Id"));
                UserReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value2, @Nullable FirebaseFirestoreException error) {
                        if(value2.exists()){
                            Farmer_Name=value2.getString("Name");
                            CropQuantity.setText(value.getDouble("CropQty") + " kg | Farmer :  "+Farmer_Name);
                        }
                    }
                });

                //Setting Other Values in TEXTVIEW
                CropName.setText(value.getString("CropName"));
                CropPrice.setText("Rs. "+value.getDouble("CropPrice")+" /kg");

                lastPrice= value.getDouble("CropPrice");
            }
        });



        PlaceYourBid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!NewCropPrice.getText().toString().isEmpty())
                {
                //Setting the NEW BID PRICE from EditText UI
                Bid_Price=Float.valueOf(NewCropPrice.getText().toString());

                //Getting the Buyer-Id
                Buyer_Id=mAuth.getCurrentUser().getUid();

                //Checking for BID PRICE to be Higher
                if(Bid_Price > lastPrice)
                {
                    //Putting NEW Bid Price and Buyer ID DATA in the Document
                    Map<String, Object> crop = new HashMap<>();
                    crop.put("CropPrice", Bid_Price);
                    crop.put("Buyer_Id",Buyer_Id);



                    //Updating BID-PRICE and BUYER_ID in our Database
                    documentReference.set(crop, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + Farmer_Id);
                            Toast.makeText(BuyerBidding.this, " Your bid is Placed ",
                                    Toast.LENGTH_LONG).show();

                            NewCropPrice.setText("");
                            BiddingSuccess.setVisibility(View.VISIBLE);

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    //progress.dismiss();
                                    Toast.makeText(BuyerBidding.this, e.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                } // If for GREATER BID Ends here
                else{
                    NewCropPrice.setError("Can't be lesser than current price");
                    NewCropPrice.requestFocus();
                    Toast.makeText(BuyerBidding.this,"Enter a HIGHER Price for Bidding",Toast.LENGTH_LONG).show();
                }


              }  //If Block for EMPTY FIELD ends here
                else{
                    NewCropPrice.setError("Can't be Empty");
                    NewCropPrice.requestFocus();
                    Toast.makeText(BuyerBidding.this,"Please Enter a Bid Price",Toast.LENGTH_LONG).show();
                }








            }
        });

    }
}
