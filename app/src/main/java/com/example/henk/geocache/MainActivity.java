package com.example.henk.geocache;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Henk on 2016/6/4.
 */
public class MainActivity extends Activity {
    TextView textView;
    Context context = this;

    public class Constants{
        static final int MAPS_REQUEST_CODE = 20;
    }

    private Trainer trainer;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trainer = new Trainer();
        textView = (TextView) findViewById(R.id.reg_user);

        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoadFragment loadFragment = new LoadFragment();
        fragmentTransaction.add(R.id.fragment_container,loadFragment);
        fragmentTransaction.commit();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegistrationFragment registrationFragment = new RegistrationFragment();
                fragmentTransaction.replace(R.id.fragment_container,registrationFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (Constants.MAPS_REQUEST_CODE): {
                if (resultCode == Activity.RESULT_OK) {
                    trainer = (Trainer) data.getSerializableExtra("Trainer");

                }
                break;
            }
        }
    }

    Trainer getTrainer(){
        return trainer;
    }
}
