package Dia18;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dia18 {

    private static final String INPUT = "Dia18\\input.txt";
    private static final String EXAMPLE = "Dia18\\example.txt";
    private static final int TAM = 25;

    public static void main(String[] args) throws FileNotFoundException {

        parte1(EXAMPLE);
        parte2(EXAMPLE);
        parte1(INPUT);
        parte2(INPUT);

    }

    private static void parte2(String file) throws FileNotFoundException {
        boolean[][][] scan = leer(file);
        boolean[][][] reachable = canBeAccessed(scan);

        int res = 0;
        for (int i = 0; i < scan.length; i++) {
            for (int j = 0; j < scan.length; j++) {
                for (int j2 = 0; j2 < scan.length; j2++) {
                    int add = 0;
                    if (!scan[i][j][j2]) {
                        if (i < TAM && scan[i + 1][j][j2])
                            add++;
                        if (j < TAM && scan[i][j + 1][j2])
                            add++;

                        if (j2 < TAM && scan[i][j][j2 + 1])
                            add++;

                        if (i > 0 && scan[i - 1][j][j2])
                            add++;

                        if (j > 0 && scan[i][j - 1][j2])
                            add++;

                        if (j2 > 0 && scan[i][j][j2 - 1])
                            add++;

                        if (reachable[i][j][j2])
                            res += add;

                    }
                }
            }
        }

        
        
        System.out.println(res);
    }

    private static void parte1(String file) throws FileNotFoundException {

        boolean[][][] scan = leer(file);

        int res = 0;
        for (int i = 0; i < scan.length; i++) {
            for (int j = 0; j < scan.length; j++) {
                for (int j2 = 0; j2 < scan.length; j2++) {
                    int add = 0;
                    if (!scan[i][j][j2]) {
                        if (i < TAM && scan[i + 1][j][j2])
                            add++;
                        if (j < TAM && scan[i][j + 1][j2])
                            add++;

                        if (j2 < TAM && scan[i][j][j2 + 1])
                            add++;

                        if (i > 0 && scan[i - 1][j][j2])
                            add++;

                        if (j > 0 && scan[i][j - 1][j2])
                            add++;

                        if (j2 > 0 && scan[i][j][j2 - 1])
                            add++;

                    }

                    res += add;
                }
            }
        }
        print(scan);
        System.out.println(res);

    }

    private static boolean[][][] leer(String file) throws FileNotFoundException {
        boolean[][][] scan = new boolean[TAM + 1][TAM + 1][TAM + 1];
        int max = 0;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String[] coords = line.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                int z = Integer.parseInt(coords[2]);

                max = Math.max(max, z);
                max = Math.max(max, x);
                max = Math.max(max, y);
                scan[x + 1][y + 1][z + 1] = true;
            }
        }
        return scan;
    }

    private static boolean[][][] canBeAccessed(boolean[][][] scan) {
        boolean[][][] res = new boolean[scan.length][scan.length][scan.length];
        boolean changed = true;
        while (changed) {
            changed = false;
            boolean auxChanged = false;

            for (int i = 0; i < scan.length; i++) {
                for (int j = 0; j < scan.length; j++) {
                    for (int j2 = 0; j2 < scan.length; j2++) {
                        if (i == 0 || i == scan.length - 1 || j == 0 || j == scan.length - 1 || j2 == 0
                                || j2 == scan.length - 1) {

                            auxChanged = !res[i][j][j2];

                            res[i][j][j2] = true;

                        } else if (res[i + 1][j][j2] || res[i][j + 1][j2] || res[i][j][j2 + 1] || res[i - 1][j][j2]
                                || res[i][j - 1][j2] || res[i][j][j2 - 1]) {

                            auxChanged = !res[i][j][j2];
                            res[i][j][j2] = true;
                        }
                        if (scan[i][j][j2]) {
                            res[i][j][j2] = false;
                            auxChanged = false;
                        }

                        if (auxChanged) {
                            changed = true;
                        }

                    }
                }
            }

        }
        //print(res);
        return res;
    }

    private static void print(boolean[][][] res) {
        for (int i = 0; i < res.length; i++) {

            for (int j = 0; j < res.length; j++) {

                for (int j2 = 0; j2 < res.length; j2++) {

                    if (res[i][j][j2])
                        System.out.print("█");
                    else
                        System.out.print(".");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

        }
    }
}
