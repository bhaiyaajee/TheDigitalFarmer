package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class ShowProfile extends AppCompatActivity {

    private TextView UserName, UserPhone, UserEmail;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        getSupportActionBar().setTitle("Your Profile");

        UserName=(TextView)findViewById(R.id.iUserName);
        UserPhone=(TextView)findViewById(R.id.iUserPhone);
        UserEmail=(TextView)findViewById(R.id.iUserEmail);

        mAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

      documentReference=db.collection("Users").document(mAuth.getUid());
        //Getting SNAPSHOT of data in Document
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                //Checking if Document Exists
                if(value.exists()) {
                    //Setting Data in the TextFields UI
                    UserName.setText(value.getString("Name"));
                    UserPhone.setText(value.getString("Phone"));
                    UserEmail.setText(value.getString("Email"));
                } //If Block Ends here for Doc-Snapshot Value

            }
        });


    }
}
