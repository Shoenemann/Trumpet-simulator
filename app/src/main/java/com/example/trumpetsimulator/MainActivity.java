package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.SoundPool;
import android.view.View;
import android.view.MotionEvent;
import android.os.Bundle;
//import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    private TextView debugText;

    View pistone1,pistone2,pistone3;
    View aria;

    private SoundPool soundPool;
    int tmpSound;


    // ISSUE:
    // il programma non funzionera se non si hanno almeno 4 tasti multitouch sul dipositivo
    // ma soprattutto se ci sono funzioni speciali a tre tasti.
    // Come disabilitare temporaneamente le funzioni speciali multitouch?
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debugText = findViewById(R.id.debugText);
        debugText.setText(R.string.debugTextCiao);

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();

        tmpSound = soundPool.load(this,R.raw.d,1);

        aria = findViewById(R.id.aria);

        aria.setOnTouchListener(this::aria_touch);

        pistone1 = findViewById(R.id.pistone1);
        pistone2 = findViewById(R.id.pistone2);
        pistone3 = findViewById(R.id.pistone3);

        pistone1.setOnTouchListener((v, event) -> piston_touch(v,event,1));
        pistone2.setOnTouchListener((v, event) -> piston_touch(v,event,2));
        pistone3.setOnTouchListener((v, event) -> piston_touch(v,event,3));
    }

    public boolean aria_touch(View v,MotionEvent event) {
        v.performClick();
        int sound = calculateSound();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            soundPool.play(sound,1,1,0,0,1);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            soundPool.stop(sound);
        }
        return true;
    }

    public int calculateSound() {
        return tmpSound;
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



    public boolean piston_touch(View v, MotionEvent event, int qualePistone){
        // praticamente un onTouchListener
        v.performClick();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            piston_press(qualePistone);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            piston_release(qualePistone);
        }
        return true;
    }

    public void piston_press(int qualePistone){
        // I pistoni di una tromba sono 1,2,3 ma le liste in Java iniziano da 0
        pistoni_premuti[qualePistone-1] = true;
        piston_effect();
    }
    public void piston_release(int qualePistone){
        // I pistoni di una tromba sono 1,2,3 ma le liste in Java iniziano da 0
        pistoni_premuti[qualePistone-1] = false;
        piston_effect();
    }
}