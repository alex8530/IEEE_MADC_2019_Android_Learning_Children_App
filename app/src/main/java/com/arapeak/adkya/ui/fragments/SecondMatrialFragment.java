package com.arapeak.adkya.ui.fragments;


import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.adapters.SecondMaterialAdapter;
 import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.getSecondMaterial.ResultGetSecondMaterial;
import com.arapeak.adkya.model.getSecondMaterial.SecondMaterialData;
import com.arapeak.adkya.ui.activites.HomeActivity;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;

import java.util.ArrayList;


public class SecondMatrialFragment extends Fragment  {

    RecyclerView my_recycler_view;
    SecondMaterialAdapter adapter;
    ArrayList<SecondMaterialData> mSecondMaterialDataList= new ArrayList<>();
    CustomProgress customProgress= new CustomProgress();

    public SecondMatrialFragment() {
        // Required empty public constructor
    }
    private static SecondMatrialFragment Instance;

    public static SecondMatrialFragment getInstance(){
        if (Instance==null) {
            Instance= new SecondMatrialFragment();
        }
        return Instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_second_matrial, container, false);
        TextView tv_title_toolbar= view.findViewById(R.id.tv_title_toolbar);
        tv_title_toolbar.setText(Common.CurrentMaterial.getName());
        ImageView menubar= view.findViewById(R.id.menubar);

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //THIS IS FOR SHOW THE DrawerLayout
                    //because the nav in the activity .. i use this
                    DrawerLayout drawer =getActivity().findViewById(R.id.drawer_layout);
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

         if (Common.IS_VISITOR==true){
            getSecondMaterialVisitor();

        }else {
            getSecondMaterial(Common.retrieveUserDataPreferance(getActivity()).getRememberToken());

        }

        my_recycler_view = view.findViewById(R.id.recyle_outer_vertical_material);

        my_recycler_view.setHasFixedSize(true);

        adapter = new SecondMaterialAdapter(getActivity() );

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(),   RecyclerView.VERTICAL, false));

        return view;
    }

    private void getSecondMaterial(final String token) {

        customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);
         APIService apiService= ServiceGenerator.createService
                (APIService.class,token );
        Call<ResultGetSecondMaterial> call =  apiService.getSecondMaterial(String.valueOf(Common.CurrentMaterial.getId()));
        call.enqueue(new Callback<ResultGetSecondMaterial>() {
            @Override
            public void onResponse(Call<ResultGetSecondMaterial> call, Response<ResultGetSecondMaterial> response) {
                if (response.isSuccessful()){

                    mSecondMaterialDataList=  (ArrayList<SecondMaterialData>) response.body().getData();

                    //set data after load from api
                    adapter.setmListMaterial(mSecondMaterialDataList);
                    my_recycler_view.setAdapter(adapter);

                     customProgress.hideProgress();


                }else {

                     customProgress.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<ResultGetSecondMaterial> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال" + t.getMessage(), Toast.LENGTH_SHORT).show();

                 customProgress.hideProgress();

            }
        });


    }


    private void getSecondMaterialVisitor( ) {

        customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);
         APIService apiService= ServiceGenerator.createService
                (APIService.class );
        Call<ResultGetSecondMaterial> call =  apiService.getSecondMaterialVisitor(
                String.valueOf(Common.VISITOR_CLASS_SELECTED),
                String.valueOf(Common.CurrentMaterial.getId()));
        call.enqueue(new Callback<ResultGetSecondMaterial>() {
            @Override
            public void onResponse(Call<ResultGetSecondMaterial> call, Response<ResultGetSecondMaterial> response) {
                if (response.isSuccessful()){

                    mSecondMaterialDataList=  (ArrayList<SecondMaterialData>) response.body().getData();

                    //set data after load from api
                    adapter.setmListMaterial(mSecondMaterialDataList);
                    my_recycler_view.setAdapter(adapter);

                     customProgress.hideProgress();


                }else {

                     customProgress.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ResultGetSecondMaterial> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال" + t.getMessage(), Toast.LENGTH_SHORT).show();

                 customProgress.hideProgress();

            }
        });


    }
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

}
