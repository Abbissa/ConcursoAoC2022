package Dia22;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dia22 {

    private static final String INPUT = "Dia22\\input.txt";
    private static final String EXAMPLE = "Dia22\\example.txt";

    private static final int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public static void main(String[] args) throws FileNotFoundException {

        parte1(EXAMPLE);
        parte1(INPUT);
        parte2EXAMPLE(EXAMPLE, 4);
        parte2INPUT(INPUT, 50);
    }

    private static void parte2INPUT(String file, int size) throws FileNotFoundException {
        String[][] mapa = new String[6][size];
        String instructions;

        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            for (int i = 0; i < mapa[0].length; i++) {
                String line = sc.nextLine().trim();

                mapa[0][i] = line.substring(0, size);
                mapa[1][i] = line.substring(size);

            }
            for (int i = 0; i < mapa[1].length; i++) {
                mapa[2][i] = sc.nextLine().trim();

            }
            for (int i = 0; i < mapa[4].length; i++) {
                String line = sc.nextLine();
                mapa[3][i] = line.substring(0, size);
                mapa[4][i] = line.substring(size);
            }
            for (int i = 0; i < mapa[4].length; i++) {
                mapa[5][i] = sc.nextLine();
            }
            sc.nextLine();
            instructions = sc.nextLine();

            instructions = instructions.replace("R", " R ");
            instructions = instructions.replace("L", " L ");
            int face = 0;
            int indexTraverse = 0;
            int x = 0;
            int y = 0;

            try (Scanner read = new Scanner(instructions)) {
                while (read.hasNextLine()) {
                    int move = read.nextInt();
                    String turn = "";
                    if (read.hasNext())
                        turn = read.next();
                    int lastX = x;
                    int lastY = y;
                    int lastFace = face;
                    int lastIndexTraverse = indexTraverse;
                    while (move > 0) {

                        x = (x + dirs[indexTraverse][0]);
                        y = (y + dirs[indexTraverse][1]);

                        if (x < 0) {
                            if (face == 0) {
                                face = 3;
                                indexTraverse = 0;
                                y = size - 1 - y;
                                x = 0;
                            } else if (face == 1) {
                                face = 0;
                                x = size - 1;

                            } else if (face == 2) {
                                face = 3;
                                indexTraverse = 1;
                                x = y;
                                y = 0;
                            } else if (face == 4) {
                                face = 3;
                                x = size - 1;
                            } else if (face == 3) {
                                face = 0;
                                indexTraverse = 0;
                                y = size - 1 - y; // inversa
                                x = 0;
                            } else if (face == 5) {
                                face = 0;
                                x = y;
                                y = 0;
                                indexTraverse=1;
                            }
                        } else if (x > size - 1) {
                            if (face == 0) {
                                face = 1;
                                x = 0;
                            } else if (face == 1) {
                                face = 4;
                                indexTraverse = 2;
                                x = size - 1;
                                y = size - 1 - y;
                            } else if (face == 2) {
                                face = 1;
                                x = y;
                                y = size - 1;
                                indexTraverse = 3;
                            } else if (face == 4) {
                                face = 1;
                                indexTraverse = 2;
                                y = size - 1 - y; // inversa
                                x = size - 1;
                            } else if (face == 3) {
                                face = 4;
                                x = 0;
                            } else if (face == 5) {
                                face = 4;
                                x = y;
                                y = size - 1;
                                indexTraverse = 3;
                            }

                        } else if (y > size - 1) {
                            if (face == 0) {
                                face = 2;
                                y = 0;
                            } else if (face == 1) {
                                face = 2;
                                y = x;
                                x = size - 1;
                                indexTraverse = 2;
                            } else if (face == 2) {
                                face = 4;
                                y = 0;

                            } else if (face == 4) {
                                face = 5;
                                y = x;
                                x = size - 1;
                                indexTraverse = 2;
                            } else if (face == 3) {
                                face = 5;
                                y = 0;
                            } else if (face == 5) {
                                face = 1;
                                y = 0;

                            }
                        }
                        if (y < 0) {
                            if (face == 0) {
                                face = 5;
                                y = x;
                                x = 0;
                                indexTraverse = 0;
                            } else if (face == 1) {
                                face = 5;
                                y = size - 1;// inversa
                            } else if (face == 2) {
                                face = 0;
                                y = size - 1;// inversa;

                            } else if (face == 4) {
                                face = 2;
                                y = size - 1;
                            } else if (face == 3) {
                                face = 2;
                                y = x;
                                x = 0;
                                indexTraverse = 0;
                            } else if (face == 5) {
                                face = 3;
                                y = size - 1; // inversa

                            }
                        }

                        if (mapa[face][y].charAt(x) == '#') {

                            x = lastX;
                            y = lastY;
                            face = lastFace;
                            indexTraverse = lastIndexTraverse;
                            break;
                        } else if (mapa[face][y].charAt(x) == '.') {
                            lastX = x;
                            lastY = y;
                            lastFace = face;
                            lastIndexTraverse = indexTraverse;
                            move--;
                        }

                    }
                    if (turn.equals("R")) {
                        indexTraverse = (indexTraverse + 1) % dirs.length;
                    } else if (turn.equals("L")) {
                        indexTraverse = (indexTraverse + dirs.length - 1) % dirs.length;
                    }

                }
            }

            switch (face) {
                case 0:
                    x += size;
                    break;
                case 1:
                    x += 2 * size;
                    break;
                case 2:
                    x += size;
                    y += size;
                    break;
                case 3:
                    y += 2 * size;
                    break;
                case 4:
                    x += size;
                    y += 2 * size;
                    break;
                case 5:
                    y += 3 * size;
                    break;
                default:
                    break;
            }
            x += 1;
            y += 1;
            System.out.println(y * 1000 + 4 * x + indexTraverse);

        }

    }

    private static void parte2EXAMPLE(String file, int size) throws FileNotFoundException {
        String[][] mapa = new String[6][size];
        String instructions;

        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            for (int i = 0; i < mapa[0].length; i++) {
                mapa[0][i] = sc.nextLine().trim();

            }
            for (int i = 0; i < mapa[1].length; i++) {
                String line = sc.nextLine();
                mapa[1][i] = line.substring(2 * size, 3 * size);
                mapa[2][i] = line.substring(size, 2 * size);
                mapa[3][i] = line.substring(2 * size);
            }
            for (int i = 0; i < mapa[4].length; i++) {
                String line = sc.nextLine();
                mapa[4][i] = line.substring(2 * size, 3 * size);
                mapa[5][i] = line.substring(3 * size, 4 * size);
            }
            sc.nextLine();
            instructions = sc.nextLine();

            instructions = instructions.replace("R", " R ");
            instructions = instructions.replace("L", " L ");
            int face = 0;
            int indexSol = 0;
            int indexTraverse = 0;
            int x = 0;
            int y = 0;

            try (Scanner read = new Scanner(instructions)) {
                while (read.hasNextLine()) {
                    int move = read.nextInt();
                    String turn = "";
                    if (read.hasNext())
                        turn = read.next();
                    int lastX = x;
                    int lastY = y;
                    int lastFace = face;
                    int lastIndexTraverse = indexTraverse;
                    int lastIndexSol = indexSol;
                    while (move > 0) {

                        x = (x + dirs[indexTraverse][0]);
                        y = (y + dirs[indexTraverse][1]);

                        if (x < 0) {
                            if (face == 0) {
                                face = 2;
                                indexTraverse = 1;
                                x = y;
                                y = 0;
                            } else if (face == 1) {
                                face = 5;
                                indexTraverse = 3;
                                x = size - 1 - y; // inversa
                                y = size - 1;
                            } else if (face == 2) {
                                face = 1;
                                x = size - 1;
                            } else if (face == 3) {
                                face = 2;
                                x = size - 1;
                            } else if (face == 4) {
                                face = 2;
                                indexTraverse = 3;
                                x = size - 1 - y; // inversa
                                y = size - 1;
                            } else if (face == 5) {
                                face = 4;
                                x = size - 1;

                            }
                        } else if (x > size - 1) {
                            if (face == 0) {
                                face = 5;
                                indexTraverse = 2;
                                y = size - 1 - y; // inversa
                                x = size - 1;
                            } else if (face == 1) {
                                face = 2;
                                x = 0;
                            } else if (face == 2) {
                                face = 3;
                                x = 0;
                            } else if (face == 3) {
                                face = 5;
                                indexTraverse = 1;
                                x = size - 1 - y; // inversa
                                y = 0;
                            } else if (face == 4) {
                                face = 5;
                                x = 0;
                            } else if (face == 5) {
                                face = 0;
                                y = size - 1 - y; // inversa
                                x = size - 1;
                            }

                        } else if (y > size - 1) {
                            if (face == 0) {
                                face = 3;
                                y = 0;
                            } else if (face == 1) {
                                face = 4;
                                x = size - 1 - x; // inversa
                                y = size - 1;
                                indexTraverse = 3;
                            } else if (face == 2) {
                                face = 4;
                                indexTraverse = 0;
                                y = size - 1 - x;// inversa;
                                x = 0;

                            } else if (face == 3) {
                                face = 4;
                                y = 0;
                            } else if (face == 4) {
                                face = 1;
                                y = size - 1;
                                indexTraverse = 3;
                                x = size - 1 - x; // inversa
                            } else if (face == 5) {
                                face = 1;
                                indexTraverse = 1;
                                y = size - 1 - x; // inversa
                                x = 0;

                            }
                        }
                        if (y < 0) {
                            if (face == 0) {
                                face = 1;
                                x = size - 1 - x;// inversa
                                y = 0;
                                indexTraverse = 1;
                            } else if (face == 1) {
                                face = 0;
                                x = size - 1 - x;// inversa
                                y = 0;
                                indexTraverse = 1;
                            } else if (face == 2) {
                                face = 0;
                                indexTraverse = 0;
                                y = x;// inversa;
                                x = 0;

                            } else if (face == 3) {
                                face = 0;
                                y = size - 1;
                            } else if (face == 4) {
                                face = 3;
                                y = size - 1;
                            } else if (face == 5) {
                                face = 3;
                                indexTraverse = 3;
                                y = size - 1 - x; // inversa
                                x = size - 1;

                            }
                        }

                        if (mapa[face][y].charAt(x) == '#') {

                            x = lastX;
                            y = lastY;
                            face = lastFace;
                            indexTraverse = lastIndexTraverse;
                            indexSol = lastIndexSol;
                            break;
                        } else if (mapa[face][y].charAt(x) == '.') {
                            lastX = x;
                            lastY = y;
                            lastFace = face;
                            lastIndexTraverse = indexTraverse;
                            lastIndexSol = indexSol;
                            move--;
                        }

                    }
                    if (turn.equals("R")) {
                        indexSol = (indexSol + 1) % dirs.length;
                        indexTraverse = (indexTraverse + 1) % dirs.length;
                    } else if (turn.equals("L")) {
                        indexSol = (indexSol + dirs.length - 1) % dirs.length;
                        indexTraverse = (indexTraverse + dirs.length - 1) % dirs.length;
                    }

                }
            }

            switch (face) {
                case 0:
                    x += 2 * size;
                    break;
                case 1:
                    y += size;
                    break;
                case 2:
                    x += size;
                    y += size;
                    break;
                case 3:
                    y += size;
                    x += 2 * size;
                    break;
                case 4:
                    x += 2 * size;
                    y += 2 * size;
                    break;
                case 5:
                    y += 2 * size;
                    x += 3 * size;
                    break;
                default:
                    break;
            }
            x += 1;
            y += 1;
            System.out.println(y * 1000 + 4 * x + indexTraverse);

        }

    }

    private static void parte1(String file) throws FileNotFoundException {
        ArrayList<String> mapa = new ArrayList<>();
        String instructions;
        int maxLength = 0;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            String line = sc.nextLine();
            while (line != "") {

                mapa.add(line);
                maxLength = Math.max(maxLength, line.length());
                line = sc.nextLine();

            }
            instructions = sc.nextLine();
        }

        instructions = instructions.replace("R", " R ");
        instructions = instructions.replace("L", " L ");
        int index = 0;
        int x = 0;
        int y = 0;

        for (int i = 0; i < mapa.get(0).length(); i++) {
            if (mapa.get(0).charAt(i) == '.') {
                x = i;
                break;
            }

        }
        try (Scanner read = new Scanner(instructions)) {
            while (read.hasNextLine()) {
                int move = read.nextInt();
                String turn = "";
                if (read.hasNext())
                    turn = read.next();
                int lastX = x;
                int lastY = y;
                while (move > 0) {

                    x = (x + dirs[index][0]) % mapa.get(y).length();
                    y = (y + dirs[index][1]) % mapa.size();
                    if (x < 0)
                        x = mapa.get(y).length() - 1;
                    if (y < 0)
                        y = mapa.size() - 1;

                    if (mapa.get(y).charAt(x) == '#') {

                        x = lastX;
                        y = lastY;
                        break;
                    } else if (mapa.get(y).charAt(x) == '.') {
                        lastX = x;
                        lastY = y;
                        move--;
                    }

                }
                if (turn.equals("R")) {
                    index = (index + 1) % dirs.length;
                } else if (turn.equals("L")) {
                    index = (index + dirs.length - 1) % dirs.length;
                }

            }
        }

        System.out.println((x + 1) + " " + (y + 1) + " " + index);
        x += 1;
        y += 1;
        System.out.println(y * 1000 + 4 * x + index);

    }

}
