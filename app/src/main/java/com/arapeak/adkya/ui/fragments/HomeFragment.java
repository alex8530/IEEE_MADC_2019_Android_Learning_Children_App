package com.arapeak.adkya.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.getMaterial.MaterialData;
import com.arapeak.adkya.model.getMaterial.ResultGetMaterial;
import com.arapeak.adkya.ui.activites.LoginActivity;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = "visitor";

    private OnFragmentInteractionListener mListener;

    CustomProgress customProgress= new CustomProgress();

    //class SecondMaterialData come from matrial package
    List<MaterialData> materials= new ArrayList<>();



    private boolean mIsDownloadReady=false;//to prevent crash if user click on item befor get data from api

    public HomeFragment() {
        // Required empty public constructor

        Log.d(TAG, "HomeFragment: constructor");
    }


    private static HomeFragment Instance;

    public static HomeFragment getInstance(){
        if (Instance==null) {
            Instance= new HomeFragment();
        }

        return Instance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // Inflate the layout for this fragment
       View view=  inflater.inflate(R.layout.fragment_home, container, false);
        TextView tv_name= view.findViewById(R.id.tv_name);
        if (Common.IS_VISITOR){
            tv_name.setText("زائر");

        }else {
            tv_name.setText(Common.retrieveUserDataPreferance(getActivity().getApplicationContext()).getName());

        }
        //  I do the next because i need to show the nav when click in image icon , and click on fragment itself


        LinearLayout math_layout= view.findViewById(R.id.math_layout);
        math_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsDownloadReady){
                    Common.CurrentMaterial=materials.get(3);//note that 3 point to matmatic
                    Fragment fragment= SecondMatrialFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
                }

            }
        });

        LinearLayout  ethraa_layout= view.findViewById(R.id.ethraa_layout);
        ethraa_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsDownloadReady){
                    Common.CurrentMaterial=materials.get(0);//note that 3 point to matmatic
                    Fragment fragment= SecondMatrialFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();


                }


            }
        });

        LinearLayout  eng_layout= view.findViewById(R.id.eng_layout);
        eng_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mIsDownloadReady){
                    Common.CurrentMaterial=materials.get(1);//note that 3 point to eng
                    Fragment fragment= SecondMatrialFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
                }



            }
        });

        LinearLayout  arabic_layout= view.findViewById(R.id.arabic_layout);
        arabic_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsDownloadReady){
                    Common.CurrentMaterial=materials.get(2);//note that 2 point to arabic
                    Fragment fragment= SecondMatrialFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

                }


            }
        });


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
             Fragment fragment= NotificationFragment.getInstance();
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

            }
        });

        //check if comme from visitor or user
        if (Common.IS_VISITOR==true){
            getMaterialVisitor();


        }else {
            //thats mean is user and login
            getMaterial(Common.retrieveUserDataPreferance(getActivity()).getRememberToken());

        }

        return  view;
    }
    private void showReqiuredRegisterVisitorDailog() {

      final   AlertDialog dialog;
        AlertDialog.Builder mBuilder;
        mBuilder = new AlertDialog.Builder(getActivity() );
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = li.inflate(R.layout.dialog_require_register_visitor, null);


        mBuilder.setView(mView);
         dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                 startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });

    }
    private void getMaterialVisitor() {

        customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);

        APIService apiService= ServiceGenerator.createService
                (APIService.class );
        Call<ResultGetMaterial> call =  apiService.getMaterialVisitor();
        call.enqueue(new Callback<ResultGetMaterial>() {
            @Override
            public void onResponse(Call<ResultGetMaterial> call, Response<ResultGetMaterial> response) {
                if (response.isSuccessful()){


                    materials= response.body().getData();
                    mIsDownloadReady=true;

                     customProgress.hideProgress();

                }else {

                     customProgress.hideProgress();
                    mIsDownloadReady=false;
                    Toast.makeText(getActivity(), "حدث خطا في الاتصال", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResultGetMaterial> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال"  , Toast.LENGTH_SHORT).show();
                mIsDownloadReady=false;

                 customProgress.hideProgress();

            }
        });
    }




    private void getMaterial(final String token) {

        customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);

        APIService apiService= ServiceGenerator.createService
                (APIService.class,token );
        Call<ResultGetMaterial> call =  apiService.getMaterial();
        call.enqueue(new Callback<ResultGetMaterial>() {
            @Override
            public void onResponse(Call<ResultGetMaterial> call, Response<ResultGetMaterial> response) {
                if (response.isSuccessful()){


                     materials= response.body().getData();
                    mIsDownloadReady=true;

                     customProgress.hideProgress();

                }else {

                     customProgress.hideProgress();
                    mIsDownloadReady=false;
                    Toast.makeText(getActivity(), "حدث خطا في الاتصال", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResultGetMaterial> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال"  , Toast.LENGTH_SHORT).show();
                mIsDownloadReady=false;

                customProgress.hideProgress();

            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
