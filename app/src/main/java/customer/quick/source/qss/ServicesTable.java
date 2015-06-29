package customer.quick.source.qss;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 21/06/15.
 */
public class ServicesTable extends SugarRecord<ServicesTable> {
    String serviceType;
    String serviceTypeID;
    String cycle;

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public ServicesTable() {
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(String serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }
}
