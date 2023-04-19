import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private static final int BORDER_ENERGY = 1000;

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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
            return BORDER_ENERGY;
        }

        return Math.sqrt(delta(x - 1, y, x + 1, y) + delta(x, y - 1, x, y + 1));
    }

    private double delta(int x1, int y1, int x2, int y2) {
        Color pixel1 = picture.get(x1, y1);
        Color pixel2 = picture.get(x2, y2);
        return Math.pow(pixel2.getRed() - pixel1.getRed(), 2)
                + Math.pow(pixel2.getGreen() - pixel1.getGreen(), 2)
                + Math.pow(pixel2.getBlue() - pixel1.getBlue(), 2);
    }

    private double[][] buildEnergyMatrix() {
        double[][] energyMatrix = new double[width()][height()];
        for (int i = 0; i < width(); ++i) {
            for (int j = 0; j < height(); ++j) {
                energyMatrix[i][j] = energy(i, j);
            }
        }
        return energyMatrix;
    }

    private void initMatrixes(double[][] distTo, int[][] from, int w, int h) {
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                distTo[i][j] = i == 0 ? 0 : Double.POSITIVE_INFINITY;
                from[i][j] = i == 0 ? 0 : -1;
            }
        }
    }

    private int[] findSeam(double[][] energyMatrix, int w, int h) {
        double[][] distTo = new double[w][h];
        int[][] from = new int[w][h];

        initMatrixes(distTo, from, w, h);

        for (int i = 1; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                double e = Double.POSITIVE_INFINITY;
                int eFrom = -1;

                // relax i - 1, j
                if (j != 0) {
                    if (distTo[i - 1][j - 1] < e) {
                        e = distTo[i - 1][j - 1];
                        eFrom = j - 1;
                    }
                }
                if (distTo[i - 1][j] < e) {
                    e = distTo[i - 1][j];
                    eFrom = j;
                }
                if (j != h - 1) {
                    if (distTo[i - 1][j + 1] < e) {
                        e = distTo[i - 1][j + 1];
                        eFrom = j + 1;
                    }
                }
                distTo[i][j] = e + energyMatrix[i][j];
                from[i][j] = eFrom;
            }
        }

        //find min value for last column
        double minCell = Double.POSITIVE_INFINITY;
        int argMinCell = -1;
        for (int j = 0; j < h; ++j) {
            double e = distTo[w - 1][j];
            if (e < minCell) {
                minCell = e;
                argMinCell = j;
            }
        }

        int[] seam = new int[w];
        seam[w - 1] = argMinCell;
        for (int i = w - 2; i >= 0; --i) {
            seam[i] = from[i + 1][seam[i + 1]];
        }

        return seam;
    }

    private double[][] transpose(double[][] m) {
        double[][] mt = new double[m[0].length][m.length];
        for (int i = 0; i < mt.length; ++i) {
            for (int j = 0; j < mt[0].length; ++j) {
                mt[i][j] = m[j][i];
            }
        }
        return mt;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energyMatrix = buildEnergyMatrix();
        return findSeam(energyMatrix, width(), height());
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energyMatrix = transpose(buildEnergyMatrix());
        return findSeam(energyMatrix, height(), width());
    }

    private void checkSeam(int[] seam) {
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("Invalid seam at position " + i);
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width() || height() <= 1) {
            throw new IllegalArgumentException();
        }
        checkSeam(seam);

        Picture newPicture = new Picture(width(), height() - 1);
        for (int i = 0; i < width(); ++i) {
            for (int j = 0, jn = 0; j < height(); ++j) {
                if (j != seam[i]) {
                    newPicture.set(i, jn, picture.get(i, j));
                    ++jn;
                }
            }
        }
        this.picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height() || width() <= 1) {
            throw new IllegalArgumentException();
        }
        checkSeam(seam);

        Picture newPicture = new Picture(width() - 1, height());
        for (int j = 0; j < height(); ++j) {
            for (int i = 0, in = 0; i < width(); ++i) {
                if (i != seam[j]) {
                    newPicture.set(in, j, picture.get(i, j));
                    ++in;
                }
            }
        }
        this.picture = newPicture;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}
