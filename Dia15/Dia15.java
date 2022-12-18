package Dia15;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;


public class Dia15 {

    private final static String INPUT = "Dia15\\input.txt";
    private final static String EXAMPLE = "Dia15\\example.txt";

    private final static int INPUT_ROW = 2000000;
    private final static int EXAMPLE_ROW = 10;

    private static final int INPUT_MIN_X = 0;
    private static final int INPUT_MAX_X = 4000000;

    private static final int INPUT_MIN_Y = 0;
    private static final int INPUT_MAX_Y = 4000000;

    private static final int EXAMPLE_MIN_X = 0;
    private static final int EXAMPLE_MAX_X = 20;
    private static final int EXAMPLE_MIN_Y = 0;
    private static final int EXAMPLE_MAX_Y = 20;

    public static void main(String[] args) throws IOException {

        parte1(EXAMPLE, EXAMPLE_ROW);
        parte1(INPUT, INPUT_ROW);
        parte2(EXAMPLE, EXAMPLE_MIN_X, EXAMPLE_MAX_X, EXAMPLE_MAX_Y, EXAMPLE_MIN_Y);
        parte2(INPUT, INPUT_MIN_X, INPUT_MAX_X, INPUT_MAX_Y, INPUT_MIN_Y);

    }

    private static void parte2(String file, int minX, int maxX, int maxY, int minY) throws IOException {
        int[] x = new int[33];
        int[] y = new int[33];
        int[] distancias = new int[33];
        int z = 0;

        try (Scanner sc = new Scanner(new FileInputStream(file))) {
                     

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String n1 = line.split(" ")[2].split("=")[1];
                String n2 = line.split(" ")[3].split("=")[1];
                String n3 = line.split(" ")[8].split("=")[1];
                String n4 = line.split(" ")[9].split("=")[1];

                int x1 = Integer.parseInt(n1.substring(0, n1.length() - 1));
                int y1 = Integer.parseInt(n2.substring(0, n2.length() - 1));

                int x2 = Integer.parseInt(n3.substring(0, n3.length() - 1));
                int y2 = Integer.parseInt(n4);

                // System.out.println("Sensor: X: " + x1 + " Y: " + y1);
                // System.out.println("Beacon: X: " + x2 + " Y: " + y2);

                // mapa.add(new CustomPair(x2, y2));

                // mapa.add(new CustomPair(x1, y1));

                int distancia = Math.abs(x2 - x1) + Math.abs(y2 - y1);

                
                x[z] = x1;
                y[z] = y1;
                distancias[z] = distancia;
                z++;
                //pw.close();
            }

        }
        boolean fin = false;

        for (int i = minY; i <= maxY && !fin; i++) {
            for (int j = minX; j <= maxX && !fin; j++) {
                boolean enRango = false;
                for (int j2 = 0; j2 < distancias.length && !fin && !enRango; j2++) {
                    int distancia = Math.abs(x[j2] - j) + Math.abs(y[j2] - i);

                    if (distancia <= distancias[j2]){
                        enRango = true;
                        j+=distancias[j2]-distancia;

                    }

                }
                if (!enRango) {
                    long xRes=j;
                    long yRes=i;
                    System.out.println(xRes*4000000+yRes);
                    System.out.println("X: "+xRes+" Y: "+yRes);
                    fin = true;
                }

            }
           

        }

    }

    private static void parte1(String file, int row) throws FileNotFoundException {
        HashSet<Integer> fila = new HashSet<>();
        HashSet<Integer> beacons = new HashSet<>();
        HashSet<Integer> sensors = new HashSet<>();

        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String n1 = line.split(" ")[2].split("=")[1];
                String n2 = line.split(" ")[3].split("=")[1];
                String n3 = line.split(" ")[8].split("=")[1];
                String n4 = line.split(" ")[9].split("=")[1];

                int x1 = Integer.parseInt(n1.substring(0, n1.length() - 1));
                int y1 = Integer.parseInt(n2.substring(0, n2.length() - 1));

                int x2 = Integer.parseInt(n3.substring(0, n3.length() - 1));
                int y2 = Integer.parseInt(n4);

                // System.out.println("Sensor: X: " + x1 + " Y: " + y1);
                // System.out.println("Beacon: X: " + x2 + " Y: " + y2);

                if (y2 == row) {
                    beacons.add(x2);
                }

                if (y1 == row) {
                    sensors.add(x1);
                }
                int distancia = Math.abs(x2 - x1) + Math.abs(y2 - y1);

                int n = distancia - Math.abs(y1 - row);

                while (n >= 0) {
                    fila.add(x1 + n);
                    fila.add(x1 - n);
                    n--;
                }

            }
            fila.removeAll(beacons);
            fila.removeAll(sensors);

            System.out.println(fila.size());
        }

    }

}
