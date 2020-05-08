package com.circleupinc.products.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.circleupinc.products.Interface.ItemLickListener;
import com.circleupinc.products.R;
import com.google.android.material.chip.Chip;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public Chip chips;

    public TextView name, price, rating;
    public RatingBar ratingBar;
    public ImageView image, addToCart;

    private ItemLickListener itemLickListener;



    public FoodViewHolder(View itemView) {
        super( itemView );


        name =  itemView.findViewById( R.id.food_name );
        price =  itemView.findViewById( R.id.food_price );
        rating =  itemView.findViewById( R.id.rating_text );
        ratingBar =  itemView.findViewById( R.id.food_rating_bar );

        image =  itemView.findViewById( R.id.food_image );
        addToCart =  itemView.findViewById( R.id.add_food );



        itemView.setOnClickListener( this );


    }

    public void setItemLickListener(ItemLickListener itemLickListener) {
        this.itemLickListener = itemLickListener;
    }

    @Override
    public void onClick(View view) {
        itemLickListener.onClick( view, getAdapterPosition( ), false );
    }




}
