package customer.quick.source.qss.adapters;

/**
 * Created by abdul-rahman on 08/07/15.
 */

public class PreferenceElement{
    String serviceType;
    int period;

    public PreferenceElement(int period, String serviceType) {
        this.period = period;
        this.serviceType = serviceType;
    }
}
