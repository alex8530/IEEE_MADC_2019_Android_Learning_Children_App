package com.arapeak.adkya.api;

import com.arapeak.adkya.model.aboutus.ResultAboutUs;
import com.arapeak.adkya.model.activateAccount.ActivateAccount;
import com.arapeak.adkya.model.country.ResultCountryModel;
import com.arapeak.adkya.model.socialLogin.ResultLoginSocialModel;
import com.arapeak.adkya.model.forgetpassword.ResultForgetPassword;
import com.arapeak.adkya.model.getMaterial.ResultGetMaterial;
import com.arapeak.adkya.model.getQuestion.ResultGetQuestion;
import com.arapeak.adkya.model.getSecondMaterial.ResultGetSecondMaterial;
import com.arapeak.adkya.model.getStatistics.ResultGetStatistics;
import com.arapeak.adkya.model.getanswer.ResultGetAnswer;
import com.arapeak.adkya.model.login.ResultLoginModel;
import com.arapeak.adkya.model.register.ResultRegisterModel;
import com.arapeak.adkya.model.resendActivationCode.ResendActivationCode;
import com.arapeak.adkya.model.updateuser.ResultUpdateUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    //The register call
    @FormUrlEncoded
    @POST("register")
    Call<ResultRegisterModel> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("country") String country,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login")
    Call<ResultLoginModel> loginUser(
             @Field("email") String email,
             @Field("password") String password);


    /*******************************************************************************/

//
    @Multipart
    @POST("student/complete-profile")
    Call<ResponseBody> completeProfile(
            @Part("class_id") RequestBody class_id,
            @Part("gender_id") RequestBody gender_id,
            @Part  MultipartBody.Part file
    );





    @GET("general/countries")
    Call<ResultCountryModel> getAllCountry();


    @FormUrlEncoded
    @POST("password/forget")
    Call<ResultForgetPassword> getForgetPassword(@Field("email") String email);


    @FormUrlEncoded
    @PATCH("password")
    Call<ResponseBody> changePassword(@Field("old_password") String old_password,
                                              @Field("password") String password,
                                              @Field("password_confirmation") String password_confirmation);



    //because the return object same as login,, so ResultLoginSocialModel is used
    @Multipart
    @POST("student/profile")
    Call<ResultUpdateUser> UpdateProfile(@Part("name")  RequestBody name,
                                         @Part("email")  RequestBody email,
                                         @Part("class_id")  RequestBody class_id,
                                         @Part("country_id")  RequestBody country_id,
                                         @Part("gender_id")  RequestBody gender_id,
                                         @Part  MultipartBody.Part file
    );



    @Headers({
            "Accept: application/json"
    })
    @GET("educational/material")
    Call<ResultGetMaterial> getMaterial();

    @GET("pages/about-us")
    Call<ResultAboutUs> getAboutUs();


    @GET("educational/{id}/secondary-material")
    Call<ResultGetSecondMaterial> getSecondMaterial(@Path("id") String id);



    @GET("educational/{material_id}/{secondary_material_id}/{section_id}/questions")
    Call<ResultGetQuestion> getQuestion(
            @Path("material_id") String material_id,
            @Path("secondary_material_id") String secondary_material_id,
            @Path("section_id") String section_id
    );


    @FormUrlEncoded
    @POST("educational/answer-check")
    Call<ResultGetAnswer> getAnswer(
            @Field("question_id") String question_id ,
            @Field("option_id") String option_id ,
            @Field("is_repeat") String is_repeat ,
            @Field("time_elapsed") String time_elapsed
    );



    @GET("student/statistics")
    Call<ResultGetStatistics> getStatistics();



    @FormUrlEncoded
    @POST("pages/contact-us")
    Call<ResponseBody> SendContactUsRequest(
            @Field("type_id") String type_id ,
            @Field("name") String name ,
            @Field("email") String email ,
            @Field("description") String description
    );


    @FormUrlEncoded
    @POST("activate")
    Call<ActivateAccount> SendActivateRequest(
            @Field("code") String code ,
            @Field("email") String email

    );

    @FormUrlEncoded
    @POST("activation/resend")
        Call<ResendActivationCode> SendResendActivationCodeRequest(
             @Field("email") String email
    );

    @FormUrlEncoded
    @POST("social-login")
        Call<ResultLoginSocialModel> SendSocialRequest(
             @Field("token") String token,
             @Field("type") String type
    );




    @Headers({
            "Accept: application/json"
    })
    @GET("visitor/educational/material")
    Call<ResultGetMaterial> getMaterialVisitor();



    @GET("visitor/educational/{class_id}/{material_id}/secondary-material")
    Call<ResultGetSecondMaterial> getSecondMaterialVisitor(@Path("class_id") String class_id , @Path("material_id") String material_id);



    @POST("token")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<ResponseBody> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code);


    @POST("educational/check-reports")
    @FormUrlEncoded
    Call<ResponseBody> SendCheckReports(
            @Field("material_id") String material_id,
            @Field("secondary_id") String secondary_id,
            @Field("section_id") String section_id);


}
