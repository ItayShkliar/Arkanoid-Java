package sprites;

import physics.Velocity;
import physics.CollisionInfo;
import game.Game;
import game.GameEnvironment;
import geometry.Line;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Represents a Ball in the game.
 * The Ball has a position, a radius, a color, and a velocity.
 * It can be drawn on the screen, move, and interact with the game environment.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private static final double EPSILON = 0.00001;
    private GameEnvironment gameEnv;

    /**
     * Constructs a ball with the specified center point, radius, and color.
     * The initial velocity of the ball is set to (0, 0).
     *
     * @param center The center of the ball.
     * @param r      The radius of the ball.
     * @param color  The color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    /**
     * Constructs a ball with the specified x and y coordinates, radius, and color.
     * The initial velocity of the ball is set to (0, 0).
     *
     * @param x     The x-coordinate of the ball's center.
     * @param y     The y-coordinate of the ball's center.
     * @param r     The radius of the ball.
     * @param color The color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    /**
     * Constructs a new Ball with a center point, radius, color, and game environment.
     *
     * @param x       the x-coordinate of the center of the ball
     * @param y       the y-coordinate of the center of the ball
     * @param r       the radius of the ball
     * @param color   the color of the ball
     * @param gameEnv the game environment in which the ball moves and detects collisions
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnv) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.gameEnv = gameEnv;
        this.v = new Velocity(0, 0);
    }

    /**
     * Gets the x-coordinate of the ball's center.
     *
     * @return The x-coordinate of the ball's center.
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Gets the y-coordinate of the ball's center.
     *
     * @return The y-coordinate of the ball's center.
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The radius of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }


    /**
     * Gets the center point of the ball.
     *
     * @return The center of the ball.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Sets the center point of the ball to the specified point.
     *
     * @param center The new center of the ball.
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Draws the ball on the given drawing surface at its current position.
     *
     * @param d The drawing surface to draw the ball on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), r);
    }

    /**
     * Notifies the ball that time has passed.
     * Causes the ball to move one step according to its velocity,
     * checking for and handling collisions along its trajectory.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets the velocity of the ball to the specified Velocity object.
     *
     * @param v The new velocity of the ball.
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * Sets the velocity of the ball using the specified components for the x and y directions.
     *
     * @param dx The velocity component in the x direction.
     * @param dy The velocity component in the y direction.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Gets the current velocity of the ball.
     *
     * @return The current velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Advances the ball by one time step: computes its trajectory, detects
     * collisions, updates its velocity, and repositions its center so that
     * it never overlaps the colliding object.  If the collision is with a
     * Paddle (exact class), the ball will also be teleported directly above
     * the paddle’s top edge to prevent sticking.
     */
    public void moveOneStep() {
        //calculate line trajectory
        Line trajectory = new Line(this.center, this.getVelocity().applyToPoint(this.center));
        CollisionInfo info = gameEnv.getClosestCollision(trajectory);

        if (info == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            // 4) If the collided object’s exact class is Paddle, teleport up
            if (info.collisionObject().getClass().equals(Paddle.class)) {
                // flip horizontal velocity again (in case paddle.hit only flipped dx)
                this.setVelocity(info.collisionObject().hit(this, info.collisionPoint(), this.getVelocity()));

                // teleport so ball’s bottom edge sits just above paddle
                Rectangle paddleRect = ((Paddle) info.collisionObject()).getCollisionRectangle();
                double cx = info.collisionPoint().getX();
                double radius = this.getSize();
                double newY = paddleRect.getUpperLeft().getY() - radius - EPSILON;
                this.center = new Point(cx, newY);
            } else {
                Point collisionPoint = info.collisionPoint();
                Point start = this.center;
                Point end = this.getVelocity().applyToPoint(start);

                double totalDistance = start.distance(end);
                double collisionDist = start.distance(collisionPoint);
                double radius = this.getSize();

                // back off by exactly the radius so the ball’s edge hits the surface:
                double travelDist = Math.max(0, collisionDist - radius);
                double ratio = travelDist / totalDistance;

                this.center = new Point(
                        start.getX() + this.getVelocity().getDx() * ratio,
                        start.getY() + this.getVelocity().getDy() * ratio
                );

                // now flip velocity
                Velocity newV = info.collisionObject().hit(this, collisionPoint, this.getVelocity());
                this.setVelocity(newV);

            }
        }
    }

    /**
     * Sets the game environment in which the ball moves and detects collisions.
     *
     * @param gameEnv the GameEnvironment that manages all collidable objects
     */
    public void setGameEnv(GameEnvironment gameEnv) {
        this.gameEnv = gameEnv;
    }

    /**
     * Adds this ball to the specified game as a sprite, so it will be drawn and updated.
     *
     * @param g the Game to add this ball to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Removes the ball from the specified game.
     *
     * @param g the game from which this object should be removed
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    /**
     * Sets the color of the ball.
     *
     * @param c the new color to set
     */
    public void setColor(Color c) {
        this.color = c;
    }
}
