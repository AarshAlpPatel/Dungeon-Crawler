package main.backend.characters;


public class StatTracker {
    private static Long startTime;
    private static Long finishTime;
    private static Long elapsedTime;

    private static double monstersKilled;
    private static double damageTaken;
    private static double damageDealt;

    private static double score;

    protected static Enemy killer;
    private static String deathReason;

    public static void startTimer() {
        if (startTime == null) {
            startTime = System.nanoTime();
        }
    }

    public static void finishTimer() {
        if (finishTime == null) {
            finishTime = System.nanoTime();
        }
    }

    public static void reset() {
        startTime = null;
        finishTime = null;
        elapsedTime = null;
        monstersKilled = 0;
        damageTaken = 0;
        damageDealt = 0;
        score = 0;
        killer = null;
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

    public static String getDeathReason() {
        if (killer == null) {
            return "I have no idea how the hell ya died but ya did it. Congrats?";
        }
        return String.format("Died at the hands of a dreaded %s.", killer.getName());
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

    public static double getScore() {
        score = score + (monstersKilled * 20) - damageTaken;
        return score;
    }

    public static void changeScore(int points) {
        score += points;

        //100 for clearing a room
        //20 per monster
        //-1 per damage taken
        //500 for clearing boss
    }

    public static double getFinalScore() {
        return score;
    }
}
