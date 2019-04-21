//package CollinearPoints;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points){
        checkNull(points);
        Point[] sorted = points.clone();
        Arrays.sort(sorted);
        checkDuplicate(sorted);

        final int N = sorted.length;
        LinkedList<LineSegment> list = new LinkedList<>();

        for (int a = 0; a < N - 3; a++) {
            Point pt1 = sorted[a];

            for (int b = a + 1; b < N - 2; b++) {
                Point pt2 = sorted[b];
                double slopeAB = pt1.slopeTo(pt2);

                for (int c = b + 1; c < N - 1; c++) {
                    Point pt3 = sorted[c];
                    double slopeAC = pt1.slopeTo(pt3);
                    if (slopeAB == slopeAC) {

                        for (int d = c + 1; d < N; d++) {
                            Point pt4 = sorted[d];
                            double slopeAD = pt1.slopeTo(pt4);
                            if (slopeAB == slopeAD) {
                                list.add(new LineSegment(pt1, pt4));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]);

    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments(){
        return lineSegments.length;

    }

    public LineSegment[] segments(){
        return lineSegments;
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
