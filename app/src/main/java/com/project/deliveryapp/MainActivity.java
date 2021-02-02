package com.project.deliveryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.project.deliveryapp.model.RestaurantModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

        List<RestaurantModel> restaurantModelList = getRestaurantData();

        initRecView();
    }

    private void initRecView() {

    }

    private List<RestaurantModel> getRestaurantData() {
        InputStream inputStream = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {

        }
        String jsonSrt = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels = gson.fromJson(jsonSrt, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);
        return restList;
    }
}