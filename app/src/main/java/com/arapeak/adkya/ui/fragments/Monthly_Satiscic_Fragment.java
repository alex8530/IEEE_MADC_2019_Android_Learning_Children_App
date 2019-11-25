package com.arapeak.adkya.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.getStatistics.ResultGetStatistics;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class Monthly_Satiscic_Fragment extends Fragment {
    TextView total_average , total_section_resolved,total_exercises_resolved;

    CustomProgress customProgress= new CustomProgress();


    public Monthly_Satiscic_Fragment() {

    }

    private static Monthly_Satiscic_Fragment Instance;

    public static Monthly_Satiscic_Fragment getInstance(){



        if (Instance==null) {
            Instance= new Monthly_Satiscic_Fragment();
        }
        return Instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_monthly__satiscic_, container, false);


        total_section_resolved= view.findViewById(R.id.textView23);
        total_exercises_resolved= view.findViewById(R.id.tv_total_exercises);
        total_average= view.findViewById(R.id.textView28);

        SendGetSatisticRequest();

        return view;
    }

    private void SendGetSatisticRequest() {

        customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);
         APIService apiService= ServiceGenerator.createService(APIService.class,Common.retrieveUserDataPreferance(getActivity()).getRememberToken());
        Call<ResultGetStatistics> call =  apiService.getStatistics();
        call.enqueue(new Callback<ResultGetStatistics>() {
            @Override
            public void onResponse(Call<ResultGetStatistics> call, Response<ResultGetStatistics> response) {

                if (response.isSuccessful()){
                     total_section_resolved.setText(response.body().getData().getMonthlySectionsResolved().intValue( )+"");
                    total_exercises_resolved.setText(response.body().getData().getMonthlyExercisesResolved().intValue( )+"");
                    total_average.setText(response.body().getData().getMonthlyCorrectAnswersRate().intValue( )+"%");

                     customProgress.hideProgress();

                }else {

                     customProgress.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<ResultGetStatistics> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال"   , Toast.LENGTH_SHORT).show();

                 customProgress.hideProgress();


            }
        });
    }


}
