package miltithread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TaskRunner {
    private static final int WIDTH = 3840;
    private static final int HEIGHT = 2160;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("ERROR: Not enough command line parameters. Expecting <numThreads> <granularity>");
            System.exit(1);
        }

        int numThreads = Integer.parseInt(args[0]);
        int granularity = Integer.parseInt(args[1]);

        if (numThreads < 1) {
            System.out.println("ERROR: The number of threads was not a natural number");
            System.exit(1);
        }

        if (granularity < 1) {
            System.out.println("ERROR: Granularity was not a natural number");
            System.exit(1);
        }

        int numTasks = numThreads * granularity;

        System.out.printf(
                "numTheads=%d - granularity=%d - numTasks=%d\n",
                numThreads, granularity, numTasks
        );

        // pass xStart and numThreads
        // when the thread finishes with the row it has it will advance by numThread steps
        // it should advance only if currentRow + numThreads <= WIDTH
        // therefore the for-loop has to only give the first numThreads rows

        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2b = bi.createGraphics();

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            ParMandelRunnable runnable = new ParMandelRunnable(i, numThreads, bi);
            threads[i] = new Thread(runnable);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("ERROR");
                e.printStackTrace();
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
