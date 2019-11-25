package com.arapeak.adkya.ui.fragments;


import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arapeak.adkya.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }
    private static NotificationFragment Instance;

    public static NotificationFragment getInstance(){
        if (Instance==null) {
            Instance= new NotificationFragment();
        }
        return Instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);


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




        return view;
    }

}
