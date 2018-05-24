package com.example.ftn.showbook;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  PmaService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("users/{id}")
    Call<ResponseBody> get(@Path("id")Long id);

    @POST("users")
    Call<User> getUser(@Body String username);

    @POST("users/registr/{id}")
    Call<ResponseBody> registr(@Body User user, @Path("id")Long id);

    @POST("users/login")
    Call<User> login(@Body UserCredentials userCredentials);

    @PUT("users/changePass")
    Call<User> changePass(@Body UserCredentials userCredentials);

}
