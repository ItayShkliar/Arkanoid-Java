package geometry;

/**
 * The {@code Line} class represents a line segment defined by two points in a 2D space.
 * It provides methods to calculate properties of the line, such as its length, midpoint,
 * and intersection with other lines or rectangles. The class also includes functionality
 * to check if two lines intersect and to find the intersection point.
 */
public class Line {
    private Point start;
    private Point end;
    private static final double EPSILON = 0.00001;

    /**
     * Constructs a line segment with the specified start and end points.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line segment using the specified coordinates.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Calculates the length of the line segment.
     *
     * @return The Euclidean distance between the start and end points of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculates the midpoint of the line segment.
     *
     * @return A {@code Point} representing the middle of the line.
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2.0;
        double middleY = (this.start.getY() + this.end.getY()) / 2.0;
        return new Point(middleX, middleY);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks if a given point {@code p} lies on the line segment defined by points {@code s} and {@code e}.
     *
     * @param s The starting point of the segment.
     * @param e The ending point of the segment.
     * @param p The point to check.
     * @return {@code true} if {@code p} lies on the segment, {@code false} otherwise.
     */
    private static boolean onSegment(Point s, Point e, Point p) {
        return p.getX() <= Math.max(s.getX(), e.getX())
                && p.getX() >= Math.min(s.getX(), e.getX())
                && p.getY() <= Math.max(s.getY(), e.getY())
                && p.getY() >= Math.min(s.getY(), e.getY());
    }

    /**
     * Determines the orientation of three ordered points (p1, p2, p3).
     * The orientation can be:
     * <ul>
     *     <li>0 if the points are collinear.</li>
     *     <li>1 if the points form a clockwise turn.</li>
     *     <li>-1 if the points form a counterclockwise turn.</li>
     * </ul>
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     * @return 0 if the points are collinear,
     * 1 if they are in clockwise order, or -1 if they are in counterclockwise order.
     */
    private static int orientation(Point p1, Point p2, Point p3) {
        double orientation = ((p2.getY() - p1.getY()) * (p3.getX() - p2.getX()))
                - ((p2.getX() - p1.getX()) * (p3.getY() - p2.getY()));

        if (orientation == 0) {
            return 0; //collinear
        } else if (orientation > 0) {
            return 1; //clockwise
        } else {
            return -1; //counterclockwise
        }
    }

    /**
     * Checks if this line intersects with another line.
     * Two lines intersect if:
     * <ul>
     *     <li>They have different orientations at their endpoints (general case).</li>
     *     <li>One of the endpoints of a line is collinear and lies on the other line's segment (special case).</li>
     * </ul>
     *
     * @param other The other line to check for intersection.
     * @return {@code true} if the lines intersect, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other) {
        double o1 = orientation(this.start(), this.end(), other.start());
        double o2 = orientation(this.start(), this.end(), other.end());
        double o3 = orientation(other.start(), other.end(), this.start());
        double o4 = orientation(other.start(), other.end(), this.end());

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(this.start(), this.end(), other.start())) {
            return true;
        }
        if (o2 == 0 && onSegment(this.start(), this.end(), other.end())) {
            return true;
        }
        if (o3 == 0 && onSegment(other.start(), other.end(), this.start())) {
            return true;
        }
        return o4 == 0 && onSegment(other.start(), other.end(), this.end());
    }

    /**
     * Checks if this line intersects with both of the given lines.
     *
     * @param other1 The first line to check for intersection.
     * @param other2 The second line to check for intersection.
     * @return {@code true} if this line intersects with both {@code other1}
     * and {@code other2}, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return (other1.isIntersecting(this) && other2.isIntersecting(this));
    }

    /**
     * Calculates the intersection point between this line and another line.
     * If the lines intersect at a point, the point is returned.
     * If the lines overlap or are collinear, but extend beyond a single intersection point, null is returned.
     * If the lines do not intersect, null is returned.
     *
     * @param other the other line to check for intersection with
     * @return the intersection point, or null if there is no intersection or infinite intersections
     */
    public Point intersectionWith(Line other) {
        // Check if the lines intersect. If not, return null.
        if (!this.isIntersecting(other)) {
            return null;
        }

        // If the line is just a dot, return null.
        if (this.start().equals(this.end()) || other.end().equals(other.start())) {
            return null;
        }

        double gradient1 = 0.0;
        double gradient2 = 0.0;

        // Check if the lines are vertical by comparing the x-coordinates of the start and end points.
        boolean isVertical1 = (Math.abs(this.end.getX() - this.start.getX()) < EPSILON);
        boolean isVertical2 = (Math.abs(other.end.getX() - other.start.getX()) < EPSILON);

        // Handle the case when both lines are vertical
        if (isVertical1 && isVertical2) {
            // If they overlap completely, return null (infinite intersections)
            if ((this.start().getY() <= other.start().getY()
                    && this.end().getY() >= other.end().getY())
                    || (other.start().getY() <= this.start().getY() && other.end().getY() >= this.end().getY())) {
                // Infinite intersections, they are part of the same line
                return null;
            }

            // If they overlap partially, return the intersection point at the edge (touching at start or end).
            if (Math.abs(this.start().getY() - other.end().getY()) < EPSILON) {
                // Intersection at the start of this line
                return this.start();
            }
            if (Math.abs(this.end().getY() - other.start().getY()) < EPSILON) {
                // Intersection at the end of this line
                return this.end();
            }
        }

        // Calculate gradients for the lines if they are not vertical.
        if (!isVertical1) {
            gradient1 = (this.end.getY() - this.start.getY())
                    / (this.end.getX() - this.start.getX());
        }

        if (!isVertical2) {
            gradient2 = (other.end.getY() - other.start.getY())
                    / (other.end.getX() - other.start.getX());
        }

        if ((Math.abs(gradient1 - gradient2) < EPSILON)
                && !((!isVertical1 && isVertical2) || (isVertical1 && !isVertical2))) { // lines are collinear
            // Check if the segments share any endpoint.
            boolean shareStart = this.start().equals(other.start());
            boolean shareEnd = this.end().equals(other.end());
            boolean shareAEnd = this.end().equals(other.start());
            boolean shareBEnd = this.start().equals(other.end());

            // Case 1: They share a start point.
            if (shareStart) {
                // If the other endpoints are different, check if one endpoint is "inside" the other segment.
                // That is, if one segment extends beyond the common start.
                if (!this.end().equals(other.end())
                        && (onSegment(this.start(), this.end(), other.end())
                        || onSegment(other.start(), other.end(), this.end()))) {
                    // Overlap is more than a single point.
                    return null;
                }
                // Otherwise, they only meet at the common start.
                return this.start();
            }

            // Case 2: They share an end point.
            if (shareEnd) {
                if (!this.start().equals(other.start())
                        && (onSegment(this.start(), this.end(), other.start())
                        || onSegment(other.start(), other.end(), this.start()))) {
                    return null;
                }
                return this.end();
            }

            // Case 3: One segment's start equals the other's end.
            if (shareAEnd) {
                if (!this.start().equals(other.end())
                        && (onSegment(this.start(), this.end(), other.end())
                        || onSegment(other.start(), other.end(), this.start()))) {
                    return null;
                }
                return this.end();
            }

            if (shareBEnd) {
                if (!this.end().equals(other.start())
                        && (onSegment(this.start(), this.end(), other.start())
                        || onSegment(other.start(), other.end(), this.end()))) {
                    return null;
                }
                return this.start();
            }

            // If they don't share any endpoint but they overlap (i.e. one endpoint is inside the other segment),
            // then they have infinitely many intersections.
            if (onSegment(this.start(), this.end(), other.start())
                    || onSegment(this.start(), this.end(), other.end())
                    || onSegment(other.start(), other.end(), this.start())
                    || onSegment(other.start(), other.end(), this.end())) {
                return null;
            }
        }

        // If the start or end points are the same, return that point.
        if (this.start().equals(other.start()) || this.start().equals(other.end())) {
            return this.start();
        }
        if (this.end().equals(other.start()) || this.end().equals(other.end())) {
            return this.end();
        }

        if (isVertical1 && !isVertical2) {
            double x = this.start.getX();
            double y = gradient2 * x - gradient2 * other.start.getX() + other.start.getY();

            return new Point(x, y);
        }

        // If one line is vertical and the other is not, calculate the intersection point.
        if (!isVertical1 && isVertical2) {
            double x = other.start.getX();
            double y = gradient1 * x - gradient1 * this.start.getX() + this.start.getY();

            return new Point(x, y);
        }

        // If neither line is vertical, calculate the intersection point using the formulas for non-parallel lines.
        double intersectionX = (this.start.getX() * gradient1 - other.start.getX() * gradient2
                + other.start.getY() - this.start.getY()) / (gradient1 - gradient2);
        double intersectionY = gradient1 * (intersectionX - this.start.getX()) + this.start.getY();

        // Check if the intersection point lies on both segments before returning it.
        if (onSegment(this.start(), this.end(), new Point(intersectionX, intersectionY))) {
            return new Point(intersectionX, intersectionY);
        }
        // If the intersection point does not lie on both segments, return null (no valid intersection).
        return null;
    }

    /**
     * Finds the closest intersection point between this line and the given rectangle,
     * starting from the beginning of the line.
     *
     * <p>
     * The method checks all intersection points between the line and the rectangle,
     * and returns the point closest to the start of the line. If there are no intersections,
     * the method returns {@code null}.
     * </p>
     *
     * @param rect the rectangle to check for intersections with
     * @return the closest intersection point to the start of the line, or {@code null} if there are no intersections
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intercectionList = rect.intersectionPoints(this);

        if (intercectionList.isEmpty()) {
            return null;
        }

        Point closestToStart = intercectionList.get(0);
        for (Point p : intercectionList) {
            if (p != null) {
                if (p.distance(this.start()) + EPSILON < closestToStart.distance(this.start())) {
                    closestToStart = p;
                }
            }
        }

        return closestToStart;
    }
}
