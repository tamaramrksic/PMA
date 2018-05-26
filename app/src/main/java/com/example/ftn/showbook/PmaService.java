package com.example.ftn.showbook;
import com.example.ftn.showbook.model.Reservation;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  PmaService {

    @Headers({
            "UserDB-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST("users")
    Call<User> getUser(@Body UserCredentials userCredentials);

    @POST("users/registr/{id}")
    Call<User> registr(@Body User user, @Path("id")Long id);

    @POST("users/login")
    Call<User> login(@Body UserCredentials userCredentials);

    @PUT("users/changePass")
    Call<User> changePass(@Body UserCredentials userCredentials);

    @PUT("users/{username}/{location}")
    Call<User> updateUser(@Body User user, @Path("username")String username, @Path("location") String location);

    @GET("reservations/{username}")
    Call<List<Reservation>> getUserReservations(@Path("username")String username);

}
