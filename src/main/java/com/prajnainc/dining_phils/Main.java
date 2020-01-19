package com.prajnainc.dining_phils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main
 */
public class Main {

    private final static int THINK_LOW_SECS = 1;
    private final static int THINK_HIGH_SECS = 10;
    private final static int EAT_LOW_SECS = 1;
    private final static int EAT_HIGH_SECS = 10;
    private final static String [] PHILOSOPHERS = { "Kant", "Marx", "Decartes", "Hegel", "Heidegger"};

    final static int N_PHILS = PHILOSOPHERS.length;

    private static class DelayGenerator {

        private int think_low, think_high, eat_low, eat_high;
        private Random gen;

        DelayGenerator(int think_low, int think_high, int eat_low, int eat_high) {
            this.gen = new Random();
            this.think_low = think_low;
            this.think_high = think_high;
            this.eat_low = eat_low;
            this.eat_high = eat_high;
        }

        DelayGenerator() {
            this(THINK_LOW_SECS,THINK_HIGH_SECS, EAT_LOW_SECS, EAT_HIGH_SECS);
        }

        int getThinkTime() {
            return gen.nextInt(think_high - think_low) + think_low;
        }

        int getEatTime() {
            return gen.nextInt(eat_high - eat_low) + eat_low;
        }
    }

    final static ScreenLogger screen = new ScreenLogger(2*N_PHILS + 3, ">");

    static boolean stopAll = false;

    private static DelayGenerator delays = new DelayGenerator();

    static int getThinkTime() {
        return delays.getThinkTime();
    }

    static int getEatTime() {
        return delays.getEatTime();
    }

    public static void main(String [] argv) {

        List<Fork> forks = new ArrayList<>(N_PHILS);
        List<Thread> philosophers = new ArrayList<>(N_PHILS);

        screen.clearScreen();
        
        // Create forks
        for(int i = 0; i < N_PHILS; i++) forks.add(new Fork(i));

        // Create and run philosophers
        for(int i = 0; i < N_PHILS; i++) {
            philosophers.add(new Thread(
                    new Philosopher(i, PHILOSOPHERS[i], forks.get(i), forks.get((i+1) % N_PHILS)))
            );
            philosophers.get(i).start();
        }
    }
}
