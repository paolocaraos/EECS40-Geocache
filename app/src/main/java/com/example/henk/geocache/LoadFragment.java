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
public class LoadFragment extends Fragment implements View.OnClickListener {
    Button resumeGame;
    Button loadUser;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.load_fragment,container,false);
        resumeGame = (Button) view.findViewById(R.id.resume);
        resumeGame.setOnClickListener(this);
        loadUser = (Button) view.findViewById(R.id.loadUser);
        loadUser.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String button_text;
        button_text = ((Button) v).getText().toString();
        if(button_text.equals("Resume Game")) {
            /*need to FIND the username data to pass to the next activity*/
            Intent gotoMaps = new Intent(getActivity(), MapsActivity.class);
            startActivity(gotoMaps);
        }
        else if(button_text.equals("Load User")) {
            /*load user information*/
        }

    }
}

