package com.example.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicCompletionReceiver extends BroadcastReceiver {

    PlayActivity playActivity;

    public MusicCompletionReceiver(){
        //empty constructor
    }

    public MusicCompletionReceiver(PlayActivity playActivity) {
        this.playActivity= playActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String musicName= intent.getStringExtra(MusicService.MUSICNAME);
        playActivity.updatePicture(musicName);
    }
}