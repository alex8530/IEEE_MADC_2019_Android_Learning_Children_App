package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.utils.Common;

public class Register1Activity extends AppCompatActivity {




    TextView edt_school;
    Integer id  =  null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Button btn_register= findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id!= null){
                    Common.classroomId=id;
                    startActivity(new Intent(getApplicationContext(),Register2Activity.class));
//                finish();
                }else {
                    Toast.makeText(Register1Activity.this, "الرجاء اختيار الصف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView imageView22= findViewById(R.id.imageView22);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Login1Activity.class));
                   finish();
            }
        });

        edt_school = findViewById(R.id.txt_school);
        edt_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Login1Activity.class));
        finish();
    }

    public void showDialog( ){
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


                //todo check if not null here>>>
                RadioButton r = (RadioButton) radio_gruop.getChildAt(idx);
                String selectedtext = r.getText().toString();
                int selectedId=r.getId();


                //get selected id

                switch (selectedId){
                    case R.id.class1 :
                        id = 1;
                        break;

                    case R.id.class2 :
                        id = 2;
                        break;

                    case R.id.class3 :
                        id = 3;
                        break;

                    case R.id.class4 :
                        id = 4;
                        break;

                    case R.id.class5 :
                        id = 5;
                        break;

                    case R.id.class6 :
                        id = 6;
                        break;

                   default:
                       id=null;

                }

                edt_school.setText(selectedtext);
                dialog.dismiss();
            }
        });


    }

}
