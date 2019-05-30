package com.prajnainc.dining_phils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Main
 */
public class Main {

    public final static int THINK_LOW_SECS = 1;
    public final static int THINK_HIGH_SECS = 10;
    public final static int EAT_LOW_SECS = 1;
    public final static int EAT_HIGH_SECS = 10;

    public final static String [] PHILOSOPHERS = { "Kant", "Marx", "Decartes", "Hegel", "Heideggar"};
    public final static int N_PHILS = PHILOSOPHERS.length;

    public static class DelayGenerator {

        private int think_low, think_high, eat_low, eat_high;
        private Random gen;

        public DelayGenerator(int think_low, int think_high, int eat_low, int eat_high) {
            this.gen = new Random();
            this.think_low = think_low;
            this.think_high = think_high;
            this.eat_low = eat_low;
            this.eat_high = eat_high;
        }

        public DelayGenerator() {
            this(THINK_LOW_SECS,THINK_HIGH_SECS, EAT_LOW_SECS, EAT_HIGH_SECS);
        }

        public int getThinkTime() {
            return gen.nextInt(think_high - think_low) + think_low;
        }

        public int getEatTime() {
            return gen.nextInt(eat_high - eat_low) + eat_low;
        }
    }

    public static boolean stopAll = false;

    private static DelayGenerator delays = new DelayGenerator();

    public static int getThinkTime() {
        return delays.getThinkTime();
    }

    public static int getEatTime() {
        return delays.getEatTime();
    }

    public static void main(String [] argv) {

        List<Fork> forks = new ArrayList<>(N_PHILS);
        List<Thread> philosophers = new ArrayList<>(N_PHILS);

        // Create forks
        for(int i = 0; i < N_PHILS; i++) forks.add(new Fork(i));

        // Create and run philosophers
        for(int i = 0; i < N_PHILS; i++) {
            philosophers.add(new Thread(
                    new Philosopher(PHILOSOPHERS[i], forks.get(i), forks.get((i+1) % N_PHILS)))
            );
            philosophers.get(i).start();
        }
    }
}
