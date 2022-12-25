package Dia24;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dia24 {
    private static final String EXAMPLE = "Dia24\\example.txt";
    private static int destX = 0;
    private static int oriX = 0;

    public static void main(String[] args) throws FileNotFoundException {

        parte1(EXAMPLE);
    }

    private static void parte1(String file) throws FileNotFoundException {
        ArrayList<boolean[]> map = new ArrayList<>();
        ArrayList<Torbellino> torbellinos = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            int i = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();
                map.add(new boolean[line.length()]);
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '#') {
                        map.get(i)[j] = true;
                    } else if (line.charAt(j) != '.') {
                        map.get(i)[j] = true;
                        torbellinos.add(new Torbellino(i, j, line.charAt(j)));
                    }
                }
                i++;
            }

        }

        oriX = 0;
        int y = 0;
        boolean encontrado = false;
        for (int i = 0; i < map.get(0).length && !encontrado; i++) {
            if (!map.get(0)[i]) {
                encontrado = true;
                oriX = i;

            }

        }
        for (int i = 0; i < map.get(map.size() - 1).length && !encontrado; i++) {
            if (!map.get(map.size() - 1)[i]) {
                encontrado = true;
                destX = i;

            }

        }

        PriorityQueue<Path> caminos = new PriorityQueue<>(new Comparator<Path>() {

            @Override
            public int compare(Path o1, Path o2) {
                return o1.pasos - o2.pasos;
            }

        });
        moverTorbellinos(map, torbellinos);
        activarTorbellinos(map, torbellinos);
        caminos.add(new Path(oriX, y, 0, torbellinos, map));

        while (!caminos.isEmpty() && caminos.peek().y != map.size() - 1) {

            Path p = caminos.poll();
            // pintar(p.map, p.x, p.y);
            // System.out.println();
            boolean p0 = false;
            boolean p1 = false;
            boolean p2 = false;
            boolean p3 = false;
            boolean p4 = false;
            if (!p.map.get(p.y)[p.x]) {
                p0 = true;
            }
            if (!p.map.get(p.y + 1)[p.x]) {
                p1 = true;
            }
            if (p.y > 0 && !p.map.get(p.y - 1)[p.x]) {
                p2 = true;
            }
            if (!p.map.get(p.y)[p.x + 1]) {
                p3 = true;
            }
            if (!p.map.get(p.y)[p.x - 1]) {
                p4 = true;
            }
            p.pasos++;
            moverTorbellinos(p.map, p.torbellinos);
            activarTorbellinos(p.map, p.torbellinos);
            if (p0) {
                caminos.add(p);
            }
            if (p1) {
                caminos.add(new Path(p.x, p.y + 1, p.pasos, p.torbellinos, p.map));
            }
            if (p2) {
                caminos.add(new Path(p.x, p.y - 1, p.pasos, p.torbellinos, p.map));
            }
            if (p3) {
                caminos.add(new Path(p.x + 1, p.y, p.pasos, p.torbellinos, p.map));
            }
            if (p4) {
                caminos.add(new Path(p.x - 1, p.y, p.pasos, p.torbellinos, p.map));
            }
            // pintar(p.map, p.x, p.y);
            // System.out.println(p.pasos);
            // System.out.println();
        }
        System.out.println(caminos.peek().pasos);

    }

    private static void pintar(ArrayList<boolean[]> map, int x, int y) {

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                if (i == y && x == j) {
                    System.out.print("A");
                } else if (map.get(i)[j]) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }

            }
            System.out.println();
        }
    }

    public static class Path {
        int x;
        int y;
        int pasos;
        ArrayList<Torbellino> torbellinos;
        ArrayList<boolean[]> map;

        public Path(int x, int y, int pasos, ArrayList<Torbellino> torbellinos, ArrayList<boolean[]> map) {
            this.x = x;
            this.y = y;
            this.pasos = pasos;
            this.torbellinos = (ArrayList<Torbellino>) torbellinos.clone();
            this.map = (ArrayList<boolean[]>) map.clone();
        }
    }

    public static void moverTorbellinos(ArrayList<boolean[]> map, ArrayList<Torbellino> torbellinos) {

        for (int i = 0; i < torbellinos.size(); i++) {
            Torbellino t = torbellinos.get(i);
            map.get(t.y)[t.x] = false;
            t.x += t.dir[0];
            t.y += t.dir[1];
            if (t.x == 0) {
                t.x = map.get(0).length - 2;
            } else if (t.x == map.get(0).length - 1) {
                t.x = 1;
            } else if (t.y == -1 || (t.y == 0 && t.x != oriX)) {
                t.y = map.size() - 2;
            } else if (t.y == map.size() || (t.y == map.size() - 1 && t.x != destX)) {
                t.y = 1;
            }
        }

    }

    public static void activarTorbellinos(ArrayList<boolean[]> map, ArrayList<Torbellino> torbellinos) {
        for (int i = 0; i < torbellinos.size(); i++) {
            Torbellino t = torbellinos.get(i);
            map.get(t.y)[t.x] = true;
        }
    }

    public static class Torbellino implements Cloneable {
        int x;
        int y;
        int[] dir;

        public Torbellino(int y, int x, char c) {
            this.x = x;
            this.y = y;
            switch (c) {
                case '^':
                    dir = new int[2];
                    dir[0] = 0;
                    dir[1] = -1;
                    break;
                case 'v':
                    dir = new int[2];
                    dir[0] = 0;
                    dir[1] = 1;
                    break;
                case '<':
                    dir = new int[2];
                    dir[0] = -1;
                    dir[1] = 0;
                    break;
                case '>':
                    dir = new int[2];
                    dir[0] = 1;
                    dir[1] = 0;
                    break;

                default:
                    break;
            }

        }

        public Torbellino(int y, int x, int[] clone) {
            this.y = y;
            this.x = x;
            this.dir = clone;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {

            return new Torbellino(y, x, dir.clone());
        }
    }

}
