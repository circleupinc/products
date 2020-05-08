package com.circleupinc.products.utility;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Bundle> bundleList = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = mFragmentList.get(position);

        fragment.setArguments(bundleList.get(position));

        return fragment;
    }

    public void addFragment(Fragment fragment, String title, Bundle bundle) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        bundleList.add(bundle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}
