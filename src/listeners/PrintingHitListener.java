package listeners;

import sprites.Block;
import sprites.Ball;

/**
 * A simple HitListener implementation that prints a message
 * to the console whenever a block is hit.
 */
public class PrintingHitListener implements HitListener {

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}
