package playingWithThreads;

import java.awt.*;

public class Test {

    public static void main(String[] args) {
//        double translatedX = mapRange(x, 0, WIDTH, -0.75, -0.45);
//        double translatedY = mapRange(y, 0, HEIGHT, -0.80, -0.30);

        double x = -0.75;
        double y = -0.45;
        System.out.println(x);
        System.out.println(y);
        System.out.println(Math.abs(x - y));

        System.out.println();

        x = -0.8;
        y = -0.3;
        System.out.println(x);
        System.out.println(y);
        System.out.println(Math.abs(x - y));
    }

}
