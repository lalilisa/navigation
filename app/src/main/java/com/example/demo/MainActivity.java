package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.demo.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        onSelectedViewPager();
        onSelectedNavigation();
        fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddItemAcitvity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        viewPager=findViewById(R.id.view_pager);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        fba=findViewById(R.id.fba);
    }

    public void onSelectedNavigation(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mHome: {
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.mHistory: {
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.mSearch: {
                    viewPager.setCurrentItem(2);
                    break;
                }

            }
            return true;
        });
    }
    public void onSelectedViewPager(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: {
                        bottomNavigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    }
                    case 1: {
                        bottomNavigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                    }
                    case 2: {
                        bottomNavigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}