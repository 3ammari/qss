package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 19/06/15.
 */
public class Stations extends SugarRecord<Stations> {
    String stationName;
    String stationLocation;
    double stationLat;
    double stationLong;
    int stationID;

    public Stations() {
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationLocation() {
        return stationLocation;
    }

    public void setStationLocation(String stationLocation) {
        this.stationLocation = stationLocation;
    }

    public double getStationLat() {
        return stationLat;
    }

    public void setStationLat(double stationLat) {
        this.stationLat = stationLat;
    }

    public double getStationLong() {
        return stationLong;
    }

    public void setStationLong(double stationLong) {
        this.stationLong = stationLong;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }
}
