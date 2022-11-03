package com.example.music;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    MediaPlayer player, s1, s2, s3;
    int count=0;
    int currentPosition, s1Pos, s2Pos, s3Pos, timer = 0;
    int p1,p2,p3;
    int musicIndex, indS1, indS2, indS3 = 0;
    int copy1, copy2, copy3;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;
    boolean paused;


    static final int[] MUSICPATH = new int[]{
            R.raw.vokal,
            R.raw.fanfare,
            R.raw.harmoni
    };


    static final String[] MUSICNAME = new String[]{
            "vokal",
            "fanfare",
            "harmoni"
    };
    static final String[] EFFECT = new String[]{
            "Cheering",
            "Clapping",
            "Go Hokies!"
    };

    public MusicPlayer(MusicService service) {

        this.musicService = service;

    }


    public int getMusicStatus() {

        return musicStatus;
    }

    public String getMusicName() {

        return MUSICNAME[musicIndex];
    }

    public int getIndex(String str){
        for( int i = 0; i<MUSICNAME.length; i++){
            if(str.compareTo( MUSICNAME[i]) == 0){
                //musicIndex=i;
                return i;
            }

        }
        return -1;
    }

    public void playMusic(int index) {
        musicIndex=index;
        paused = false;
        player= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        timer = 0;
        player.start();
        player.setOnCompletionListener(this);
        musicService.onUpdateMusicName(getMusicName());
        musicStatus = 1;







    }


    public void pauseMusic() {
        paused = true;
        if(player!= null && player.isPlaying()){
            player.pause();
            currentPosition= player.getCurrentPosition();
            musicStatus= 2;
        }

    }

    public void resumeMusic(int index, int c) {
        if(player!= null){
            if(c == count){
                Log.i("Continue Playing", count+"");
                paused = false;

                player.seekTo(currentPosition);
                player.start();

                musicStatus=1;
            }
            else {
                Log.i("NewSong", c+"");
                player.release();
                player = null;


                count = c;
                musicIndex = index;
                timer =0;
                playMusic(index);

            }


        }
    }

    public void restartMusic() {
        paused = true;
        timer = 0;
        if(player!= null && player.isPlaying()) {
            player.stop();
            currentPosition = 0;
            musicStatus =2 ;

            musicService.onUpdateMusicName(MUSICNAME[musicIndex]);
            //paused = false;
            playMusic(musicIndex);
        }

    }


    public void setMusicStatus(int i) {
        musicStatus = 0;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }
}
