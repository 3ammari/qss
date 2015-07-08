package customer.quick.source.qss;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import net.danlew.android.joda.JodaTimeAndroid;

import org.apache.http.Header;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class MyService extends Service {
    String baseUrl;
    String email;
    String userID;
    String password;
    AsyncHttpClient client= new AsyncHttpClient();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(MyService.this,"Service started!",Toast.LENGTH_LONG).show();
        final boolean networkState= new GeneralUtilities(MyService.this).isNetworkConnected(MyService.this);
        baseUrl = GeneralUtilities.getFromPrefs(this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
        email=GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERNAME_KEY,"");
        password= GeneralUtilities.getFromPrefs(this,GeneralUtilities.PASSWORD_KEY,"");
        userID=GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERID_KEY,"");
        if (networkState) {

                //check if needed
            client.get(MyService.this, baseUrl + "locations", null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("-=-=-=", Integer.toString(statusCode));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("-=-=-=", responseString);
                    /*try{
                        Locations.deleteAll(Locations.class);
                    }catch(Exception e){e.printStackTrace();}*/
                    int x=0;
                    ArrayList<Locations> bufferList= new ArrayList<Locations>();
                    try {
                        JSONArray jsonArray= new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject= new JSONObject(jsonArray.getString(i));
                            String id=jsonObject.getString("id");
                            String name=jsonObject.getString("name");
                            Locations location= new Locations();
                            location.setLocationId(Integer.parseInt(id));
                            location.setName(name);
                            bufferList.add(location);
                            //location.save();
                        }
                    } catch (JSONException e) {
                        x++;
                        e.printStackTrace();
                    }
                    if (x==0){
                        try {
                            Locations.deleteAll(Locations.class);
                            Locations.saveInTx(bufferList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });


                //list of vehicles
            client.get(MyService.this,baseUrl+userID+"/vehicles",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("*******",Integer.toString(statusCode));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("********", responseString);
                    /*try{
                        Vehicles.deleteAll(Vehicles.class);
                        RecentServices.deleteAll(RecentServices.class);
                    }catch (Exception e){e.printStackTrace();}*/
                    ArrayList<Vehicles> bufferList= new ArrayList<Vehicles>();
                    int x=0;

                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            Vehicles vehicle = new Vehicles();
                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

                            String vehicleID=jsonObject.getString("id");
                            String fuel= jsonObject.getString("fuel");
                            String year = jsonObject.getString("year");
                            JSONObject modelObject=new JSONObject(jsonObject.getString("model"));
                            String model=modelObject.getString("name");
                            JSONObject makeObject= new JSONObject(modelObject.getString("make"));
                            String make= makeObject.getString("name");
                            Log.d("[4]",vehicleID+" "+fuel+" "+make+" "+make);

                            vehicle.setVehicleID(vehicleID);
                            vehicle.setFuel(fuel);
                            vehicle.setMake(make);
                            vehicle.setModel(model);
                            vehicle.setYear(year);

                            bufferList.add(vehicle);
                            //vehicle.save();
                        }
                    } catch (JSONException e) {
                        x++;
                        e.printStackTrace();
                    }
                    if (x==0){
                        try {
                            Vehicles.deleteAll(Vehicles.class);
                            Vehicles.saveInTx(bufferList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


                // // baseUrl+userID+"/services" http://www.mocky.io/v2/558b2c445f3dcb661406716d
            // fetching the recent services for every vehicle associated with this client
            client.get(MyService.this,baseUrl+userID+"/services",null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("///////", String.valueOf(statusCode));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("///////",responseString);
                    /*try {
                        RecentServices.deleteAll(RecentServices.class);
                    }catch (Exception e){e.printStackTrace();}*/
                    int x=0;
                    ArrayList<RecentServices> bufferList= new ArrayList<RecentServices>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {

                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                            String vehicleID=jsonObject.getString("id");
                            JSONObject servicesObject= new JSONObject(jsonObject.getString("services"));
                                String oilChangeDate="never";
                                String filterChangeDate="never";
                                String checkupDate="never";
                            if (servicesObject.has("1")){

                                oilChangeDate=((new JSONObject(servicesObject.getString("1"))).getString("created_at").split(" "))[0];
                            }
                            if(servicesObject.has("2")){
                                filterChangeDate=((new JSONObject(servicesObject.getString("2"))).getString("created_at").split(" "))[0];
                            }
                            if (servicesObject.has("3")){
                                checkupDate=((new JSONObject(servicesObject.getString("3"))).getString("created_at").split(" "))[0];
                            }


                            RecentServices recentServices = new RecentServices();
                            recentServices.setCheckUp(checkupDate);
                            recentServices.setFilterChange(filterChangeDate);
                            recentServices.setOilChange(oilChangeDate);
                            recentServices.setVehicleID(Integer.parseInt(vehicleID));
                            bufferList.add(recentServices);
                            //recentServices.save();




                            Log.d("[][][]", recentServices.toString());

                        }
                    } catch (JSONException e) {
                        x++;
                        e.printStackTrace();
                    }
                    if (x==0){
                        try {
                            RecentServices.deleteAll(RecentServices.class);
                            RecentServices.saveInTx(bufferList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            //fetching the stations and their details
            client.get(MyService.this,baseUrl+"stations/positions",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("[stations]", String.valueOf(statusCode));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("[stations]",responseString);
                    /*try {
                        Stations.deleteAll(Stations.class);
                    }catch (Exception e){e.printStackTrace();}*/
                    ArrayList<Stations> bufferList = new ArrayList<>();
                    int x=0;

                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject= new JSONObject(jsonArray.getString(i));
                            double lon =jsonObject.getDouble("longitude");
                            double lat = jsonObject.getDouble("latitude");
                            String idString = jsonObject.getString("station_id");
                            JSONObject stationObj=new JSONObject(jsonObject.getString("station"));
                            String stationName = stationObj.getString("name");
                            String stationLocation = stationObj.getString("address");
                            Log.d("mapsCoordinates", String.valueOf(lon));
                            Log.d("mapsCoordinates", String.valueOf(lat));
                            Stations station = new Stations();
                            station.setStationID(Integer.parseInt(idString));
                            station.setStationLat(lat);
                            station.setStationLong(lon);
                            station.setStationName(stationName);
                            station.setStationLocation(stationLocation);
                            bufferList.add(station);

                        }
                    } catch (JSONException e) {
                        x++;
                        e.printStackTrace();
                    }
                    if (x==0){
                        try {
                            Stations.deleteAll(Stations.class);
                            Stations.saveInTx(bufferList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            client.get(MyService.this,baseUrl+userID+"/services/types",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("[99]",Integer.toString(statusCode));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("[99]",responseString);
                   /*try {
                       ServicesTable.deleteAll(ServicesTable.class);
                   }catch (Exception e){e.printStackTrace();}*/
                        int x=0;
                    ArrayList<ServicesTable> bufferList = new ArrayList<ServicesTable>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject= new JSONObject(jsonArray.getString(i));
                            String type = jsonObject.getString("name");
                            String id = jsonObject.getString("id");
                            ServicesTable servicesTable = new ServicesTable();
                            servicesTable.setServiceType(type);
                            servicesTable.setServiceTypeID(id);
                            servicesTable.setCycle("3");
                            bufferList.add(servicesTable);


//                            servicesTable.save();

                        }
                    } catch (JSONException e) {
                        x++;
                        e.printStackTrace();
                    }
                    if (x==0){
                        try {
                            ServicesTable.deleteAll(ServicesTable.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ServicesTable.saveInTx(bufferList);

                    }
                }
            });


            //fetching rewards
            client.get(MyService.this,baseUrl+userID+"/points",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.d("[rewards_points]",responseString);
                    try {
                        JSONObject jsonObject= new JSONObject(responseString);
                        String points=jsonObject.getString("points");
                        GeneralUtilities.saveToPrefs(MyService.this,"POINTS",points);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // check oil change
        /*ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                JodaTimeAndroid.init(MyService.this);

                List<RecentServices> recentServicesList=RecentServices.find(RecentServices.class,"service_type = ?","Filter Change");
                String recentServiceDate=recentServicesList.get(recentServicesList.size()-1).getDate();
                String date[]=recentServiceDate.split(" ");
                String date1= date[0];
                Log.d("[11]",date1);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
                LocalDate localDate = dtf.parseLocalDate(date1);
                LocalDate currentDate = new LocalDate();
                Period period1= new Period(localDate,currentDate);
                //Period period = Period.fieldDifference(localDate.dayOfMonth().withMaximumValue(),currentDate.dayOfMonth().withMinimumValue());
                // Period period = Period.fieldDifference(currentDate.dayOfMonth().withMaximumValue(),localDate.dayOfMonth().withMinimumValue());
                Log.d("[1]", currentDate.getDayOfMonth()+"\n"+String.valueOf(period1.getDays()));
                if(period1.getDays()>=1){
                    Intent intent = new Intent(MyService.this,FindStation.class);
                    PendingIntent pendingIntent= PendingIntent.getActivity(MyService.this, 0, intent, 0);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this);
                    Notification notification=builder.setContentTitle("content title").setContentText("content Text").setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(++filterNotificationID).build();
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    nm.notify(filterNotificationID++,notification);
                    filterNotificationID++;
                    GeneralUtilities.saveToPrefs(MyService.this,GeneralUtilities.KEY, String.valueOf(filterNotificationID));


                }



            }
        },0,30, TimeUnit.SECONDS);*/




        return START_STICKY;
    }
}
