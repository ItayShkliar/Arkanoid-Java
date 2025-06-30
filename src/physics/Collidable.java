package physics;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;

/**
 * The Collidable interface represents objects that can be involved in collisions.
 *
 * <p>
 * It provides methods for defining the collision shape and handling the response
 * to collisions by updating the object's velocity.
 * </p>
 */
public interface Collidable {
    /**
     * Returns the "collision shape" of the object, represented as a Rectangle.
     *
     * @return the Rectangle representing the collision boundary of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that it has been collided with at the specified collision point,
     * with a given velocity. The object should respond by returning the new velocity
     * after the collision, taking into account its own physical response (e.g., bounce, deflection).
     *
     * @param hitter the ball that hits the object
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the velocity of the object before the collision
     * @return the new velocity of the object after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}