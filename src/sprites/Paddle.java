package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import physics.Velocity;
import physics.Collidable;
import game.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.*;

/**
 * The Paddle class represents a paddle in a game, which is controlled by the player using keyboard input.
 * The paddle can move left or right and interacts with objects for collision detection and reflection.
 *
 * <p>
 * The paddle is a rectangular object that can be drawn on the screen and updated in response to player input.
 * It also handles its own collisions and adjusts the movement of other objects (such as a ball) accordingly.
 * </p>
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard;
    private Rectangle paddleRect;
    private final java.awt.Color color;
    private final double speed;
    private static final double EPSILON = 0.00001;

    /**
     * Constructs a new Paddle controlled by the keyboard.
     *
     * @param keyboard   the keyboard sensor used to detect user input
     * @param paddleRect the rectangle defining the paddle's position and size
     * @param color      the color to fill the paddle with
     * @param speed      the movement speed of the paddle in pixels per frame
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle paddleRect, java.awt.Color color, double speed) {
        this.keyboard = keyboard;
        this.paddleRect = paddleRect;
        this.color = color;
        this.speed = speed;
    }

    /**
     * Moves the paddle left by its speed, updating its position accordingly.
     * This does not handle screen boundary checks — it assumes movement is always allowed.
     */
    public void moveLeft() {
        this.paddleRect = new Rectangle(new Point(this.paddleRect.getUpperLeft().getX() - speed,
                this.paddleRect.getUpperLeft().getY()), this.paddleRect.getWidth(), this.paddleRect.getHeight());
    }

    /**
     * Moves the paddle right by its speed, updating its position accordingly.
     * This does not handle screen boundary checks — it assumes movement is always allowed.
     */
    public void moveRight() {
        this.paddleRect = new Rectangle(new Point(this.paddleRect.getUpperLeft().getX() + speed,
                this.paddleRect.getUpperLeft().getY()), this.paddleRect.getWidth(), this.paddleRect.getHeight());
    }

    /**
     * Updates the paddle's position based on user input and ensures it stays within screen bounds.
     *
     * <p>
     * If the paddle reaches the left or right edge of the screen, it wraps around to the opposite edge.
     * It listens for keyboard input (left or right arrow keys) to move the paddle accordingly.
     * </p>
     */
    public void timePassed() {
        if (this.paddleRect.getUpperLeft().getX() < 0) {
            this.paddleRect = new Rectangle(new Point(800 - this.paddleRect.getWidth(),
                    this.paddleRect.getUpperLeft().getY()), this.paddleRect.getWidth(), this.paddleRect.getHeight());
        }
        if (this.paddleRect.getUpperLeft().getX() + this.paddleRect.getWidth() > 800) {
            this.paddleRect = new Rectangle(new Point(0,
                    this.paddleRect.getUpperLeft().getY()), this.paddleRect.getWidth(), this.paddleRect.getHeight());
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the specified DrawSurface.
     *
     * <p>
     * The paddle is drawn as a filled rectangle at its current position with its assigned color.
     * </p>
     *
     * @param d the DrawSurface to draw the paddle on
     */
    public void drawOn(DrawSurface d) {
        //d.setColor(this.color);
        //d.fillRectangle((int) this.paddleRect.getUpperLeft().getX(), (int) this.paddleRect.getUpperLeft().getY(),
        //        (int) this.paddleRect.getWidth(), (int) this.paddleRect.getHeight());
        Image img = Toolkit.getDefaultToolkit().getImage("assets/appa.png");
        d.drawImage((int) this.paddleRect.getUpperLeft().getX(), (int) this.paddleRect.getUpperLeft().getY(), img);
    }

    /**
     * Returns the paddle's collision rectangle.
     *
     * <p>
     * The collision rectangle is used to detect and handle collisions with other objects.
     * </p>
     *
     * @return the paddle's collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddleRect;
    }

    /**
     * Handles the collision of the paddle with another object and adjusts the velocity accordingly.
     *
     * <p>
     * The method checks whether the collision occurred at the top, bottom, left, or right of the paddle.
     * If the collision happens at the top or bottom, the vertical velocity is inverted and the angle of the
     * reflection is adjusted based on the horizontal position of the collision (creating a bounce effect).
     * If the collision happens at the left or right side of the paddle, the horizontal velocity is inverted.
     * </p>
     *
     * @param hitter the ball that hits the paddle
     * @param collisionPoint  the point where the collision occurs
     * @param currentVelocity the current velocity of the object before the collision
     * @return a new Velocity object representing the updated velocity after the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle rect = this.getCollisionRectangle();

        // Check if hit top or bottom
        if (Math.abs(collisionPoint.getY() - rect.getUpperLeft().getY()) < EPSILON
                || Math.abs(collisionPoint.getY() - (rect.getUpperLeft().getY() + rect.getHeight())) < EPSILON) {
            dy = -dy;

            double width = this.paddleRect.getWidth();
            double leftPointX = rect.getUpperLeft().getX();
            double speed = Math.sqrt(dx * dx + dy * dy);

            for (int i = 1; i <= 5; i++) {
                if (collisionPoint.getX() <= leftPointX + (width / 5) * i - EPSILON
                        && collisionPoint.getX() >= leftPointX + (width / 5) * (i - 1) + EPSILON) {

                    double angle;
                    if (i == 1) {
                        angle = 240;
                    } else if (i == 2) {
                        angle = 255;
                    } else if (i == 3) {
                        angle = 270;
                    } else if (i == 4) {
                        angle = 285;
                    } else { // i == 5
                        angle = 300;
                    }

                    return Velocity.fromAngleAndSpeed(angle, speed);
                }
            }
        }

        // Check if hit left or right
        if (Math.abs(collisionPoint.getX() - rect.getUpperLeft().getX()) < EPSILON
                || Math.abs(collisionPoint.getX() - (rect.getUpperLeft().getX() + rect.getWidth())) < EPSILON) {
            dx = -dx;
        }

        return new Velocity(dx, dy);
    }

    /**
     * Adds the paddle to the specified game as both a collidable and a sprite.
     *
     * <p>
     * This method registers the paddle with the game environment for collision detection and
     * with the sprite collection for rendering during the game loop.
     * </p>
     *
     * @param g the game to which the paddle will be added
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}