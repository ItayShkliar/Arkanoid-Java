package listeners;

import game.Game;
import sprites.Ball;
import sprites.Block;

/**
 * The BlockRemover class is responsible for removing blocks from the game
 * when they are hit, as well as keeping track of the number of remaining blocks.
 * It implements the HitListener interface to respond to hit events on blocks.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;

    /**
     * Constructs a BlockRemover.
     *
     * @param game            the game instance from which blocks will be removed
     * @param remainingBlocks a counter tracking the number of blocks remaining in the game
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getColor());
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
    }
}