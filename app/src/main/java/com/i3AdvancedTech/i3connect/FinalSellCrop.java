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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FinalSellCrop extends AppCompatActivity {

    private static final String TAG = "Selling Finally";

    private TextView HintAddCrop ;
    private TextView CropName, CropQuantity ,CropPrice;
    private TextView SellingPrice, TransactionSuccessful;
    private Button ConfirmSell;
    private ImageView SuccessLogo;
    private LinearLayout LinLayout;

    FirebaseFirestore db, db2;
    FirebaseAuth mAuth;

     String HCropName,HBuyerId,HFarmerId;
     double HCropSellingPrice,HCropQty;

    String DocId, Buyer_Name; //for getting extra from Intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sell_crop);
        getSupportActionBar().setTitle("Confirm Sell");

        //Binding UI Elements
        CropName=(TextView) findViewById(R.id.iCName);
        CropQuantity=(TextView) findViewById(R.id.iCQty);
        SellingPrice=(TextView) findViewById(R.id.iSellingPrice);
        CropPrice=(TextView) findViewById(R.id.iCPrice);
        TransactionSuccessful=(TextView) findViewById(R.id.iTradeSuccessful);
        ConfirmSell=(Button) findViewById(R.id.iConfirmSellBtn);
        SuccessLogo=(ImageView) findViewById(R.id.iSuccessLogo);
        LinLayout=(LinearLayout) findViewById(R.id.iLinLayout);

        //Setting VISIBILITY of Image
        SuccessLogo.setVisibility(View.INVISIBLE);
        TransactionSuccessful.setVisibility(View.INVISIBLE);

        //Getting Instance of Databases
        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        DocId =getIntent().getExtras().get("Doc_Id").toString();  //Fetching Document_ID from previous Activity

        //Creating reference for particular Document in FARMER Collection
        final DocumentReference documentReference = db.collection("Farmers").document(DocId);


        //Getting SNAPSHOT of data in Document
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                //Checking if Document Exists
                if(value.exists()) {

                    if(value.contains("Buyer_Id")){
                        //Connecting gto USERS Collection
                        DocumentReference UserReference = db2.collection("Users").document(value.getString("Buyer_Id"));
                        UserReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value2, @Nullable FirebaseFirestoreException error) {
                                if(value2.exists()){
                                    Buyer_Name=value2.getString("Name");
                                    CropPrice.setText("Rs. " + value.getDouble("CropPrice") + " /kg | Buyer : "+Buyer_Name);
                                }
                            }
                        });

                    } //IF Block for BuyerID Ends Here
                    else if(!value.contains("Buyer_Id")){
                        //ConfirmSell.setVisibility(View.INVISIBLE);
                        //startActivity(new Intent(FinalSellCrop.this,SellCrop.class));
                        Toast.makeText(getApplicationContext(),"No Buyer Available",Toast.LENGTH_LONG).show();
                        finish();
                    }



                    //Setting Data in the TextFields UI
                    CropName.setText(value.getString("CropName"));
                    CropQuantity.setText(value.getDouble("CropQty") + " Kilograms");


                    //Calculating Final Price
                    double SP = value.getDouble("CropQty") * value.getDouble("CropPrice");
                    SellingPrice.setText("Selling Price: Rs.  " + SP);

                    //Setting up data for HISTORY Collection
                    HCropName = value.getString("CropName");
                    HCropSellingPrice = SP;
                    HCropQty = value.getDouble("CropQty");
                    HBuyerId = value.getString("Buyer_Id");
                    HFarmerId = value.getString("Farmer_Id");
                } //If Block Ends here for Doc-Snapshot Value


            }
        });


        //After Clicking the CONFIRM_SELL Button
        ConfirmSell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CropName.setText("");
                CropQuantity.setText("");
                CropPrice.setText("");
                SellingPrice.setText("");

                //Document Reference for HISTORY Collection

                DocumentReference historyDocRef= db.collection("History").document(DocId);

                //Putting Data
                Map<String, Object> crop = new HashMap<>();
                crop.put("CropName", HCropName);
                crop.put("SellingPrice", HCropSellingPrice);
                crop.put("CropQty", HCropQty);
                crop.put("Farmer_Id", HFarmerId);
                crop.put("Buyer_Id", HBuyerId);



                //Adding HISTORY in our Database
                historyDocRef.set(crop).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + DocId);

                        //Setting VISIBILITY of Image
                        SuccessLogo.setVisibility(View.VISIBLE);
                        TransactionSuccessful.setVisibility(View.VISIBLE);

                        //Setting VISIBILITY of Button
                        LinLayout.setVisibility(View.INVISIBLE);
                        //ConfirmSell.setVisibility(View.INVISIBLE);


                        Toast.makeText(getApplicationContext(),"History Made",Toast.LENGTH_LONG).show();

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(FinalSellCrop.this, e.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });


                //DELETING Crop From FARMERS collection
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document Deleted from FARMERS with ID: ");
                        //startActivity(new Intent(getApplicationContext(),SellCrop.class));
                        //finish();

                    }
                }).
                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error Deleting Document from FARMERS with ID: " );
                            }
                        });





            }
        });  // CONFIRM_SELL Button ends here

    }

}
