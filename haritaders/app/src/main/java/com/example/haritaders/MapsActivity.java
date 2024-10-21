package com.example.haritaders;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.haritaders.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.Console;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Place;
import roomdatabase.PlaceDao;
import roomdatabase.PlaceDataBase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    //googlemap.onlongclicklistener eklendi

    private GoogleMap mMap;
    Location lastLocation;
    private ActivityMapsBinding binding;
    ActivityResultLauncher permissionLouncher;
    LocationManager locationManager;
    LocationListener locationListener;
    //RoomDataBase kullanmak için genel tanımlama
    PlaceDao placeDao;
    PlaceDataBase db;
    Double selectedLatitude;
    Double selectedLongitude;
    Place selectedplace;
    //---------------
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        registerlauncher();
        //database i oluşturma
        db = Room.databaseBuilder(getApplicationContext(), PlaceDataBase.class, "Places")
                //.allowMainThreadQueries()
                .build();
        //dao alımı
        placeDao = db.placeDao();
        selectedLatitude = 0.0;
        selectedLongitude = 0.0;
        binding.savebutton.setEnabled(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // mMap.setOnMapClickListener(this);
        mMap = googleMap;

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if (info.equals("new")) {
            binding.savebutton.setVisibility(View.VISIBLE);
            binding.deletebutton.setVisibility(View.GONE);//gone denilince tamamen gider-invisible de ise sadece görünmez olur

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            //listener değişen konumla ilgili değişimler yapılabilir
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    mMap.setOnMapClickListener(MapsActivity.this);
                    //kullanıcının konumuna ulaşma
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(binding.getRoot(), "harita için izin", Snackbar.LENGTH_INDEFINITE).setAction("izin ver", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            permissionLouncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                    }).show();
                } else {
                    permissionLouncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10000, locationListener);
                //opsiyanel
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {//son bilinen konuma gitme
                    LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15));
                }
            }
        } else {
            mMap.clear();
            selectedplace = (Place) intent.getSerializableExtra("place");
            LatLng latLng = new LatLng(selectedplace.latitude, selectedplace.longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title(selectedplace.yername));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));//kamerayı seçilen yere götürme
            binding.name.setText(selectedplace.yername);
            binding.savebutton.setVisibility(View.GONE);
            binding.deletebutton.setVisibility(View.VISIBLE);
        }
    }

    private void registerlauncher() {
        permissionLouncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o) {
                    //izin verildiyse == kullandık
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000, locationListener);
                        if (lastLocation != null) {//son bilinen konuma gitme
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15));
                        }

                    }
                } else {
                    Toast.makeText(MapsActivity.this, "izin gerekli", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        selectedLongitude = latLng.longitude;
        selectedLatitude = latLng.latitude;
        //kullanıcı bier yer seçemeden save butonuna tıklayamaz
        binding.savebutton.setEnabled(true);
    }

    public void save(View view) {
        Place place = new Place(binding.name.getText().toString(), selectedLatitude, selectedLongitude);
        compositeDisposable.add(placeDao.insert(place)
                .subscribeOn(Schedulers.io())//io da yap
                .observeOn(AndroidSchedulers.mainThread())//mainthread da gözlemle
                .subscribe(MapsActivity.this::handleResponse)//referans verdik
        );

        // placeDao.insert(place);

        //çalışır ama uygulama yük bindirir uygulama blocklanıt
        //placeDao.insert(place).subscribeOn(Schedulers.io()).subscribe();

    }

    private void handleResponse() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
//en son delete buttonunun kodlerını yaz compositeDisposabele boş kalır hata verir
    public void delete(View view) {
        compositeDisposable.add(placeDao.delete(selectedplace)
                .subscribeOn(Schedulers.io())//io da yap
                .observeOn(AndroidSchedulers.mainThread())//mainthread da gözlemle
                .subscribe(MapsActivity.this::handleResponse)//referans verdik
        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //oluşturulan kolları siler
        compositeDisposable.clear();
    }
}