package com.prajnainc.dining_phils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fork
 */
class Fork {

    private static final Logger logger = LoggerFactory.getLogger(Fork.class);

    // owner must be volatile so that any subsequent pickUp sees that the fork is being used
    private final int id;
    private volatile Philosopher owner;

    Fork(int id) {
        this.id = id;
        this.owner = null;
    }

    int getId() {
        return id;
    }

    synchronized void pickUp(Philosopher requester) throws InterruptedException {
        while(owner != null && !owner.equals(requester)) {
            try {
                Main.screen.writeLine(requester.getId()+1, String.format("%s is waiting for fork %d",requester.getName(), id));
                wait();
            } catch (InterruptedException e) {
                if(Main.stopAll) {
                    throw e;
                }
            }
        }
        Main.screen.writeLine(requester.getId()+1, String.format("%s picks up fork %d",requester.getName(), id));
        logger.debug("{} picks up fork {}",requester.getName(), id);
        owner = requester;
    }

    synchronized void putDown() {
        Main.screen.writeLine(owner.getId()+1, String.format("%s puts down fork %d",owner.getName(), id));
        logger.debug("{} puts down fork {}",owner.getName(), id);
        owner = null;
        notifyAll();
    }
}
