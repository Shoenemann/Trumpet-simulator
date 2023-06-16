package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    TextView debugText;
    int a;

    Button pistone1,pistone2,pistone3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debugText = findViewById(R.id.debugText);
        debugText.setText("ciao");


        pistone1 = findViewById(R.id.pistone1);
        pistone2 = findViewById(R.id.pistone2);
        pistone3 = findViewById(R.id.pistone3);

        pistone1.setOnClickListener( v-> piston_press(1));
        pistone2.setOnClickListener(v -> piston_press(2));
        pistone3.setOnClickListener(v -> piston_press(3));
    }

    public void piston_effect() {
        String debugPistoni = "";
        for (int i = 0; i<3; i++) {
            if (pistoni_premuti[i]){
                debugPistoni += "pistone " +String.valueOf(i+1) ;
            }
        }
        debugText.setText(debugPistoni);
    }

    public void piston_press(int qualePistone){
        // I pistoni di una tromba sono 1,2,3 ma le liste in Java iniziano da 0
        pistoni_premuti[qualePistone-1] = true;
        piston_effect();
    }
}