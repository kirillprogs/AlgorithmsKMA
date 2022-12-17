/* Written by Kyrylo Pervushyn on 25 October 2022 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] segmentArray;

    public FastCollinearPoints(Point[] pointsArray) {
        if (pointsArray == null)
            throw new IllegalArgumentException();
        Point[] points = Arrays.copyOf(pointsArray, pointsArray.length);
        checkNullPoints(points);
        checkEqualPoints(points);
        List<LineSegment> segmentList = new LinkedList<>();
        for (Point point : pointsArray) {
            Arrays.sort(points, point.slopeOrder().thenComparing(Point::compareTo)); // O(n*log(n))
            int j = 0;
            while (j < points.length) {
                int count = 1;
                int k = j + 1;
                /* Bad shows whether to add this segment. Segment is    *
                 * bad if any of the points with equal slope to base    *
                 * point is less than base point. Thus, if the segment  *
                 * is not bad (it is good though, actually), there will *
                 * be no segments with equal slopes in final answer.    */
                boolean bad = point.compareTo(points[j]) > 0;
                for (; k < points.length; ++k) {
                    if (point.slopeTo(points[j]) == point.slopeTo(points[k])) {
                        count++;
                        if (point.compareTo(points[k]) > 0)
                            bad = true;
                    } else
                        break;
                }
                // we found more than 4 equal slopes
                if (count >= 3 && !bad) {
                    LineSegment newSegment = new LineSegment(point, points[k - 1]);
                    for (int el = j; el < k - 1; el++) // comment for Coursera
                        newSegment.add(points[el]);    // comment for Coursera
                    segmentList.add(newSegment);
                }
                j = k; // add number of equal points to the index
            }
        }
        segmentArray = new LineSegment[segmentList.size()];
        int pos = 0;
        for (LineSegment segment : segmentList)
            segmentArray[pos++] = segment;
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