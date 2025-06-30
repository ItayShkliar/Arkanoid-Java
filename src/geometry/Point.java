package geometry;

/**
 * Represents a point in a 2D Cartesian coordinate system with x and y coordinates.
 * Provides methods to calculate distance between points, check equality, and retrieve the coordinates.
 *
 * <p>
 * The class also uses an epsilon value to handle floating-point precision issues when comparing points.
 * </p>
 */
public class Point {
    private double x;
    private double y;
    private static final double EPSILON = 0.00001;

    /**
     * Constructs a new Point object with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param p the point to calculate the distance to
     * @return the distance between this point and the given point
     */
    public double distance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    /**
     * Checks if this point is equal to another point.
     * Two points are considered equal if their x and y coordinates are
     * within a small epsilon distance of each other.
     *
     * @param p the point to compare this point to
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point p) {
        return Math.abs(this.x - p.x) < EPSILON && Math.abs(this.y - p.y) < EPSILON;
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return the x value.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return the y value.
     */
    public double getY() {
        return y;
    }

}
