package com.circleupinc.products.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.circleupinc.products.R;
import com.circleupinc.products.utility.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FoodMenu extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String id = "";

    public FoodMenu() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FoodMenu newInstance() {
        FoodMenu fragment = new FoodMenu();

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
        View view = inflater.inflate(R.layout.fragment_food_menu, container, false);


        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabview);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());


        FirebaseDatabase.getInstance().getReference().child("foodMenu").orderByChild("sort")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot tab : dataSnapshot.getChildren()) {

                            Bundle args = new Bundle();
                            args.putString("menuId", tab.getKey());


                            adapter.addFragment(new FoodList(), tab.child("name").getValue().toString(), args);


                        }

                        viewPager.setAdapter(adapter);

                        tabLayout.setupWithViewPager(viewPager);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        return view;

    }
}
