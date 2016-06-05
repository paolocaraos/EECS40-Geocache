package com.example.henk.geocache;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Henk on 2016/6/5.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    Button regAndStart;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_fragment,container,false);
        regAndStart = (Button) view.findViewById(R.id.regStart);
        regAndStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        /*need to CREATE the username data and pass to the next activity*/
        Intent gotoMaps = new Intent(getActivity(), MapsActivity.class);
        startActivity(gotoMaps);
    }
}
