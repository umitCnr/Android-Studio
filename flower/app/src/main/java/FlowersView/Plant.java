package FlowersView;

import com.google.gson.annotations.SerializedName;

public class Plant {
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @SerializedName("common_name")
    private String commonName;

    @SerializedName("scientific_name")
    private String scientificName;
    @SerializedName("image_url")
    private String imageUrl;
}
