package HavaDurumu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface theInterface {
    @GET("weather?lat=39.57&lon=32,53&appid=40a52fb19945df8445110dc13adc6a40")
    Call<Model> getModel();
}
