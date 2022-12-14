package Dia14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class dia14 {

    private final static String INPUT = "Dia14\\input.txt";
    private final static String EXAMPLE = "Dia14\\example.txt";

    public static void main(String[] args) throws FileNotFoundException {

        parte1(INPUT);
        parte2(INPUT);

    }

    private static void parte2(String file) throws FileNotFoundException {

        boolean[][] mapa = new boolean[700][170];
        int maxY = 0;
        maxY = leer(mapa, maxY, file);

        for (int i = 0; i < mapa.length; i++) {
            mapa[i][maxY + 2] = true;

        }

        boolean fin = false;
        int res = 0;
        boolean[][] sand = new boolean[mapa.length][mapa[0].length];
        while (!sand[500][0]) {

            int xS = 500;
            int yS = 0;

            boolean caido = false;
            while (!caido) {
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
                }

            }
        }

        escribirOutput(mapa, sand);
        
        
        System.out.println(res);

    }

   

    private static void parte1(String file) throws FileNotFoundException {
        boolean[][] mapa = new boolean[530][170];
        int maxY = 0;
        maxY = leer(mapa, maxY, file);

        boolean fin = false;
        int res = 0;
        boolean[][] sand = new boolean[mapa.length][mapa[0].length];
        while (!fin) {

            int xS = 500;
            int yS = 0;

            boolean caido = false;
            while (!caido) {
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
                }

            }
            //escribirOutput(mapa, sand);
        }
        System.out.println(res);

    }
    private static void escribirOutput(boolean[][] mapa, boolean[][] sand) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
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
                            // for (int k = 0; k < 10; k++) {
                            // for (int l = 493; l < 505; l++) {
                            // if(mapa[l][k]){
                            // System.out.print("#");
                            // }else{
                            // System.out.print(".");
                            // }
                            // }
                            // System.out.println();

                            // }
                            // System.out.println();
                        }
                    } else if (y1 == y2) {
                        int add = (x2 - x1) / Math.abs(x1 - x2);
                        for (int j = 0; x1 + j != x2 + add; j = j + add) {
                            mapa[x1 + j][y1] = true;
                            // for (int k = 0; k < 10; k++) {
                            // for (int l = 493; l < 505; l++) {
                            // if(mapa[l][k]){
                            // System.out.print("#");
                            // }else{
                            // System.out.print(".");
                            // }
                            // }
                            // System.out.println();
                            // }
                            // System.out.println();
                        }
                    }

                }
            }
        }
        return maxY;
    }
}
