package com.k.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.k.menu.interface1.ItemClickListener;
import com.k.menu.model.Foods;
import com.k.menu.viewholder.FoodViewHolder;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Foodslist extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";

     FirebaseRecyclerAdapter<Foods,FoodViewHolder>adapter;
     FirebaseRecyclerAdapter<Foods,FoodViewHolder>searchadapter;
     List<String>suggestList = new ArrayList<>();
     MaterialSearchBar materialSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodslist);

        database= FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId !=null)
        {
            loadListFood(categoryId);
        }

        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             List<String> suggest = new ArrayList<String>();
                     for(String search:suggestList)
                     {
                         if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                             suggest.add(search);
                     }
                      materialSearchBar.setLastSuggestions(suggest);
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
               startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        loadSuggest();
    }

    private void startSearch(CharSequence text) {
        searchadapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                Foods.class,
                R.layout.fooditems,
               FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(text.toString())

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                final Foods local =model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent des = new Intent(Foodslist.this,FoodDetails.class);
                        des.putExtra("FoodId",searchadapter.getRef(position).getKey());
                        startActivity(des);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

            }
        };
        recyclerView.setAdapter(searchadapter);
    }

    private void loadSuggest() {
        foodList.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Foods item = postSnapshot.getValue(Foods.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(Foods.class,
                R.layout.fooditems,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
              viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.food_image);

                final Foods local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent des = new Intent(Foodslist.this,FoodDetails.class);
                        des.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(des);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        };
            recyclerView.setAdapter(adapter);
    }
}
