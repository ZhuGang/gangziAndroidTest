package com.test.gangzi.retrofittest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.test.gangzi.retrofittest.Model.Contributor;
import com.test.gangzi.retrofittest.R;
import com.test.gangzi.retrofittest.service.GitHub;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.hello);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build(); //默认设置了GsonConverter的转换器.把response直接解析为一个POJO对象.
        GitHub github = restAdapter.create(GitHub.class);

        //访问这个地址返回的是一个JsonArray,JsonArray的每一个元素都有login和contributions这2个key和其对应的value.提取出来封装进POJO对象中.
        github.contributors("square", "retrofit", new Callback<List<Contributor>>() {
            @Override
            public void success(List<Contributor> contributors, Response response) {
                for (Contributor contributor : contributors) {
                    Log.v("aaaaa: retrofit", contributor.getLogin() + " (" + contributor.getContributions() + ")");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
