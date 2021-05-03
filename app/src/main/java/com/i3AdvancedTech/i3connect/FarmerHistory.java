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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FarmerHistory extends AppCompatActivity {

    //Creating instances
    private RecyclerView RecHFarmerHistoryView;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;

    FirebaseAuth mAuth;
    String Farmer_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_history);
        getSupportActionBar().setTitle("Your History");

        db = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        RecHFarmerHistoryView = (RecyclerView) findViewById(R.id.iRecyclerFarmerHistory);
        RecHFarmerHistoryView.setLayoutManager(new LinearLayoutManager(this));

        //Getting the Farmers USER_ID
        Farmer_Id= mAuth.getCurrentUser().getUid();



        //Query for FARMER'S own CROPS
        Query query=db.collection("History").whereEqualTo("Farmer_Id",Farmer_Id).orderBy("SellingPrice", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<HistoryModel> options= new FirestoreRecyclerOptions.Builder<HistoryModel>().
                setQuery(query,HistoryModel.class).build();

        adapter= new FirestoreRecyclerAdapter<HistoryModel, FarmerHistory.HistoryViewHolder>(options) {
            @NonNull
            @Override
            public FarmerHistory.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowhistory,parent,false);

                return new FarmerHistory.HistoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final FarmerHistory.HistoryViewHolder holder, int position, @NonNull HistoryModel model) {

                holder.C_Name.setText("Crop:  "+model.getCropName());
                holder.S_Price.setText("Sold for : Rs  "+model.getSellingPrice());
                holder.C_Qty.setText("Qty:  "+model.getCropQty() +""+" kgs");
                holder.Buyer_Name.setText("Purchased by: "+model.getBuyer_Id());


            }
        };

        RecHFarmerHistoryView.setHasFixedSize(true);
        RecHFarmerHistoryView.setLayoutManager(new LinearLayoutManager(this));
        RecHFarmerHistoryView.setAdapter(adapter);

    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView C_Name;
        private TextView S_Price;
        private TextView C_Qty;
        private TextView Buyer_Name;
        public HistoryViewHolder(@NonNull final View itemView) {
            super(itemView);

            C_Name=itemView.findViewById(R.id.iCName);
            S_Price=itemView.findViewById(R.id.iSellingPrice);
            C_Qty=itemView.findViewById(R.id.iCQty);
            Buyer_Name=itemView.findViewById(R.id.iBuyerName);

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
