package main.backend;

import main.backend.characters.Player;

public class StatTracker {
    private static Long startTime;
    private static Long finishTime;
    private static Long elapsedTime;

    private static double monstersKilled;
    private static double damageTaken;
    private static double damageDealt;

    public static void startTimer() {
        if (startTime == null) startTime = System.nanoTime();
    }

    public static void finishTimer() {
        if (finishTime == null) finishTime = System.nanoTime();
    }

    public static void resetClock() {
        startTime = null;
        finishTime = null;
        elapsedTime = null;
    }

    public static Integer[] getElapsedTime() {
        //return a list of hours, minutes, seconds
        if (finishTime == null) {
            System.out.println("finishTimeIsNull");
            return new Integer[3];
        } else if (startTime == null) {
            System.out.println("startTimeIsNull");
            return new Integer[3];
        } else if (elapsedTime == null) {
            elapsedTime = finishTime - startTime;
        }
        double etSeconds = elapsedTime / 1000000000;
        int hours = (int) etSeconds / 3600;
        etSeconds %= 3600;
        int minutes = (int) etSeconds / 60;
        etSeconds %= 60;
        int seconds = (int) etSeconds;
        Integer[] time = new Integer[3];
        time[0] = hours;
        time[1] = minutes;
        time[2] = seconds;
        return time;
    }

    //delete
    public static long getStartTime() {
        return startTime;
    }

    public static double getMonstersKilled() {
        return monstersKilled;
    }

    public static void addMonster() {
        monstersKilled++;
    }

    public static double getDamageTaken() {
        return damageTaken;
    }

    public static void addDamageTaken(double damage) {
        damageTaken += damage;
    }

    public static double getDamageDealt() {
        return damageDealt;
    }

    public static void addDamageDealt(double damage) {
        damageDealt += damage;
    }
}
