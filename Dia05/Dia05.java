package Dia05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dia05 {

    public static void main(String[] args) throws FileNotFoundException {
        parte1();
        parte2();

    }

    private static void parte2() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia05\\input.txt"))) {
            String line = sc.nextLine();
            List<List<Character>> cajas = new ArrayList<List<Character>>();
            for (int i = 0; i < (line.length() + 1) / 4; i++) {
                cajas.add(i, new ArrayList<Character>());
            }
            while (!Character.isDigit(line.charAt(1))) {
                int count = 0;
                for (int i = 1; i < line.length(); i = i + 4) {

                    if (line.charAt(i) != ' ') {
                        cajas.get(count).add(line.charAt(i));
                    }
                    count++;
                }
                line = sc.nextLine();
            }

            System.out.println(cajas.toString());
            sc.nextLine();
            int iter = 0;
            while (sc.hasNext()) {
                sc.next();
                int nCajas = sc.nextInt();
                sc.next();
                int from = sc.nextInt();
                sc.next();
                int to = sc.nextInt();
                List<Character> pilaOrigen = cajas.get(from - 1);
                List<Character> pilaDestino = cajas.get(to - 1);
                for (int i = 0; i < nCajas; i++) {
                    pilaDestino.add(0, pilaOrigen.remove(nCajas-1-i));
                }
                System.out.println(iter++ + ": " + cajas.toString());

            }

            for (List<Character> list : cajas) {
                System.out.print(list.get(0));
            }
            System.out.println();
        }

    }

    private static void parte1() throws FileNotFoundException {

        try (Scanner sc = new Scanner(new FileInputStream("Dia05\\input.txt"))) {
            String line = sc.nextLine();
            List<List<Character>> cajas = new ArrayList<List<Character>>();
            for (int i = 0; i < (line.length() + 1) / 4; i++) {
                cajas.add(i, new ArrayList<Character>());
            }
            while (line != "" && !Character.isDigit(line.charAt(1))) {
                int count = 0;
                for (int i = 1; i < line.length(); i = i + 4) {

                    if (line.charAt(i) != ' ') {
                        cajas.get(count).add(line.charAt(i));
                    }
                    count++;
                }
                line = sc.nextLine();
            }

            System.out.println(cajas.toString());
            sc.nextLine();
            int iter = 0;
            while (sc.hasNext()) {
                sc.next();
                int nCajas = sc.nextInt();
                sc.next();
                int from = sc.nextInt();
                sc.next();
                int to = sc.nextInt();
                List<Character> pilaOrigen = cajas.get(from - 1);
                List<Character> pilaDestino = cajas.get(to - 1);
                for (int i = 0; i < nCajas; i++) {
                    pilaDestino.add(0, pilaOrigen.remove(0));
                }
                System.out.println(iter++ + ": " + cajas.toString());

            }

            for (List<Character> list : cajas) {
                System.out.print(list.get(0));
            }
            System.out.println();
        }
    }

}
