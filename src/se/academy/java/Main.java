package se.academy.java;

import javafx.embed.swing.JFXPanel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final JFXPanel fxPanel = new JFXPanel();

        //MP3Player m = new MP3Player();
        MusicPlayer mVictory = new MusicPlayer("victory.mp3");
        MusicPlayer mBlues = new MusicPlayer("Blues-Loop.mp3", true);
        MusicPlayer metroid = new MusicPlayer("Brinstar.mp3");
        MusicPlayer button = new MusicPlayer("button-3.mp3");
        MusicPlayer.musicPlayers.add(mVictory);
        MusicPlayer.musicPlayers.add(mBlues);
        MusicPlayer.musicPlayers.add(metroid);
        MusicPlayer.musicPlayers.add(button);
        mVictory.start();
        mBlues.start();
        metroid.start();
        button.start();

        Scanner sc = new Scanner(System.in);
        boolean again = true;
        System.out.println("0\tExit program");
        System.out.println("1\tPlay tune (one time)");
        System.out.println("2\tPlay tune (loop)");
        System.out.println("3\tStop tune (the loop)");
        System.out.println("4\tPlay tune (Metroid)");
        System.out.println("5\tStop tune (Metroid)");
        System.out.println("6\tStop all tunes");
        System.out.println("7\tPause all tunes");
        System.out.println("8\tResume all tunes (does not work, will start over all looped tunes)");
        System.out.println("Default: Play a sound (try smashing ENTER)");
        do {
            System.out.print("\nCommand >> ");
            String command = sc.nextLine();
            switch (command) {
                case "0":
                    again = false;
                    System.exit(0);
                    break;

                case "1":
                    //m.playTune("victory.mp3");
                    mVictory.playMusic();
                    break;
                case "2":
                    //m.playTune("Blues-Loop.mp3", true);
                    mBlues.playMusic();
                    break;
                case "3":
                    //m.stopTune("Blues-Loop.mp3");
                    mBlues.stopMusic();
                    break;
                case "4":
                    metroid.playMusic();
                    break;
                case "5":
                    metroid.stopMusic();
                    break;
                case "6":
                    //m.stopAllTunes();
                    MusicPlayer.stopAllMusic();
                case "7":
                    //m.pauseAllTunes();
                    MusicPlayer.pauseAllMusic();
                    break;
                case "8":
                    //m.resumeAllTunes();
                    MusicPlayer.resumeAllMusic();
                    break;

                default:
                    //m.playSound("button-3.mp3");
                    button.playMusic();
                    break;
            }
        } while (again);
    }
}
