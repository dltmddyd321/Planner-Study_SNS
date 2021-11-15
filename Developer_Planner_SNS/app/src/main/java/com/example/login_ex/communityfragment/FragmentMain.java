package com.example.login_ex.communityfragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.login_ex.R;
import com.example.login_ex.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FirstFragment(), "\uD83D\uDC98");
        viewPagerAdapter.AddFragment(new SecondFragment(), "\uD83D\uDCD3");
        viewPagerAdapter.AddFragment(new ThirdFragment(), "\uD83D\uDD0D");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}