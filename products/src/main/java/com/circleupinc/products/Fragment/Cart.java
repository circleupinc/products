package com.circleupinc.products.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.circleupinc.products.Interface.ItemLickListener;
import com.circleupinc.products.R;
import com.circleupinc.products.ViewHolder.CartViewHolder;
import com.circleupinc.products.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class Cart extends Fragment {


    private DatabaseReference database;

    private RecyclerView recFoodList;

    private FirebaseRecyclerOptions<FoodModel> foodOptions;
    private FirebaseRecyclerAdapter<FoodModel, CartViewHolder> adapter;

    String menuId = "01";


    public Cart() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Cart newInstance() {
        Cart fragment = new Cart();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        database = FirebaseDatabase.getInstance().getReference();


        recFoodList = view.findViewById(R.id.rec_season);
        recFoodList.setHasFixedSize(false);
       // recFoodList.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
         recFoodList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        loadTrending(menuId);

        return view;

    }

    private void loadTrending(final String menuId) {


        foodOptions = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(database.child("pendingOrder").child(menuId).child("food"), FoodModel.class)

                .setLifecycleOwner(this)

                .build();

        adapter = new FirebaseRecyclerAdapter<FoodModel, CartViewHolder>(foodOptions) {

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(final CartViewHolder viewHolder, final int position, final FoodModel model) {

                // String image = Common.getServerName(Common.server, model.getServer())+"/image/"+model.getImage()+".jpg";


                viewHolder.quantity.setText(model.getQuantity()+"");
                double price=Double.parseDouble(model.getPrice());
                viewHolder.price.setText("$ "+Double.toString(price*model.getQuantity()));


                database.child("Food").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        FoodModel foodDetails = dataSnapshot.getValue(FoodModel.class);
                       // viewHolder.price.setText(foodDetails.getPrice());
                        viewHolder.name.setText(foodDetails.getName());

                        Picasso.with(getActivity()).load(model.getImage()).fit()
                                .placeholder(R.drawable.demo_food).into(viewHolder.image);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                viewHolder.removeFromCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey()).setValue(null);


                    }
                });


                viewHolder.quantityIncrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                int oldQuantity = dataSnapshot.child("quantity").getValue(Integer.class);

                                int newQuantity = oldQuantity + 1;


                                double price=Double.parseDouble(model.getPrice());


                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("quantity", newQuantity);
                                hashMap.put("totalPrice", Double.toString(price*newQuantity));


                                database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey())
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        //add


                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });




                viewHolder.quantityDecrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                int oldQuantity = dataSnapshot.child("quantity").getValue(Integer.class);

                                if (oldQuantity <= 1) {

                                    database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey()).setValue(null);



                                } else {


                                    int newQuantity = oldQuantity - 1;

                                    double price=Double.parseDouble(model.getPrice());


                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("quantity", newQuantity);
                                    hashMap.put("totalPrice", Double.toString(price*newQuantity));

                                    database.child("pendingOrder").child(menuId).child("food").child(adapter.getRef(position).getKey())
                                            .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            //add


                                        }
                                    });
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });


                viewHolder.setItemLickListener(new ItemLickListener() {
                    @Override
                    public void onClick(View view, final int position, boolean isLongClick) {

                        // if you want to add food details activity
                        // else just keep it blank  for avoid error


                    }
                });


            }

            @Override
            public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemVIew = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_cart_layout, parent, false);
                return new CartViewHolder(itemVIew);
            }
        };

        adapter.notifyDataSetChanged();
        recFoodList.setAdapter(adapter);
        adapter.startListening();

    }


}
