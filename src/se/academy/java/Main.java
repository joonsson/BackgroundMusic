package se.academy.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MP3Player m = new MP3Player();

        Scanner sc = new Scanner(System.in);
        boolean again = true;
        System.out.println("0\tExit program");
        System.out.println("1\tPlay tune (one time)");
        System.out.println("2\tPlay tune (loop)");
        System.out.println("3\tStop tune (the loop)");
        System.out.println("4\tStop all tunes");
        System.out.println("5\tPause all tunes");
        System.out.println("6\tResume all tunes (does not work, will start over all looped tunes)");
        System.out.println("Default: Play a sound (try smashing ENTER)");
        do {
            System.out.print("\nCommand >> ");
            String command = sc.nextLine();
            switch (command) {
                case "0":
                    again = false;
                    break;

                case "1":
                    m.playTune("victory.mp3");
                    break;
                case "2":
                    m.playTune("Blues-Loop.mp3", true);
                    break;
                case "3":
                    m.stopTune("Blues-Loop.mp3");
                    break;
                case "4":
                    m.stopAllTunes();
                case "5":
                    m.pauseAllTunes();
                    break;
                case "6":
                    m.resumeAllTunes();
                    break;

                default:
                    m.playSound("button-3.mp3");
                    break;
            }
        } while (again);
    }
}
