package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    TextView debugText = (TextView) findViewById(R.id.debugText);
    Button [] pistoni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pistoni[0] = (Button) findViewById(R.id.pistone1);
        pistoni[1] = (Button) findViewById(R.id.pistone2);
        pistoni[2] = (Button) findViewById(R.id.pistone3);
        for (int i=0; i<3;i++) {
            pistoni[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    piston_press(i+1);
                }
            });
        }
    }

    public void piston_effect() {
        String debugPistoni = "";
        for (int i = 0; i<3; i++) {
            if (pistoni_premuti(i)){
                debugPistoni += "pistone " +String.valueOf(i+1) ;
            }
        }
        debugText.setText(debugPistoni);
    }

    public void piston_press(int qualePistone){
        // I pistoni di una tromba sono 1,2,3 ma le liste in Java iniziano da 0
        pistoni_premuti[qualePistone-1] = true;
        pistoni();
    }
}