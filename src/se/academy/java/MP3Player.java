package se.academy.java;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


enum AudioStatus{
    INIT,
    STOPPED,
    PLAYING,
    PAUSED;
}
class Audio extends Thread{

    private boolean loop;
    private String filepath;
    private int pausedOnFrame;
    private AdvancedPlayer player;
    private AudioStatus status;

    Audio(String filepath, boolean loop, int frameToPlayFrom){
        this.loop = loop;
        this.filepath = filepath;
        this.pausedOnFrame = frameToPlayFrom;
        this.player = null;
        this.status = AudioStatus.INIT;
    }

    public AudioStatus getStatus() {
        return status;
    }

    public void setStatus(AudioStatus status) {
        this.status = status;
    }

    public boolean isLoop() {
        return loop;
    }

    public int getPausedOnFrame() {
        return this.pausedOnFrame;
    }

    public void stopAudio(){
        if (this.player == null){
            return;
        }
        this.loop = false;
        this.player.stop();
        this.status = AudioStatus.STOPPED;
    }


    @Override
    public void run(){
        do{
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.filepath));
                try {
                    player = new AdvancedPlayer(bis);
                    player.setPlayBackListener(new PlaybackListener() {
                        @Override
                        public void playbackStarted(PlaybackEvent evt) {
                            evt.setSource(player);
                            evt.setFrame(Audio.this.pausedOnFrame);
                            super.playbackStarted(evt);
                        }

                        @Override
                        public void playbackFinished(PlaybackEvent evt) {
                            evt.setSource(player);
                            Audio.this.pausedOnFrame = evt.getFrame();
                            super.playbackFinished(evt);
                        }
                    });

                    player.play(this.pausedOnFrame, Integer.MAX_VALUE);

                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }while(this.loop);
        this.status = AudioStatus.STOPPED;
    }


    @Override
    public String toString() {
        return "Audio{" +
                "loop=" + loop +
                ", filepath='" + filepath + '\'' +
                ", pausedOnFrame=" + pausedOnFrame +
                ", player=" + player +
                ", status=" + status +
                '}';
    }
}

public class MP3Player {

    private ConcurrentHashMap<String, Audio> audioMap;

    public MP3Player(){
        audioMap = new ConcurrentHashMap<>();
    }

    public void playTune(String filepath){
        playTune(filepath,false);
    }
    public void playTune(String filepath, boolean loop){
        if ( audioMap.containsKey(filepath) &&
                audioMap.get(filepath).getStatus() == AudioStatus.PLAYING){
            return;
        }
        Audio tuneThread = new Audio(filepath, loop, 0);
        tuneThread.setStatus(AudioStatus.PLAYING);
        audioMap.put(filepath, tuneThread);
        tuneThread.start();

    }

    public void stopTune(String filepath){
        if ( !audioMap.containsKey(filepath) ||
                audioMap.get(filepath).getStatus() == AudioStatus.STOPPED){
            return;
        }

        if (audioMap.get(filepath) == null){
            audioMap.remove(filepath);
            return;
        }

        Audio tuneThread = audioMap.get(filepath);

        tuneThread.setStatus(AudioStatus.STOPPED);
        tuneThread.stopAudio();
        audioMap.remove(filepath);
    }

    public void stopAllTunes(){
        Set<String> keys = audioMap.keySet();
        keys.forEach(this::stopTune);
    }

    public void pauseTune(String filepath){
        if ( !audioMap.containsKey(filepath) ||
                audioMap.get(filepath).getStatus() == AudioStatus.STOPPED ||
                audioMap.get(filepath).getStatus() == AudioStatus.PAUSED ){
            return;
        }
        if (audioMap.get(filepath) == null){
            return;
        }

        Audio tuneThread = audioMap.get(filepath);
        boolean loop = tuneThread.isLoop();
        tuneThread.stopAudio();
        int pausedOnFrame = tuneThread.getPausedOnFrame();

        audioMap.remove(filepath);

        tuneThread = new Audio(filepath, loop, pausedOnFrame);
        tuneThread.setStatus(AudioStatus.PAUSED);
        audioMap.put(filepath, tuneThread);
    }

    public void pauseAllTunes(){
        Set<String> keys = audioMap.keySet();
        keys.forEach(this::pauseTune);
    }

    public void resumeTune(String filepath){
        if ( !audioMap.containsKey(filepath) ){
            return;
        }
        if (audioMap.get(filepath) == null){
            return;
        }
        Audio prevThread = audioMap.get(filepath);
        if (prevThread.getStatus() == AudioStatus.PAUSED){
            Audio tuneThread = new Audio(filepath, prevThread.isLoop(), prevThread.getPausedOnFrame());
            tuneThread.setStatus(AudioStatus.PLAYING);
            audioMap.put(filepath, tuneThread);
            tuneThread.start();
        }
    }

    public void resumeAllTunes(){
        Set<String> keys = audioMap.keySet();
        keys.forEach(this::resumeTune);
    }

    public void playSound(String filepath){
        new Thread( () -> {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));
                try {
                    Player player = new Player(bis);
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }).start();
    }
}

