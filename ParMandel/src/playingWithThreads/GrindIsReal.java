package playingWithThreads;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrindIsReal {
    private static final int WIDTH = 3840; // 3840;
    private static final int HEIGHT = 2160; // 2160;
    private static final int MAX_ITERATIONS = 256; // The higher they are the lower the brightness.

    public static double mapRange(double s, double a1, double a2, double b1, double b2) {
        return (b1 + ((s - a1) * (b2 - b1)) / (a2 - a1));
    }

    public static void main(String[] args) {
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2b = bi.createGraphics();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double translatedX = mapRange(x, 0, WIDTH, -0.8, -0.3);
                double translatedY = mapRange(y, 0, HEIGHT, 0.8, 0.3);

                double cx = translatedX;
                double cy = translatedY;

                int iterations = 0;

                while (iterations < MAX_ITERATIONS) {
                    double trXX = translatedX * translatedX - translatedY * translatedY;
                    double trYY = 2 * translatedX * translatedY;

                    translatedX = trXX + cx;
                    translatedY = trYY + cy;

                    if (Math.abs(translatedX + translatedY) > 16) {
                        break;
                    }

                    ++iterations;
                }

                if (iterations == MAX_ITERATIONS) {
                    bi.setRGB(x, y, 0);
                } else {
                    Color your_color = new Color(iterations, iterations, iterations);
                    String hex = Integer.toHexString(your_color.getRGB()).substring(2);
                    bi.setRGB(x, y, Integer.parseInt(hex, 16));
                }
            }
        }

        g2b.drawRect(0, 0, WIDTH, HEIGHT);
        try {
            ImageIO.write(bi, "PNG", new File("res.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}
