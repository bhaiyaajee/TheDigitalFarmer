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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.List;

public class BuyCrop extends AppCompatActivity {

    private RecyclerView RecBuyerView;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_crop);
        getSupportActionBar().setTitle("Select Crop");


        db = FirebaseFirestore.getInstance();

        RecBuyerView = (RecyclerView) findViewById(R.id.iRecyclerBuyer);
        RecBuyerView.setLayoutManager(new LinearLayoutManager(this));



        //Query
        Query query=db.collection("Farmers").orderBy("CropName");

        FirestoreRecyclerOptions<CropModel> options= new FirestoreRecyclerOptions.Builder<CropModel>().
                setQuery(query,CropModel.class).build();

        adapter= new FirestoreRecyclerAdapter<CropModel, CropViewHolder>(options) {
            @NonNull
            @Override
            public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

                return new CropViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final CropViewHolder holder, int position, @NonNull CropModel model) {

                holder.C_Name.setText("Crop:  "+model.getCropName());
                holder.C_Price.setText("Price:  "+model.getCropPrice() +""+" /kg");
                holder.C_Qty.setText("Qty:  "+model.getCropQty() +""+" kgs");
                //holder.F_Id.setText(model.getFarmer_Id());

                //EXP
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                        final String DocId = snapshot.getId();

                        Intent intent= new Intent(getApplicationContext(), BuyerBidding.class);
                        intent.putExtra("Doc_Id", DocId );
                        startActivity(intent);
                    }
                });
            }
        };

        RecBuyerView.setHasFixedSize(true);
        RecBuyerView.setLayoutManager(new LinearLayoutManager(this));
        RecBuyerView.setAdapter(adapter);

    }

    private class CropViewHolder extends RecyclerView.ViewHolder{

        private TextView C_Name;
        //private TextView F_Id;
        private TextView C_Price;
        private TextView C_Qty;
        public CropViewHolder(@NonNull final View itemView) {
            super(itemView);

            C_Name=itemView.findViewById(R.id.iCName);
           // F_Id=itemView.findViewById(R.id.iFId);
            C_Price=itemView.findViewById(R.id.iCPrice);
            C_Qty=itemView.findViewById(R.id.iCQty);

            //EXPERIMENT

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}

