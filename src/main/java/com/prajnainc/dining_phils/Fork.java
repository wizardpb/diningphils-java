package com.prajnainc.dining_phils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fork
 */
public class Fork {

    private static final Logger logger = LoggerFactory.getLogger(Fork.class);

    private final int id;
    private volatile String owner;

    public Fork(int id) {
        this.id = id;
        this.owner = null;
    }

    int getId() {
        return id;
    }

    synchronized void pickUp(String requester) throws InterruptedException {
        while(owner != null && !owner.equals(requester)) {
            try {
                wait();
            } catch (InterruptedException e) {
                if(Main.stopAll) {
                    throw e;
                }
            }
        }
        logger.debug("{} picks up fork {}",requester, id);
        owner = requester;
    }

    synchronized void putDown() {
        logger.debug("{} puts down fork {}",owner, id);
        owner = null;
        notifyAll();
    }
}
