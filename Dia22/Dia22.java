package Dia22;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dia22 {

    private static final String INPUT = "Dia22\\input.txt";

    private static final int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public static void main(String[] args) throws FileNotFoundException {

        parte1(INPUT);

    }

    private static void parte1(String file) throws FileNotFoundException {
        ArrayList<char[]> mapa = new ArrayList<>();
        String instructions;

        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            String line = sc.nextLine();
            while (line != "") {

                mapa.add(new char[line.length()]);
                mapa.add(line.toCharArray());
                line = sc.nextLine();
            }
            instructions = sc.nextLine();
        }
        instructions = instructions.replace("R", " R ");
        instructions = instructions.replace("L", " L ");
        System.out.println(instructions);
        int index = 0;
        int x = 0;
        int y = 0;

        for (int i = 0; i < mapa.get(0).length; i++) {
            if (mapa.get(0)[i] == '.') {
                x = i;
            }

        }
        try (Scanner read = new Scanner(instructions)) {
            for (int i = 0; i < instructions.length(); i += 2) {
                int move = read.nextInt();
                String turn = read.next();
                int lastX = x;
                int lastY = y;
                while (move > 0) {

                    x = (x + dirs[index][0]) % mapa.get(y).length;
                    y = (y + dirs[index][1]) % mapa.size();

                    if (mapa.get(y)[x] == '#') {
                        // girar
                        if (turn.equals("R")) {
                            index = (index + 1) % dirs.length;
                        } else {
                            index = (index + dirs.length - 1) % dirs.length;
                        }

                        x = lastX;
                        y = lastY;
                        break;
                    } else if (mapa.get(y)[x] == '.') {
                        lastX = x;
                        lastY = y;
                        move--;
                    }
                }

            }
        }

    }

}
