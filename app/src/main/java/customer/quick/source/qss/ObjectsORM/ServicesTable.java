package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 21/06/15.
 */
public class ServicesTable extends SugarRecord<ServicesTable> {
    String serviceType;
    int serviceTypeID;



    public ServicesTable() {
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }
}
