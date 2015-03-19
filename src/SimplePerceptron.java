import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Project ArtificialIntelligence created by Kotelnickiy Pavel on 19.03.2015.
 */

public class SimplePerceptron {

    int q = 100;

    String l = null;
    double[][] patterns = new double[q][3];

    int[][] rezult = new int[q][4];
    int i, x0 = -1;
    int iteration = 0;
    boolean error = true;

    double W0, W1, W2;
    double x1, x2, n = 0.5;

    SimplePerceptron() {
        ui();
    }

    public void ui() {
        // Learning
        test();

        // Setup window
        JFrame frame = new JFrame("Простой перцептрон Розенблатта");
        JPanel panel = new JPanel(new BorderLayout());
        BorderLayout bl = new BorderLayout();
        frame.setContentPane(panel);

        // Top panel
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(new JLabel("Сеть обучилась за - " + iteration + " итераций"));
        top.add(new JLabel("Проводим тестирование на 20 примерах (81-100)"));
        panel.add(BorderLayout.NORTH, top);

        // Left panel
        JPanel left = new JPanel(new GridLayout(21,5, 6, 2));
        left.add(new JLabel("№"));
        left.add(new JLabel("x1"));
        left.add(new JLabel("x2"));
        left.add(new JLabel("d"));
        left.add(new JLabel("New y"));

        // Right panel
        for (int i = 80; i < q; i++) {
            left.add(new JLabel(Integer.toString(i + 1)));
            left.add(new JLabel(Double.toString(patterns[i][0])));
            left.add(new JLabel(Double.toString(patterns[i][1])));
            left.add(new JLabel(Integer.toString((int) patterns[i][2])));
            left.add(new JLabel(Integer.toString(rezult[i][2])));
        }
        panel.add(BorderLayout.WEST, left);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("data/img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(img));
        panel.add(BorderLayout.EAST, picLabel);

        // Bottom panel
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(new JLabel("Подходящие коэфициенты:"));
        bottom.add(new JLabel("W0 = " + W0 + " W1 = " + W1 + " W2 = " + W2));
        panel.add(BorderLayout.SOUTH, bottom);

        // Show window
        frame.pack();
        frame.setVisible(true);
    }

    public void input() {
        BufferedReader inputStream = null;
        try {
            try {
                inputStream = new BufferedReader(new FileReader("data/data.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < q; i++) {
                try {
                    l = inputStream.readLine();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                String[] rad = l.split(":");
                for (int j = 0; j < 3; j++) {
                    patterns[i][j] = Double.parseDouble(rad[j]);
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void study() {
        input();

        W2 = Math.random();
        W1 = Math.random();
        W0 = Math.random();

//      while (iteration < maxIteration) {
        while (true) {
            error = false;

            for (i = 0; i < 80; i++) {
                x1 = patterns[i][0];
                x2 = patterns[i][1];
                if (((W1 * x1) + (W2 * x2) - W0) > 0) {
                    rezult[i][0] = 1;
                } else {
                    rezult[i][0] = 0;
                }
                if (patterns[i][2] != rezult[i][0]) {
                    error = true;

                    rezult[i][1] = 0;
                    W0 = (W0 + n * (patterns[i][2] - rezult[i][0]) * x0);
                    W1 = (W1 + n * (patterns[i][2] - rezult[i][0]) * x1);
                    W2 = (W2 + n * (patterns[i][2] - rezult[i][0]) * x2);
                } else {
                    rezult[i][1] = 1;
                }

            }
            iteration++;
            if (error == false) {
//                System.out.println("Сеть обучилась за - " + iteration + " итераций");
                break;
            }
        }
        if (error == true) {
//            System.out.println("Сеть не обучилась, нужно больше итераций");
            System.exit(0);
        }
    }

    public void test() {
        study();
        for (i = 80; i < q; i++) {
            x1 = patterns[i][0];
            x2 = patterns[i][1];
            if (((W1 * x1) + (W2 * x2) - W0) > 0) {
                rezult[i][2] = 1;
            } else {
                rezult[i][2] = 0;
            }
        }
//        System.out.println();
//        System.out.println("Проводим тестирование на 20 примерах (80-100)");
//        System.out.println(" №|  x1 |  x2 |d == new_y");
//        for (i = 80; i < q; i++) {
//            System.out.print(i + 1);
//            System.out.print("|" + patterns[i][0]);
//            System.out.print("|" + patterns[i][1]);
//            System.out.print("|" + (int) patterns[i][2]);
//            System.out.print(" == " + rezult[i][2]);
//            System.out.println();
//        }
//        System.out.println("Подходящие коефициенты");
//        System.out.println("W0 = " + W0 + " \nW1 = " + W1 + "\nW2 = " + W2);
    }

    public static void main(String[] args) {
        new SimplePerceptron();
    }
}
