package com.example.henk.geocache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Button finishButton = (Button)findViewById(R.id.button3);
        finishButton.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v)
        {
            //do the same stuff or use switch/case and get each button ID and do different
            finish();
            //stuff depending on the ID
        }

    };
}
