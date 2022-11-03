package com.example.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView music;
    ImageView image;
    Button play, restart;
    String main, s1,s2,s3;
    int indexMain, indexS1,indexS2,indexS3, prog1, prog2, prog3;
    int count = 0;
    int temp;
    boolean newSong;


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
        setContentView(R.layout.activity_play);
        image = (ImageView) findViewById(R.id.imageView);
        music= (TextView) findViewById(R.id.songName);
        play= (Button) findViewById(R.id.buttonPlay);
        play.setOnClickListener(this);
        restart = (Button) findViewById(R.id.buttonRestart);
        restart.setOnClickListener(this);

        Bundle b1 = getIntent().getExtras();
        main = b1.getString("main");
        updatePicture(main);
        music.setText(main);
        //indexMain = musicService.getMusicIndex(main);
        newSong = false;
        temp = count;
        count = b1.getInt("count");
        if(temp != count){
            newSong = true;
        }


        Log.i("Count", count+"");




        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            music.setText(savedInstanceState.getString(MUSIC_PLAYING));
        }

        startMusicServiceIntent= new Intent(this, MusicService.class);

        if(!isInitialized){
            startService(startMusicServiceIntent);
            isInitialized= true;
        }

        musicCompletionReceiver = new MusicCompletionReceiver(this);


    }

    @Override
    public void onClick(View view) {


        if (isBound) {
            switch (view.getId()) {
                case R.id.buttonPlay:
                    switch (musicService.getPlayingStatus()) {
                        case 0:
                            indexMain = musicService.getMusicIndex(main);
                            musicService.startMusic(indexMain);
                            play.setText("Pause");
                            break;
                        case 1:
                            musicService.pauseMusic();
                            play.setText("Play");
                            break;
                        case 2:
                            indexMain = musicService.getMusicIndex(main);
                            musicService.resumeMusic(indexMain, count);
                            play.setText("Pause");
                            break;
                    }
                    break;
                case R.id.buttonRestart:
                    musicService.restartMusic();
                    play.setText("Pause");
                    break;
            }

        }
    }


    public void updatePicture(String musicName) {
        Log.i("Musicname", musicName);
        switch(musicName){
            case "vokal":
                image.setImageResource(R.drawable.album);
                break;
            case "fanfare":
                image.setImageResource(R.drawable.album);
                break;
            case "harmoni":
                image.setImageResource(R.drawable.album);
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isInitialized && !isBound){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }

        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isBound){
            unbindService(musicServiceConnection);
            isBound= false;
        }

        unregisterReceiver(musicCompletionReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putString(MUSIC_PLAYING, music.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public int effectIndex(String str){
        if(str.compareTo("Cheering") == 0){
            return 0;
        }
        if(str.compareTo("Clapping") == 0){
            return 1;
        }
        if(str.compareTo("Go Hokies!") == 0){
            return 2;
        }
        return -1;

    }


    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            musicService = binder.getService();
            if(newSong){
                musicService.pauseMusic();
                musicService.setPlayingStatus(0);
            }
            isBound = true;

            switch (musicService.getPlayingStatus()) {
                case 0:
                    play.setText("Play");
                    break;
                case 1:
                    play.setText("Pause");
                    break;
                case 2:

                    play.setText("Play");
                    break;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
            isBound = false;
        }
    };
}