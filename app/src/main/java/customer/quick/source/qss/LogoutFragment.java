package customer.quick.source.qss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LogoutFragment extends Fragment {
Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        Button yesButton = (Button) view.findViewById(R.id.yesButton);
        Button noButton = (Button) view.findViewById(R.id.noButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtilities.clearPrefsUrl(context,GeneralUtilities.USERID_KEY);
                GeneralUtilities.clearPrefsUrl(context,GeneralUtilities.PASSWORD_KEY);
                GeneralUtilities.clearPrefsUrl(context,GeneralUtilities.USERNAME_KEY);
                GeneralUtilities.clearPrefsUrl(context,GeneralUtilities.SEASSION_KEY);
                startActivity(new Intent(context, Login.class));
                getActivity().finish();
                Home.fa.finish();
            }
        });
        return view;
    }
}
