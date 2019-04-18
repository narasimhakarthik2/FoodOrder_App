package com.k.menu.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.k.menu.R;
import com.k.menu.interface1.ItemClickListener;
import com.k.menu.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Karthik on 15-01-2018.
 */
class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_cart_name,text_cart_price;
    public ImageView img_cart_count;

    private ItemClickListener itemClickListener;

    public void setText_cart_name(TextView text_cart_name) {
        this.text_cart_name = text_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        text_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        img_cart_count =(ImageView)itemView.findViewById(R.id.cart_item_count);
        text_cart_price=(TextView)itemView.findViewById(R.id.cart_item_price);

    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listdata = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listdata,Context context){
        this.listdata=listdata;
        this.context=context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(""+listdata.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(textDrawable);

        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        int price = (Integer.parseInt(listdata.get(position).getPrice()))*(Integer.parseInt(listdata.get(position).getQuantity()));
        holder.text_cart_price.setText(listdata.get(position).getPrice());
        holder.text_cart_name.setText(listdata.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
