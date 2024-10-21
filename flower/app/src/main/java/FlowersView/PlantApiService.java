package FlowersView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlantApiService {
    @GET("search?token=-cAgimhxEOO--QjYiiGT9vq6izABMy2mNEUQFRmwKoI&q=flower")
    Call<PlantResponse> searchPlants();
}
