package com.k.menu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.k.menu.R;
import com.k.menu.interface1.ItemClickListener;

/**
 * Created by Karthik on 13-01-2018.
 */

  public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
  public TextView food_name;
  public ImageView food_image;

 private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);
        food_name = (TextView)itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.img_food);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);

    }
}
