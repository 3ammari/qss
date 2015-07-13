package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 13/07/15.
 */
public class RemindersPreferencesORM extends SugarRecord<RemindersPreferencesORM> {

    private int serviceTypeID;
    private int period;

    public RemindersPreferencesORM() {
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
