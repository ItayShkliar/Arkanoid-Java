package physics;

import geometry.Point;

/**
 * The CollisionInfo class represents information about a collision event.
 * It holds the point where the collision occurred and the object involved in the collision.
 *
 * <p>
 * This class is used to store and retrieve details about collisions in the game environment.
 * </p>
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructs a CollisionInfo instance with the given collision point and collidable object.
     *
     * @param collisionPoint  the point where the collision occurs
     * @param collisionObject the object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}