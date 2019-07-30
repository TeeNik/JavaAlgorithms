import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private final double BORDER_ENERGY = 195075.0;

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if (picture == null) {
            throw new IllegalArgumentException("Picture cannot be null");
        }

        this.picture = new Picture(picture);;
    }

    // current picture
    public Picture picture(){
        return picture;
    }

    // width of current picture
    public int width(){
        return picture.width();
    }

    // height of current picture
    public int height(){
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if (x == 0 || y == 0 || x == (width() - 1) || y == (height() - 1)) {
            return 3 * 255 * 255;
        }

        return deltaX(x, y) + deltaY(x, y);
    }

    private double deltaX(int x, int y) {
        Color c1 = picture.get(x - 1, y);
        Color c2 = picture.get(x + 1, y);
        return Math.pow(c2.getRed() - c1.getRed(), 2) +
                Math.pow(c2.getBlue() - c1.getBlue(), 2) +
                Math.pow(c2.getGreen() - c1.getGreen(), 2);
    }

    private double deltaY(int x, int y) {
        Color c1 = picture.get(x, y - 1);
        Color c2 = picture.get(x, y + 1);
        return Math.pow(c2.getRed() - c1.getRed(), 2) +
                Math.pow(c2.getBlue() - c1.getBlue(), 2) +
                Math.pow(c2.getGreen() - c1.getGreen(), 2);
    }

    private double[][] buildEnergyMatrix() {
        double[][] energy = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energy[i][j] = energy(i, j);
            }
        }
        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        double[][] energyMatrix = buildEnergyMatrix();
        return findSeam(energyMatrix, width(), height());
    }

    private int[] findSeam(double[][] energyMatrix, int w, int h) {
        double[][] distTo = new double[w][h];
        int[][] from = new int[w][h];

        initMatrices(distTo, from, w, h);

        for (int i = 1; i < w; i++) {
            // for each column
            for (int j = 0; j < h; j++) {
                double e = Double.POSITIVE_INFINITY;
                int eFrom = -1;

                // relax i-1, j
                if (j != 0) {
                    if (distTo[i-1][j-1] < e) {
                        e = distTo[i-1][j-1];
                        eFrom = j-1;
                    }
                }
                if (distTo[i-1][j] < e) {
                    e = distTo[i-1][j];
                    eFrom = j;
                }
                if (j != (h - 1)) {
                    if (distTo[i-1][j+1] < e) {
                        e = distTo[i-1][j+1];
                        eFrom = j+1;
                    }
                }
                distTo[i][j] = e + energyMatrix[i][j];
                from[i][j] = eFrom;
            }
        }

        // find min value for last column
        double minCell = Double.POSITIVE_INFINITY;
        int argMinCell = -1;
        for (int j = 0; j < h; j++) {
            double e = distTo[w - 1][j];
            if (e < minCell) {
                minCell = e;
                argMinCell = j;
            }
        }

        int[] seam = new int[w];
        seam[w - 1] = argMinCell;
        for (int i = w - 2; i >= 0; i--) {
            seam[i] = from[i+1][seam[i + 1]];
        }

        return seam;
    }

    private void initMatrices(double[][] distTo, int[][] from, int w, int h) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                distTo[i][j] = i == 0 ?  0 : Double.POSITIVE_INFINITY;
                from[i][j] = i == 0 ? 0 : -1;
            }
        }
    }

    private double[][] transpose(double[][] m) {
        double[][] mt = new double[m[0].length][m.length];
        for (int i = 0; i < mt.length; i++) {
            for (int j = 0; j < mt[0].length; j++) {
                mt[i][j] = m[j][i];
            }
        }
        return mt;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        double[][] energyMatrix = transpose(buildEnergyMatrix());
        return findSeam(energyMatrix, height(), width());
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){
        if (seam == null || seam.length != width()) {
            throw new IllegalArgumentException("Invalid seam");
        }
        checkSeam(seam);

        Picture newPicture = new Picture(width(), height()  - 1);
        for (int i = 0; i < width(); i++) {
            for (int j = 0, jn = 0; j < height(); j++) {
                if (j != seam[i]) {
                    newPicture.set(i, jn, picture.get(i, j));
                    jn++;
                }
            }
        }

        this.picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException("Invalid seam");
        }
        checkSeam(seam);

        Picture newPicture = new Picture(width() - 1, height() );
        for (int j = 0; j < height(); j++) {
            for (int i = 0, in = 0; i < width(); i++) {
                if (i != seam[j]) {
                    newPicture.set(in, j, picture.get(i, j));
                    in++;
                }
            }
        }

        this.picture = newPicture;
    }

    private void checkSeam(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new IllegalArgumentException("Invalid seam at position " + i);
            }
        }
    }

    //  unit testing (optional)
    public static void main(String[] args){

    }

}