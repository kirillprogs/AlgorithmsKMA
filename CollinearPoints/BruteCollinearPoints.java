/* Written by Kyrylo Pervushyn on 24 October 2022 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segmentArray;

    public BruteCollinearPoints(Point[] pointsArray) {
        if (pointsArray == null)
            throw new IllegalArgumentException();
        Point[] points = Arrays.copyOf(pointsArray, pointsArray.length);
        checkNullPoints(points);
        checkEqualPoints(points);
        int count = 0;
        List<LineSegment> segments = new LinkedList<>();
        for (int i = 0; i < points.length - 3; ++i) {
            for (int j = i + 1; j < points.length - 2; ++j) {
                for (int k = j + 1; k < points.length - 1; ++k) {
                    for (int el = k + 1; el < points.length; ++el) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                            && points[i].slopeTo(points[k]) == points[i].slopeTo(points[el])) {
                            LineSegment newSegment = new LineSegment(points[i], points[el]);
                            newSegment.add(points[j]); // comment for Coursera
                            newSegment.add(points[k]); // comment for Coursera
                            segments.add(newSegment);
                            count++;
                        }
                    }
                }
            }
        }
        segmentArray = new LineSegment[count];
        int pos = 0;
        for (LineSegment ls : segments)
            segmentArray[pos++] = ls;
    }

    public int numberOfSegments() {
        return segmentArray.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segmentArray, segmentArray.length);
    }

    private void checkNullPoints(Point[] points) {
        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("Null point in the array!");
    }

    private void checkEqualPoints(Point[] points) {
        Arrays.sort(points, Point::compareTo);
        for (int i = 0; i < points.length - 1; ++i)
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Equal points!");
    }
}