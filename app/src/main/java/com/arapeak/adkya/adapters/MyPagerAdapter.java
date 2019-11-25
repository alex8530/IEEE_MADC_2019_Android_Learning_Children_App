package com.arapeak.adkya.adapters;

import android.content.Context;
import android.util.Log;

import com.arapeak.adkya.ui.fragments.All_Satistic_Fragment;
import com.arapeak.adkya.ui.fragments.Daily_Satistic_Fragment;
import com.arapeak.adkya.ui.fragments.Monthly_Satiscic_Fragment;
import com.arapeak.adkya.ui.fragments.Weekly_Satistic_Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private static final String TAG = "PersonalFragment";

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext=context;
        Log.d(TAG, "MyPagerAdapter: construcor");
        getItem(0);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);

        if (position == 0) {

            return All_Satistic_Fragment.getInstance();

        }else if (position==1){
            return Daily_Satistic_Fragment.getInstance();

        }else if  (position==2){

            return Weekly_Satistic_Fragment.getInstance();


        }else if  (position==3){

            return Monthly_Satiscic_Fragment.getInstance();

        }else {
            return null;
        }
     }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tupe= "";
        Log.d(TAG, "getPageTitle: " + position);
        switch (position){

            case 0:
                tupe= "الكل";
                break;
            case 1:
                tupe= "اليومي";
                break;
            case 2:
                tupe= "الاسبوعي";
                break;
            case 3:
                tupe= "الشهري";
                break;
        }

        return tupe;
    }

}
