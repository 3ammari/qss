package customer.quick.source.qss;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.app.Fragment;

import net.glxn.qrgen.android.QRCode;


public class UserIDShowQR extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_user_idshow_qr,container,false);
        String userID=GeneralUtilities.getFromPrefs(super.getActivity(),GeneralUtilities.USERID_KEY,"");

        Bitmap myBitmap = QRCode.from(userID).bitmap();
        ImageView myImage = (ImageView) view.findViewById(R.id.qrBitmap);
        myImage.setImageBitmap(myBitmap);
        return view;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_idshow_qr);
        String userID=GeneralUtilities.getFromPrefs(UserIDShowQR.this,GeneralUtilities.USERID_KEY,"");

        Bitmap myBitmap = QRCode.from(userID).bitmap();
        ImageView myImage = (ImageView) findViewById(R.id.qrBitmap);
        myImage.setImageBitmap(myBitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_idshow_qr, menu);
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
}
