package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.CurrentUserSaved;
import com.arapeak.adkya.model.login.APIErrorLogin;
import com.arapeak.adkya.model.login.Datum;
import com.arapeak.adkya.model.socialLogin.Data;
import com.arapeak.adkya.model.socialLogin.ResultLoginSocialModel;
import com.arapeak.adkya.model.login.ResultLoginModel;
import com.arapeak.adkya.model.resendActivationCode.ResendActivationCode;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.arapeak.adkya.utils.ErrorUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
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
import java.util.Arrays;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edt_mail, edt_pass ;
    private static final String TAG = "LoginActivity";
    AlertDialog dialog;
    AlertDialog.Builder mBuilder;




    CallbackManager callbackManager;
    String token="";

    ImageView login_fb_image,login_google_image;

    GoogleSignInClient mGoogleSignInClient;

//  SignInButton sign_in_google_button;
    LoginButton loginButton;

    String access_token_google=null;

    CustomProgress customProgress= new CustomProgress();

    private final int RC_SIGN_IN = 666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


        setContentView(R.layout.activity_login);

        checkIfUserLoginSocial();



        initFacebookLogin();
        initGoogleLogin();


        Button btn_enter= findViewById(R.id.btn_register);
        TextView tv_forget_pass= findViewById(R.id.txt_forget_pass);

        TextView  txt_create_new_account= findViewById(R.id.txt_create_new_account);
        txt_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

            }
        });

        edt_mail= findViewById(R.id.edt_mail);
        edt_pass= findViewById(R.id.edt_pass);
        edt_pass.setTransformationMethod(new PasswordTransformationMethod());
        TextView   textView7= findViewById(R.id.textView7);
        login_fb_image=findViewById(R.id.login_fb_image);
        login_google_image=findViewById(R.id.login_google_image);


        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEnhanceLoginRequest();

            }
        });

        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),RestorPassActivity.class));



            }
        });

        login_fb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if login befor
                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                if (accessToken!=null){
                    signOutFacebook();
                }else{
                    loginButton.performClick(); // because i want to change facebook shape<<
                    //so i create another button and hide origen fb button
                }

            }
        });



        login_google_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount  mGoogleSignInClient =  GoogleSignIn.getLastSignedInAccount(getApplicationContext());
               if (mGoogleSignInClient!=null){
                   //thats mean google is sgin in and we need get that token so
                   SharedPreferences prefs = getSharedPreferences("google", MODE_PRIVATE);
                   String token = prefs.getString("token_google", null);//"No name defined" is the default value.

                   if (token!=null)
                       sendEnhancedLoginScoicalRequest(token, "google");

               }else {
                   signInGoogle();

               }

            }
        });

    }



    private void checkIfUserLoginSocial() {

        //i do not need to check if o come from   TextView txt_sign_in_account  from Register activity
        // and maybe i shold sign out all user ( google, facebook, current user)
        //and i will use intent to know where i come from>>>

        boolean is_from=false;
        if (getIntent().getExtras()!=null) {
            if (getIntent().getBooleanExtra("is_from_txt_sign_in_account",false) ){

                if (getIntent().getBooleanExtra("is_from_txt_sign_in_account",false)==true){
                    is_from=true;
                }
            }
        }
        if (is_from){

            //do nothing>> maybe you want to sign out
            signOutFacebook();

        }else {
            //google
            GoogleSignInAccount  mGoogleSignInClient =  GoogleSignIn.getLastSignedInAccount(this);

            //facebook
            AccessToken accessToken = AccessToken.getCurrentAccessToken();

            if (mGoogleSignInClient != null ) {
                //thats mean google is sgin in and we need get that token so
                SharedPreferences prefs = getSharedPreferences("google", MODE_PRIVATE);
                String token = prefs.getString("token_google", null);//"No name defined" is the default value.

                if (token!=null){
                    sendEnhancedLoginScoicalRequest(token, "google");
                    return;
                }


            }else if (accessToken != null ){
                sendEnhancedLoginScoicalRequest(accessToken.getToken(),"facebook");

            }else if (Common.pass_registered_saved!=null && Common.email_registered_saved!=null){
                //that mean is register and should sign in


                sendLoginAfterSignUp(Common.email_registered_saved,Common.pass_registered_saved);

            }

        }

    }


    private void initGoogleLogin() {

        Log.d(TAG, "google initGoogleLogin: ");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestServerAuthCode("87183878812-is1tomusfoi7iaj65due40os3elml3ol.apps.googleusercontent.com",true)
                .build();


          mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signInGoogle() {
        Log.d(TAG, "handleSignInResult signInGoogle: ");

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);



    }


    private void initFacebookLogin() {
        Log.d(TAG, "initFacebookLogin: ");


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
                Log.d(TAG, "onSuccess:token1: --"+token+"--");

              //  sendLoginFacebookRequest(token);
                 Log.d(TAG, "onSuccess: name : " +Profile.getCurrentProfile().getFirstName());
                Log.d(TAG, "onSuccess: id : " +Profile.getCurrentProfile().getId());

//
////                //store and save some data that i need it later
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.d("onSuccess:LoginActivity", response.toString());
//
//                                sendEnhancedLoginScoicalRequest(token,"facebook");
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();

             sendEnhancedLoginScoicalRequest(token,"facebook");


            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "onSuccess onCancel: ");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "حدث خطا بسبب ضعف الاتصال بالانترنت", Toast.LENGTH_SHORT).show();

            }
        });

     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Login1Activity.class));
        finish();
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
                        Common.IS_VISITOR=false;
                        Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL=true;
                    Data data = response.body().getData();

                    boolean isNotComplete=false;


                        if (data==null) return;

                        if (data.getUser().getIsCompleted() ==null) {
                            isNotComplete=true;
                        }else if (data.getUser().getIsCompleted()==0){
                            isNotComplete=true;

                        }

                        if (isNotComplete){
                        //note that <<<>> complete is set to 1 if gender and class were not null
//                        Common.isCompleteProfileCountry=false;

                            //this is need for registerActivity3
                            Common.TEMP_REGISTER_TOKEN=data.getApiToken();
                            Log.d(TAG, "onResponse: "+data.getApiToken());


                            RegisterActivity.REGISTER_EMAIL= data.getUser().getEmail();


                            //save these data for register3 activity .. i need it temporory


                            Common.TEMP_REGISTER_EMAIL=data.getUser().getEmail();
                            Common.TEMP_REGISTER_NAME=data.getUser().getName();

                        Intent intent= new Intent(getApplicationContext(), Register1Activity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        //store this if the user has complete his registeration process


                        Log.d(TAG, "getall: :"+data.getApiToken());


                        Log.d(TAG, "onResponse: else  store user data" );
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
//                        finish();
                    }


                    }else {

                        signOutFacebook();

                        Common.showFalidDailog(LoginActivity.this, "البريد الاكتروني مسجل من قبل ! ");
                    }
                }

                @Override
                public void onFailure(Call<ResultLoginSocialModel> call, Throwable t) {
                     customProgress.hideProgress();
                    Common.showFalidDailog(LoginActivity.this, "خطأ في الاتصال");
                    Log.d(TAG, "onFailure: "+t.getMessage());

                }
            });
        }

    private void signOutFacebook() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            LoginManager.getInstance().logOut();
        }


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
//

        if (completedTask.isSuccessful()){
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                String authCode = account.getServerAuthCode();
                //get access token from code auth <>> and send to api
                getAccessTokenFromCode(authCode);

                Log.d(TAG, "handleSignInResult: ");


            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(TAG, " handleSignInResult signInResult:failed code=" + e.getStatusCode());

            }
        }else {
            try {
                Log.d(TAG, "handleSignInResult: not " +  completedTask.getResult(ApiException.class).getServerAuthCode());
                Log.d(TAG, "handleSignInResult: " +  completedTask.getException());
            } catch (ApiException e) {
                e.printStackTrace();
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
                        String   access_token_google = jsonObject.get("access_token").toString();
                         Log.d(TAG, "handleSignInResult access_token_google: "+jsonObject.toString());


                         //store this token to check befor login later
                         SharedPreferences.Editor editor = getSharedPreferences("google", MODE_PRIVATE).edit();
                         editor.putString("token_google", access_token_google);
                         editor.apply();

                         sendEnhancedLoginScoicalRequest(access_token_google, "google");
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

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    private void sendEnhanceLoginRequest(){


       if (!isValidEmailId(edt_mail.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "يرجى التأكد من البريد الإلكتروني", Toast.LENGTH_SHORT).show();

        }else if (edt_pass.getText().toString().trim().length()<6){
            Toast.makeText(getApplicationContext(),    "يجب أن يكون طول النص كلمة المرور على الأقل 6 حروفٍ/حرفًا", Toast.LENGTH_SHORT).show();

        }else {
           customProgress.showProgress(this,"الرجاء الإنتظار",false);
            //because the object is static >> when i sign out from the app >> it still save the object until close the whole app
           //so i create new object here !!!
           String url = "https://adkya.com/api/v1/";


           Retrofit.Builder builder =
                   new Retrofit.Builder()
                           .baseUrl(url)
                           .addConverterFactory(GsonConverterFactory.create());

           Retrofit retrofit = builder.build();
           APIService apiService=  retrofit.create(APIService.class);

           Call<ResultLoginModel> call =  apiService.loginUser(
                   edt_mail.getText().toString(),
                   edt_pass.getText().toString()
           );

           call.enqueue(new Callback<ResultLoginModel>() {
               @Override
               public void onResponse(Call<ResultLoginModel> call, Response<ResultLoginModel> response) {
                   if (response.isSuccessful()) {

                       //to be sure that the current user is not visitor
                       Common.IS_VISITOR=false;
                       Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL=false;


                       customProgress.hideProgress();
                       if (response.body().getStatus()==true){



                           boolean isNotComplete=false;



                           if (response.body().getData() .getUser().getIsCompleted() ==null) {
                               isNotComplete=true;
                           }else if (response.body().getData() .getUser().getIsCompleted()==0){
                               isNotComplete=true;

                           }

                           if (isNotComplete){
                               //this is need for registerActivity3
                               Common.TEMP_REGISTER_TOKEN=response.body().getData() .getApiToken();


                               //save these data for register3 activity .. i need it temporory
                               RegisterActivity.REGISTER_EMAIL=edt_mail.getText().toString();
                               RegisterActivity.REGISTER_PASS=edt_pass.getText().toString();




                                       Intent intent= new Intent(getApplicationContext(), Register1Activity.class);
                               startActivity(intent);
                               finish();
                           }else {
                               //SAVE USER DATA WITH TOKEN

                               com.arapeak.adkya.model.login.Data data = response.body().getData();
                               CurrentUserSaved userSaved = new CurrentUserSaved();
                               userSaved.setIsLogin(true);
                               Log.d(TAG, "onResponse: :"+data.getApiToken());
                               userSaved.setRememberToken(data.getApiToken());
                               userSaved.setName(data.getUser().getName());
                               userSaved.setEmail(data.getUser().getEmail());
                               userSaved.setId(data.getUser().getId());
                               userSaved.setClassId(data.getUser().getClassId());
                               userSaved.setGenderId(data.getUser().getGenderId());
                               userSaved.setImage(data.getUser().getImage());
                               userSaved.setCountry_name(data.getUser().getAddress().getCountry().getName());
                               userSaved.setCountry_id(data.getUser().getAddress().getCountry().getId());



                               Common.saveUserDataPreferance(getApplicationContext(), userSaved);
                               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                               startActivity(intent);
                               finish();
                           }



                       }else {
                           showActivateDailog();
                        }
                   }

                   else {
                        customProgress.hideProgress();

                       // parse the response body …
                       APIErrorLogin error = ErrorUtils.parseError(response);
                       // … and use it to show error information

                       // … or just log the issue like we’re doing :)
//                       Log.d("handelLogin", error.getMessage());
                       if (error.getData()!=null){
                           Datum datum =  error.getData().get(0);
                           Log.d("handelLogin", datum.getEmail().get(0));
                           Toast.makeText(LoginActivity.this,
                                   datum.getEmail().get(0), Toast.LENGTH_SHORT).show();


                       }


                    }
               }
               @Override
               public void onFailure(Call<ResultLoginModel> call, Throwable t) {
                    customProgress.hideProgress();
                    Toast.makeText(LoginActivity.this,
                           "البيانات المدخلة غير صالحة يرجى التأكد من كلمة المرور", Toast.LENGTH_SHORT).show();

                   Log.d("handelLogin",t.getLocalizedMessage());
               }
           });


       }


    }

    private void showActivateDailog() {

        mBuilder = new AlertDialog.Builder(this );
        View mView = getLayoutInflater().inflate(R.layout.show_request_activate_dailog, null);


        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);
        final Button btn_cancel =  mView.findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

//todo store email in intent
//                intent.putExtra("email",edt_mail.getText().toString().trim());

                //store register email
                RegisterActivity.REGISTER_EMAIL= edt_mail.getText().toString().trim();
                sendResendVervicationCodeAndGoToAvtivationActivity();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }

    private void sendResendVervicationCodeAndGoToAvtivationActivity() {
        customProgress.showProgress(this,"الرجاء الإنتظار",false);

        APIService apiService= ServiceGenerator.createService(APIService.class);
         Call<ResendActivationCode> call =  apiService.SendResendActivationCodeRequest(RegisterActivity.REGISTER_EMAIL);
        call.enqueue(new Callback<ResendActivationCode>() {
            @Override
            public void onResponse(Call<ResendActivationCode> call, Response<ResendActivationCode> response) {
                 customProgress.hideProgress();
                 if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isStatus()){
                        showResendCodeDailog();
                    }else {
                        Toast.makeText(getApplicationContext(), "البريد الالكتروني غير موجود", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResendActivationCode> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "يرجى التحقق من الانترنت", Toast.LENGTH_SHORT).show();
                 customProgress.hideProgress();            }
        });
    }

    private void showResendCodeDailog() {
        mBuilder = new AlertDialog.Builder(LoginActivity.this );
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
                Intent intent= new Intent(getApplicationContext(), ActivateActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void sendLoginAfterSignUp(String email , String pass){

            customProgress.showProgress(this,"الرجاء الإنتظار",false);
            //because the object is static >> when i sign out from the app >> it still save the object until close the whole app
            //so i create new object here !!!
            String url = "https://adkya.com/api/v1/";


            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            APIService apiService=  retrofit.create(APIService.class);

            Call<ResultLoginModel> call =  apiService.loginUser(email,pass);

            call.enqueue(new Callback<ResultLoginModel>() {
                @Override
                public void onResponse(Call<ResultLoginModel> call, Response<ResultLoginModel> response) {
                    if (response.isSuccessful()) {

                        //to be sure that the current user is not visitor
                        Common.IS_VISITOR=false;
                        Common.IS_REGISTER_OR_LOGIN_USING_SOCAIL=false;


                        customProgress.hideProgress();
                        if (response.body().getStatus()==true){



                            boolean isNotComplete=false;



                            if (response.body().getData() .getUser().getIsCompleted() ==null) {
                                isNotComplete=true;
                            }else if (response.body().getData() .getUser().getIsCompleted()==0){
                                isNotComplete=true;

                            }

                            if (isNotComplete){
                                //this is need for registerActivity3
                                Common.TEMP_REGISTER_TOKEN=response.body().getData() .getApiToken();


                                //save these data for register3 activity .. i need it temporory

                                Intent intent= new Intent(getApplicationContext(), Register1Activity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                //SAVE USER DATA WITH TOKEN

                                com.arapeak.adkya.model.login.Data data = response.body().getData();
                                CurrentUserSaved userSaved = new CurrentUserSaved();
                                userSaved.setIsLogin(true);
                                Log.d(TAG, "onResponse: :"+data.getApiToken());
                                userSaved.setRememberToken(data.getApiToken());
                                userSaved.setName(data.getUser().getName());
                                userSaved.setEmail(data.getUser().getEmail());
                                userSaved.setId(data.getUser().getId());
                                userSaved.setClassId(data.getUser().getClassId());
                                userSaved.setGenderId(data.getUser().getGenderId());
                                userSaved.setImage(data.getUser().getImage());
                                userSaved.setCountry_name(data.getUser().getAddress().getCountry().getName());
                                userSaved.setCountry_id(data.getUser().getAddress().getCountry().getId());



                                Common.saveUserDataPreferance(getApplicationContext(), userSaved);
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }



                        }else {
                            showActivateDailog();
                        }
                    }

                    else {
                        customProgress.hideProgress();

                        // parse the response body …
                        APIErrorLogin error = ErrorUtils.parseError(response);
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
//                       Log.d("handelLogin", error.getMessage());
                        if (error.getData()!=null){
                            Datum datum =  error.getData().get(0);
                            Log.d("handelLogin", datum.getEmail().get(0));
                            Toast.makeText(LoginActivity.this,
                                    datum.getEmail().get(0), Toast.LENGTH_SHORT).show();


                        }


                    }
                }
                @Override
                public void onFailure(Call<ResultLoginModel> call, Throwable t) {
                    customProgress.hideProgress();
                    Toast.makeText(LoginActivity.this,
                            "البيانات المدخلة غير صالحة يرجى التأكد من كلمة المرور", Toast.LENGTH_SHORT).show();

                    Log.d("handelLogin",t.getLocalizedMessage());
                }
            });


        }

}

