package anket;

import static android.app.DownloadManager.COLUMN_ID;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flower.R;
import com.example.flower.databinding.ActivityAnketBinding;
import com.example.flower.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class AnketActivity extends AppCompatActivity {

    private ActivityAnketBinding binding;
    private SQLiteDatabase database;
    private Cursor cursor;
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnketBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        previousButton = null;
        try {
            SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);

            String createsql = "CREATE TABLE IF NOT EXISTS flower (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "yer TEXT, " +
                    "bilgi TEXT)";
            database.execSQL(createsql);



            String countQuery = "SELECT COUNT(*) FROM flower";
            Cursor cursor = database.rawQuery(countQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int count = cursor.getInt(0);
                cursor.close();

                if (count == 0) {
                    insertSampleData(database);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.akdeniz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (previousButton != null) {
                    previousButton.setBackgroundResource(android.R.drawable.btn_default);
                }

                if (binding.akdeniz.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.anadolu).getConstantState())) {
                    binding.akdeniz.setBackgroundResource(R.drawable.anadolu);
                } else {
                    binding.akdeniz.setBackgroundResource(R.drawable.anadolu);
                }

                // Şu an tıklanan butonu önceki olarak ayarla
                previousButton = binding.akdeniz;




                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();


                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"akdeniz"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        binding.ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.ic.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.ic.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.ic.setBackgroundResource(R.drawable.feslegen);
                }

                // Şu an tıklanan butonu önceki olarak ayarla
                previousButton = binding.ic;



                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();


                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"iç anadolu"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.dogu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.dogu.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.dogu.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.dogu.setBackgroundResource(R.drawable.vanters);
                }

                // Şu an tıklanan butonu önceki olarak ayarla
                previousButton = binding.dogu;


                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();

                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"doğu anadolu bölgesi"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }


                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.marmara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.marmara.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.marmara.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.marmara.setBackgroundResource(R.drawable.kardelen);
                }

                // Şu an tıklanan butonu önceki olarak ayarla
                previousButton = binding.marmara;



                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();

                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"marmara bölgesi"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        binding.ege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.ege.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.ege.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.ege.setBackgroundResource(R.drawable.images);
                }

                previousButton = binding.ege;


                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();

                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"ege"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        binding.guney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.guney.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.guney.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.guney.setBackgroundResource(R.drawable.zakkum);
                }


                previousButton =binding.guney;


                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();

                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"güney"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }


                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        binding.karadeniz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (previousButton != null) {
                    previousButton.setBackgroundResource(R.drawable.bolge);
                }

                if (binding.karadeniz.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bolge).getConstantState())) {
                    binding.ic.setBackgroundResource(R.drawable.bolge);
                } else {
                    binding.karadeniz.setBackgroundResource(R.drawable.muge);
                }

                previousButton = binding.karadeniz;


                try {
                    SQLiteDatabase database = AnketActivity.this.openOrCreateDatabase("flower.db", MODE_PRIVATE, null);
                    ArrayList<String> dataList = new ArrayList<>();

                    String sql = "SELECT * FROM flower WHERE yer = ?";
                    Cursor cursor = database.rawQuery(sql, new String[]{"karadeniz"});

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int namex = cursor.getColumnIndex("name");
                            int bilgix = cursor.getColumnIndex("bilgi");
                            String name = cursor.getString(namex);
                            String bilgi = cursor.getString(bilgix);
                            String dataString = "\nName: " + name + "\nBilgi: " + bilgi + "\n";
                            dataList.add(dataString);
                        } while (cursor.moveToNext());

                        cursor.close();
                    } else {
                        dataList.add("Veri bulunamadı.");
                    }


                    StringBuilder stringBuilder = new StringBuilder();
                    for (String data : dataList) {
                        stringBuilder.append(data).append("\n");
                    }
                    String dataToPass = stringBuilder.toString();
                    database.close();

                    Akdenizfragment akdenizFragment = new Akdenizfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", dataToPass);
                    akdenizFragment.setArguments(bundle);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, akdenizFragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void insertSampleData(SQLiteDatabase database) {
        String data1 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Anadolu Glayölü', 'akdeniz', 'Bu çiçek, güneşli veya hafif gölgeli bölgelerde yetişir. Günde en az altı saat güneş ışığı almalıdır.Bu çiçek, güneşli veya hafif gölgeli bölgelerde yetişir. Günde en az altı saat güneş ışığı almalıdır. Ancak, aşırı sıcak havalarda gölge bir alanda korunmalıdır.Sulama, toprağın yüzeyi hafifçe kuruduğunda yapılmalıdır. Özellikle sıcak havalarda daha sık sulama gerekebilir.');";
        String data2 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Fesleğen', 'iç anadolu', 'Humusça zengin topraklarda en iyi şekilde yetişir. Bu nedenle torflu topraklarını tercih etmelisiniz. Sert rüzgârları hiç sevmez. Hem güneş ışığını hem de sıcağı sever. Bu nedenle, bakım için güneşe maruz kalan pencere kenarlarını ve balkonları seçmek daha iyidir.\n" +
                "\n" +
                "Haftada bir kez sulanması yeterlidir. Ancak güneşi bol bir yerdeyseniz bunu 2 ye " +
                "\n kadar çıkarabilirsiniz. Toprak çok ıslak ve kuru olmamalıdır. Saksının içlerinden fazla su akan dip deliklerine dikkat etmelisiniz. ');";
        String data3 = "INSERT INTO flower (name, yer, bilgi) VALUES ('İstanbul Kardeleni', 'marmara bölgesi', 'Lale bitkisinin saksı değişimi 2 yılda bir sıcak ayların başladığı zamanlarda yapılmalıdır.\n" +
                "Lale bitkisinin toprak değişimi Nisan ayının başlarında yapılmalıdır. Toprak değişimi bir büyük saksıya geçecek şekilde olmalıdır. \n" +
                "Süs bitkisi olan lale ev içerisinde yetiştirmeye uygun değildir. Alan seçiminde buna dikkat etmelisiniz.\n" +
                "Lale sulaması aynı kap ile aynı periyotta yapılmalıdır. Kışın su ihtiyacı azaldığından aralık uzatılabilir. Suyun toprağın tamamına yayıldığından emin olmak gereklidir.\n" +
                "sulama suyunun oda sıcaklığında olması oldukça önemlidir. \n" +
                "Laleler yaz aylarında çiçek açan kış aylarında yaprak dökerek dinlenme sürecine girer. \n" +
                "Lale bulunduğu alanda başka cisimlere temas etmemelidir. \n" +
                "Yılda bir kez laleye çiçek besini, vitamin vs. vermek gereklidir.\n" +
                "Yaz aylarında oluşabilecek yaprak bitlerine dikkat edilmelidir. Yaprak biti buluşan lale hemen sökülmelidir. \n" +
                "Solmaya başlayan ya da hastalanan lale kesilmelidir. Aksi takdirde diğer bitkilere yayılabilir.');";
        String data4 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Van Ters Lalesi', 'doğu anadolu bölgesi', 'Dik gövde kısmının üzerinde gelişim sergileyen ters lalenin boyutları 30 ila 90 santimetreye, eni ise 20-30 santimetreye erişebilir. Yer aldıkları bölgeye benzersiz bir görsel sunum kazandırmalarına ilaveten bu çiçekler aynı zamanda geyikler tarafından çok rahatsız edilmezler ve bu çiçeklerin miske benzeyen, neredeyse bir kokarcayı düşündüren, kuvvetli bir kokuları bulunmaktadır. Bu koku, bahçelerden kemirgenleri ve tarla farelerini uzak tutma konusunda destek olur.');";
        String data5 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Manolya', 'ege', 'Manolya bakımı önemlidir. Doğru bakılmadığında kaybedebileceğiniz hassas bir çiçektir. Çiçeğin bulunduğu ortamın büyüklüğü yeterli olmalıdır. Oda sıcaklığında yetişebilir. Çok sıcak ve çok soğuktan hoşlanmayan bir çiçektir.\n" +
                "\n" +
                "Büyük çiçekli manolya dayanıklı olduğu söylense de kış aylarında soğuğa karşı korunması gereken bir bitkidir. Geceleri yapraklarını yıkayarak sulama ihtiyacını giderebilirsiniz. Gübre olarak da hayvansal kaynaklı gübreler kullanılması önerilmektedir.\n" +
                "\n" +
                "Manolyanın yetiştirmesini kapalı ortamda yapıyorsanız ortamın nemli ve ılıman olması gerektiğini hatırlatalım. Ancak çok sıcak ve soğuk ortamlarda güzel çiçek açmayacaktır. Manolya çiçeğini ekerken çok derine ekmemelisiniz." +
                "');";
        String data6 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Zakkum', 'güney', 'Zakkum yetiştirilmesi için güneşli bir konum tercih edilmelidir, tam güneş ışığı bitkinin çiçeklenmesini teşvik eder. Toprak nemli ve iyi drene edilebilir olmalıdır, aşırı sulamaktan kaçınılmalıdır çünkü zakkum bitkisi suya toleranslıdır. Düzenli olarak gübreleme yapılmalıdır, özellikle büyüme mevsiminde bitkiyi desteklemek için azotlu bir gübre kullanılabilir.\n" +
                "\n" +
                "Ayrıca, zakkumun budama gerektiren bir bitki olduğunu unutmamak önemlidir; ilkbahar veya sonbaharda uygun budama teknikleri uygulanarak bitkinin şekli ve boyutu kontrol edilebilir. Ancak, zakkumun tüm kısımlarının zehirli olduğu göz önünde bulundurularak, bakımı sırasında dikkatli olunmalı ve çocukların ve evcil hayvanların erişiminden uzak tutulmalıdır.');";
        String data7 = "INSERT INTO flower (name, yer, bilgi) VALUES ('Müge Çiçeği ', 'karadeniz', 'Müge çiçeği doğrudan güneş ışığına ihtiyaç duymamaktadır. Müge çiçeğini dolaylı yollardan alabileceği bir alanda konumlandırmanız gerekmektedir. Yarı gölgeli alanlar müge çiçeği için oldukça elverişli alanlardır. Ancak dikkat edilmesi gereken durumlardan bir tanesi de müge çiçeğinin yanında başka bir çiçek yetiştirilmemesi gerektiğidir. Müge çiçeğinin bulunduğu ortamda tek başına olması ve herhangi bir cisme temas etmemesi gerekmektedir.\n" +
                "\n" +
                "Müge çiçeğini mutlaka aşırı sıcak veya soğuk havalardan korumanız gerekmektedir. Aynı zamanda hava akımına karşı da korumanız gerekmektedir. Müge çiçeğinin muazzam bir kokusu vardır.');";
        database.execSQL(data1);
        database.execSQL(data2);
        database.execSQL(data3);
        database.execSQL(data4);
        database.execSQL(data5);
        database.execSQL(data6);
        database.execSQL(data7);
    }
}
