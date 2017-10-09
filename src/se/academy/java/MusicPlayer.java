package se.academy.java;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends Thread {
    public static List<MusicPlayer> musicPlayers =  new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private String fileName;
    private Media hit;
    private boolean loop;

    public MusicPlayer(String fileName) {
        this(fileName, false);
    }
    public MusicPlayer(String fileName, boolean loop) {
        this.fileName = fileName;
        this.loop = loop;
        hit = new Media(new File(fileName).toURI().toString());

    }
    public void run() {
        mediaPlayer = new MediaPlayer(hit);
        if (loop) {
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO);
            });
        }
    }
    public void playMusic() {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play();
    }
    public void stopMusic() {
        mediaPlayer.pause();
    }
    public void pauseMusic() {
        mediaPlayer.pause();
        mediaPlayer.seek(Duration.ZERO);
    }
    public void resumeMusic() {
        mediaPlayer.play();
    }
    public static void stopAllMusic() {
        for (MusicPlayer m: musicPlayers) {
            m.stopMusic();
        }
    }
    public static void pauseAllMusic() {
        for (MusicPlayer m: musicPlayers) {
            m.pauseMusic();
        }
    }
    public static void resumeAllMusic() {
        for (MusicPlayer m: musicPlayers) {
            m.resumeMusic();
        }
    }
}
