package FlowersView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlantResponse {

    @SerializedName("data")
    private List<Plant> plants;

    public List<Plant> getPlants() {
        return plants;
    }
}
