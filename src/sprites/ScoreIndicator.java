package sprites;

import game.Game;
import biuoop.DrawSurface;
import listeners.Counter;

import java.awt.Color;

/**
 * The {@code ScoreIndicator} class is a sprite that displays the current score on the screen.
 * It retrieves the score from a {@link Counter} object and renders it on a {@link DrawSurface}.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score;

    /**
     * Constructs a {@code ScoreIndicator} with a given {@link Counter} that holds the score.
     *
     * @param score the {@link Counter} representing the current score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.RED);
        d.drawText(350, 20, "Score: " + score.getValue(), 20);
    }

    @Override
    public void timePassed() {
    }

    /**
     * Adds this {@code ScoreIndicator} to the given {@link Game}.
     *
     * @param g the game to which the score indicator will be added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
