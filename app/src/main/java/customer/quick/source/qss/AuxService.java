package customer.quick.source.qss;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 27/06/15.
 */
public class AuxService extends SugarRecord<AuxService> {
    int vehicleID;
    String serviceType;
    String serviceDate;

    public AuxService() {
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
}
