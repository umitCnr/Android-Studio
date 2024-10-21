package HavaDurumu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flower.MainActivity;
import com.example.flower.R;
import com.example.flower.databinding.ActivityMainBinding;
import com.example.flower.databinding.ActivityWeatherMainBinding;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherMain extends AppCompatActivity {
    private ActivityWeatherMainBinding binding;
    private Retrofit retrofit;
    private Call<Model> call;
    private theInterface interface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        binding = ActivityWeatherMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        flowerInformation();

        //https://api.openweathermap.org/data/2.5/weather?lat=39.57&lon=32,53&appid=40a52fb19945df8445110dc13adc6a40
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interface1 = retrofit.create(theInterface.class);
        call = interface1.getModel();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    Main main = model.main;
                    double temp = main.sicaklik;
                    int temp1 = (int) temp;
                    double feel = main.hissedilen;
                    int feel1 = (int) feel;
                    int humenity = main.nem;
                    binding.weatherInformation.setText(String.valueOf(temp1 - 273)+"C\n\n"+"Nem :"+String.valueOf(humenity)+"\n\n"+"Hissedilen : "+String.valueOf(feel1-273));
                }

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(WeatherMain.this, "VERİ GELMEDİ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
    public void flowerInformation(){
        String[] dizi={"gübreleme ilkbahar ve yaz aylarında yapılır kışın dinlenmeye bırakılır."
                ,"Evde çiçek bakımının yine temel adımlarından sayılan toprak seçiminde dikkatli olmak çok önemli. Aksi halde çiçek ihtiyaç duyduğu besinleri ve diğer yararlı maddeleri bağlandığı topraktan temin edemez ve kısa ömürlü olur."};


        Random random =new Random();
        int random1=random.nextInt(dizi.length);
        String write=dizi[random1];
        binding.textinformation.setText(write);
    }
}