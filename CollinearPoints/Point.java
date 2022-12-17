/* Written by Kyrylo Pervushyn on 23 October 2022 */
import java.util.Comparator;
/*import edu.princeton.cs.algs4.StdDraw;*/ // comment out dor Coursera

public class Point implements Comparable<Point> {

    public final Comparator<Point> slopeOrder;  // comment for Coursera
    private final int x;
    private final int y;

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        slopeOrder = new SOrder(); // comment for Coursera
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        if (this.y == that.y)
            return 0;
        return (that.y - this.y) / ((double) (that.x - this.x));
    }

    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        return Integer.compare(this.x, that.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SOrder();
    }

    private class SOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            return Double.compare(slopeTo(p), slopeTo(q));
        }
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}