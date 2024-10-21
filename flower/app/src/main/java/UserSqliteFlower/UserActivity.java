package UserSqliteFlower;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.flower.MainActivity;


import com.example.flower.databinding.ActivityUserBinding;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    private int year;
    private int month;
    private int day;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if (info.equals("new")) {
            binding.edittext.setText("");
            binding.textviewDatapicker.setText("");
            binding.delete.setVisibility(View.GONE);
            binding.AlarmStopButton.setVisibility(View.GONE);
            binding.SaveButton.setVisibility(View.VISIBLE);
        } else {
            binding.image10.setVisibility(View.GONE);
            binding.datapicterbutton.setVisibility(View.GONE);
            binding.SaveButton.setVisibility(View.GONE);
            int id = intent.getIntExtra("id", 0);
            try {
                SQLiteDatabase database = UserActivity.this.openOrCreateDatabase("Flowers", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("SELECT * FROM flowers WHERE id=?", new String[]{String.valueOf(id)});
                int nameIndex = cursor.getColumnIndex("name");
                int dateIndex = cursor.getColumnIndex("date");
                while (cursor.moveToNext()) {
                    binding.edittext.setText(cursor.getString(nameIndex));
                    binding.textviewDatapicker.setText(cursor.getString(dateIndex)); // Doğru sütunu kullanın
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//Tarih alma
        binding.datapicterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.textviewDatapicker.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        binding.SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edittext.getText().toString();
                String date = binding.textviewDatapicker.getText().toString();
                try {
                    SQLiteDatabase database = UserActivity.this.openOrCreateDatabase("Flowers", MODE_PRIVATE, null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS flowers(id INTEGER PRIMARY KEY, name VARCHAR, date DATE)");
                    String sql = "INSERT INTO flowers(name, date) VALUES (?, ?)";
                    SQLiteStatement sqLiteStatement = database.compileStatement(sql);
                    sqLiteStatement.bindString(1, name);
                    sqLiteStatement.bindString(2, date);
                    sqLiteStatement.execute();
                    Toast.makeText(UserActivity.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = intent.getIntExtra("id", 0);
                try {
                    SQLiteDatabase database = UserActivity.this.openOrCreateDatabase("Flowers", MODE_PRIVATE, null);
                    database.execSQL("DELETE FROM flowers WHERE id=?", new String[]{String.valueOf(id)});

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent1 = new Intent(getApplication(), MainActivity.class);
                startActivity(intent1);
            }
        });
        binding.AlarmStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = binding.textviewDatapicker.getText().toString();
                String[] currentDateArray = currentDate.split("-");
                int year = Integer.parseInt(currentDateArray[0]);
                int month = Integer.parseInt(currentDateArray[1]) - 1;
                int day = Integer.parseInt(currentDateArray[2]);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String newDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        binding.textviewDatapicker.setText(newDate);

                        try {
                            SQLiteDatabase database = UserActivity.this.openOrCreateDatabase("Flowers", MODE_PRIVATE, null);
                            int id = intent.getIntExtra("id", 0);

                            String updateSql = "UPDATE flowers SET date = ? WHERE id = ?";
                            SQLiteStatement updateStatement = database.compileStatement(updateSql);
                            updateStatement.bindString(1, newDate);
                            updateStatement.bindLong(2, id);
                            updateStatement.executeUpdateDelete();

                            Toast.makeText(UserActivity.this, "Tarih güncellendi", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

    }
}