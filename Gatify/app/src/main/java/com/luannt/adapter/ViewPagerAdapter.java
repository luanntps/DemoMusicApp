package com.luannt.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.luannt.fragment.bxh_fragment;
import com.luannt.fragment.home_fragment;
import com.luannt.fragment.search_fragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new home_fragment();
            case 1:
                return new bxh_fragment();
            case 2:
                return new search_fragment();
            default:
                return new home_fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
