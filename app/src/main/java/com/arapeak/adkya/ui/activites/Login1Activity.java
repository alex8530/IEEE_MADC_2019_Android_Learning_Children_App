package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.Connectivity;

public class Login1Activity extends AppCompatActivity {

    Integer idClass  =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);


        Button btn_enter= findViewById(R.id.btn_enter);
        Button btn_register= findViewById(R.id.btn_register);
        TextView tv_login_visitor= findViewById(R.id.tv_login_visitor);



        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnectedWifi(getApplicationContext())) {
                    //check if user has login current !!

                    if (Common.retrieveUserDataPreferance(getApplicationContext()) == null) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    } else if (Common.retrieveUserDataPreferance(getApplicationContext()).isLogin()){
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    }
                }else {
                    Toast.makeText(Login1Activity.this, "الرجاء التحقق من اتصالك في الانترنت", Toast.LENGTH_SHORT).show();
                } 
            }

       
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Connectivity.isConnectedWifi(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    finish();

                  }else {
                Toast.makeText(Login1Activity.this, "الرجاء التحقق من اتصالك في الانترنت", Toast.LENGTH_SHORT).show();
            }



            }
        });


         tv_login_visitor.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (Connectivity.isConnectedWifi(getApplicationContext())) {
                     Common.IS_VISITOR=true;
                     showRequestClassIdDailog();
                 }else {
                     Toast.makeText(Login1Activity.this, "الرجاء التحقق من اتصالك في الانترنت", Toast.LENGTH_SHORT).show();

                 }

              }
         });


    }


    private void showRequestClassIdDailog(){

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this );
            View mView = getLayoutInflater().inflate(R.layout.my_custom_dailog, null);

            final Button btn_ok =  mView.findViewById(R.id.btn_ok);
            final Button btn_cancel =  mView.findViewById(R.id.btn_cancel);
            final RadioGroup radio_gruop =  mView.findViewById(R.id.radioGroup2);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //get selected item from radio gruop
                    int radioButtonID = radio_gruop.getCheckedRadioButtonId();
                    View radioButton = radio_gruop.findViewById(radioButtonID);
                    int idx = radio_gruop.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) radio_gruop.getChildAt(idx);


                    if (r !=null){
                        int selectedId=r.getId();


                        //get selected id

                        switch (selectedId){
                            case R.id.class1 :
                                idClass = 1;
                                break;

                            case R.id.class2 :
                                idClass = 2;
                                break;

                            case R.id.class3 :
                                idClass = 3;
                                break;

                            case R.id.class4 :
                                idClass = 4;
                                break;

                            case R.id.class5 :
                                idClass = 5;
                                break;

                            case R.id.class6 :
                                idClass = 6;
                                break;

                            default:
                                idClass=null;

                        }


//                        goToHome();
                        if (idClass!= null){
                            dialog.dismiss();
                            Common.VISITOR_CLASS_SELECTED=idClass;
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                           finish();
                        }else {
                            Toast.makeText(Login1Activity.this, "الرجاء اختيار الصف", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Toast.makeText(Login1Activity.this, "الرجاء اختيار الصف", Toast.LENGTH_SHORT).show();
                    }

                }
            });



    }

}
