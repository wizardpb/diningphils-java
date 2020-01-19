package com.prajnainc.dining_phils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Philosopher
 */
public class Philosopher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Philosopher.class);

    private int id;
    private String name;
    private Fork [] forks ;

    private int eatCount = 0;
    private int eatTime = 0;

    private static Fork [] newForks(Fork lower, Fork higher) {
        var f = new Fork[2];
        f[0] = lower; f[1] = higher;
        return f;
    }

    Philosopher(int id, String name, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.name = name;
        // Set forks so that forks[0] always has the lower id
        this.forks = leftFork.getId() < rightFork.getId() ?
                newForks(leftFork, rightFork) :
                newForks(rightFork, leftFork);
    }

    @Override
    public void run() {
        while(!Main.stopAll) {
            try {
                // Since forks always has the fork with the lower ID in fork[0], forks are
                // always picked up in increasing ID. This enforces the resource ordering that
                // avoids deadlock
                think();
                for(Fork f : forks) f.pickUp(this);
                eat();
                for(Fork f : forks) f.putDown();
            } catch (InterruptedException e) {
                logger.debug(this.name+" interrupted");
            }
        }
    }

    private void sleep(int waitSecs, String msg) throws InterruptedException {
        Main.screen.writeLine(this.id+1, msg);
        logger.debug(msg);
        Thread.sleep(waitSecs * 1000);
    }
    private void think() throws InterruptedException {
        var waitSecs = Main.getThinkTime();
        sleep(Main.getThinkTime(), String.format("%s is thinking for %d secs", name, waitSecs));
    }

    private void eat() throws InterruptedException{
        var waitSecs = Main.getEatTime();
        sleep(Main.getThinkTime(), String.format("%s is eating for %d secs", name, waitSecs));

        eatCount += 1;
        eatTime += waitSecs;
        var avgWait = (float)eatTime/eatCount;
        Main.screen.writeLine(
                this.id+Main.N_PHILS+2,
                String.format("%-10s has eaten %d times, total=%ds, avg=%.2fs", name, eatCount, eatTime, avgWait));
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
