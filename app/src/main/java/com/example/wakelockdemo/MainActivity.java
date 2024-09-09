package com.example.wakelockdemo;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private PowerManager.WakeLock wakelock;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        acquireWakeLock();
        playMusic();
    }
    private void acquireWakeLock(){
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if(powerManager != null){
            wakelock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyApp:Music");
            wakelock.acquire();
            Log.d("Wakelock","Wakelock acquired for music playback");
        }
    }
    private void playMusic(){
        mediaPlayer = MediaPlayer.create(this,R.raw.test);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp ->{
            releaseWakeLock();
        });
    }
    private void releaseWakeLock(){
        if(wakelock !=null && wakelock.isHeld()){
            wakelock.release();
            Log.d("Wakelock","Wakelock release for music playback");
        }
    }
}