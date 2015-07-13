package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 23/06/15.
 */
public class RecentServices extends SugarRecord<RecentServices> {
    int vehicleID;

    String date;

    int serviceTypeID;

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public RecentServices() {
    }

   }
