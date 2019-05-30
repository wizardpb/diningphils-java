package com.prajnainc.dining_phils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Philosopher
 */
public class Philosopher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Philosopher.class);

    private String name;
    private Fork [] forks ;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
        this.name = name;
        // Set forks so that forks[0] always has the lower id
        this.forks = (Fork [])(leftFork.getId() < rightFork.getId() ?
                Arrays.asList(leftFork, rightFork) :
                Arrays.asList(rightFork, leftFork)).toArray();
        ;
    }

    @Override
    public void run() {
        while(!Main.stopAll) {
            try {
                think();
                for(Fork f : forks) f.pickUp(name);
                eat();
                for(Fork f : forks) f.putDown();
            } catch (InterruptedException e) {
                logger.debug(this.name+" interrupted");
            }
        }
    }

    private void think() throws InterruptedException{
        int waitSecs = Main.getThinkTime();
        logger.debug("{} is thinking for {} secs", name, waitSecs);
        Thread.sleep(waitSecs * 1000);
    }

    private void eat() throws InterruptedException{
        int waitSecs = Main.getEatTime();
        logger.debug("{} is eating for {}", name, waitSecs);
        Thread.sleep(waitSecs * 1000);
    }
}
