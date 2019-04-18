package com.k.menu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.k.menu.R;
import com.k.menu.interface1.ItemClickListener;

/**
 * Created by Karthik on 26-01-2018.
 */

public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtorderId,txtorderStatus,txtorderPhone,txtorderAddress;
    private ItemClickListener itemClickListener;

    public orderViewHolder(View itemView) {
        super(itemView);

        txtorderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtorderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtorderId = (TextView)itemView.findViewById(R.id.order_id);
        txtorderPhone= (TextView)itemView.findViewById(R.id.order_phone);

    itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }
}
