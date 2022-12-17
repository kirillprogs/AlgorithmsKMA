import java.util.ArrayList;

public class LineSegment {

    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    private final ArrayList<Point> allPoints;

    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
        allPoints = new ArrayList<>();
        allPoints.add(p);
        allPoints.add(q);
    }

    public void add(Point p) {
        allPoints.add(1, p);
    }

    public void draw() {
        p.drawTo(q);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(allPoints.get(0));
        for (int i = 1; i < allPoints.size(); ++i)
            sb.append(" -> ").append(allPoints.get(i));
        return sb.toString();
    }

    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}