package com.example.henk.geocache;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Henk on 2016/6/5.
 */
public class LoadFragment extends Fragment implements View.OnClickListener {
    Button loadUser;
    View view;
    EditText oldUsername;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.load_fragment,container,false);
        loadUser = (Button) view.findViewById(R.id.loadUser);
        loadUser.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Trainer trainer = new Trainer();
        /*gets username*/
        oldUsername = (EditText) view.findViewById(R.id.oldUsername);
        String string = oldUsername.getText().toString();
        oldUsername.setText(" ");

        /*access file in storage*/
        File folder = new File ("sdcard/Geocache/user_profile");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String fileName = string + ".txt";
        File player_file = new File (folder,fileName);

        /*gets trainer object*/
        if(!player_file.exists()){
            /*error message*/
            CharSequence fText = "Username hasn't been created yet. Please try again";
            int fDuration = Toast.LENGTH_LONG;
            Context fContext = getActivity();
            Toast fToast = Toast.makeText(fContext,fText,fDuration);
            fToast.show();
        }
        else if(player_file.exists()){
            try{
                FileInputStream fis = new FileInputStream(player_file.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                trainer = (Trainer)ois.readObject();
                ois.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
            /*passing trainer obj to maps*/
            Intent resultIntent = new Intent(getActivity(), MapsActivity.class);
            resultIntent.putExtra("Trainer", trainer);
            startActivity(resultIntent);
        }

    }
}

