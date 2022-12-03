package Dia03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dia03 {

    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();
    }

    private static void parte1() throws FileNotFoundException {

        try (Scanner sc = new Scanner(new FileInputStream("Dia03\\input.txt"))) {

            int res = 0;
            while (sc.hasNext()) {
                String rucksack = sc.nextLine();
                String firstCompartment = rucksack.substring(0, rucksack.length() / 2);
                String secondCompartment = rucksack.substring(rucksack.length() / 2);
                String commonChars = "";

                // Calculate the sum of the priorities of the characters that appear in both
                // compartments
                for (int i = 0; i < firstCompartment.length(); i++) {
                    char c = firstCompartment.charAt(i);
                    if (secondCompartment.indexOf(c) >= 0 && commonChars.indexOf(c) < 0) {
                        // Convert the character to its priority and add it to the sum
                        if (Character.isLowerCase(c)) {
                            res += c - 'a' + 1;
                        } else {
                            res += c - 'A' + 27;
                        }
                        // Add the character to the list of common characters
                        commonChars += c;
                    }
                }
            }
        System.out.println(res);

        }

    }

    private static void parte2() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia03\\input.txt"))) {

            int res = 0;
            while (sc.hasNext()) {
                HashSet<Character> bag1 = new HashSet<>();
                HashSet<Character> bag2 = new HashSet<>();
                HashSet<Character> bag3 = new HashSet<>();
                String line = sc.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    bag1.add(line.charAt(i));
                }
                line = sc.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    bag2.add(line.charAt(i));
                }
                line = sc.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    bag3.add(line.charAt(i));
                }

                bag1.retainAll(bag2);
                bag1.retainAll(bag3);
                Character c = (Character) bag1.toArray()[0];

                int priority = priority(c);
                //System.out.println(c + ": " + priority);
                res += priority;

            }
            System.out.println(res);
        }
    }

    private static int priority(Character c) {
        if (Character.isLowerCase(c)) {
            return c - 'a' + 1;
        } else {
            return c - 'A' + 27;
        }
    }

}
