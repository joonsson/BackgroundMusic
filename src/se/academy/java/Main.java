package se.academy.java;

import javafx.embed.swing.JFXPanel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final JFXPanel fxPanel = new JFXPanel();

        MusicPlayer mVictory = new MusicPlayer("victory.mp3");
        MusicPlayer mBlues = new MusicPlayer("Blues-Loop.mp3", true);
        MusicPlayer metroid = new MusicPlayer("Brinstar.mp3");
        //MusicPlayer button = new MusicPlayer("button-3.mp3");
        MusicPlayer.musicPlayers.add(mVictory);
        MusicPlayer.musicPlayers.add(mBlues);
        MusicPlayer.musicPlayers.add(metroid);
        //MusicPlayer.musicPlayers.add(button);
        mVictory.start();
        mBlues.start();
        metroid.start();
        //button.start();

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
            for (int i = 0; i < MusicPlayer.musicPlayers.size(); i++) {
                if (MusicPlayer.musicPlayers.get(i).isSelfDestruct()) {
                    MusicPlayer.musicPlayers.remove(i);
                }
            }
            System.out.print("\nCommand >> ");
            String command = sc.nextLine();
            switch (command) {
                case "0":
                    again = false;
                    System.exit(0);
                    break;

                case "1":
                    mVictory.playMusic();
                    break;
                case "2":
                    mBlues.playMusic();
                    break;
                case "3":
                    mBlues.stopMusic();
                    break;
                case "4":
                    metroid.playMusic();
                    break;
                case "5":
                    metroid.stopMusic();
                    break;
                case "6":
                    MusicPlayer.stopAllMusic();
                case "7":
                    MusicPlayer.pauseAllMusic();
                    break;
                case "8":
                    MusicPlayer.resumeAllMusic();
                    break;

                default:
                    //button.playMusic();
                    MusicPlayer button = new MusicPlayer("button-3.mp3", false, true);
                    MusicPlayer.musicPlayers.add(button);
                    button.start();
                    Thread.sleep(10);
                    button.playMusic();
                    break;
            }
        } while (again);
    }
}
