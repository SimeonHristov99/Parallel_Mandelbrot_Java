package miltithread;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParMandelRunnable implements Runnable {
    private static final double minX = -0.8; // -2;
    private static final double maxX = -0.3; // 2;

    private static final double minY = 0.8; // 2;
    private static final double maxY = 0.3; // -2;

    private static final int MAX_ITERATIONS = 256;
    private static final int INFINITY = 16;

    private static final int WIDTH = 3840;
    private static final int HEIGHT = 2160;

    private final int NUM_THREADS;
    private final BufferedImage bi;
    private int rowStart;

    public ParMandelRunnable(int rowStart, int NUM_THREADS, BufferedImage bi) {
        this.rowStart = rowStart;
        this.NUM_THREADS = NUM_THREADS;
        this.bi = bi;
    }

    @Override
    public void run() {

        while (rowStart < WIDTH) {
            for (int y = 0; y < HEIGHT; y++) {
                double translatedX = minX + (rowStart * (maxX - minX)) / WIDTH;
                double translatedY = minY + (y * (maxY - minY)) / HEIGHT;
                double cx = translatedX;
                double cy = translatedY;
                int iterations = 0;

                while (iterations < MAX_ITERATIONS) {
                    double trXX = translatedX * translatedX - translatedY * translatedY;
                    double trYY = 2 * translatedX * translatedY;

                    translatedX = trXX + cx;
                    translatedY = trYY + cy;

                    if (Math.abs(translatedX + translatedY) > INFINITY) {
                        break;
                    }

                    ++iterations;
                }

                // Maldelbrot test
                if (iterations == MAX_ITERATIONS) {
                    bi.setRGB(rowStart, y, 0);
                } else {
                    Color color = Color.getHSBColor(
                            (float) iterations * 2.0f / (float) MAX_ITERATIONS, 1.0f, 1.0f
                    );

                    String hex = Integer.toHexString(color.getRGB()).substring(2);
                    bi.setRGB(rowStart, y, Integer.parseInt(hex, 16));
                }
            }

            rowStart += NUM_THREADS;
        }

    }
}
