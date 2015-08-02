package customer.quick.source.qss;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.adapters.SingleVehilcleAdapter;


public class VehicleStatus extends ActionBarActivity {
    ListView servicesListView;
    public static final int PICK_PICTURE=213;
    public static final int TAKE_PICTURE=523;
    ImageView imageView;
    Dialog dialog;
    int vehicleID;
    String dir;
    File newdir;
    int count=0;
    private static final String TAG="VEHICLE_STATUS_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vehicle_status);
        servicesListView = (ListView) findViewById(R.id.servicesListView);
        imageView = (ImageView) findViewById(R.id.vehicleImage);
        imageView.setClickable(true);
        /*vehicleID=1;*/
        vehicleID = getIntent().getExtras().getInt("vehicleID");
        try{
            Uri uri=Uri.parse(GeneralUtilities.getFromPrefs(VehicleStatus.this,GeneralUtilities.IMAGE_URI_KEY+String.valueOf(vehicleID),"none"));
            imageView.setImageURI(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog= new Dialog(VehicleStatus.this);
                dialog.setContentView(R.layout.vehicle_photo_dialog_fragment);
                Button cameraButton = (Button) dialog.findViewById(R.id.cameraButton);
                Button galleryButton = (Button) dialog.findViewById(R.id.openGalleryButton);
                Log.d(TAG,"just before dialog.show()");
                dialog.show();
                galleryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PICTURE);

                    }
                });
                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
                        newdir= new File(dir);
                        newdir.mkdir();
                        count++;
                        String file = dir+count+".jpg";
                        File newFile = new File(file);
                        try {
                            newFile.createNewFile();
                        } catch (IOException e) {}

                        Uri outputFileUri = Uri.fromFile(newFile);

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                    }
                });

            }
        });

        

        Log.d(TAG, String.valueOf(vehicleID));
        List<RecentServices> recentServicesList= RecentServices.find(RecentServices.class, "vehicle_id = ?", String.valueOf(vehicleID));
        Log.d(TAG, String.valueOf(recentServicesList.size()));
        servicesListView.setAdapter(new SingleVehilcleAdapter(this,recentServicesList));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_PICTURE&&resultCode==RESULT_OK){
            try {
                Uri uri = data.getData();
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                imageView.setImageURI(uri);
                GeneralUtilities.saveToPrefs(VehicleStatus.this,GeneralUtilities.IMAGE_URI_KEY+String.valueOf(vehicleID),uri.toString());


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode==TAKE_PICTURE&&resultCode==RESULT_OK){
            Uri uri= data.getData();
            imageView.setImageURI(uri);
            GeneralUtilities.saveToPrefs(VehicleStatus.this,GeneralUtilities.IMAGE_URI_KEY+String.valueOf(vehicleID),uri.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
