package com.arapeak.adkya.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.adapters.CountryAdapter;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.listener.MyItemListener;
import com.arapeak.adkya.model.CurrentUserSaved;
import com.arapeak.adkya.model.country.Country;
import com.arapeak.adkya.model.country.ResultCountryModel;
import com.arapeak.adkya.model.updateuser.ResultUpdateUser;
import com.arapeak.adkya.ui.activites.HomeActivity;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class EditPersonalFragment extends Fragment implements MyItemListener {
       private TextView  tv_classroom , txt_country ,tv_change_pass;
   private TextInputEditText edt_name,edt_email;
   Button btn_save;
   
    Integer idOfSelectedRadioBtnClassRoom  =  null;
    AlertDialog dialog;
    AlertDialog.Builder mBuilder;

    RadioGroup radioGroupMaleFemale ;
    RadioButton radioMale ;
    RadioButton radioFemale ;

    RecyclerView mRecyleCountry;
    CountryAdapter countryAdapter;
    ArrayList<Country> listCountry;
    Country mSelectedCountry=new Country();
    Integer mGenderId=null;
    String imageUserPath=null;
    String realPathImageFromDevise;

    CircleImageView profile_image;


    CustomProgress customProgress= new CustomProgress();




    public EditPersonalFragment() {
        // Required empty public constructor

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity() , Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);


}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private static EditPersonalFragment Instance;

    public static EditPersonalFragment getInstance(){
        if (Instance==null) {
            Instance= new EditPersonalFragment();
        }
        return Instance;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_personal, container, false);
        initView(view);

        //Requesting storage permission
        requestStoragePermission();


        //init adapter
        countryAdapter = new CountryAdapter(getActivity()  );
        countryAdapter.setMyItemListener(this);
        listCountry=  getListCountry();



        //get previes information before update...
         getPrevoiusDataDefault();



        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCountry();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=edt_name.getText().toString();
                String email=edt_email.getText().toString();
                mGenderId=  getmGenderIdSlectedFromGenderGruop();

                if (mGenderId==null) {
               Toast.makeText(getActivity(), "يرجى اختيار نوع الجنس ", Toast.LENGTH_SHORT).show();
                }

                if (idOfSelectedRadioBtnClassRoom==null) {
                    Toast.makeText(getActivity(), "يرجى اختيار الصف ", Toast.LENGTH_SHORT).show();
                }else if (mSelectedCountry.getName()==null){
                Toast.makeText(getActivity(), "يرجى اختيار الدولة ", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty( email)){
                Toast.makeText(getActivity(), "يرجى كتابة الايميل ", Toast.LENGTH_SHORT).show();

            }  else if (TextUtils.isEmpty( name)){
                Toast.makeText(getActivity(), "يرجى كتابة الاسم ", Toast.LENGTH_SHORT).show();

             } else{
                    //get token
                    String token= Common.retrieveUserDataPreferance(getActivity()).getRememberToken();
                    APIService apiService=  ServiceGenerator.createService(APIService.class,token);

                    customProgress.showProgress(getActivity(),"الرجاء الإنتظار..",false);

                    /************image*************/
                    // create RequestBody instance from file

                    MultipartBody.Part img=null ;
                    //because the image not requirdd
                    if (realPathImageFromDevise!=null){
                        File file=  new File(realPathImageFromDevise);

                        File compressedImageFile =   compressFile(file);
                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("*/*"),
                                        compressedImageFile
                                );

                        // MultipartBody.Part is used to send also the actual file name
                     img =MultipartBody.Part.createFormData("img", file.getName(), requestFile);

                    }



/************image*************/


                    RequestBody classroom =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, String.valueOf(idOfSelectedRadioBtnClassRoom));



                    RequestBody gender =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM,String.valueOf(mGenderId));





                    RequestBody country =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, String.valueOf(mSelectedCountry.getId()));



                    RequestBody namee =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, name);



                    RequestBody emaill =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, email);



                    Call<ResultUpdateUser>  call= apiService.UpdateProfile(
                            namee,
                            emaill,
                            classroom,
                            country,
                            gender,
                            img);

                call.enqueue(new Callback<ResultUpdateUser>() {
                    @Override
                    public void onResponse(Call<ResultUpdateUser> call, Response<ResultUpdateUser> response) {
                        if (response.isSuccessful()){

                             Log.d("changeToken", "onResponse: "+response.body().getData().getImage());

                                CurrentUserSaved oldSavedUser=Common.retrieveUserDataPreferance(getActivity());
                                oldSavedUser.setName(response.body().getData().getName());
                                oldSavedUser.setClassId(Integer.parseInt(response.body().getData().getClassId()));
                                oldSavedUser.setGenderId(Integer.parseInt(response.body().getData().getGenderId()));
                                oldSavedUser.setCountry_name(response.body().getData().getAddress().getCountry().getName());
                                oldSavedUser.setCountry_id( response.body().getData().getAddress().getCountry().getId( ));
                                oldSavedUser.setEmail(response.body().getData().getEmail());
                                oldSavedUser.setImage(response.body().getData().getImage());

                            Common.saveUserDataPreferance(getActivity(),oldSavedUser);

                             customProgress.hideProgress();
                             Common.showSuccessDailog(getActivity(), " تم تحديث بيانات ملفك الشخصي بنجاح");

                             //update the data on the navigation view
                            updateNavegationHeader();

                        }else {

                            customProgress.hideProgress();
                            Common.showFalidDailog(getActivity(), "لم تتم العملية.. يرجى التأكد من البيانات المطلوبة");


                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUpdateUser> call, Throwable t) {

                        Common.showFalidDailog(getActivity(), "خطأ في الاتصال");

                        customProgress.hideProgress();

                    }
                });

                }
            }
        });



            tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity.replaceFragmentFromActivity(ChangePasswordFragment.getInstance());
            }
        });
        changeStatusBarColor();



        ImageView menubar= view.findViewById(R.id.menubar);

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //THIS IS FOR SHOW THE DrawerLayout
                //because the nav in the activity .. i use this
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

                drawer.openDrawer(GravityCompat.START);
            }
        });


        ImageView notImage= view.findViewById(R.id.imageView9);
        notImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.replaceFragmentFromActivity(NotificationFragment.getInstance());

            }
        });


        tv_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogClassRoom();
            }
        });



        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });

        return view;
    }

    private void updateNavegationHeader() {
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

        View  mHeaderView = navigationView.getHeaderView(0);
        TextView  mDrawerHeaderTitle =  mHeaderView.findViewById(R.id.textView13);
        CircleImageView profile_image_header =  mHeaderView.findViewById(R.id.profile_image_header);

        TextView  mDrawerHeaderClass =  mHeaderView.findViewById(R.id.textView134);
        mDrawerHeaderClass.setText(getClassStudentName(Common.retrieveUserDataPreferance(getActivity()).getClassId()));

        mDrawerHeaderTitle.setText(Common.retrieveUserDataPreferance(getActivity()).getName());
        Picasso.get().load(Common.retrieveUserDataPreferance(getActivity()).getImage()).into(profile_image_header);



    }
    private String getClassStudentName(Integer  classId) {


        if (classId==null) return null;

        String classStudentName=null;

        switch (classId) {
            case 1:
                classStudentName = getResources().getString(R.string.class1);
                break;

            case 2:
                classStudentName = getResources().getString(R.string.class2);

                break;

            case 3:
                classStudentName = getResources().getString(R.string.class3);


                break;

            case 4:
                classStudentName = getResources().getString(R.string.class4);


                break;

            case 5:
                classStudentName = getResources().getString(R.string.class5);


                break;

            case 6:
                classStudentName = getResources().getString(R.string.class6);

                break;

            default:
                return classStudentName;

        }
        return classStudentName;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case 100:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    realPathImageFromDevise = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    profile_image.setImageBitmap(BitmapFactory.decodeFile(realPathImageFromDevise));

                    break;

            }

        }

    }


    private File compressFile(File file) {
        File f =null;
        try {
            f =  new Compressor(getActivity())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .compressToFile(file);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return f;
    }

    private Integer getmGenderIdSlectedFromGenderGruop() {
        //get selected item from radio gruop
        int radioButtonID = radioGroupMaleFemale.getCheckedRadioButtonId();
        View radioButton = radioGroupMaleFemale.findViewById(radioButtonID);
        int idx = radioGroupMaleFemale.indexOfChild(radioButton);

        RadioButton r = (RadioButton) radioGroupMaleFemale.getChildAt(idx);
        if (r==null) return null;
        //get selected id
        int selectedId=r.getId();

        //get selected id

        switch (selectedId){
            case R.id.radio_male :
                mGenderId = 2;
                break;

            case R.id.radio_fmale :
                mGenderId = 3;
                break;

            default:
                mGenderId=null;

        }

        return mGenderId;
    }


    private void initView( View view ) {
        tv_classroom = view.findViewById(R.id.tv_classroom);
        txt_country = view.findViewById(R.id.txt_country);
        edt_name = view.findViewById(R.id.edt_name);
        edt_email = view.findViewById(R.id.edt_email);
        tv_change_pass= view.findViewById(R.id.tv_change_pass);
        radioGroupMaleFemale=view.findViewById(R.id.radioGroupMaleFemale);
        radioMale=view.findViewById(R.id.radio_male);
        radioFemale=view.findViewById(R.id.radio_fmale );
        btn_save=view.findViewById(R.id.btn_save);
        profile_image=view.findViewById(R.id.profile_image);

    }

    private void getPrevoiusDataDefault() {
        edt_email.setText( Common.retrieveUserDataPreferance(getActivity()).getEmail());
        edt_name.setText( Common.retrieveUserDataPreferance(getActivity()).getName());
        //todo .. get class and country and gender .. but before that ... store it in share preferance in register and login
        Integer classid= Common.retrieveUserDataPreferance(getActivity()).getClassId()  ;
        idOfSelectedRadioBtnClassRoom=classid;
        //get selected id
    if (classid!=null){
     switch (classid){
        case 1:
           tv_classroom.setText(R.string.class1);
            break;

        case 2:
            tv_classroom.setText(R.string.class2);
            break;

        case 3:
            tv_classroom.setText(R.string.class3);

            break;

        case 4:
            tv_classroom.setText(R.string.class4);

            break;

        case 5:
            tv_classroom.setText(R.string.class5);

            break;

        case 6:
            tv_classroom.setText(R.string.class6);

            break;

    }
}

    Integer genderId= Common.retrieveUserDataPreferance(getActivity()).getGenderId()  ;

    if (genderId!=null){

        if (genderId==2){
            mGenderId=2;
            radioMale.setChecked(true);
        }
        if (genderId==3){
            mGenderId=3;

            radioFemale.setChecked(true);

        }

    }

    //get image
        imageUserPath=Common.retrieveUserDataPreferance(getActivity()).getImage();
        if (imageUserPath!=null){
            //set to image src
            Picasso.get()
                    .load(imageUserPath)
                    .into(profile_image);

        }


        //restore country info

        mSelectedCountry.setId(Common.retrieveUserDataPreferance(getActivity()).getCountry_id());
        mSelectedCountry.setName(Common.retrieveUserDataPreferance(getActivity()).getCountry_name());

        if (mSelectedCountry.getName()!=null)
        txt_country.setText(mSelectedCountry.getName());
    }


    private ArrayList<Country> getListCountry() {
        final ArrayList<Country>countries = new ArrayList<>();
        APIService apiService=  ServiceGenerator.createService(APIService.class);
        Call<ResultCountryModel> call= apiService.getAllCountry();
        call.enqueue(new Callback<ResultCountryModel>() {
            @Override
            public void onResponse(Call<ResultCountryModel> call, Response<ResultCountryModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    countries.addAll(response.body().getCountries());

                }
            }

            @Override
            public void onFailure(Call<ResultCountryModel> call, Throwable t) {

                Common.showFalidDailog(getActivity(), "حدث خطأ في الاتصال في السيرفر");
            }
        });


        return countries;
    }


    public void showDialogClassRoom( ){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity()  );
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
                if (r==null) return;
                String selectedtext = r.getText().toString();
//                Toast.makeText(getActivity(), selectedtext, Toast.LENGTH_SHORT).show();

                //get selected id
                int selectedId=r.getId();


                //get selected id

                switch (selectedId){
                    case R.id.class1 :
                        idOfSelectedRadioBtnClassRoom = 1;
                        break;

                    case R.id.class2 :
                        idOfSelectedRadioBtnClassRoom = 2;
                        break;

                    case R.id.class3 :
                        idOfSelectedRadioBtnClassRoom = 3;
                        break;

                    case R.id.class4 :
                        idOfSelectedRadioBtnClassRoom = 4;
                        break;

                    case R.id.class5 :
                        idOfSelectedRadioBtnClassRoom = 5;
                        break;

                    case R.id.class6 :
                        idOfSelectedRadioBtnClassRoom = 6;
                        break;

                    default:
                        idOfSelectedRadioBtnClassRoom=null;

                }

//                Toast.makeText(getActivity(), idOfSelectedRadioBtnClassRoom+"", Toast.LENGTH_SHORT).show();
                tv_classroom.setText(selectedtext);
                dialog.dismiss();
            }
        });
        
        
    }

    private void changeStatusBarColor() {
        Window window =  getActivity().getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.statusbarColor));
    }

    public void showDialogCountry( ){
        mBuilder = new AlertDialog.Builder(getActivity() );
        View mView = getLayoutInflater().inflate(R.layout.my_custom_dailog_countries, null);
        mRecyleCountry=mView.findViewById(R.id.rec_countries);
        mRecyleCountry.setLayoutManager(new LinearLayoutManager(getActivity() ));
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
        dialog.dismiss();
    }


    @Override
    public void onResume() {
        super.onResume();
        //this is for handel back press button inside fragment
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    Fragment fragment= HomeFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();


                    return true;
                }
                return false;
            }
        });
    }
}
