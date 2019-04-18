package com.k.menu;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.k.menu.Database.Database;
import com.k.menu.common.common;
import com.k.menu.model.Order;
import com.k.menu.model.Request;
import com.k.menu.viewholder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

     FirebaseDatabase database;
     DatabaseReference requests;
     TextView txttotalprice;
     Button btnplace;

   List<Order> cart = new ArrayList<>();
   CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database= FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

      txttotalprice = (TextView)findViewById(R.id.total);
      btnplace =(Button)findViewById(R.id.btnPlaceorder);

      btnplace.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              showAlertdialog();
          }
      });

      loadListFood();
    }

    private void showAlertdialog() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(Cart.this);
        alertdialog.setTitle("One more step");
        alertdialog.setMessage("Enter your Address: ");

        final EditText eatAddress =new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        eatAddress.setLayoutParams(lp);
        alertdialog.setView(eatAddress);
        alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        common.currentuser.getPhone(),
                        common.currentuser.getName(),
                        eatAddress.getText().toString(),
                        txttotalprice.getText().toString(),
                        cart
                );
              requests .child(String.valueOf(System.currentTimeMillis()))
                      .setValue(request);

              new Database(getBaseContext()).clearCart();
                Toast.makeText(Cart.this,"Thank You , Order Placed",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertdialog.show();
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
            for(Order order:cart){
               // try{
                    total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));//}
               // catch(Exception e){}
            }
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txttotalprice.setText(fmt.format(total));
    }


}



