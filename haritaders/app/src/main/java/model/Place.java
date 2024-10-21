package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String yername;
    @ColumnInfo(name = "latitude")
    public Double latitude;
    @ColumnInfo(name = "longitude")
    public Double longitude;

    public Place( String yername, Double latitude, Double longitude) {

        this.yername = yername;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
