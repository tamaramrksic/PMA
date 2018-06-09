package com.example.ftn.showbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.UserDB;
import com.example.ftn.showbook.model.Comment;
import com.example.ftn.showbook.model.Mail;
import com.example.ftn.showbook.model.User;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment implements View.OnClickListener {

    private EditText commentEditText;
    private Bundle args;
    private List<User> users = new ArrayList<>();
    private String notificationTitle;
    private String notificationText;
    private String username;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        args = this.getArguments();
        context = getActivity().getApplicationContext();

//        getActivity().setTitle(getResources().getString(R.string.leave_comment_title) + args.getString("showName"));
        ((ImageView)rootView.findViewById(R.id.show_image)).setImageResource(ShowListAdapter.getImage(args.getLong("showId")));
        ((TextView)rootView.findViewById(R.id.show_name_heading)).setText(args.getString("showName"));
        commentEditText = rootView.findViewById(R.id.write_comment);
        Button sendCommentBtn = rootView.findViewById(R.id.button_send_comment);
        sendCommentBtn.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        Comment comment = new Comment();
        comment.setText(commentEditText.getText().toString());

        Call<Comment> call = ServiceUtils.pmaService.commentShow(comment, args.getLong("showId"), getActivity().getIntent().getStringExtra("drawerUsername"));
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                ShowCommentsFragment fragment = new ShowCommentsFragment();
                Bundle scArgs = new Bundle();
                scArgs.putLong("showId", args.getLong("showId"));
                scArgs.putString("showName", args.getString("showName"));
                fragment.setArguments(scArgs);

                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                manager.popBackStack();
                manager.beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();

                notificationTitle = getActivity().getString(R.string.notification_title);
                notificationText = getActivity().getString(R.string.notification_text);
                username = getActivity().getIntent().getStringExtra("drawerUsername"); //mozda nece biti potrebno kad se zavrse notifikacije
                getUsersForNotifications();

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.send_comment_failure), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getUsersForNotifications() {
        Call<List<User>> call = ServiceUtils.pmaService.getUserForNotifications(getActivity().getIntent().getStringExtra("drawerUsername"), args.getLong("showId"));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.body().size() > 0) {
                    users = response.body();
                    new Notify().execute();
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.send_comment_notifications_failure), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void sendMail(User user) {
        Mail mail = new Mail(user.getEmail(), notificationTitle + " " + args.getString("showName"), notificationText, username);

        Call<ResponseBody> call = ServiceUtils.pmaService.sendMail(mail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("Mejl poslat.");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Mejl nije poslat.");
            }

        });
    }


    public class Notify extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            for (User user : users) {
                if (user.isCommentNotification() && user.getToken() != null) {
                    try {

                        URL url = new URL("https://fcm.googleapis.com/fcm/send");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setUseCaches(false);
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Authorization", "key=AIzaSyCciWUvLSc7xAE8f-ViK0MxRxJbsTqBCj8");
                        conn.setRequestProperty("Content-Type", "application/json");

                        JSONObject json = new JSONObject();

                        json.put("to", user.getToken());

                        JSONObject info = new JSONObject();
                        info.put("title", notificationTitle + " " +  args.getString("showName"));   // Notification title
                        info.put("body", notificationText); // Notification body
                        info.put("showName",args.getString("showName"));
                        info.put("showId", args.getLong("showId"));

                        info.put("comment_user", username);
                        info.put("username", user.getUsername());


                        json.put("data", info);


                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write(json.toString());
                        wr.flush();
                        conn.getInputStream();

                    } catch (Exception e) {
                        Log.d("Error in notify", "" + e);
                    }
                } else {
                    sendMail(user);
                }

            }


            return null;
        }
    }
}
