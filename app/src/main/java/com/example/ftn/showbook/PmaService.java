package com.example.ftn.showbook;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface  PmaService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{id}")
    Call<ResponseBody> get(@Path("id")Long id);

    @POST("users/registr")
    Call<ResponseBody> registr(@Body User user);

    @POST("users/login")
    Call<User> login(@Body UserCredentials userCredentials);
}
