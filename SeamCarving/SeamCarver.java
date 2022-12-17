/* Written by Pervushyn Kyrylo on 13 December 2022. */
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private static final double CORNER_PIXEL_ENERGY = 1000;
    private Picture picture;

    private enum Mode { Horizontal, Vertical }

    private static class Item {
        private final double distance;
        private final int edgeToDiff;
        private Item(double distance) {
            this.distance = distance;
            edgeToDiff = 0;
        }
        private Item(double distance, int edgeToDiff) {
            this.distance = distance;
            this.edgeToDiff = edgeToDiff;
        }
    }

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();
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
        if (x < 0 || x >= picture.width() || y < 0 || y >= picture.height())
            throw new IllegalArgumentException("Uncorrect column or row number x="+x+" y="+y);
        else if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1)
            return CORNER_PIXEL_ENERGY;
        return Math.sqrt(squareOfDeltaX(x, y) + squareOfDeltaY(x, y));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return getSeam(Mode.Horizontal);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return getSeam(Mode.Vertical);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != picture.width())
            throw new IllegalArgumentException();
        int row = 0;
        int col = 0;
        Picture newPicture = new Picture(picture.width(), picture.height() - 1);
        for (int i = 0; i < picture.width(); i++) {
            row = 0;
            if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException("Array is not a valid seam");
            for (int j = 0; j < picture.height(); j++)
                if (j != seam[i]) {
                    newPicture.setRGB(col, row, picture.getRGB(i, j));
                    row++;
                }
            col++;
        }
        this.picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != picture.height())
            throw new IllegalArgumentException();
        int row = 0;
        int col = 0;
        Picture newPicture = new Picture(picture.width() - 1, picture.height());
        for (int i = 0; i < picture.height(); i++) {
            col = 0;
            if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException("Array is not a valid seam");
            for (int j = 0; j < picture.width(); j++)
                if (j != seam[i]) {
                    newPicture.setRGB(col, row, picture.getRGB(j, i));
                    col++;
                }
            row++;
        }
        this.picture = newPicture;
    }

    private double squareOfDeltaX(int col, int row) {
        double rX = picture.get(col + 1, row).getRed() - picture.get(col - 1, row).getRed();
        double gX = picture.get(col + 1, row).getGreen() - picture.get(col - 1, row).getGreen();
        double bX = picture.get(col + 1, row).getBlue() - picture.get(col - 1, row).getBlue();
        return Math.pow(rX, 2) + Math.pow(gX, 2) + Math.pow(bX, 2);
    }

    private double squareOfDeltaY(int col, int row) {
        double rY = picture.get(col, row + 1).getRed() - picture.get(col, row - 1).getRed();
        double gY = picture.get(col, row + 1).getGreen() - picture.get(col, row - 1).getGreen();
        double bY = picture.get(col, row + 1).getBlue() - picture.get(col, row - 1).getBlue();
        return Math.pow(rY, 2) + Math.pow(gY, 2) + Math.pow(bY, 2);
    }

    private int[] getSeam(Mode mode) {
        Item[][] distance;
        double[][] energy;
        int width, height;
        if (mode == Mode.Vertical) {
            distance = new Item[height()][width()];
            energy = new double[height()][width()];
            for (int i = 0; i < height(); ++i)
                for (int j = 0; j < width(); ++j)
                    energy[i][j] = energy(j, i);
            width = width();
            height = height();
        } else {
            distance = new Item[width()][height()];
            energy = new double[width()][height()];
            for (int i = 0; i < width(); ++i)
                for (int j = 0; j < height(); ++j)
                    energy[i][j] = energy(i, j);
            width = height();
            height = width();
        }
        for (int i = 0; i < width; ++i)
            distance[0][i] = new Item(energy[0][i]);
        for (int i = 1; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                double a = j > 0 ? distance[i-1][j-1].distance + energy[i][j]
                        : Double.POSITIVE_INFINITY;
                double b = distance[i-1][j].distance + energy[i][j];
                double c = j < width - 1 ? distance[i-1][j+1].distance + energy[i][j]
                        : Double.POSITIVE_INFINITY;
                distance[i][j] = new Item(Math.min(Math.min(a, b), c));
                if (a <= Math.min(b, c))
                    distance[i][j] = new Item(a, -1);
                else if (b <= Math.min(a, c))
                    distance[i][j] = new Item(b, 0);
                else
                    distance[i][j] = new Item(c, 1);
            }
        }
        double min = Double.POSITIVE_INFINITY;
        int vert = -1;
        for (int i = 0; i < width; ++i)
            if (distance[height - 1][i].distance < min) {
                min = distance[height - 1][i].distance;
                vert = i;
            }
        int[] seam = new int[height];
        int y = height - 1;
        int x = vert;
        while (y >= 0) {
            seam[y] = x;
            x += distance[y][x].edgeToDiff;
            y--;
        }
        return seam;
    }

//    private Picture gradient() {
//        Picture gradient = new Picture(width(), height());
//        for (int col = 0; col < gradient.width(); col++)
//            for (int row = 0; row < gradient.height(); row++)
//                gradient.setRGB(col, row,(int)(255*energy(col, row)/CORNER_PIXEL_ENERGY)<<16);
//        return gradient;
//    }

    //  unit testing (optional)

    public static void main(String[] args) {
        Picture picture1 = new Picture("ship.jpg");
        SeamCarver seamCarver = new SeamCarver(picture1);
        for (int i = 0; i < 20; ++i) {
            seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        }
        picture1.show();
    }
}