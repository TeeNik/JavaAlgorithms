import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        checkNull(points);

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        checkDuplicates(sortedPoints);

        final int N = points.length;
        final ArrayList<LineSegment> segmentsList = new ArrayList<>();

        for (int i = 0; i < sortedPoints.length; ++i) {

            Point p = sortedPoints[i];
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int x = 1;
            while (x < N)
            {
                LinkedList<Point> candidates = new LinkedList<>();
                final double SLOPE_REF = p.slopeTo(pointsBySlope[x]);
                do {
                    candidates.add(pointsBySlope[x++]);
                } while (x < N && p.slopeTo(pointsBySlope[x]) == SLOPE_REF);

                if(candidates.size() >=3 && p.compareTo(candidates.peek()) < 0) {
                    segmentsList.add(new LineSegment(p, candidates.peekLast()));
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
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

    private void checkDuplicates(Point[] sortedPoints) {
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
