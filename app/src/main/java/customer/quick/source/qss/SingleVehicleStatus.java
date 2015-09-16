package customer.quick.source.qss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.adapters.SingleVehicleAdapter;


public class SingleVehicleStatus extends ActionBarActivity {
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
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vehicle_status);
        context=this;
        servicesListView = (ListView) findViewById(R.id.servicesListView);
        imageView = (ImageView) findViewById(R.id.vehicleImage);
        imageView.setClickable(true);
/*
        vehicleID=1;
*/
        vehicleID = getIntent().getExtras().getInt("vehicleID");
        Log.d(TAG, String.valueOf(vehicleID));
        try{
            Uri uri=Uri.parse(GeneralUtilities.getFromPrefs(SingleVehicleStatus.this,GeneralUtilities.IMAGE_URI_KEY+String.valueOf(vehicleID),"none"));
            imageView.setImageURI(uri);
        }catch (Exception e){
            Resources resources= getResources();
            imageView.setImageDrawable(resources.getDrawable(R.drawable.rsz_02acd9e93753acffa98fd7198c7ee4e4));
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog= new Dialog(SingleVehicleStatus.this);
                dialog.setContentView(R.layout.vehicle_photo_dialog_fragment);
                dialog.setCancelable(true);
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
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


                        String file = dir+timeStamp+".jpg";
                        File newFile = new File(file);
                        try {
                            newFile.createNewFile();
                            Log.d(TAG,"File created");

                        } catch (IOException e) {e.printStackTrace();}

                        Uri outputFileUri = Uri.fromFile(newFile);

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                    }
                });

            }
        });

        

        Log.d(TAG, String.valueOf(vehicleID));
        List<RecentServices> recentServicesList= RecentServices.find(RecentServices.class, "vehicle_id = ?", String.valueOf(vehicleID));
        Log.d(TAG, String.valueOf(recentServicesList.size()));
        servicesListView.setAdapter(new SingleVehicleAdapter(this,recentServicesList));


    }

/*
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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_PICTURE&&resultCode==RESULT_OK){
            try {
                Uri uri = data.getData();
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
                newdir= new File(dir);
                newdir.mkdir();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


                String file = dir+timeStamp+".jpg";
                File newFile = new File(file);
                try {
                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri1 = Uri.fromFile(newFile);

                GeneralUtilities.saveToPrefs(SingleVehicleStatus.this,GeneralUtilities.IMAGE_URI_KEY+String.valueOf(vehicleID),uri1.toString());


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode==TAKE_PICTURE&&resultCode==RESULT_OK){
           // Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d(TAG,data.toString());
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            /*InputStream inputStream = null;
            try {
                inputStream = this.getContentResolver().openInputStream((Uri) data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
*/

            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
            newdir= new File(dir);
            newdir.mkdir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


            String file = dir+timeStamp+".jpg";
            File newFile = new File(file);
            try {
                FileOutputStream outputStream = new FileOutputStream(newFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri uri1 = Uri.fromFile(newFile);
            imageView.setImageURI(uri1);
            Log.d(TAG, String.valueOf(vehicleID));
            Log.d(TAG, uri1.toString());
            imageView.setImageBitmap(bitmap);
            Log.d(TAG, context.getPackageCodePath());
            GeneralUtilities.saveToPrefs(SingleVehicleStatus.this, GeneralUtilities.IMAGE_URI_KEY + String.valueOf(vehicleID), uri1.toString());
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

    public void assignPhoto(View view) {
        dialog= new Dialog(SingleVehicleStatus.this);
        dialog.setContentView(R.layout.vehicle_photo_dialog_fragment);
        dialog.setCancelable(true);
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
});}

}

