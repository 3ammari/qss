package customer.quick.source.qss;

import android.app.Application;

import com.orm.SugarApp;

import org.acra.*;
import org.acra.annotation.*;
/**
 * Created by abdul-rahman on 03/10/15.
 */
@ReportsCrashes(formKey = "",formUri ="http://10.0.3.2:5984/acra-qss/_design/acra-storage/_update/report",reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT )

public class QSS extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
