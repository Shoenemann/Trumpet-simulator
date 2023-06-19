package com.example.trumpetsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.SoundPool;
import android.view.View;
import android.view.MotionEvent;
import android.os.Bundle;
//import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    boolean [] pistoni_premuti = {false,false,false};
    int semitoni_giu = 0;
    float intonazione = 440F;
    float [] temperamento = {1,15F/16,8F/9,5F/6,4F/5,3F/4,32F/45};
    private TextView debugText;

    View pistone1,pistone2,pistone3;
    View aria;
    Integer airX=0,airY=0;
    int airXMax, airYMax;

    private SoundPool soundPool;
    int tmpSound;
    private int streamId = 0; // id dello stream sonoro che sta venendo riprodotto al momento


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
        airXMax = 400;
        airYMax = 600;

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
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            airX = (int) event.getX();
            airY = (int) event.getY();
            debugText.setText(debugText.getText() + "\n" + airX.toString() + "," + airY.toString());
            playSound();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            soundPool.stop(streamId);
            streamId = 0;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            airX = (int) event.getX();
            airY = (int) event.getY();
            modifySound();
        }
        return true;
    }

    public void playSound(){
        Float vol = calculateVolume();
        debugText.setText(debugText.getText() + " " + vol.toString());
        int sound = calculateSound();
        float rate = calculateSoundRate();
        //loop -1 is to make it repeat ad infinitum
        streamId = soundPool.play(sound,vol,vol,0,0, rate);
    }
    public void changeSound(){
        float rate = calculateSoundRate();
        soundPool.setRate(streamId,rate);
    }
    public void modifySound(){
        Float vol = calculateVolume();
        soundPool.setVolume(streamId,vol,vol);
    }
    public float calculateVolume() {
        return (float) Math.min(1F*airX/airXMax,1);
    }
    public int calculateSound() {
        return tmpSound;
    }
    public float calculateSoundRate() {
        float a = intonazione/440;
        float b = temperamento[semitoni_giu];
        return a*b;
    }
    public void calculateSemitoni() {
        int r = 0;
        if (pistoni_premuti[0]) {
            r+=2;
        }
        if (pistoni_premuti[1]) {
            r+=1;
        }
        if (pistoni_premuti[2]) {
            r+=3;
        }
        semitoni_giu = r;
    }

    public void piston_effect() {
        StringBuilder debugPistoni = new StringBuilder();
        for (int i = 0; i<3; i++) {
            if (pistoni_premuti[i]){
                debugPistoni.append("\npistone ").append(i + 1);
            }
        }
        calculateSemitoni();
        if (streamId != 0) {
            changeSound();
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