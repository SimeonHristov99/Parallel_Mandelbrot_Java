package miltithread;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParMandelRunnable implements Runnable {
    private static final double minX = -0.8;
    private static final double maxX = -0.3;

    private static final double minY = 0.8;
    private static final double maxY = 0.3;

    private static final int MAX_ITERATIONS = 256;
    private static final int INFINITY = 16;

    private static final int WIDTH = 3840;
    private static final int HEIGHT = 2160;
    private final int NUM_ROWS;

    private final int NUM_THREADS;
    private int rowStart;
    private final BufferedImage bi;

    public ParMandelRunnable(int rowStart, int NUM_THREADS, int NUM_ROWS, BufferedImage bi) {
        this.rowStart = rowStart * NUM_ROWS;
        this.NUM_THREADS = NUM_THREADS;
        this.NUM_ROWS = NUM_ROWS;
        this.bi = bi;
    }

    @Override
    public void run() {

        while (rowStart < HEIGHT) {
            int xEnd = rowStart + NUM_ROWS;

            for (int y = rowStart; y < xEnd && y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    double translatedX = minX + x * (maxX - minX) / WIDTH;
                    double translatedY = minY + y * (maxY - minY) / HEIGHT;
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
                    if (iterations >= MAX_ITERATIONS) {
                        bi.setRGB(x, y, 0);
                    } else {
                        Color color = Color.getHSBColor(
                                (float) iterations * 2.0f / (float) MAX_ITERATIONS, 1.0f, 1.0f
                        );

                        String hex = Integer.toHexString(color.getRGB()).substring(2);
                        bi.setRGB(x, y, Integer.parseInt(hex, 16));
                    }
                }
            }

            rowStart += NUM_THREADS * NUM_ROWS;
        }

    }
}
