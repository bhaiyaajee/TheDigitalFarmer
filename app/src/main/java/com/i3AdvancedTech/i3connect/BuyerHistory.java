package com.i3AdvancedTech.i3connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BuyerHistory extends AppCompatActivity {

    private static final String TAG = "Buyer's History";

    //Creating instances
    private RecyclerView RecBuyerHistoryView;
    private FirebaseFirestore db3;
    private FirestoreRecyclerAdapter adapter;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String BuyerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_history);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        db3 = FirebaseFirestore.getInstance();


        RecBuyerHistoryView = (RecyclerView) findViewById(R.id.iRecyclerBuyerHistory);
        RecBuyerHistoryView.setLayoutManager(new LinearLayoutManager(this));

        //Getting the Farmers USER_ID
        BuyerId= mAuth.getCurrentUser().getUid();



        //Query for BUYER'S own CROPS
        Query query2=db3.collection("History").whereEqualTo("Buyer_Id",BuyerId);

        FirestoreRecyclerOptions<BuyerModel> options= new FirestoreRecyclerOptions.Builder<BuyerModel>().
                setQuery(query2,BuyerModel.class).build();

        adapter= new FirestoreRecyclerAdapter<BuyerModel, BuyerHistory.BuyHistoryViewHolder>(options) {
            @NonNull
            @Override
            public BuyerHistory.BuyHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowbuyerhistory,parent,false);

                return new BuyerHistory.BuyHistoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final BuyerHistory.BuyHistoryViewHolder holder, int position, @NonNull BuyerModel model) {

                holder.C_Name.setText("Crop:  "+model.getCropName());
                holder.S_Price.setText("Cost:  Rs  "+model.getSellingPrice()+"");
                holder.C_Qty.setText("Qty:  "+model.getCropQty() +""+" kgs");
                holder.Farmer_Name.setText("Sold By: "+ model.getFarmer_Id());

            }
        };

        RecBuyerHistoryView.setHasFixedSize(true);
        RecBuyerHistoryView.setLayoutManager(new LinearLayoutManager(this));
        RecBuyerHistoryView.setAdapter(adapter);

    }

    private class BuyHistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView C_Name;
        private TextView S_Price;
        private TextView C_Qty;
        private TextView Farmer_Name;
        public BuyHistoryViewHolder(@NonNull final View itemView) {
            super(itemView);

            C_Name=itemView.findViewById(R.id.iCName);
            S_Price=itemView.findViewById(R.id.iSellingPrice);
            C_Qty=itemView.findViewById(R.id.iCQty);
            Farmer_Name=itemView.findViewById(R.id.iFarmerName);

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
