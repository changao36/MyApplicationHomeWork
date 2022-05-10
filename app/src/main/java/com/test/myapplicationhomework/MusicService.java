package com.test.myapplicationhomework;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {

    private final IBinder binder = new LocalBinder();
    public static MediaPlayer mp = new MediaPlayer();
    private String path;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        path = intent.getStringExtra("path");
        init();
        return binder;
    }

    public class LocalBinder extends Binder{
        MusicService getService(){
            return MusicService.this;
        }
    }

    public MusicService() {

    }

    public void init(){
        try {
            System.out.println("22222"+path);
            mp.setDataSource(path);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        mp.start();
    }

    public void stop() throws IOException {
        if (mp !=null){
            mp.stop();
            mp.prepare();
            mp.seekTo(0);
        }
    }
}
