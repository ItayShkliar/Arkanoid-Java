package sprites;

import biuoop.DrawSurface;

/**
 * This interface represents a Sprite object in the game.
 *
 * <p>
 * A Sprite is an object that can be drawn to the screen and updated based on the passage of time.
 * Implementing classes should define how to draw themselves and how to respond to time passing (e.g., movement).
 * </p>
 */
public interface Sprite {
    /**
     * Draws the sprite on the specified DrawSurface.
     *
     * @param d the DrawSurface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed.
     *
     * <p>
     * Implementing classes should update the state of the sprite, such as its position or animation, based on time.
     * </p>
     */
    void timePassed();
}