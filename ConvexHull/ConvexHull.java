import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvexHull {

//    static String input = "./grid6x6.txt";
//    static String input = "./horizontal100.txt";
//    static String input = "./check100.txt";
//    static String input = "./input6.txt";
//    static String input = "./input8.txt";
//    static String input = "./input40.txt";
//    static String input = "./input50.txt";
    static String input = "./input56.txt";
//    static String input = "./input100.txt";
//    static String input = "./input400.txt";
//    static String input = "./rs1423.txt";

    private final List<Point2D> sequence;

    public ConvexHull(Point2D[] pointa) {
        Point2D[] points = Arrays.copyOf(pointa, pointa.length);
        Point2D minPoint = points[0];
        for (int i = 1; i < points.length; ++i)
            if (points[i].compareTo(minPoint) < 0)
                minPoint = points[i];

//        for (Point2D d : points) System.out.print(minPoint.polarAngle(d)+" ");
//        System.out.println();
        Arrays.sort(points, Point2D::compareTo);
        Arrays.sort(points, minPoint.getPolarOrder());

//        for (Point2D point2D : points) System.out.print(minPoint.polarAngle(point2D)+" ");
//            System.out.println();
        sequence = new ArrayList<>();
        for (int i = -1; i < points.length - 1; i++) {
            Point2D point = points[(i + points.length) % points.length];
            sequence.add(point);
//            System.out.println(point);
            int lastIdx = sequence.size() - 1;
            while (lastIdx >= 2 && Point2D.ccw(sequence.get(lastIdx - 2),
                    sequence.get(lastIdx - 1), sequence.get(lastIdx)) <= 0) {
                sequence.remove(lastIdx - 1);
                lastIdx = sequence.size() - 1;
            }
        }
    }

    public void draw() {
        int size = sequence.size();
        System.out.println(size);
        for (int i = 0; i < size; ++i) {
            System.out.println(sequence.get(i));
            sequence.get(i).drawTo(sequence.get((i + 1) % size));
        }
    }

    public static void main(String[] args) {
        In in = new In(input);
        int n = in.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point2D(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point2D p : points) {
            p.draw();
        }
        StdDraw.show();

        ConvexHull convexHull = new ConvexHull(points);
        convexHull.draw();

        StdDraw.show();
    }
}