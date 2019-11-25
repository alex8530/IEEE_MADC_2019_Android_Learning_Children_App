package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import id.zelory.compressor.Compressor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
 import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.updateuser.ResultUpdateUser;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;

import java.io.File;
 import java.io.IOException;

public class Register3Activity extends AppCompatActivity {
      String realPathImage;

    private static final String TAG = "Register3Activity";
    ImageView profile_image;
    CustomProgress customProgress= new CustomProgress();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        //Requesting storage permission
        requestStoragePermission();


        Button btn_register= findViewById(R.id.btn_register);



        profile_image= findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (realPathImage==null) {
                    Toast.makeText(getApplicationContext(), "يرجى اختيار صورة", Toast.LENGTH_SHORT).show();
                }else if (Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL){

                    sendUpdateProfileRequest();
                }else {
                    uploadImageAndData( );
                }
            }
        });

    }

    private void sendUpdateProfileRequest() {

         customProgress.showProgress(this,"جاري التسجيل..",false);
        APIService apiService= ServiceGenerator.createService
                (APIService.class, Common.TEMP_REGISTER_TOKEN);

        /************image*************/
        // create RequestBody instance from file


        File file=  new File(realPathImage);

        File compressedImageFile =   compressFile(file);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("*/*"),
                        compressedImageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part img =
                MultipartBody.Part.createFormData("img", file.getName(), requestFile);


        // add another part within the multipart request
        String classString = String.valueOf(Common.classroomId);
        String genderString = String.valueOf(Common.genderId);
        String emailString = String.valueOf(Common.TEMP_REGISTER_EMAIL);
        String CountryString = String.valueOf(Common.TEMP_REGISTER_COUNTRY_ID);
        String nameString = String.valueOf(Common.TEMP_REGISTER_NAME);

        RequestBody classroom =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, classString);



        RequestBody gender =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, genderString);


        RequestBody email =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, emailString);


        RequestBody country =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, CountryString);


        RequestBody name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, nameString);



        Call<ResultUpdateUser> call =  apiService.UpdateProfile(
                name,email,classroom,country,gender,img
        );


        call.enqueue(new Callback<ResultUpdateUser>() {
            @Override
            public void onResponse(Call<ResultUpdateUser> call, Response<ResultUpdateUser> response) {

                customProgress.hideProgress();
                if (response.isSuccessful()){
                    Toast.makeText(Register3Activity.this, " تم اكمال بيانات ملفك الشخصي بنجاح", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: " + response.body());
                    //i send to login activity<< because i need to enter from this activity because i store the data and save current user from it


                    Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();


                }else {

                    try {
                        Log.d(TAG, "onResponse: "+ response.errorBody().string());
                        Toast.makeText(Register3Activity.this, "لم تتم العملية.. يرجى اختيار صورة أصغر حجما"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultUpdateUser> call, Throwable t) {
                Toast.makeText(Register3Activity.this, "العملية فشلت", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png","image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,100);
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }



    private void uploadImageAndData(){

        customProgress.showProgress(this,"جاري التسجيل..",false);

        APIService apiService= ServiceGenerator.createService
                (APIService.class, Common.TEMP_REGISTER_TOKEN);

        /************image*************/
      // create RequestBody instance from file


        File file=  new File(realPathImage);

        File compressedImageFile =   compressFile(file);
        RequestBody requestFile =
                RequestBody.create(
                      MediaType.parse("*/*"),
                        compressedImageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part img =
                MultipartBody.Part.createFormData("img", file.getName(), requestFile);



/************image*************/

        // add another part within the multipart request
        String classString = String.valueOf(Common.classroomId);
        String genderString = String.valueOf(Common.genderId);

        RequestBody classroom =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, classString);



        RequestBody gender =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, genderString);



       Call<ResponseBody> call =  apiService.completeProfile(classroom,gender,img);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                customProgress.hideProgress();

                if (response.isSuccessful()){
                     Toast.makeText(Register3Activity.this, " تم اكمال بيانات ملفك الشخصي بنجاح", Toast.LENGTH_SHORT).show();

                     //i send to login activity<< because i need to enter from this activity because i store the data and save current user from it

                    //save these data
                    Common.email_registered_saved=RegisterActivity.REGISTER_EMAIL;
                    Common.pass_registered_saved=RegisterActivity.REGISTER_PASS;
                    Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();


                 }else {

                     try {
                        Toast.makeText(Register3Activity.this, "لم تتم العملية.. يرجى اختيار صورة أصغر حجما"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register3Activity.this, "العملية فشلت", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private File compressFile(File file) {
        File f =null;
        try {
            f =  new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .compressToFile(file);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return f;
    }

    @Override
    protected void   onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 100:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    realPathImage = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    profile_image.setImageBitmap(BitmapFactory.decodeFile(realPathImage));
                    break;

            }
    }


}
