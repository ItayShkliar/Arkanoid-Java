package sprites;

import physics.Velocity;
import physics.Collidable;
import game.Game;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitNotifier;
import listeners.HitListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block in the game that can be collided with.
 * The block has a rectangle that defines its position and size, and it can change velocity
 * when hit by other objects.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private final Rectangle rectangle;
    private final Color color;
    private static final double EPSILON = 0.00001;
    private int border;

    /**
     * Constructs a Block with the given rectangle and a default color of black.
     *
     * @param rectangle the rectangle that defines the block's position and size
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.color = Color.BLACK;
        border = 0;
    }

    /**
     * Constructs a Block with the given rectangle and color.
     *
     * @param rectangle the rectangle that defines the block's position and size
     * @param color     the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        border = 0;
    }

    public Block(Rectangle rectangle, Color color, int border) {
        this.rectangle = rectangle;
        this.color = color;
        this.border = border;
    }

    /**
     * Returns the rectangle that defines the block's position and size.
     *
     * @return the collision rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles the collision of the block with another object and adjusts the object's velocity accordingly.
     *
     * <p>
     * The method checks which edge of the block was hit (top, bottom, left, or right) and inverts the appropriate
     * component of the velocity (horizontal or vertical) based on the collision.
     * </p>
     *
     * @param hitter the ball that hits the block
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the velocity of the object before the collision
     * @return a new Velocity object representing the updated velocity after the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle rect = this.getCollisionRectangle();

        // Cache the four edges of the rectangle
        double topY = rect.getUpperLeft().getY();
        double bottomY = topY + rect.getHeight();
        double leftX = rect.getUpperLeft().getX();
        double rightX = leftX + rect.getWidth();

        // Compute how close the collision point is to each edge
        double topDelta = Math.abs(collisionPoint.getY() - topY);
        double bottomDelta = Math.abs(collisionPoint.getY() - bottomY);
        double leftDelta = Math.abs(collisionPoint.getX() - leftX);
        double rightDelta = Math.abs(collisionPoint.getX() - rightX);

        // Find the smallest distance: that tells us which edge was truly hit
        double minDelta = Math.min(
                Math.min(topDelta, bottomDelta),
                Math.min(leftDelta, rightDelta)
        );

        // Only one component should flip—choose based on the closest edge
        if (minDelta <= EPSILON) {
            if (minDelta == topDelta || minDelta == bottomDelta) {
                // Hit top or bottom edge: invert vertical velocity
                dy = -dy;
            } else {
                // Hit left or right edge: invert horizontal velocity
                dx = -dx;
            }
        } else {
            // Fuzzy corner or no clear edge: fallback to a vertical bounce
            dy = -dy;
        }
        if (!ballColorMatch(hitter)) {
            if (this.hitListeners != null) {
                this.notifyHit(hitter);
            }
        }

        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on the specified DrawSurface.
     *
     * <p>
     * The block is drawn as a filled rectangle with its assigned color, and its outline is drawn in black.
     * </p>
     *
     * @param d the DrawSurface to draw the block on
     */
    public void drawOn(DrawSurface d) {
        if (border == 0) {
            d.setColor(this.color);
            d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
            d.setColor(Color.BLACK);
            d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        }
        if (border == 1) {
            Image img = Toolkit.getDefaultToolkit().getImage("assets/clouds_horizontal.png");
            d.drawImage((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(), img);
        }
        if (border == 2) {
            Image img = Toolkit.getDefaultToolkit().getImage("assets/clouds_vertical.png");
            d.drawImage((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(), img);
        }

    }

    /**
     * This method is a placeholder and does nothing for the Block class.
     * It is required by the Sprite interface but is not relevant for blocks.
     */
    public void timePassed() {
    }

    /**
     * Adds the block to the game as both a sprite and a collidable.
     *
     * <p>
     * The block is added to the game for collision detection and rendering during the game loop.
     * </p>
     *
     * @param g the game to which the block will be added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Checks if the color of the given ball matches this object's color.
     *
     * @param ball the Ball to compare color with
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    /**
     * Removes this object from the specified game.
     * It removes both from the game's collidable and sprite lists.
     *
     * @param game the Game instance to remove this object from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Notifies all registered hit listeners that this object has been hit by the given ball.
     *
     * @param hitter the Ball that hit this object
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        if (this.hitListeners == null) {
            this.hitListeners = new ArrayList<>();
        }
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }


    /**
     * Returns the current color of this object.
     *
     * @return the Color of this object
     */
    public Color getColor() {
        return this.color;
    }
}
