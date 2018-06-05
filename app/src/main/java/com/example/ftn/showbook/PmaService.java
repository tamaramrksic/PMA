package com.example.ftn.showbook;
import com.example.ftn.showbook.model.Comment;
import com.example.ftn.showbook.model.Facility;
import com.example.ftn.showbook.model.Repertoire;
import com.example.ftn.showbook.model.Reservation;
import com.example.ftn.showbook.model.Show;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("reservations/seen/{username}")
    Call<List<Reservation>> getUserSeenShows(@Path("username")String username);

    @GET("shows/interested/{username}")
    Call<List<Show>> getUserInterestedShows(@Path("username")String username);

    @GET("shows/interested/{username}/{idFacility}")
    Call<List<Show>> getFacilityInterestedShows(@Path("username")String username, @Path("idFacility")Long idFacility);

    @GET("shows/interested/is/{username}/{idShow}")
    Call<Boolean> isInterestedShow(@Path("username")String username, @Path("idShow")Long idShow);

    @POST("shows/interested/add/{username}/{idShow}")
    Call<Show> addInterestedShow(@Path("username")String username, @Path("idShow")Long idShow);

    @POST("shows/interested/remove/{username}/{idShow}")
    Call<Show> removeInterestedShow(@Path("username")String username, @Path("idShow")Long idShow);

    @GET("facilities")
    Call<List<Facility>> getAllFacilities();

    @GET("shows/{idFacility}")
    Call<List<Show>> getShowsByFacility(@Path("idFacility")Long idFacility);

    @DELETE("reservations/{id}")
    Call<ResponseBody> cancelReservation(@Path("id")Long id);

    @POST("comments/{showId}/{username}")
    Call<Comment> commentShow(@Body Comment comment, @Path("showId")Long id, @Path("username")String username);

    @GET("comments/{showId}")
    Call<List<Comment>> getCommentsByShow(@Path("showId")Long id);


    @GET("reservations/rating/{id}/{username}/{rating}")
    Call<ResponseBody> ratingReservation(@Path("id")Long id,@Path("username")String username,  @Path("rating")Integer rating);


}
