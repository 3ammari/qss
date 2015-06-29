package customer.quick.source.qss;

import com.orm.SugarRecord;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * Created by abdul-rahman on 23/06/15.
 */
public class RecentServices extends SugarRecord<RecentServices> {
    int vehicleID;

    String oilChange;
    String filterChange;
    String checkUp;

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }


    public String getOilChange() {
        return oilChange;
    }

    public void setOilChange(String oilChange) {
        this.oilChange = oilChange;
    }

    public String getFilterChange() {
        return filterChange;
    }

    public void setFilterChange(String filterChange) {
        this.filterChange = filterChange;
    }

    public String getCheckUp() {
        return checkUp;
    }

    public void setCheckUp(String checkUp) {
        this.checkUp = checkUp;
    }

    public RecentServices() {
    }

   }
