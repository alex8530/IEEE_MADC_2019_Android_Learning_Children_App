package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.arapeak.adkya.model.CurrentUserSaved;
import com.arapeak.adkya.model.country.Country;
import com.arapeak.adkya.model.country.ResultCountryModel;
import com.arapeak.adkya.model.login.APIErrorLogin;
import com.arapeak.adkya.model.login.Datum;
import com.arapeak.adkya.model.register.Data;
import com.arapeak.adkya.model.register.ResultRegisterModel;
import com.arapeak.adkya.model.socialLogin.ResultLoginSocialModel;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.arapeak.adkya.utils.ErrorUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements MyItemListener {
     TextInputEditText edt_mail,edt_name,edt_pass;
    Button btn_register;
    TextView txt_country;
    Integer id  =  null;
    RecyclerView mRecyleCountry;
    CountryAdapter countryAdapter;
    ArrayList<Country> listCountry;
    CustomProgress customProgress= new CustomProgress();


    AlertDialog dialog;
    AlertDialog.Builder mBuilder;
    Country selectedCountry=null;

    public static String REGISTER_EMAIL=null;
    public static String REGISTER_PASS=null;

    ImageView login_fb_image,login_google_image;
    LoginButton loginButton;
    CallbackManager callbackManager;
    String token="";

    GoogleSignInClient mGoogleSignInClient;

    private final int RC_SIGN_IN = 666;

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init
          btn_register= findViewById(R.id.btn_register);
          edt_mail= findViewById(R.id.edt_mail);
          edt_name= findViewById(R.id.edt_name);
          edt_pass= findViewById(R.id.edt_pass);
        edt_pass.setTransformationMethod(new PasswordTransformationMethod());

        TextView txt_sign_in_account= findViewById(R.id.txt_sign_in_account);
        txt_sign_in_account.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent
                       = new Intent(getApplicationContext(),LoginActivity.class);
               intent.putExtra("is_from_txt_sign_in_account",true);

               startActivity(intent);
                finish();
           }
       });

         //init adapter
        countryAdapter = new CountryAdapter(this  );
        countryAdapter.setMyItemListener(this);


        listCountry=  getListCountry();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              sendRgisterRequest();
                sendEnhanceRgisterRequest();

            }
        });

        txt_country = findViewById(R.id.txt_country);
        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialog();
             }
        });



        initFacebookLogin();
        initGoogleLogin();



        login_fb_image=findViewById(R.id.login_fb_image);
        login_google_image=findViewById(R.id.login_google_image);

        login_fb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               signOutSoical();

                loginButton.performClick(); // because i want to change facebook shape<<
                //so i create another button and hide origen fb button
            }
        });



        login_google_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signOutSoical();
                signInGoogle();
            }
        });





    }

    public void signOutSoical(){

        //logout from facebook to avoid the logout nutton appear when you login and then click on register>> the logout button will apear so>>
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            LoginManager.getInstance().logOut();
        }



        signOutFromGoogle();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Login1Activity.class));
        finish();
    }

    private void signOutFromGoogle() {


        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        if (googleSignInClient!=null){

            googleSignInClient.signOut();
        }

    }

    private void initFacebookLogin() {


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        loginButton = findViewById(R.id.login_fb_button);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                token =   loginResult.getAccessToken().getToken();


                //**************** i dont need the graph because i dont need the data like name>> or email>>> only oken
//                //store and save some data that i need it later
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.d("onSuccess:LoginActivity", response.toString());
//
//
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
                /******************************************/


                sendEnhancedLoginScoicalRequest(token,"facebook");

            }

            @Override
            public void onCancel() {
              
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(RegisterActivity.this, "حدث خطا بسبب ضعف الاتصال بالانترنت", Toast.LENGTH_SHORT).show();

            }
        });

    }

//    In this adkya for getting login response we are not going to use the callback methods with LoginButton instead we use the AccessTokenTracker class.

    private void sendEnhancedLoginScoicalRequest(String token, String type) {
        customProgress.showProgress(this,"الرجاء الإنتظار",false);

        String url = "https://adkya.com/api/v1/";

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        APIService apiService=  retrofit.create(APIService.class);


        Call<ResultLoginSocialModel> call = apiService.SendSocialRequest(token,type);
        call.enqueue(new Callback<ResultLoginSocialModel>() {
            @Override
            public void onResponse(Call<ResultLoginSocialModel> call, Response<ResultLoginSocialModel> response) {
                 customProgress.hideProgress();

                if (response.isSuccessful()){

                    //to be sure that the current user is not visitor
                    Common.IS_VISITOR=false;

                    Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL=true;

                    com.arapeak.adkya.model.socialLogin.Data data = response.body().getData();

                    boolean isNotComplete=false;

                    if (data.getUser().getIsCompleted() ==null) {
                        isNotComplete=true;
                    }else if (data.getUser().getIsCompleted()==0){
                        isNotComplete=true;
                    }

                    if (isNotComplete){
                        //note that <<<>> complete is set to 1 if gender and class were not null
                        //Common.isCompleteProfileCountry=false;
                        //store register email
                        RegisterActivity.REGISTER_EMAIL= data.getUser().getEmail();

                        //this is need for registerActivity3
                        Common.TEMP_REGISTER_TOKEN=data.getApiToken();


                        //save these data for register3 activity .. i need it temporory


                        Common.TEMP_REGISTER_EMAIL=data.getUser().getEmail();
                        Common.TEMP_REGISTER_NAME=data.getUser().getName();


                        Intent intent= new Intent(getApplicationContext(), Register1Activity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        //store this if the user has complete his registeration process

//                        Common.isCompleteProfileCountry=true;

                      
                        CurrentUserSaved userSaved = new CurrentUserSaved();
                        userSaved.setIsLogin(true);
                        userSaved.setRememberToken(data.getApiToken());
                        userSaved.setName(data.getUser().getName());
                        userSaved.setEmail(data.getUser().getEmail());
                        userSaved.setId(data.getUser().getId());
                        userSaved.setClassId(data.getUser().getClassId());
                        userSaved.setGenderId(data.getUser().getGenderId());
                        userSaved.setImage(data.getUser().getImage());


                        //check if address is not null
                        if (data.getUser().getAddress().getCountry()!=null){

                            userSaved.setCountry_name(data.getUser().getAddress().getCountry().getName());
                            userSaved.setCountry_id(data.getUser().getAddress().getCountry().getId());

                        }
                        Common.saveUserDataPreferance(getApplicationContext(), userSaved);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }else {

                    Common.showFalidDailog(RegisterActivity.this, "البريد الاكتروني مسجل من قبل ! ");
 


                }
            }

            @Override
            public void onFailure(Call<ResultLoginSocialModel> call, Throwable t) {
                 customProgress.hideProgress();

                Toast.makeText(RegisterActivity.this,  "خطأ في الاتصال" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initGoogleLogin() {
       // signOutFromGoogle();

         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestServerAuthCode("87183878812-is1tomusfoi7iaj65due40os3elml3ol.apps.googleusercontent.com",true)
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signInGoogle() {
 
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "handleSignInResult onActivityResult: ");
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else{
            //If not request code is RC_SIGN_IN it must be facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }





    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {


        if (completedTask.isSuccessful()){
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                String authCode = account.getServerAuthCode();
                //get access token from code auth <>> and send to api
                getAccessTokenFromCode(authCode);



            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(TAG, " handleSignInResult signInResult:failed code=" + e.getStatusCode());

            }
        }

    }

    private void getAccessTokenFromCode(String authCode) {


        String url = "https://www.googleapis.com/oauth2/v4/";

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        APIService apiService=  retrofit.create(APIService.class);



        //this data come from google api console
        Call<ResponseBody> call =   apiService.getToken(
                "authorization_code",
                getResources().getString(R.string.google_client_id),
                "UEQ76OjBzO4sppJDS13eLkw2" ,
                "",
                authCode
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {



                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        //because the web developer need the access token >> not the id token>>
                        String access_token = jsonObject.get("access_token").toString();


                        //store this token to check befor login later
                        SharedPreferences.Editor editor = getSharedPreferences("google", MODE_PRIVATE).edit();
                        editor.putString("token_google", access_token);
                        editor.apply();

                        sendEnhancedLoginScoicalRequest(access_token, "google");
                    }catch (Exception e){

                    }


                }else {
                    try {
                        Log.d(TAG, " handleSignInResult onResponse: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    
    

    public void showDialog( ){
          mBuilder = new AlertDialog.Builder(this );
        View mView = getLayoutInflater().inflate(R.layout.my_custom_dailog_countries, null);
        mRecyleCountry=mView.findViewById(R.id.rec_countries);
        mRecyleCountry.setLayoutManager(new LinearLayoutManager(this ));
        mRecyleCountry.setHasFixedSize(true);

        countryAdapter.setmListCountry(listCountry);
        countryAdapter.notifyDataSetChanged();
        mRecyleCountry.setAdapter(countryAdapter);

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

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

            }
        });


        return countries;
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    private void sendRgisterRequest() {
        if (selectedCountry==null){

            Toast.makeText(this, "يرجى اختيار الدولة ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty( edt_mail.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الايميل ", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty( edt_name.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الاسم ", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty( edt_pass.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الباسورد ", Toast.LENGTH_SHORT).show();

        }
        else {
            APIService apiService=  ServiceGenerator.createService(APIService.class);
            Call<ResultRegisterModel>  call =  apiService.createUser(
                    edt_name.getText().toString().trim(),
                    edt_mail.getText().toString().trim(),
                    String.valueOf(selectedCountry.getId()),
                    edt_pass.getText().toString().trim()
            );

            call.enqueue(new Callback<ResultRegisterModel>() {
                @Override
                public void onResponse(Call<ResultRegisterModel> call, Response<ResultRegisterModel> response) {
                    if (response.isSuccessful()){

                        // save token and data of user

                        Data  data= response.body().getData() ;
                        Common.TEMP_REGISTER_TOKEN=data.getApiToken();

//                        Toast.makeText(RegisterActivity.this, "تمت عملية التسجيل بنجاح !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Register1Activity.class));
                        finish();

                    }else {
                        try {
                            Toast.makeText(RegisterActivity.this,
                                    "البيانات المدخلة غير صالحة"+response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onFailure(Call<ResultRegisterModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,
                            "حدث خطأ بالاتصال ..", Toast.LENGTH_SHORT).show();
                }
            });
        }
 }
    private void sendEnhanceRgisterRequest() {
        if (selectedCountry==null){

            Toast.makeText(this, "يرجى اختيار الدولة ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty( edt_mail.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الايميل ", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty( edt_name.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الاسم ", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty( edt_pass.getText().toString())){
            Toast.makeText(this, "يرجى كتابة الباسورد ", Toast.LENGTH_SHORT).show();

        }else if (!isValidEmailId(edt_mail.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "يرجى التأكد من البريد الإلكتروني", Toast.LENGTH_SHORT).show();

        }else if (edt_name.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "يجب أن يكون طول النص الاسم على الأقل 3 حروفٍ/حرفًا", Toast.LENGTH_SHORT).show();

        }else if (edt_pass.getText().toString().trim().length()<6){
            Toast.makeText(getApplicationContext(),    "يجب أن يكون طول النص كلمة المرور على الأقل 6 حروفٍ/حرفًا", Toast.LENGTH_SHORT).show();

        }
        else {
            customProgress.showProgress(this,"الرجاء الإنتظار",false);
         String url = "https://adkya.com/api/v1/";

            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            APIService apiService=  retrofit.create(APIService.class);



            Call<ResultRegisterModel>  call =  apiService.createUser(
                    edt_name.getText().toString().trim(),
                    edt_mail.getText().toString().trim(),
                    String.valueOf(selectedCountry.getId()),
                    edt_pass.getText().toString().trim()
            );

            call.enqueue(new Callback<ResultRegisterModel>() {
                @Override
                public void onResponse(Call<ResultRegisterModel> call, Response<ResultRegisterModel> response) {
                    if (response.isSuccessful()){
                         customProgress.hideProgress();

                       //store register email and pass
                        REGISTER_EMAIL= edt_mail.getText().toString().trim();
                        REGISTER_PASS= edt_pass.getText().toString().trim();

                        Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL=false;


                        //you can be sure if check if user activate or not here>> as you want
                         Intent intent= new Intent(getApplicationContext(), ActivateActivity.class);
                         startActivity(intent);
                         finish();

                    }else {
                         customProgress.hideProgress();


                        // parse the response body …
                        APIErrorLogin error = ErrorUtils.parseError(response);
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
//                       Log.d("handelLogin", error.getMessage());
                        if (error.getData()!=null){
                            Datum datum =  error.getData().get(0);
                            Log.d("handelLogin", datum.getEmail().get(0));
                            Toast.makeText(RegisterActivity.this,
                                    datum.getEmail().get(0), Toast.LENGTH_SHORT).show();


                        }

                    }
                }
                @Override
                public void onFailure(Call<ResultRegisterModel> call, Throwable t) {
                     customProgress.hideProgress();

                    Toast.makeText(RegisterActivity.this,
                            "حدث خطأ بالاتصال ..يرجى تسجيل الخروج والمحاولة مرة آخرى", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    public void onClickItem(int position) {
        selectedCountry=listCountry.get(position);
        txt_country.setText(selectedCountry.getName());
        dialog.dismiss();
    }



    
    
    
    
    
}
