package customer.quick.source.qss;

import android.app.Service;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import customer.quick.source.qss.ObjectsORM.Locations;
import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.ObjectsORM.RewardORM;
import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.ObjectsORM.Stations;
import customer.quick.source.qss.ObjectsORM.Vehicles;
import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.adapters.RewardObject;
import customer.quick.source.qss.adapters.RewardsAdapter;

//This service is responsible of fetching the data from the server ,all the data come from the server from different pages
// several requests are being made every time this service is started
// this service is usually called from the AlarmService when there's data coming from the server
// this service is not sticky , means that if the android system shuts it down it will not bother starting it again

// the local variable x is to ensure that there was no exception during the parsing and it will save the data ONLY and ONLY if x=0
//x is incremented in the catch blocks, if no catch block is called then the data is correct in it is safe to drop old tables and save the new ones
public class MyService extends Service {
    String baseUrl;
    String email;
    String userID;
    String password;
    int errors=0;
    private static final String TAG = "MY_SERVICE";
    AsyncHttpClient client= new AsyncHttpClient();
    SyncHttpClient syncClient= new SyncHttpClient();
    public static final String DONE="DONE_SUCCESS";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        /*Toast.makeText(MyService.this,"Service started!",Toast.LENGTH_LONG).show();*/
        final boolean networkState= new GeneralUtilities(MyService.this).isNetworkConnected(MyService.this);
        baseUrl=GeneralUtilities.BASE_URL;
        email=GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERNAME_KEY,"");
        password= GeneralUtilities.getFromPrefs(this,GeneralUtilities.PASSWORD_KEY,"");
        userID=GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERID_KEY,"");
        BackgroundAsync backgroundAsync= new BackgroundAsync();
        backgroundAsync.execute();
        if(action!=null){
           if (action.equals(Login.ACTION_INITIALIZE)&&errors==0){
               sendBroadcast(new Intent(DONE));
           }
       }





        return START_NOT_STICKY;
    }

    private void fetchInSyncMode(boolean networkState) {
        if (networkState) {

                syncClient.addHeader("Authorization", "Bearer " + GeneralUtilities.getFromPrefs(MyService.this, GeneralUtilities.TOKEN_KEY, null));
            syncClient.get(MyService.this, baseUrl + "locations", null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d(TAG, Integer.toString(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);

                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG, responseString);
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
                        errors++;
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
            syncClient.get(MyService.this,baseUrl+userID+"/vehicles",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("*******",Integer.toString(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
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

                            int vehicleID=jsonObject.getInt("id");
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
                        errors++;
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
            syncClient.get(MyService.this,baseUrl+userID+"/services",null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d(TAG, String.valueOf(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG,responseString);
                    /*try {
                        RecentServices.deleteAll(RecentServices.class);
                    }catch (Exception e){e.printStackTrace();}*/
                    int x=0;
                    ArrayList<RecentServices> bufferList= new ArrayList<RecentServices>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {

                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                            int vehicleID=jsonObject.getInt("vehicle_id");
                            int serviceTypeID=jsonObject.getInt("service_type_id");
                            int serviceID=jsonObject.getInt("id");
                            String dateString=jsonObject.getString("created_at");
                            //dateString = dateString.split(" ")[0];
                            RecentServices recentServices= new RecentServices();
                            recentServices.setDate(dateString);
                            recentServices.setServiceTypeID(serviceTypeID);
                            recentServices.setVehicleID(vehicleID);
                            recentServices.setServiceID(serviceID);
                            bufferList.add(recentServices);

                            /*JSONArray servicesObject= new JSONArray(jsonObject.getString("services"));
                            JSONArray jsonArray1 = new JSONArray(servicesObject.toString());*/
                            /*for (int j = 0; j <servicesObject.length() ; j++) {
                                JSONObject jsonObject1 = new JSONObject(servicesObject.getString(j));
                                int serviceTypeID = jsonObject1.getInt("service_type_id");
                                String date = jsonObject1.getString("created_at");
                               // date = date.split(" ")[0];
                                RecentServices recentServices = new RecentServices();
                                recentServices.setServiceTypeID(serviceTypeID);
                                recentServices.setVehicleID(Integer.parseInt(vehicleID));
                                recentServices.setDate(date);
                                bufferList.add(recentServices);
                            }*/




                            //recentServices.save();






                        }
                    } catch (JSONException e) {
                        x++;
                        errors++;
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
            syncClient.get(MyService.this,baseUrl+"stations/positions",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("[stations]", String.valueOf(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
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
                            int id = jsonObject.getInt("id");
                            String name= jsonObject.getString("name");
                            /*JSONObject stationObj=new JSONObject(jsonObject.getString("station"));
                            String stationName = stationObj.getString("name");
                            String stationLocation = stationObj.getString("address");*/
                            Log.d("mapsCoordinates", String.valueOf(lon));
                            Log.d("mapsCoordinates", String.valueOf(lat));
                            Stations station = new Stations();
                            station.setStationID(id);
                            station.setStationLat(lat);
                            station.setStationLong(lon);
                            station.setStationName(name);
                            station.setStationLocation(name);
                            bufferList.add(station);

                        }
                    } catch (JSONException e) {
                        x++;
                        errors++;
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

            syncClient.get(MyService.this,baseUrl+userID+"/services/types",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("[99]",Integer.toString(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
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
                            int id = jsonObject.getInt("id");
                            ServicesTable servicesTable = new ServicesTable();
                            servicesTable.setServiceType(type);
                            servicesTable.setServiceTypeID(id);
                            bufferList.add(servicesTable);


//                            servicesTable.save();

                        }
                    } catch (JSONException e) {
                        x++;
                        errors++;
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

            //preferences for reminders
            syncClient.get(MyService.this, baseUrl + userID + "/preferences/reminders", null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("[preferences]", responseString);
                    int x=0;
                    ArrayList <RemindersPreferencesORM> bufferList= new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                            int serviceTypeID = jsonObject.getInt("service_type_id");
                            int period = jsonObject.getInt("period");
                            RemindersPreferencesORM remindersPreferencesORM = new RemindersPreferencesORM();
                            remindersPreferencesORM.setServiceTypeID(serviceTypeID);
                            remindersPreferencesORM.setPeriod(period);
                            bufferList.add(remindersPreferencesORM);

                        }
                    } catch (JSONException e) {
                        x++;
                        errors++;
                        e.printStackTrace();
                    }

                    if (x==0){
                        RemindersPreferencesORM.deleteAll(RemindersPreferencesORM.class);
                        RemindersPreferencesORM.saveInTx(bufferList);
                    }



                }
            });



            //fetching rewards
            syncClient.get(MyService.this,baseUrl+userID+"/points",null,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
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
                        errors++;
                    }
                }
            });

            syncClient.get(this,baseUrl+"rewards",null,new TextHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("[rewards]",Integer.toString(statusCode));
                    errors++;
                    if (statusCode==401){
                        GeneralUtilities.refreshToken(MyService.this);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("[rewards]",responseString);
                    ArrayList<RewardORM> rewardORMs= new ArrayList<RewardORM>();
                    int x=0;
                    try {
                        JSONArray jsonArray = new JSONArray(responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                            String title=jsonObject.getString("title");
                            String cost= jsonObject.getString("cost");
                            String description = jsonObject.getString("description");
                            RewardORM rewardORM= new RewardORM();
                            rewardORM.setTitle(title);
                            rewardORM.setDescription(description);
                            rewardORM.setPrice(Integer.parseInt(cost));
                            rewardORMs.add(rewardORM);
                        }
;



                    } catch (JSONException e) {
                        x++;
                        errors++;
                        e.printStackTrace();
                    }

                    if (x==0){
                        RewardORM.deleteAll(RewardORM.class);
                        RewardORM.saveInTx(rewardORMs);
                    }
                }
            });



        }
    }
    private class BackgroundAsync extends AsyncTask<Void,Void,Void>{



        @Override
        protected Void doInBackground(Void... params) {
            fetchInSyncMode(true);

            return null;
        }
    }
}
