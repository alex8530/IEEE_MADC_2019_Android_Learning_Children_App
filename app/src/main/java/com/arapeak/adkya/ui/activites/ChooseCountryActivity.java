package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.adapters.CountryAdapter;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.listener.MyItemListener;
import com.arapeak.adkya.model.country.Country;
import com.arapeak.adkya.model.country.ResultCountryModel;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;

import java.util.ArrayList;

public class ChooseCountryActivity extends AppCompatActivity implements MyItemListener {
    AlertDialog dialog;
    AlertDialog.Builder mBuilder;
    Country mSelectedCountry=new Country();
    RecyclerView mRecyleCountry;
    CountryAdapter countryAdapter;
    ArrayList<Country> listCountry;

    TextView txt_country;
    CustomProgress customProgress= new CustomProgress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);


        txt_country =  findViewById(R.id.txt_country);


        //init adapter
        countryAdapter = new CountryAdapter(this  );
        countryAdapter.setMyItemListener(this);
        listCountry=  getListCountry();


        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCountry();
            }
        });


        Button button= findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedCountry.getName()==null){
                    Toast.makeText(ChooseCountryActivity.this, "يرجى اختيار الدولة", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(getApplicationContext(),Register3Activity.class));

                }

            }
        } );


        ImageView imageView22= findViewById(R.id.imageView22);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }


    private ArrayList<Country> getListCountry() {
        customProgress.showProgress(this,"الرجاء الإنتظار",false);
        final ArrayList<Country>countries = new ArrayList<>();
        APIService apiService=  ServiceGenerator.createService(APIService.class);
        Call<ResultCountryModel> call= apiService.getAllCountry();
        call.enqueue(new Callback<ResultCountryModel>() {
            @Override
            public void onResponse(Call<ResultCountryModel> call, Response<ResultCountryModel> response) {

                customProgress.hideProgress();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    countries.addAll(response.body().getCountries());

                }
            }

            @Override
            public void onFailure(Call<ResultCountryModel> call, Throwable t) {
                customProgress.hideProgress();

                Common.showFalidDailog(getApplicationContext(), "حدث خطأ في الاتصال في السيرفر");
            }
        });


        return countries;
    }

    public void showDialogCountry( ){

        mBuilder = new AlertDialog.Builder(this );
        View mView = getLayoutInflater().inflate(R.layout.my_custom_dailog_countries, null);
        mRecyleCountry=mView.findViewById(R.id.rec_countries);
        mRecyleCountry.setLayoutManager(new LinearLayoutManager(this));
        mRecyleCountry.setHasFixedSize(true);

        countryAdapter.setmListCountry(listCountry);
        countryAdapter.notifyDataSetChanged();
        mRecyleCountry.setAdapter(countryAdapter);

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

    }

    @Override
    public void onClickItem(int position) {
        mSelectedCountry=listCountry.get(position);
//        Toast.makeText(getActivity(), ""+mSelectedCountry.getName(), Toast.LENGTH_SHORT).show();
        txt_country.setText(mSelectedCountry.getName());
        //save this country id
        Common.TEMP_REGISTER_COUNTRY_ID=mSelectedCountry.getId();
        dialog.dismiss();
    }
}
