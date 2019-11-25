package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.activateAccount.ActivateAccount;
import com.arapeak.adkya.model.resendActivationCode.ResendActivationCode;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.google.android.material.textfield.TextInputEditText;

public class ActivateActivity extends AppCompatActivity {
     TextInputEditText edt_code;
    Button btn_activate ;
    TextView tv_resend_code ;
    AlertDialog dialog;
    AlertDialog.Builder mBuilder;
    CustomProgress customProgress= new CustomProgress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtivate);

        edt_code= findViewById(R.id.edt_code);
        btn_activate= findViewById(R.id.btn_activate);
        tv_resend_code=findViewById(R.id.tv_resend_code);

        btn_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = edt_code.getText().toString().trim();

                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(ActivateActivity.this, "يرجى ادخال الكود", Toast.LENGTH_SHORT).show();
                } else {
                    customProgress.showProgress(ActivateActivity.this,"جاري التفعيل..",false);
                    APIService apiService = ServiceGenerator.createService(APIService.class);
                    Call<ActivateAccount> call =  apiService.SendActivateRequest(code,RegisterActivity.REGISTER_EMAIL);
                    call.enqueue(new Callback<ActivateAccount>() {
                        @Override
                        public void onResponse(Call<ActivateAccount> call, Response<ActivateAccount> response) {

                            customProgress.hideProgress();
                            if (response.isSuccessful()){

                                if (response.body().getStatus()){
                                    //store register email and pass

                                    //to be sure that the current user is not visitor
                                    Common.IS_VISITOR=false;

                                    Common.TEMP_REGISTER_TOKEN=response.body().getData().getApiToken();
                                    showSuccessDailog();

                                }else {
                                    Toast.makeText(ActivateActivity.this, "يرجى التأكد من الكود المدخل", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ActivateAccount> call, Throwable t) {
                            Toast.makeText(ActivateActivity.this, "يرجى التحقق من الانترنت", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


        tv_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendResendVervicationCode();
            }
        });

    }


    private void sendResendVervicationCode() {
        customProgress.showProgress(this,"الرجاء الإنتظار",false);


        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<ResendActivationCode> call =  apiService.SendResendActivationCodeRequest(RegisterActivity.REGISTER_EMAIL);
        call.enqueue(new Callback<ResendActivationCode>() {
            @Override
            public void onResponse(Call<ResendActivationCode> call, Response<ResendActivationCode> response) {
                 customProgress.hideProgress();                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isStatus()){

                        showResendCodeDailog();
                    }else {
                        Toast.makeText(ActivateActivity.this, "البريد الالكتروني غير موجود", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResendActivationCode> call, Throwable t) {
                Toast.makeText(ActivateActivity.this, "يرجى التحقق من الانترنت", Toast.LENGTH_SHORT).show();
                 customProgress.hideProgress();            }
        });
    }

    private void showResendCodeDailog() {
        mBuilder = new AlertDialog.Builder(ActivateActivity.this );
        View mView = getLayoutInflater().inflate(R.layout.dialog_resend_code_message, null);


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

    private void showSuccessDailog(){
        mBuilder = new AlertDialog.Builder(ActivateActivity.this );
        View mView = getLayoutInflater().inflate(R.layout.dialog_sucuss_vervication_message, null);


        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                clearFields();
                startActivity(new Intent(getApplicationContext(),Register1Activity.class));
                finish();
            }
        });
    }

}
