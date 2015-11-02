package customer.quick.source.qss;

import android.app.Application;
import android.util.Log;

import com.orm.SugarApp;

import org.acra.*;
import org.acra.annotation.*;
/**
 * Created by abdul-rahman on 03/10/15.
 */
/*@ReportsCrashes(formKey = "",formUri ="http://192.168.1.115/acra-qss/_design/acra-storage/_update/report",reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT )*/
@ReportsCrashes(
        formKey = "",
        formUri = "https://3ammari.cloudant.com/acra-qss/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="therionstleardstainstord",
        formUriBasicAuthPassword="32294c5141f73e53d88078166bec3d26732c273f")

public class QSS extends SugarApp {
    @Override
    public void onCreate() {
        ACRA.init(this);
        Log.d("ACRA_INITIALIZED","acra");
        super.onCreate();

    }
}
