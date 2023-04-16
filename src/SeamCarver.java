import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private static final int CORNER_ENERGY = 1000;

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException();
        }

        if (x == 0 || y == 0 || x == width() -1 || y == height() -1) {
            return CORNER_ENERGY;
        }

        return Math.sqrt(delta(x - 1, y, x + 1, y) + delta(x, y - 1, x, y + 1));
    }

    private double delta(int x1, int y1, int x2, int y2) {
        Color pixel1 = picture().get(x1, y1);
        Color pixel2 = picture().get(x2, y2);
        return Math.pow(pixel1.getRed() + pixel2.getRed(), 2)
                + Math.pow(pixel1.getGreen() + pixel2.getGreen(), 2)
                + Math.pow(pixel1.getBlue() + pixel2.getBlue(), 2);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}
