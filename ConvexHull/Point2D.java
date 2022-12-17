/* Written by Kyrylo Pervushyn on 1 November 2022 */
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point2D implements Comparable<Point2D> {

    private final int x;
    private final int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* Checks whether these three points form clockwise
    * turn, counter-clockwise turn or lie on the same line. */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    /* Compares points firstly by their ordinates,
    * then by theis abscissas (if equal ordinates). */
    public int compareTo(Point2D that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        return Integer.compare(this.x, that.x);
    }

    public Comparator<Point2D> getPolarOrder() {
        return new PolarOrder();
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass())
            return false;
        Point2D p = (Point2D)other;
        return this.x == p.x && this.y == p.y;
    }

    public String toString() {
        return "("+x+", "+y+")";
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point2D that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    private class PolarOrder implements Comparator<Point2D> {
        @Override
        public int compare(Point2D o1, Point2D o2) {
            return Double.compare(polarAngle(o1), polarAngle(o2));
        }
    }

    /* Returns a polar argument of point <other>
    * in respect to this point as polar center. */
    private double polarAngle(Point2D other) {
        double atan = Math.atan(Math.abs(other.y - this.y) / Math.abs((double)(other.x - this.x)));
        if (this.x <= other.x && this.y <= other.y)
            return atan;
        else if (this.x > other.x && this.y < other.y)
            return Math.PI - atan;
        else if (this.y > other.y && this.x < other.x)
            return -atan;
        return Math.PI + atan;
    }
}