package physics;

import geometry.Point;

/**
 * The Velocity class represents a change in position along the x and y axes.
 * It is used to describe the speed and direction of an object's movement.
 *
 * <p>
 * The class provides methods for getting the individual changes along the x and y axes,
 * applying the velocity to a point,
 * and creating a velocity object from a specified angle and speed.
 * </p>
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity object with the specified changes in position along the x and y axes.
     *
     * @param dx The change in position along the x-axis.
     * @param dy The change in position along the y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the change in position along the x-axis.
     *
     * @return The change in position along the x-axis (dx).
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the change in position along the y-axis.
     *
     * @return The change in position along the y-axis (dy).
     */
    public double getDy() {
        return dy;
    }


    /**
     * Applies the velocity change to a given point by adding the dx and dy to the point's coordinates.
     *
     * @param p The point to which the velocity is applied.
     * @return A new point with the updated position (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Creates a Velocity object from an angle and speed. The angle is in degrees,
     * and the speed is the magnitude of the velocity.
     *
     * @param angle The angle in degrees at which the velocity is directed.
     * @param speed The magnitude of the velocity (speed).
     * @return A Velocity object representing the velocity in the direction of the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }
}