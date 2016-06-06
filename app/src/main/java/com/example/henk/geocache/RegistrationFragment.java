package com.example.henk.geocache;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Henk on 2016/6/5.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    Button regAndStart;
    View view;
    EditText newUsername;
    Trainer trainer;
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
        /*need to CREATE the username data*/
        newUsername = (EditText) view.findViewById(R.id.newUsername);
        String string = newUsername.getText().toString();
        trainer = ((MainActivity)getActivity()).getTrainer().setName(string);
        File folder = new File ("sdcard/Geocache/user_profile");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String fileName = string + ".txt";
        File player_file = new File (folder,fileName);

        try {
            FileOutputStream fos = new FileOutputStream(player_file.getAbsolutePath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trainer);
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }



        Intent gotoMaps = new Intent(getActivity(), MapsActivity.class);
        startActivity(gotoMaps);
    }
}
