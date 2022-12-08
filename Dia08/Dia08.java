package Dia08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dia08 {

    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();

    }

    private static void parte2() throws FileNotFoundException {

        ArrayList<List<Integer>> bosque = leer("Dia08\\input.txt");
        int maxScenicScore = 0;
        for (int i = 0; i < bosque.size(); i++) {
            for (int j = 0; j < bosque.get(i).size(); j++) {

                maxScenicScore = Math.max(maxScenicScore, calculateScenicScore(i, j, bosque));

            }
        }
        System.out.println(maxScenicScore);

    }

    private static int calculateScenicScore(int i, int j, ArrayList<List<Integer>> bosque) {

        int altitud = bosque.get(i).get(j);

        int preC = 0;
        int postC = 0;
        int postR = 0;
        int preR = 0;

        for (int k = i - 1; k >= 0; k--) {
            preC++;

            if (altitud <= bosque.get(k).get(j)) {
                break;
            }
        }

        for (int k = i + 1; k < bosque.size(); k++) {
            postC++;
            if (altitud <= bosque.get(k).get(j)) {
                break;
            }
        }
        for (int k = j - 1; k >= 0; k--) {
            preR++;
            if (altitud <= bosque.get(i).get(k)) {
                break;
            }
        }

        for (int k = j + 1; k < bosque.size(); k++) {
            postR++;
            if (altitud <= bosque.get(i).get(k)) {
                break;
            }
        }

        return preC * preR * postC * postR;
    }

    private static void parte1() throws FileNotFoundException {
        ArrayList<List<Integer>> bosque = leer("Dia08\\input.txt");

        int cont = 0;
        for (int i = 0; i < bosque.size(); i++) {
            for (int j = 0; j < bosque.get(i).size(); j++) {

                if (!findMax(i, j, bosque)) {
                    cont++;
                }

            }

        }
        System.out.println(cont);
    }

    private static boolean findMax(int i, int j, ArrayList<List<Integer>> bosque) {

        int altitud = bosque.get(i).get(j);

        boolean preC = false;
        boolean postC = false;
        boolean preR = false;
        boolean postR = false;

        for (int k = 0; k < i; k++) {
            if (altitud <= bosque.get(k).get(j)) {
                preC = true;
            }
        }
        if (!preC)
            return false;

        for (int k = i + 1; k < bosque.size(); k++) {
            if (altitud <= bosque.get(k).get(j)) {
                postC = true;
            }
        }
        if (!postC)
            return false;
        for (int k = 0; k < j; k++) {
            if (altitud <= bosque.get(i).get(k)) {
                preR = true;
            }
        }
        if (!preR)
            return false;
        for (int k = j + 1; k < bosque.size(); k++) {
            if (altitud <= bosque.get(i).get(k)) {
                postR = true;
            }
        }
        if (!postR)
            return false;

        return true;

    }

    private static ArrayList<List<Integer>> leer(String file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            ArrayList<List<Integer>> bosque = new ArrayList<List<Integer>>();
            int i = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();

                for (int j = 0; j < line.length(); j++) {

                    if (bosque.size() <= i)
                        bosque.add(i, new ArrayList<Integer>());
                    bosque.get(i).add(j, Integer.parseInt(line.charAt(j) + ""));
                }
                i++;

            }

            return bosque;

        }
    }
}
