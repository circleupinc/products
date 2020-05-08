package com.circleupinc.products.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.circleupinc.products.Interface.ItemLickListener;
import com.circleupinc.products.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



    public TextView name, quantityIncrease , quantityDecrease, quantity,price;
    public ImageView image, removeFromCart;

    private ItemLickListener itemLickListener;



    public CartViewHolder(View itemView) {
        super( itemView );


        name =  itemView.findViewById( R.id.cart_food_name );
        quantityIncrease =  itemView.findViewById( R.id.cart_qnt_increase );
        quantityDecrease =  itemView.findViewById( R.id.cart_food_qnt_decrease );
        quantity =  itemView.findViewById( R.id.cart_food_qnt );

        image =  itemView.findViewById( R.id.cart_image );
        price =  itemView.findViewById( R.id.cart_food_price );
        removeFromCart =  itemView.findViewById( R.id.cart_food_remove );



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
