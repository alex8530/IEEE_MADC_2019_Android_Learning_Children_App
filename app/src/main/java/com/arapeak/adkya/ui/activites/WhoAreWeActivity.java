package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.aboutus.ResultAboutUs;

public class WhoAreWeActivity extends AppCompatActivity {
TextView tv_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_are_we);

        LinearLayout linearlayout_back= findViewById(R.id.linearlayout_back);
        linearlayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        tv_description=findViewById(R.id.tv_description);
         tv_description.setText("Hi , I am Alex .. I am Google Certified Android Developer And Computer Engineer");
        //get disc from internet
    //    sendAboutAsRequst();

    }

    private void sendAboutAsRequst() {
        APIService apiService= ServiceGenerator.createService(APIService.class);
        Call<ResultAboutUs> call =  apiService.getAboutUs();
        call.enqueue(new Callback<ResultAboutUs>() {
            @Override
            public void onResponse(Call<ResultAboutUs> call, Response<ResultAboutUs> response) {
                if (response.isSuccessful()){
                    String desc=response.body().getData().getContent().getDescription();
                    tv_description.setText(Html.fromHtml( desc ));

                }
            }

            @Override
            public void onFailure(Call<ResultAboutUs> call, Throwable t) {
                Toast.makeText(WhoAreWeActivity.this, "حدث خطأ في الاتصال", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
