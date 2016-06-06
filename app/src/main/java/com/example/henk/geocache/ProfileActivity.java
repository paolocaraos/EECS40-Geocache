package com.example.henk.geocache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProfileActivity extends AppCompatActivity {

    private Trainer trainer;

    private Button backToMaps;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        trainer = (Trainer) getIntent().getSerializableExtra("Trainer");

        backToMaps = (Button) findViewById(R.id.backToMaps);
        backToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("Trainer", trainer);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, trainer.getListOfCaughtPokemon());
        listView.setAdapter(adapter);
    }
}
