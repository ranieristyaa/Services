package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView music;
    Button play;
    Spinner mainSong, soundEffect1, soundEffect2, soundEffect3;
    SeekBar seekBar1, seekBar2, seekBar3;
    ArrayList<String> mainSongs, soundEffects;
    ArrayAdapter<String> main, s1, s2, s3;
    int indexMain, indexS1, indexS2, indexS3;
    int count =0;


    MusicService musicService;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    boolean isBound = false;
    boolean isInitialized = false;

    public static final String INITIALIZE_STATUS = "intialization status";
    public static final String MUSIC_PLAYING = "music playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSong = (Spinner) findViewById(R.id.spinnerMain);
        mainSongs = new ArrayList<String>();
        mainSongs.add("vokal");
        mainSongs.add("fanfare");
        mainSongs.add("harmoni");
        main = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mainSongs);
        main.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSong.setAdapter(main);



        play= (Button) findViewById(R.id.play);
        play.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, PlayActivity.class);

        i.putExtra("main", mainSong.getSelectedItem().toString());

        i.putExtra("count", count);

        count++;
        startActivity(i);

    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        super.onSaveInstanceState(outState);
    }


}
