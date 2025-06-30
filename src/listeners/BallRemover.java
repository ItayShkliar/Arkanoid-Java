package listeners;

import sprites.Ball;
import sprites.Block;
import game.Game;

/**
 * The {@code BallRemover} class is a {@link HitListener} that removes balls from the game
 * when they hit a specific block (e.g., the "death region" at the bottom of the screen).
 * It also updates a counter that tracks the number of remaining balls.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructs a {@code BallRemover}.
     *
     * @param game           the game from which balls will be removed
     * @param remainingBalls a counter for the number of balls still in play
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }


    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}