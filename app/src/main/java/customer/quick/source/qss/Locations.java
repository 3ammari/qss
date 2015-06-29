package customer.quick.source.qss;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 19/06/15.
 */
public class Locations extends SugarRecord<Locations> {
    String name;
    int locationId;

    public Locations() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
