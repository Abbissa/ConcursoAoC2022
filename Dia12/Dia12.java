package Dia12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dia12 {
    static int xE;
    static int yE;

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

        ArrayList<ArrayList<Character>> mapa = leer();
        parte1(mapa);
        parte2(mapa);
    }

    private static void parte2(ArrayList<ArrayList<Character>> mapa) throws CloneNotSupportedException {

        Path base = new Path(xE, yE);

        PriorityQueue<Path> queue = new PriorityQueue<>(new Comparator<Path>() {

            @Override
            public int compare(Path o1, Path o2) {
                return o1.steps - o2.steps;

            }

        });
        queue.add(base);
        HashSet<CustomPair> fijados = new HashSet<>();
        while (mapa.get(queue.element().y).get(queue.element().x) != 'a') {

            Path p = queue.remove();

            int x = p.x;
            int y = p.y;
            if (fijados.contains(new CustomPair(x, y))) {
                continue;
            }

            if (x > 0 && mapa.get(y).get(x) <= mapa.get(y).get(x - 1) + 1) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.x = x - 1;
                queue.add(nuevo);

            }
            if (x + 1 < mapa.get(0).size() && mapa.get(y).get(x) <= mapa.get(y).get(x + 1) + 1) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.x = x + 1;
                queue.add(nuevo);

            }
            if (y > 0 && mapa.get(y).get(x) <= mapa.get(y - 1).get(x) + 1) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.y = y - 1;
                queue.add(nuevo);

            }

            if (y + 1 < mapa.size() && mapa.get(y).get(x) <= mapa.get(y + 1).get(x) + 1) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.y = y + 1;
                queue.add(nuevo);

            }

            fijados.add(new CustomPair(x, y));

        }
        System.out.println(queue.element().steps);
    }

    private static void parte1(ArrayList<ArrayList<Character>> mapa) throws CloneNotSupportedException {
        int xS = 0;
        int yS = 0;
        int auxxE = 0;
        int auxyE = 0;
        for (int i = 0; i < mapa.size(); i++) {
            if (mapa.get(i).contains('S')) {
                xS = mapa.get(i).indexOf('S');
                yS = i;
            }
            if (mapa.get(i).contains('E')) {
                auxxE = mapa.get(i).indexOf('E');
                auxyE = i;
            }

        }
        mapa.get(yS).set(xS, 'a');
        mapa.get(auxyE).set(auxxE, 'z');
        xE = auxxE;
        yE = auxyE;

        Path base = new Path(xS, yS);

        PriorityQueue<Path> queue = new PriorityQueue<>(new Comparator<Path>() {

            @Override
            public int compare(Path o1, Path o2) {
                return o1.distanciaFin(xE, yE) - o2.distanciaFin(xE, yE);

            }

        });
        queue.add(base);
        HashSet<CustomPair> fijados = new HashSet<>();
        while (queue.element().x != xE || queue.element().y != yE) {

            Path p = queue.remove();

            int x = p.x;
            int y = p.y;
            if (fijados.contains(new CustomPair(x, y))) {
                continue;
            }

            if (x > 0 && mapa.get(y).get(x) + 1 >= mapa.get(y).get(x - 1)) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.x = x - 1;
                queue.add(nuevo);

            }
            if (x + 1 < mapa.get(0).size() && mapa.get(y).get(x) + 1 >= mapa.get(y).get(x + 1)) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.x = x + 1;
                queue.add(nuevo);

            }
            if (y > 0 && mapa.get(y).get(x) + 1 >= mapa.get(y - 1).get(x)) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.y = y - 1;
                queue.add(nuevo);

            }

            if (y + 1 < mapa.size() && mapa.get(y).get(x) + 1 >= mapa.get(y + 1).get(x)) {

                Path nuevo = (Path) p.clone();
                nuevo.steps++;
                nuevo.y = y + 1;
                queue.add(nuevo);

            }

            fijados.add(new CustomPair(x, y));

        }
        System.out.println(queue.element().steps);
    }

    private static ArrayList<ArrayList<Character>> leer() throws FileNotFoundException {
        ArrayList<ArrayList<Character>> mapa = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream("Dia12\\input.txt"))) {
            int i = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                mapa.add(new ArrayList<>());
                for (int j = 0; j < line.length(); j++) {

                    mapa.get(i).add(line.charAt(j));
                }
                i++;

            }
        }
        return mapa;
    }

    private static class Path implements Cloneable {

        int x;
        int y;
        int steps;

        public Path(int x, int y) {
            this.x = x;
            this.y = y;
            this.steps = 0;
        }

        public int distanciaFin(int xE, int yE) {

            int res = steps + (int) Math.sqrt(Math.pow(xE - this.x, 2) + Math.pow(yE - this.y, 2));

            return res;

        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
        }

    }

    public static class CustomPair {
        private int key;
        private int value;

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

    }

}
