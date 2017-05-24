package com.dudwo.gyrocounter;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by user on 2017-05-24.
 */

public class Help extends AppCompatActivity {

    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        vp=(ViewPager)findViewById(R.id.vp);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
    }


    private class pagerAdapter extends FragmentStatePagerAdapter{
        public pagerAdapter(android.support.v4.app.FragmentManager fm){
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position){
            switch (position){
                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondFragment();
                case 2:
                    return new ThirdFragment();
                case 3:
                    return new FourthFragment();
                case 4:
                    return new FifthFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 5;
        }
    }
}
//