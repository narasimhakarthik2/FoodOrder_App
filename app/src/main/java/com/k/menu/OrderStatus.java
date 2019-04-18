package com.k.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.k.menu.common.common;
import com.k.menu.model.Order;
import com.k.menu.model.Request;
import com.k.menu.viewholder.orderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, orderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        recyclerView = (RecyclerView) findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent()== null)
        loadOrders(common.currentuser.getPhone());
        else
            loadOrders(getIntent().getStringExtra("userPhone"));
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, orderViewHolder>(
                Request.class,
                R.layout.order_layout,
                orderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(orderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtorderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtorderStatus.setText(common.converCodeToStatus(model.getStatus()));
                viewHolder.txtorderPhone.setText(model.getPhone());
                viewHolder.txtorderAddress.setText(model.getAddress());

            }
        };
        recyclerView.setAdapter(adapter);
    }


}