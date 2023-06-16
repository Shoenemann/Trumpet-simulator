package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    TextView debugText;
    Button pistone1,pistone2,pistone3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debugText = (TextView) findViewById(R.id.debugText);
        //debugText.setText("ciao");

        pistone1 = (Button) findViewById(R.id.pistone1);
        pistone2 = (Button) findViewById(R.id.pistone2);
        pistone3 = (Button) findViewById(R.id.pistone3);
    }
}