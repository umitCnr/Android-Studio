package MyFlower;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.MainActivity;
import com.example.flower.databinding.ActivityMyFlowerBinding;
import com.example.flower.flower;
import com.example.flower.recycler;

import java.util.ArrayList;

public class MyFlowerActivity extends AppCompatActivity {
    private ActivityMyFlowerBinding binding;
    ArrayList<flower> array;
    recycler recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyFlowerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //getdata();
        array = new ArrayList<>();
        //recyclerview
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler recycler = new recycler(array);
        binding.recycler.setAdapter(recycler);


        try {

            SQLiteDatabase database = MyFlowerActivity.this.openOrCreateDatabase("Flowers", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM flowers", null);
            int nameix = cursor.getColumnIndex("name");
            int idix = cursor.getColumnIndex("id");
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameix);
                int id = cursor.getInt(idix);
                flower flower = new flower(name, id);
                array.add(flower);
            }
            recycler.notifyDataSetChanged();
            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();

        }
        binding.back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

}