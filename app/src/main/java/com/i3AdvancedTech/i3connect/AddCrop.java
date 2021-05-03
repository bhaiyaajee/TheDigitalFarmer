package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCrop extends AppCompatActivity {

    private static final String TAG = "AddCrop";

    private TextView HintAddCrop ;
    private EditText CropName, CropQuantity , CropPrice;
    private Button AddCropSubmit;
    private ProgressDialog progress;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String Farmer_Id;

    String Crop_Name;
    float Crop_Qty,Crop_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        //ActionBar
        getSupportActionBar().setTitle("Add Crop");

        //Binding UI Elements
        HintAddCrop=(TextView)findViewById(R.id.iHelloAddCrop);
        CropName=(EditText)findViewById(R.id.iCropName);
        CropQuantity=(EditText)findViewById(R.id.iCropQuantity);
        CropPrice=(EditText)findViewById(R.id.iCropPrice);
        AddCropSubmit=(Button) findViewById(R.id.iAddCropSubmit);

        //Getting Instance of Databases
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        //ProgressBar Instance
        progress = new ProgressDialog(this);


        //Functionality for SUBMIT Button click
        AddCropSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //Putting Conditions for Testing
              if(!CropName.getText().toString().isEmpty() && !CropPrice.getText().toString().isEmpty() && !CropQuantity.getText().toString().isEmpty()) {

                  //Fetching data from UI
                  Crop_Name= CropName.getText().toString().trim();
                  Crop_Qty=Float.valueOf(CropQuantity.getText().toString());
                  Crop_Price=Float.valueOf(CropPrice.getText().toString());
                  Farmer_Id= mAuth.getCurrentUser().getUid();

                  //Checking for CROP QTY to be NON_ZERO
                  if(Crop_Qty !=0) {

                      //Displaying Progress Bar
                      progress.setTitle("Adding Crop");
                      progress.setMessage("Crop Details are being added");
                      progress.show();

                      //Creating reference for FARMERS collection
                      DocumentReference documentReference = db.collection("Farmers").document();

                      //Setting up the Collection of Data
                      Map<String, Object> crop = new HashMap<>();
                      //crop.put("CropBid", 0);
                      crop.put("CropName", Crop_Name);
                      crop.put("CropPrice", Crop_Price);
                      crop.put("CropQty", Crop_Qty);
                      crop.put("Farmer_Id", Farmer_Id);

                      //Adding Crop in our Database
                      documentReference.set(crop).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Log.d(TAG, "DocumentSnapshot added with ID: " + Farmer_Id);
                              progress.dismiss();
                              Toast.makeText(AddCrop.this, " Crop Added for Sale ",
                                      Toast.LENGTH_LONG).show();
                              CropName.setText("");
                              CropPrice.setText("");
                              CropQuantity.setText("");

                              startActivity(new Intent(getApplicationContext(), FarmerScreen.class));
                              finish();
                          }
                      })
                              .addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Log.w(TAG, "Error adding document", e);
                                      progress.dismiss();
                                      Toast.makeText(AddCrop.this, e.toString(),
                                              Toast.LENGTH_LONG).show();
                                  }
                              });

                  } //IF BLOCK FOR QUANTITY ENDS HERE
                  else{
                      CropQuantity.requestFocus();
                      CropQuantity.setError("Can't be Zero");
                      Toast.makeText(getApplicationContext(),"Quantity can't be ZERO",Toast.LENGTH_LONG).show();
                  }

              } //IF BLOCK FOR EMPTY FIELDS END HERE
              else if(CropName.getText().toString().isEmpty()){
                  CropName.setError("Name can't be Empty");
                  CropName.requestFocus();
                  Toast.makeText(getApplicationContext(),"Please Fill all the Details",Toast.LENGTH_LONG).show();
              }
              else if(CropQuantity.getText().toString().isEmpty()){
                  CropQuantity.setError("Quantity can't be Empty");
                  CropQuantity.requestFocus();
              }
              else{
                  CropPrice.setError("Price can't be Empty");
                  CropPrice.requestFocus();
              }



            }
        });

    }
}
