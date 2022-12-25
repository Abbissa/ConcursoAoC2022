package Dia09;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Dia09 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = leer("Dia09\\input.txt");
        parte1(input);
        parte2(input);
    }

    private static void parte2(ArrayList<String> input) {

        HashSet<CustomPair> visitados = new HashSet<>();
        int[][] nudos = new int[10][2];
        visitados.add(new CustomPair(nudos[nudos.length - 1][0], nudos[nudos.length - 1][1]));

        for (int i = 0; i < input.size(); i++) {
            char dir = input.get(i).charAt(0);
            int move = Integer.parseInt(input.get(i).substring(2) + "");

            for (int j = 0; j < move; j++) {

                switch (dir) {
                    case 'R':
                        nudos[0][0]++;
                        break;
                    case 'L':
                        nudos[0][0]--;
                        break;
                    case 'U':
                        nudos[0][1]++;
                        break;
                    case 'D':
                        nudos[0][1]--;
                        break;

                    default:
                        break;
                }
                for (int j2 = 1; j2 < nudos.length; j2++) {

                    if (nudos[j2 - 1][0] > nudos[j2][0] + 1) {
                        if (nudos[j2 - 1][1] > nudos[j2][1]) {
                            nudos[j2][1]++;
                        } else if (nudos[j2 - 1][1] < nudos[j2][1]) {
                            nudos[j2][1]--;
                        }
                        nudos[j2][0]++;

                    } else if (nudos[j2 - 1][0] + 1 < nudos[j2][0]) {
                        if (nudos[j2 - 1][1] > nudos[j2][1]) {
                            nudos[j2][1]++;
                        } else if (nudos[j2 - 1][1] < nudos[j2][1]) {
                            nudos[j2][1]--;
                        }
                        nudos[j2][0]--;

                    } else if (nudos[j2 - 1][1] > nudos[j2][1] + 1) {
                        if (nudos[j2 - 1][0] > nudos[j2][0]) {
                            nudos[j2][0]++;
                        } else if (nudos[j2 - 1][0] < nudos[j2][0]) {
                            nudos[j2][0]--;
                        }
                        nudos[j2][1]++;

                    } else if (nudos[j2 - 1][1] + 1 < nudos[j2][1]) {
                        if (nudos[j2 - 1][0] > nudos[j2][0]) {
                            nudos[j2][0]++;
                        } else if (nudos[j2 - 1][0] < nudos[j2][0]) {
                            nudos[j2][0]--;
                        }
                        nudos[j2][1]--;

                    }
                }

                visitados.add(new CustomPair(nudos[nudos.length - 1][0], nudos[nudos.length - 1][1]));
                System.out.println("Head: " + nudos[nudos.length - 2][0] + " " + nudos[nudos.length - 2][1]);
                System.out.println("Tail: " + nudos[nudos.length - 1][0] + " " + nudos[nudos.length - 1][1]);

            }

        }
        System.out.println(visitados.size());
    }

    private static void parte1(ArrayList<String> input) {
        HashSet<CustomPair> visitados = new HashSet<>();
        int hx = 0;
        int hy = 0;
        int tx = 0;
        int ty = 0;
        visitados.add(new CustomPair(tx, ty));

        for (int i = 0; i < input.size(); i++) {
            char dir = input.get(i).charAt(0);
            int move = Integer.parseInt(input.get(i).substring(2) + "");

            for (int j = 0; j < move; j++) {

                switch (dir) {
                    case 'R':
                        hx++;
                        break;
                    case 'L':
                        hx--;
                        break;
                    case 'U':
                        hy++;
                        break;
                    case 'D':
                        hy--;
                        break;

                    default:
                        break;
                }
                if (hx > tx + 1) {
                    if (hy > ty) {
                        tx++;
                        ty++;
                    } else if (hy < ty) {
                        tx++;
                        ty--;
                    } else {
                        tx++;
                    }
                } else if (hx + 1 < tx) {
                    if (hy > ty) {
                        tx--;
                        ty++;
                    } else if (hy < ty) {
                        tx--;
                        ty--;
                    } else {
                        tx--;
                    }
                } else if (hy > ty + 1) {
                    if (hx > tx) {
                        ty++;
                        tx++;
                    } else if (hx < tx) {
                        ty++;
                        tx--;
                    } else {
                        ty++;
                    }

                } else if (hy + 1 < ty) {
                    if (hx > tx) {
                        ty--;
                        tx++;
                    } else if (hx < tx) {
                        ty--;
                        ;
                        tx--;
                    } else {
                        ty--;
                    }
                }

                // System.out.println("Head: " + hx + " " + hy);
                // System.out.println("Tail: " + tx + " " + ty);
                visitados.add(new CustomPair(tx, ty));

            }
        }
        System.out.println(visitados.size());

    }

    private static ArrayList<String> leer(String input) throws FileNotFoundException {
        ArrayList<String> res = new ArrayList<>();

        try (Scanner sc = new Scanner(new FileInputStream(input))) {
            while (sc.hasNextLine()) {
                res.add(sc.nextLine());
            }
        }

        return res;
    }

    public static class CustomPair implements Cloneable{
        public int key;
        public int value;

        public CustomPair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + key;
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;

            CustomPair other = (CustomPair) obj;
            if (key != other.key)
                return false;
            if (value != other.value)
                return false;
            return true;
        }
        @Override
        public CustomPair clone() throws CloneNotSupportedException {
            
            return new CustomPair(key, value);
        }

    }

}
