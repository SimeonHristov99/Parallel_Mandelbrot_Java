package solution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ParMandel {
    private static final double minX = -0.8; // -2;
    private static final double maxX = -0.3; // 2;

    private static final double minY = 0.8; // 2;
    private static final double maxY = 0.3; // -2;

    private static final int WIDTH = 3840;
    private static final int HEIGHT = 2160;
    private static final int MAX_ITERATIONS = 256;
    private static final int INFINITY = 16;

    public static void main(String[] args) {
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2b = bi.createGraphics();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                // Scaling
                double translatedX = minX + (x * (maxX - minX)) / WIDTH;
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

        g2b.drawRect(0, 0, WIDTH, HEIGHT);
        try {
            ImageIO.write(bi, "PNG", new File("res.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}
