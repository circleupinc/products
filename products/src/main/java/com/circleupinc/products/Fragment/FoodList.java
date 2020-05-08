package com.circleupinc.products.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.circleupinc.products.Interface.ItemLickListener;
import com.circleupinc.products.R;
import com.circleupinc.products.ViewHolder.FoodViewHolder;
import com.circleupinc.products.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class FoodList extends Fragment {


    private DatabaseReference database;

    private RecyclerView recFoodList;

    private FirebaseRecyclerOptions<FoodModel> foodOptions;
    private FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> adapter;

    String menuId = "";


    public FoodList() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FoodList newInstance() {
        FoodList fragment = new FoodList();

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
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        menuId = getArguments().getString("menuId");


        database = FirebaseDatabase.getInstance().getReference();


        recFoodList = view.findViewById(R.id.rec_season);
        recFoodList.setHasFixedSize(false);
        recFoodList.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        //  recFoodList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        loadTrending(menuId);
        return view;
    }


    private void loadTrending(final String menuId) {


        foodOptions = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(database.child("Food").orderByChild("menuId").equalTo(menuId), FoodModel.class)
                .setLifecycleOwner(this)

                .build();

        adapter = new FirebaseRecyclerAdapter<FoodModel, FoodViewHolder>(foodOptions) {

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(FoodViewHolder viewHolder, int position, final FoodModel model) {

                // String image = Common.getServerName(Common.server, model.getServer())+"/image/"+model.getImage()+".jpg";


                Picasso.with(getActivity()).load(model.getImage()).fit()
                        .placeholder(R.drawable.demo_food).into(viewHolder.image);


                viewHolder.name.setText(model.getName());

                viewHolder.ratingBar.setRating(model.getRating());
                viewHolder.rating.setText("" + model.getRating());
                viewHolder.price.setText("$" + model.getPrice());


                viewHolder.setItemLickListener(new ItemLickListener() {
                    @Override
                    public void onClick(View view, final int position, boolean isLongClick) {

                        // if you want to add food details activity
                        // else just keep it blank  for avoid error


                    }
                });


            }

            @Override
            public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemVIew = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_food_layout, parent, false);
                return new FoodViewHolder(itemVIew);
            }
        };

        adapter.notifyDataSetChanged();
        recFoodList.setAdapter(adapter);
        adapter.startListening();

    }

}
