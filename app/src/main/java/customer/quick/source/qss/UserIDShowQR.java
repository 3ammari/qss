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
        try {
            String userID=GeneralUtilities.getFromPrefs(super.getActivity(),GeneralUtilities.USERID_KEY,"");

            Bitmap myBitmap = QRCode.from(userID).bitmap();
            ImageView myImage = (ImageView) view.findViewById(R.id.qrBitmap);
            myImage.setImageBitmap(myBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


}
