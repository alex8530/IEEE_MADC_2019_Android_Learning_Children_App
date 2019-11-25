package com.arapeak.adkya.ui.fragments;


import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arapeak.adkya.R;
import com.arapeak.adkya.ui.activites.HomeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
        //this is for handel back press button inside fragment
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    Fragment fragment= HomeFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();


                    return true;
                }
                return false;
            }
        });
    }

    public MaterialFragment() {
        // Required empty public constructor
    }
    private static MaterialFragment Instance;

    public static MaterialFragment getInstance(){
        if (Instance==null) {
            Instance= new MaterialFragment();
        }
        return Instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_material, container, false);


        ImageView menubar= view.findViewById(R.id.menubar);

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //THIS IS FOR SHOW THE DrawerLayout
                //because the nav in the activity .. i use this
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

                drawer.openDrawer(GravityCompat.START);
            }
        });


        ImageView notImage= view.findViewById(R.id.imageView9);
        notImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              HomeActivity.replaceFragmentFromActivity(NotificationFragment.getInstance());

            }
        });
        return view;
    }
}
