package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    TextView debugText;

    Button pistone1,pistone2,pistone3;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debugText = findViewById(R.id.debugText);
        debugText.setText(R.string.debugTextCiao);


        pistone1 = findViewById(R.id.pistone1);
        pistone2 = findViewById(R.id.pistone2);
        pistone3 = findViewById(R.id.pistone3);

        pistone1.setOnClickListener( v-> piston_press(1));
        pistone2.setOnClickListener(v -> piston_press(2));
        pistone3.setOnClickListener(v -> piston_press(3));
    }

    public void piston_effect() {
        StringBuilder debugPistoni = new StringBuilder();
        for (int i = 0; i<3; i++) {
            if (pistoni_premuti[i]){
                debugPistoni.append("pistone ").append(i + 1);
            }
        }
        debugText.setText(debugPistoni.toString());
    }

    public void piston_press(int qualePistone){
        // I pistoni di una tromba sono 1,2,3 ma le liste in Java iniziano da 0
        pistoni_premuti[qualePistone-1] = true;
        piston_effect();
    }
}