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
        parte2(EXAMPLE);

    }

    private static void parte2(String file) throws FileNotFoundException {
        String[][] mapa = new String[6][50];
        String instructions;

        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            for (int i = 0; i < mapa[0].length; i++) {
                mapa[0][i] = sc.nextLine().trim();

            }
            for (int i = 0; i < mapa[1].length; i++) {
                String line = sc.nextLine();
                mapa[1][i] = line.substring(0, 50);
                mapa[2][i] = line.substring(50, 100);
                mapa[3][i] = line.substring(100);
            }
            for (int i = 0; i < mapa[4].length; i++) {
                String line = sc.nextLine();
                mapa[1][i] = line.substring(0, 50);
                mapa[2][i] = line.substring(50, 100);
            }
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
                                x = y - 49;
                                y = 49;
                            } else if (face == 2) {
                                face = 1;
                                x = 49;
                            } else if (face == 3) {
                                face = 2;
                                x = 49;
                            } else if (face == 4) {
                                face = 2;
                                indexTraverse = 3;
                                x = y - 49;
                                y = 49;
                            } else if (face == 5) {
                                face = 4;
                                x = 49;

                            }
                        } else if (x > 49) {
                            if (face == 0) {
                                face = 5;
                                indexTraverse = 2;
                                y = y - 49;
                                x = 0;
                            } else if (face == 1) {
                                face = 2;
                                x=0;
                            } else if (face == 2) {
                                face = 3;
                                x=0;
                            } else if (face == 3) {
                                face = 5;
                                indexTraverse = 1;
                                x=y-49;
                                y=0;
                            } else if (face == 4) {
                                face = 5;
                            }

                        }

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
