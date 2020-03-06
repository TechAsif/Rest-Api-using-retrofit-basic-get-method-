package com.example.workingwithrestapiwithretrofitbasiget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        //create Retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         //auto implement api class help of retrofit

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        //call getPosts auto implemented method..it does low level networking.

        Call<List<PostModel>> call = jsonPlaceholderApi.getPosts();

        //now we will execute call and get response back

        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                //it will call when response back

                //if response does not desired

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                //if we get our desire response
                List<PostModel> posts = response.body();

                for(PostModel post: posts){
                    String content = "";

                    content += "ID: "+post.getId()+ "\n";
                    content += "User Id: "+post.getUserId()+ "\n";
                    content += "Title: "+post.getTitle()+ "\n";
                    content += "Text: "+post.getText();
                    content += "\n\n";

                    textViewResult.append(content);


                }

            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                //it will cll when call was not successful
                textViewResult.setText(t.getMessage());//t contain exception message
            }
        });








    }
}
