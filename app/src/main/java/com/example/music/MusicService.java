package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MusicPlayer musicPlayer;
    private final IBinder iBinder= new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
    }

    public void startMusic(int index){

        musicPlayer.playMusic(index);
    }

    public void pauseMusic(){

        musicPlayer.pauseMusic();
    }

    public void resumeMusic(int index, int c){

        musicPlayer.resumeMusic(index, c);
    }
    public void restartMusic(){

        musicPlayer.restartMusic();
    }

    public int getPlayingStatus(){

        return musicPlayer.getMusicStatus();
    }

    public void setPlayingStatus(int i){
        musicPlayer.setMusicStatus(i);
    }

    public int getMusicIndex(String str){
        return musicPlayer.getIndex(str);
    }


    public void onUpdateMusicName(String musicname) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(MUSICNAME, musicname);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }


    public class MyBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }
    }
}
