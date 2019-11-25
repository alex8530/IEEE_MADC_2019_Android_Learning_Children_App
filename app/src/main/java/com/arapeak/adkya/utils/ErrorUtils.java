package com.arapeak.adkya.utils;

import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.login.APIErrorLogin;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIErrorLogin parseError(Response<?> response) {
        Converter<ResponseBody, APIErrorLogin> converter =
                ServiceGenerator.retrofit
                        .responseBodyConverter(APIErrorLogin.class, new Annotation[0]);

        APIErrorLogin error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIErrorLogin();
        }

        return error;
    }
}
