package listeners;

import sprites.Block;
import sprites.Ball;

/**
 * The {@code ScoreTrackingListener} class is responsible for tracking the player's score.
 * It listens to hit events and increases the score when a block is hit.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Constructs a {@code ScoreTrackingListener} with a reference to the score counter.
     *
     * @param scoreCounter the counter used to keep track of the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        currentScore.increase(5);
    }
}