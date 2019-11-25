package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.forgetpassword.ResultForgetPassword;
import com.arapeak.adkya.utils.CustomProgress;
import com.google.android.material.textfield.TextInputEditText;

public class RestorPassActivity extends AppCompatActivity {
    TextInputEditText edt_email;

    CustomProgress customProgress= new CustomProgress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

          edt_email= findViewById(R.id.edt_email);
        Button btn_resetPass= findViewById(R.id.btn_resetPass);
        btn_resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_email.getText().toString();

                if (!TextUtils.isEmpty(email)){
                    sendForgetPasswordRequest(email);

                }else {
                    Toast.makeText(RestorPassActivity.this, "الرجاء كتابة الإيميل بشكل صحيح", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendForgetPasswordRequest(String email) {
        customProgress.showProgress(this,"الرجاء الانتظار..",false);

        APIService apiService=  ServiceGenerator.createService(APIService.class);
        Call<ResultForgetPassword> call =  apiService.getForgetPassword(email.trim());
        call.enqueue(new Callback<ResultForgetPassword>() {
            @Override
            public void onResponse(Call<ResultForgetPassword> call, Response<ResultForgetPassword> response) {
                if (response.isSuccessful()){
                    customProgress.hideProgress();
                    if (response.body().getStatus()){
                        showSuccessDailog("تم ارسال رابط استرجاع كلمة المرور الى بريدك الإلكتروني");


                    }else {
                        showFalidDailog("لم يتم العثور على أيّ حسابٍ بهذا البريد الإلكتروني");

                    }


                }else {
                    customProgress.hideProgress();
                    showFalidDailog("يرجى التأكد من البريد الالكتروني");


                }
            }

            @Override
            public void onFailure(Call<ResultForgetPassword> call, Throwable t) {
                showFalidDailog("غير قادر على الإتصال..يرجى التأكد من البريد الالكتروني");
                customProgress.hideProgress();

            }
        });
    }

    private void showFalidDailog(String message) {
        final AlertDialog dialog;
        AlertDialog.Builder mBuilder;

        mBuilder = new AlertDialog.Builder(RestorPassActivity.this );
        View mView = getLayoutInflater().inflate(R.layout.no_question_dailog, null);
        TextView textView = mView.findViewById(R.id.textView);
        textView.setText(message);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();



        final Button btn_ok =  mView.findViewById(R.id.btn_ok);
        btn_ok.setText("رجوع");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showSuccessDailog(String message){
        final AlertDialog dialog;
        AlertDialog.Builder mBuilder;

        mBuilder = new AlertDialog.Builder(RestorPassActivity.this );
        View mView = getLayoutInflater().inflate(R.layout.dialog_sucuss_vervication_message, null);
        TextView textView = mView.findViewById(R.id.textView);
        textView.setText(message);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
