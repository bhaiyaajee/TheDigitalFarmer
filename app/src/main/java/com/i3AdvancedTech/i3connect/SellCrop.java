package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellCrop extends AppCompatActivity {

    //Creating instances
    private RecyclerView RecSellerView;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;

    FirebaseAuth mAuth;
    String Farmer_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop);
        getSupportActionBar().setTitle("Your Active Deals");

        db = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        RecSellerView = (RecyclerView) findViewById(R.id.iRecyclerSeller);
        RecSellerView.setLayoutManager(new LinearLayoutManager(this));

        //Getting the Farmers USER_ID
        Farmer_Id= mAuth.getCurrentUser().getUid();



        //Query for FARMER'S own CROPS
        Query query=db.collection("Farmers").whereEqualTo("Farmer_Id",Farmer_Id).orderBy("CropPrice", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CropModel> options= new FirestoreRecyclerOptions.Builder<CropModel>().
                setQuery(query,CropModel.class).build();

        adapter= new FirestoreRecyclerAdapter<CropModel, SellCrop.SellViewHolder>(options) {
            @NonNull
            @Override
            public SellCrop.SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sinnglesellrow,parent,false);

                return new SellCrop.SellViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final SellCrop.SellViewHolder holder, int position, @NonNull CropModel model) {

                holder.C_Name.setText("Crop:  "+model.getCropName());
                holder.C_Price.setText("Latest Bid:  "+model.getCropPrice() +""+" /kg");
                holder.C_Qty.setText("Qty:  "+model.getCropQty() +""+" kgs");


                //Events after CLICK on any CROP's SingleRow View
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Getting the Snapshot of Document which is Clicked
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

                        //Storing the Document ID of the Document which is clicked
                        final String DocId = snapshot.getId();

                        //Creating an INTENT for next Activity
                        Intent intent= new Intent(getApplicationContext(), FinalSellCrop.class);

                        //Passing the Document ID in next Activity
                        intent.putExtra("Doc_Id", DocId );
                        startActivity(intent);
                       // Toast.makeText(getApplicationContext(),DocId,Toast.LENGTH_LONG).show();;
                    }
                });
            }
        };

        RecSellerView.setHasFixedSize(true);
        RecSellerView.setLayoutManager(new LinearLayoutManager(this));
        RecSellerView.setAdapter(adapter);

    }

    private class SellViewHolder extends RecyclerView.ViewHolder{

        private TextView C_Name;
        private TextView C_Price;
        private TextView C_Qty;
        public SellViewHolder(@NonNull final View itemView) {
            super(itemView);

            C_Name=itemView.findViewById(R.id.iCName);
            C_Price=itemView.findViewById(R.id.iCPrice);
            C_Qty=itemView.findViewById(R.id.iCQty);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
