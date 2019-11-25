package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.google.android.material.textfield.TextInputEditText;

public class ContactUsActivity extends AppCompatActivity {

    TextInputEditText txt_message,edt_email,edt_name;
    Button btn_register;
    CustomProgress customProgress= new CustomProgress();

    AlertDialog dialog;
    AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        txt_message= findViewById(R.id.txt_message);
        edt_email= findViewById(R.id.edt_email);
        edt_name= findViewById(R.id.edt_name);
        btn_register= findViewById(R.id.btn_register);


        LinearLayout linearlayout_back= findViewById(R.id.linearlayout_back);
        linearlayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        edt_name.getText().toString().equals("")  ||
                                edt_email.getText().toString().equals("")||
                                txt_message.getText().toString().equals("")

                        ){
                    Toast.makeText(ContactUsActivity.this, "يرجى ملئ البيانات", Toast.LENGTH_SHORT).show();
                }else {
                    sendContactUsRequestApi();

                }
            }
        });

    }

    private void showSuccessDailog(){
        mBuilder = new AlertDialog.Builder(this );
        View mView = getLayoutInflater().inflate(R.layout.dialog_sucuss_send_message, null);


        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                clearFields();
            }
        });
    }

    private void clearFields() {
        edt_name.setText("");
        edt_email.setText("");
        txt_message.setText("");
    }

    private void sendContactUsRequestApi() {
        customProgress.showProgress(this,"الرجاء الإنتظار",false);

        APIService apiService= ServiceGenerator.
                createService(APIService.class,Common.retrieveUserDataPreferance(this).getRememberToken());

        Call<ResponseBody> call = apiService.SendContactUsRequest(
                "8",
                edt_name.getText().toString(),
                edt_email.getText().toString(),
                txt_message.getText().toString()
        );
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,Response<ResponseBody> response) {
                if (response.isSuccessful()){
                     customProgress.hideProgress();                  showSuccessDailog();
                }else {
                     customProgress.hideProgress();
                    Toast.makeText(ContactUsActivity.this, "لم يتم ارسال الطلب , يرجى التأكد من البيانات", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customProgress.hideProgress();

                Toast.makeText(ContactUsActivity.this, "هناك مشكلة في الاتصال", Toast.LENGTH_SHORT).show();
            }
        });
        
    }




}
