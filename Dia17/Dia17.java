package Dia17;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dia17 {

    private static String inputLine;
    private static int maxY = 0;
    private static long maxHeight = 0;

    private static int fig = 0;


    private static String EXAMPLE = "Dia17\\example.txt";
    private static String INPUT = "Dia17\\input.txt";

    public static void main(String[] args) throws FileNotFoundException {
        parte2(EXAMPLE, 2022);
        parte2(INPUT, 2022);
        long a = (long) Math.pow(10, 12);
        parte2(EXAMPLE, a);
        parte2(INPUT, a);

    }

    private static void parte2(String file, long l) throws FileNotFoundException {
        ArrayList<boolean[]> lista = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            lista.add(0, new boolean[7]);
        }

        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            inputLine = sc.nextLine();

            HashMap<String, Integer> height = new HashMap<>();
            HashMap<String, Integer> num = new HashMap<>();

            int index = 0;
            maxY = 0;
            maxHeight = 0;
            fig = 0;
            boolean superado = false;

            for (long i = 0; i < l; i++) {
                boolean[][] objeto = elegirObjeto();

                int x = 2;
                int y = maxY + 6;
                boolean caido = false;
                while (!caido) {
                    int add = 0;
                    switch (inputLine.charAt(index % inputLine.length())) {
                        case '>':
                            add++;

                            break;
                        case '<':
                            add--;

                            break;
                        default:
                            break;
                    }
                    index = (index + 1) % inputLine.length();
                    boolean fallo = false;
                    for (int j = 0; j < objeto.length && !fallo; j++) {
                        for (int j2 = 0; j2 < objeto.length && !fallo; j2++) {
                            if (objeto[j][j2]
                                    && (x + j2 + add >= 7 || x + j2 + add < 0 || lista.get(y - j)[x + j2 + add]))
                                fallo = true;
                        }
                    }
                    if (!fallo)
                        x += add;

                    add = -1;
                    fallo = false;

                    for (int j = 0; j < objeto.length && !fallo; j++) {
                        for (int j2 = 0; j2 < objeto.length && !fallo; j2++) {
                            if (objeto[j][j2]
                                    && (y - j + add < 0 || lista.get(y - j + add)[x + j2]))
                                fallo = true;
                        }
                    }

                    if (fallo) {
                        int preMax = maxY;
                        for (int j = 0; j < objeto.length; j++) {
                            for (int j2 = 0; j2 < objeto.length; j2++) {
                                if (objeto[j][j2]) {
                                    lista.get(y - j)[x + j2] = objeto[j][j2];
                                    maxY = Math.max(maxY, y - j + 1);
                                }
                            }

                        }
                        caido = true;
                        maxHeight += maxY - preMax;
                    }

                    y--;

                }
                // if (superado) {
                // pintarLista(lista, 20);
                // System.out.println(maxHeight);
                // }
                String key = estado(lista) + "," + x + "," + index + "," + fig;
                if (num.get(key) != null && !superado) {

                    int add = (int) (maxHeight - height.get(key));
                    int addi = (int) (i - num.get(key));
                    long n = ((l - 1) - i) / addi;

                    i += addi * n;
                    maxHeight += add * n;

                    superado = true;

                    // pintarLista(lista, 20);
                    // System.out.println(maxHeight);

                } else {

                    height.put(key, (int)maxHeight);
                    num.put(key, (int)i);

                }

                while (maxY + 8 > lista.size()) {
                    lista.add(new boolean[7]);
                }

            }

            System.out.println(maxHeight);

        }

    }

    private static String estado(ArrayList<boolean[]> lista) {
        String key = "";
        for (int j = lista.size() - 1; j >= lista.size() - 1 - 100 && j >= 0; j--) {
            for (int j2 = 0; j2 < lista.get(j).length; j2++) {
                if (lista.get(j)[j2])
                    key += "@";
                else
                    key += ".";

            }

        }

        return key;
    }

    private static void parte1(String file, long k) throws FileNotFoundException {
        ArrayList<boolean[]> lista = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            lista.add(0, new boolean[7]);
        }
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                inputLine = sc.nextLine();

            }
            int index = 0;
            maxY = 0;
            maxHeight = 0;
            fig = 0;
            for (long i = 0; i < k; i++) {
                boolean[][] objeto = elegirObjeto();

                int x = 2;
                int y = maxY + 6;

                boolean caido = false;
                while (!caido) {
                    int add = 0;
                    switch (inputLine.charAt(index % inputLine.length())) {
                        case '>':
                            add++;

                            break;
                        case '<':
                            add--;

                            break;
                        default:
                            break;
                    }
                    index = (index + 1) % inputLine.length();
                    boolean fallo = false;
                    for (int j = 0; j < objeto.length && !fallo; j++) {
                        for (int j2 = 0; j2 < objeto.length && !fallo; j2++) {
                            if (objeto[j][j2]
                                    && (x + j2 + add >= 7 || x + j2 + add < 0 || lista.get(y - j)[x + j2 + add]))
                                fallo = true;
                        }
                    }
                    if (!fallo)
                        x += add;

                    // pintarRoca(lista, objeto, x, y);

                    add = -1;
                    fallo = false;

                    for (int j = 0; j < objeto.length && !fallo; j++) {
                        for (int j2 = 0; j2 < objeto.length && !fallo; j2++) {
                            if (objeto[j][j2]
                                    && (y - j + add < 0 || lista.get(y - j + add)[x + j2]))
                                fallo = true;
                        }
                    }

                    if (fallo) {
                        int preMax = maxY;

                        for (int j = 0; j < objeto.length; j++) {
                            for (int j2 = 0; j2 < objeto.length; j2++) {
                                if (objeto[j][j2]) {
                                    lista.get(y - j)[x + j2] = objeto[j][j2];
                                    maxY = Math.max(maxY, y - j + 1);
                                }
                            }

                        }
                        caido = true;
                        maxHeight += maxY - preMax;

                    }

                    y--;

                }

                while (maxY + 8 > lista.size()) {
                    lista.add(new boolean[7]);
                }

            }

            System.out.println(maxHeight);
            // pintarLista(lista, lista.size()-1);

        }
    }

    private static void pintarLista(ArrayList<boolean[]> lista, int n) {
        for (int j = lista.size() - 1; j >= lista.size() - 1 - n; j--) {
            for (int j2 = 0; j2 < lista.get(j).length; j2++) {
                if (lista.get(j)[j2])
                    System.out.print("@");
                else
                    System.out.print(".");

            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private static void pintarRoca(ArrayList<boolean[]> lista, boolean[][] objeto, int x, int y) {

        for (int j = 0; j < objeto.length; j++) {
            for (int j2 = 0; j2 < objeto.length; j2++) {
                if (objeto[j][j2]) {
                    lista.get(y - j)[x + j2] = objeto[j][j2];
                }
            }

        }

        for (int j = lista.size() - 1; j >= 0; j--) {
            for (int j2 = 0; j2 < lista.get(j).length; j2++) {
                if (lista.get(j)[j2])
                    System.out.print("@");
                else
                    System.out.print(".");

            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();

        for (int j = 0; j < objeto.length; j++) {
            for (int j2 = 0; j2 < objeto.length; j2++) {
                if (objeto[j][j2]) {
                    lista.get(y - j)[x + j2] = false;
                }
            }

        }

    }

    private static boolean[][] elegirObjeto() {

        boolean[][] res = new boolean[4][4];
        switch (fig) {
            case 0:
                res[3][0] = true;
                res[3][1] = true;
                res[3][2] = true;
                res[3][3] = true;
                break;
            case 1:
                res[1][1] = true;
                res[2][0] = true;
                res[2][1] = true;
                res[2][2] = true;
                res[3][1] = true;

                break;
            case 2:
                res[1][2] = true;
                res[2][2] = true;
                res[3][0] = true;
                res[3][1] = true;
                res[3][2] = true;
                break;
            case 3:
                res[0][0] = true;
                res[1][0] = true;
                res[2][0] = true;
                res[3][0] = true;

                break;
            case 4:
                res[2][0] = true;
                res[2][1] = true;
                res[3][0] = true;
                res[3][1] = true;

                break;

        }
        fig = (fig + 1) % 5;

        return res;
    }

}
