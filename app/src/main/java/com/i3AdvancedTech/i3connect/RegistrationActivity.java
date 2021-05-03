package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText Name, Email,Phone, Password;
    private Button SignUp;
    private TextView AlreadyRegistered;
    private ImageView UserLogo;
    private RadioButton GenderRadioButton;
    private RadioGroup radioGroup;

    String UName , UEmail , UPhone , UGender , UPassword , CountryCode="+91",JustPhone;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Sign Up");

        mAuth = FirebaseAuth.getInstance();

        radioGroup=(RadioGroup)findViewById(R.id.iRadioGroup);

        setUpUI();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                UEmail= Email.getText().toString().trim();
                UPassword= Password.getText().toString().trim();
                JustPhone= Phone.getText().toString().trim();
                UPhone= CountryCode.concat(JustPhone);
                UName = Name.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                GenderRadioButton = (RadioButton) findViewById(selectedId);
                    UGender= GenderRadioButton.getText().toString();

                if(!UName.isEmpty() && !UEmail.isEmpty()  && !JustPhone.isEmpty() && !UPassword.isEmpty()) {


                        mAuth.createUserWithEmailAndPassword(UEmail, UPassword).
                                addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegistrationActivity.this, "Please Check Your Email for Verification", Toast.LENGTH_LONG).show();
                                                        Log.d(TAG, "Email Verification sent.");
                                                    } else {
                                                        try {
                                                            throw task.getException();
                                                        } catch (Exception e) {
                                                            String err = e.getMessage().toString();
                                                            Toast.makeText(RegistrationActivity.this, err, Toast.LENGTH_LONG).show();
                                                        }

                                                    }
                                                }
                                            });


                                            if (!UName.isEmpty() && !UEmail.isEmpty() && !UGender.isEmpty() && !UPhone.isEmpty() && !UPassword.isEmpty()) {

                                                sendUserData();

                                                Log.d(TAG, "createUserWithEmail:success");

                                                mAuth.signOut();
                                                finish();
                                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                                Toast.makeText(RegistrationActivity.this, "Registration Successful",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {

                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                String error = e.getMessage().toString();

                                                Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    }
                                });

                }
                else
                    {
                    Snackbar snackbar;
                    snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "         Please  Enter  all  the  Details ", Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(Color.RED);
                    snackbar.show();

                    }

            }
        });


        AlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    private void sendUserData()
    {
        //Getting Instance of Databases
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        //Creating reference for USERS collection
        DocumentReference documentReference = db.collection("Users").document(mAuth.getUid());

        //Setting up the Collection of Data
        Map<String, Object> user = new HashMap<>();

        user.put("Name", UName);
        user.put("Email", UEmail);
        user.put("Phone", UPhone);
        user.put("Password", UPassword);
        user.put("Gender", UGender);

        //Adding Crop in our Database
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + mAuth.getUid());


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);

                    }
                });

    }

    private void setUpUI()
    {
        Email = (EditText) findViewById(R.id.iEmailForRegistration);
        Password = (EditText) findViewById(R.id.iPassword);
        Phone = (EditText) findViewById(R.id.iPhone);
        Name = (EditText) findViewById(R.id.iName);
        SignUp = (Button) findViewById(R.id.iSubmit);
        AlreadyRegistered = (TextView) findViewById(R.id.iAlreadyRegistered);
        UserLogo= (ImageView)findViewById(R.id.iUserLogo);


    }
}


