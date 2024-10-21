package com.example.haritaders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.example.haritaders.databinding.ActivityMainBinding;

import java.util.List;

import adapter.placeAdater;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Place;
import roomdatabase.PlaceDao;
import roomdatabase.PlaceDataBase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    PlaceDao placeDao;
    PlaceDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //database i oluşturma
        db = Room.databaseBuilder(getApplicationContext(), PlaceDataBase.class, "Places")
                .build();
        //dao alımı
        placeDao = db.placeDao();
        compositeDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())//io da yap
                .observeOn(AndroidSchedulers.mainThread())//mainthread da gözlemle
                .subscribe(MainActivity.this::handleResponse)
        );

    }
    private void handleResponse(List<Place> placeList){
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        placeAdater placeAdater=new placeAdater(placeList);
        binding.recyclerview.setAdapter(placeAdater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}