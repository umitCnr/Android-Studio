package com.example.flower;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.flower.databinding.ActivityMainBinding;

import android.view.View;

import FlowersView.ApiActivity;
import HavaDurumu.WeatherMain;
import MyFlower.MyFlowerActivity;
import UserSqliteFlower.UserActivity;
import anket.AnketActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.cardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyFlowerActivity.class);
                startActivity(intent);
            }
        });

        binding.cardview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("info","new");
                startActivity(intent);
            }
        });
        binding.cardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(MainActivity.this, ApiActivity.class);
                startActivity(intent1);

            }
        });
        binding.anket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, AnketActivity.class);
                startActivity(intent);
            }
        });

    }
    public void hava(View view) {
        Intent intent = new Intent(this, WeatherMain.class);
        startActivity(intent);
    }

}