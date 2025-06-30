package game;

import java.util.ArrayList;
import java.util.List;

import physics.Collidable;
import physics.CollisionInfo;
import geometry.Point;
import geometry.Line;

/**
 * The {@code GameEnvironment} class is responsible for managing all collidable objects in the game.
 * It allows for the addition of new collidables and provides functionality to detect the closest collision
 * along a given trajectory.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Constructs a new, empty GameEnvironment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a collidable object to the environment.
     *
     * @param c the Collidable object to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Given a trajectory (the path an object is moving along), this method returns
     * the information about the closest collision that is going to occur, if any.
     * If there are no collisions, it returns {@code null}.
     *
     * @param trajectory the path the object is expected to follow
     * @return a {@link CollisionInfo} object describing the closest collision, or {@code null} if none
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;

        List<Collidable> collidables2 = new ArrayList<>(this.collidables);
        for (Collidable c : collidables2) {
            Point intersection = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(intersection, c);
                }
            }
        }

        return closestCollision;
    }

    /**
     * Returns the list of collidable objects currently in the environment.
     *
     * @return a list of Collidables
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }
}