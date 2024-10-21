package FlowersView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.MainActivity;
import com.example.flower.R;
import com.example.flower.databinding.ActivityApiBinding;
import com.example.flower.databinding.ActivityWeatherMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiActivity extends AppCompatActivity {
    private ActivityApiBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayList<Plant> plantList =new ArrayList<>();
        binding.recyclerFlower.setLayoutManager(new LinearLayoutManager(ApiActivity.this));
        FlowerAdapter adapter =new FlowerAdapter(plantList);
        binding.recyclerFlower.setAdapter(adapter);


        //https://trefle.io/api/v1/plants/search?token=-cAgimhxEOO--QjYiiGT9vq6izABMy2mNEUQFRmwKoI&q=flower

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trefle.io/api/v1/plants/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlantApiService plantApiService=retrofit.create(PlantApiService.class);
        Call<PlantResponse> call =plantApiService.searchPlants();
        call.enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()){
                    PlantResponse plantResponse =response.body();
                    ArrayList<Plant> plants= (ArrayList<Plant>) plantResponse.getPlants();
                    binding.recyclerFlower.setLayoutManager(new LinearLayoutManager(ApiActivity.this));
                    FlowerAdapter adapter =new FlowerAdapter(plants);
                    binding.recyclerFlower.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {


            }
        });

        binding.don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}