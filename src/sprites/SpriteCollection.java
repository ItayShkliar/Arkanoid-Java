package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class is responsible for managing a collection of sprite objects.
 * It allows adding sprites, notifying them that time has passed, and drawing them on a given surface.
 *
 * <p>
 * This class maintains a list of sprites and provides methods to interact with them,
 * such as updating their state and rendering them on the screen.
 * </p>
 */
public class SpriteCollection {
    private final java.util.List<Sprite> sprites;

    /**
     * Constructs an empty SpriteCollection.
     * Initializes the list to hold sprites.
     */
    public SpriteCollection() {
        sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to be added to the collection
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     *
     * <p>
     * This method calls the {@code timePassed()} method on each sprite, which allows
     * sprites to update their states accordingly (e.g., moving or changing).
     * </p>
     */
    public void notifyAllTimePassed() {
        ArrayList<Sprite> sprites2 = new ArrayList<>(this.sprites);
        for (Sprite s : sprites2) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the provided DrawSurface.
     *
     * <p>
     * This method calls the {@code drawOn(d)} method on each sprite to render it on the screen.
     * </p>
     *
     * @param d the DrawSurface on which the sprites will be drawn
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }

    /**
     * Returns the list of sprites currently in the game.
     *
     * @return a List containing all the sprites
     */
    public List<Sprite> getSprites() {
        return sprites;
    }

}