package Dia06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dia06 {

    static String file = "Dia06\\input.txt";
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Resultado parte 1:");
        parte1(file);
        System.out.println("Resultado parte 2:");

        parte2(file);

    }

    private static void parte2(String file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                HashSet<Character> set = new HashSet<>();
                String line = sc.nextLine();
                boolean fin = false;
                for (int i = 0; i < line.length() - 14 && !fin; i++) {
                    for (int j = 0; j < 14; j++) {
                        set.add(line.charAt(i+j));

                    }

                    if (set.size() == 14) {
                        System.out.println(i + 14);
                        fin = true;
                    }
                    set.clear();
                }
            }

        }
    }

    private static void parte1(String file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                HashSet<Character> set = new HashSet<>();
                String line = sc.nextLine();
                boolean fin = false;
                for (int i = 0; i < line.length() - 4 && !fin; i++) {
                    set.add(line.charAt(i));
                    set.add(line.charAt(i + 1));
                    set.add(line.charAt(i + 2));
                    set.add(line.charAt(i + 3));

                    if (set.size() == 4) {
                        System.out.println(i + 4);
                        fin = true;
                    }
                    set.clear();
                }
            }

        }
    }

}
