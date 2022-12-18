package Dia14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class dia14 {

    private final static String INPUT = "Dia14\\input.txt";
    private final static String EXAMPLE = "Dia14\\example.txt";

    private final static int T = 10;
    private final static int T_ESPERA = 2000;

    private final static boolean IMPRIMIR = false;

    public static void main(String[] args) throws IOException, InterruptedException {

        int res1 = parte1(EXAMPLE, IMPRIMIR, T);
        Thread.sleep(T_ESPERA);
        int res2 = parte2(EXAMPLE, IMPRIMIR, T);
        Thread.sleep(T_ESPERA);
        int res3 = parte1(INPUT, IMPRIMIR, T);
        Thread.sleep(T_ESPERA);
        int res4 = parte2(INPUT, IMPRIMIR, T);
        Thread.sleep(T_ESPERA);
        if (IMPRIMIR) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.println("Ejemplo: ");
        System.out.println("Parte 1: " + res1);
        System.out.println("Parte 2: " + res2);
        System.out.println("Input");
        System.out.println("Parte 1: " + res3);
        System.out.println("Parte 2: " + res4);

    }

    private static int parte2(String file, boolean paint, int t) throws IOException, InterruptedException {

        boolean[][] mapa = new boolean[700][170];

        int maxY = leer(mapa, 0, file);

        for (int i = 0; i < mapa.length; i++) {
            mapa[i][maxY + 2] = true;

        }
        if (paint)
            pintarRocas(mapa);

        int res = 0;
        boolean[][] sand = new boolean[mapa.length][mapa[0].length];
        while (!sand[500][0]) {

            int xS = 500;
            int yS = 0;

            boolean caido = false;
            while (!caido) {
                char escCode = 0x1B;
                if (paint) {
                    System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                    System.out.print("o");

                    Thread.sleep(t);
                    System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                    System.out.print(".");
                }
                if (!mapa[xS][yS + 1]) {
                    yS++;

                } else if (!mapa[xS - 1][yS + 1]) {

                    xS--;
                    yS++;

                } else if (!mapa[xS + 1][yS + 1]) {

                    xS++;
                    yS++;

                } else {
                    mapa[xS][yS] = true;
                    caido = true;
                    res++;
                    sand[xS][yS] = true;
                    if (paint) {
                        System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                        System.out.print("o");
                    }
                }

                // System.out.print("\033[H\033[2J");
                // System.out.flush();

            }

        }
        escribirOutput(mapa, sand);

        char escCode = 0x1B;
        if (paint)
            System.out.print(String.format("%c[%d;%df", escCode, mapa[0].length, 0));

        return res;
    }

    private static int parte1(String file, boolean paint, int t) throws FileNotFoundException, InterruptedException {

        boolean[][] mapa = new boolean[530][170];
        int maxY = 0;
        maxY = leer(mapa, maxY, file);

        boolean fin = false;
        int res = 0;
        boolean[][] sand = new boolean[mapa.length][mapa[0].length];
        char escCode = 0x1B;
        if (paint)
            pintarRocas(mapa);
        while (!fin) {

            int xS = 500;
            int yS = 0;

            boolean caido = false;
            while (!caido) {
                if (paint) {
                    System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                    System.out.print("o");

                    Thread.sleep(t);
                    System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                    System.out.print(".");
                }
                if (yS > maxY) {
                    caido = true;
                    fin = true;
                }
                if (!mapa[xS][yS + 1]) {
                    yS++;
                } else if (!mapa[xS - 1][yS + 1]) {
                    xS--;
                    yS++;

                } else if (!mapa[xS + 1][yS + 1]) {
                    xS++;
                    yS++;

                } else {
                    mapa[xS][yS] = true;
                    caido = true;
                    res++;
                    // System.out.println(xS + " " + yS);
                    sand[xS][yS] = true;
                    if (paint) {
                        System.out.print(String.format("%c[%d;%df", escCode, yS + 1, xS));
                        System.out.print("o");
                    }
                }

            }
            escribirOutput(mapa, sand);
        }

        if (paint)
            System.out.print(String.format("%c[%d;%df", escCode, mapa[0].length + 1, 0));

        return res;

    }

    private static void escribirOutput(boolean[][] mapa, boolean[][] sand) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("Dia14\\output.txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < sand[0].length; i++) {
                for (int j = 0; j < sand.length; j++) {
                    if (sand[j][i]) {
                        pw.print("o");
                    } else if (mapa[j][i]) {
                        pw.print("#");
                    } else {
                        pw.print(".");
                    }
                }
                pw.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void pintarRocas(boolean[][] mapa) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < mapa[0].length; i++) {
            for (int j = 0; j < mapa.length; j++) {

                if (mapa[j][i]) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();

        }
    }

    private static int leer(boolean[][] mapa, int maxY, String file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] limites = line.split(" -> ");

                for (int i = 0; i < limites.length - 1; i++) {

                    String[] nums = limites[i].split(",");
                    int x1 = Integer.parseInt(nums[0]);
                    int y1 = Integer.parseInt(nums[1]);

                    nums = limites[i + 1].split(",");
                    int x2 = Integer.parseInt(nums[0]);
                    int y2 = Integer.parseInt(nums[1]);

                    if (x1 == x2) {
                        int add = (y2 - y1) / Math.abs(y1 - y2);
                        for (int j = 0; y1 + j != y2 + add; j = j + add) {
                            maxY = Math.max(maxY, y1 + j);
                            mapa[x1][y1 + j] = true;

                        }
                    } else if (y1 == y2) {
                        int add = (x2 - x1) / Math.abs(x1 - x2);
                        for (int j = 0; x1 + j != x2 + add; j = j + add) {
                            mapa[x1 + j][y1] = true;

                        }
                    }

                }
            }
        }
        return maxY;
    }
}
