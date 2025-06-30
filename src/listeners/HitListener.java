package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * The HitListener interface should be implemented by any class
 * that wants to be notified when a Block is hit by a Ball.
 */
public interface HitListener {

    /**
     * This method is called whenever the specified block (beingHit)
     * is hit by the specified ball (hitter).
     *
     * @param beingHit the Block that was hit
     * @param hitter   the Ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}