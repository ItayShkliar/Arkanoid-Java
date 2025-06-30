package geometry;
import biuoop.DrawSurface;

import java.util.ArrayList;

/**
 * Represents a rectangle defined by its upper-left corner point, width, and height.
 * Provides methods to calculate intersections with lines, retrieve rectangle edges, and draw the rectangle.
 *
 * <p>
 * The class defines methods for getting the top, bottom, left, and right edges of the rectangle,
 * checking if a line intersects the rectangle, and drawing the rectangle on a specified surface.
 * </p>
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructs a new Rectangle with a given upper-left point, width, and height.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a list of points where a given line intersects this rectangle's edges.
     * The method checks for intersections with the rectangle's top, bottom, left, and right sides.
     *
     * @param line the line to check for intersections with the rectangle
     * @return a list of points where the line intersects the rectangle's edges
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intercectionList = new ArrayList<>();

        if (line.isIntersecting(getBottomLine())) {
            intercectionList.add(line.intersectionWith(getBottomLine()));
        }
        if (line.isIntersecting(getTopLine())) {
            intercectionList.add(line.intersectionWith(getTopLine()));
        }
        if (line.isIntersecting(getLeftLine())) {
            intercectionList.add(line.intersectionWith(getLeftLine()));
        }
        if (line.isIntersecting(getRightLine())) {
            intercectionList.add(line.intersectionWith(getRightLine()));
        }
        return intercectionList;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left corner point of the rectangle.
     *
     * @return the upper-left corner point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns the top edge of the rectangle as a Line.
     *
     * @return the top edge of the rectangle
     */
    public Line getTopLine() {
        return new Line(this.upperLeft,
                new Point(upperLeft.getX() + width, upperLeft.getY()));
    }

    /**
     * Returns the bottom edge of the rectangle as a Line.
     *
     * @return the bottom edge of the rectangle
     */
    public Line getBottomLine() {
        return new Line(
                new Point(upperLeft.getX(), upperLeft.getY() + height),
                new Point(upperLeft.getX() + width, upperLeft.getY() + height));
    }

    /**
     * Returns the left edge of the rectangle as a Line.
     *
     * @return the left edge of the rectangle
     */
    public Line getLeftLine() {
        return new Line(
                this.upperLeft,
                new Point(upperLeft.getX(), upperLeft.getY() + height));
    }

    /**
     * Returns the right edge of the rectangle as a Line.
     *
     * @return the right edge of the rectangle
     */
    public Line getRightLine() {
        return new Line(
                new Point(upperLeft.getX() + width, upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY() + height));
    }

    /**
     * Draws the rectangle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the rectangle on
     */
    public void drawOn(DrawSurface d) {
        d.fillRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }
}