package com.example.demo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo.fragment.HistoryFragment;
import com.example.demo.fragment.HomeFragment;
import com.example.demo.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                     return new HomeFragment();
            case 1:
                     return new HistoryFragment();
            case 2:
                     return new SearchFragment();
            default:
                     return new HistoryFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
