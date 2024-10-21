package HavaDurumu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    double sicaklik;
    @SerializedName("feels_like")
    @Expose
    double hissedilen;
    @SerializedName("humidity")
    @Expose
    int nem;

}
