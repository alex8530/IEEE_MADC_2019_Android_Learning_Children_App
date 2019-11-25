package com.arapeak.adkya.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arapeak.adkya.R;
import com.arapeak.adkya.model.CurrentUserSaved;

import com.arapeak.adkya.model.getMaterial.MaterialData;
import com.arapeak.adkya.model.getSecondMaterial.SecondMaterialData;
import com.arapeak.adkya.model.getSecondMaterial.Section;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Common {
    public static final String KEY_USER_PREF="USER_KEY";


    public static void saveUserDataPreferance(Context context, CurrentUserSaved user){
        SharedPreferences mPrefs = context.getSharedPreferences("preferanceFileUser",MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(KEY_USER_PREF, json);
        prefsEditor.apply();
    }
    public static CurrentUserSaved retrieveUserDataPreferance(Context context   ){
        SharedPreferences mPrefs = context.getSharedPreferences("preferanceFileUser",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString(KEY_USER_PREF, "");
        CurrentUserSaved userSaved=  gson.fromJson(json, CurrentUserSaved.class  );
        if (userSaved ==null){
            //that mean no user save before ...
            return new CurrentUserSaved();

        }else {
            return userSaved;
        }
    }


    public static  Integer classroomId=null;
    public static  Integer genderId=null;

    public static   String TEMP_REGISTER_TOKEN ;


    public static  Integer country_from_scoial_activity=null;




  public static MaterialData CurrentMaterial= new MaterialData();
  public static Section CurrentSection= new Section();
  public static SecondMaterialData CurrentSecondMaterial= new SecondMaterialData();




   public static int CURRENT_ADAPTER_SECTION_POSITION=0;
   public static int CURRENT_ADAPTER_SECTION_PARENT_POSITION=0;
   public static ArrayList<SecondMaterialData> CURRENT_List_MATERIAL_DATA= new ArrayList<>();



    public static boolean IS_VISITOR = false;
    public static Integer  VISITOR_CLASS_SELECTED = null;



    public static  void showSuccessDailog(Context context , String message){
        final AlertDialog dialog;
        AlertDialog.Builder mBuilder;

        mBuilder = new AlertDialog.Builder(context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View mView = inflater.inflate(R.layout.dialog_sucuss_vervication_message, null);
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

    public static void showFalidDailog(Context context , String message) {
        final AlertDialog dialog;
        AlertDialog.Builder mBuilder;

        mBuilder = new AlertDialog.Builder(context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View mView = inflater.inflate(R.layout.no_question_dailog, null);
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




    public static boolean IS_REGISTER_OR_LOGIN_USING_SOCAIL=false;
    public static  String TEMP_REGISTER_NAME = null;
    public static   Integer TEMP_REGISTER_COUNTRY_ID = null;
    public static String TEMP_REGISTER_EMAIL=null;








    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }




    public static String email_registered_saved;
    public static String pass_registered_saved;

}
