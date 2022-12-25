package Dia24;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Dia24 {
    private static final String EXAMPLE = "Dia24\\example.txt";
    private static final String INPUT = "Dia24\\input.txt";
    private static int destX = 0;
    private static int oriX = 0;

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

       // parte1(INPUT);
        parte2(INPUT);
    }

    private static void parte2(String file) throws FileNotFoundException, CloneNotSupportedException {

        boolean[][] map = new boolean[37][102];
        ArrayList<Torbellino> torbellinos = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            int i = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '#') {
                        map[i][j] = true;
                    } else if (line.charAt(j) != '.') {
                        map[i][j] = true;
                        torbellinos.add(new Torbellino(j, i, line.charAt(j)));
                    }
                }
                i++;
            }

        }
        Torbellino[] torbs = new Torbellino[torbellinos.size()];
        torbellinos.toArray(torbs);

        oriX = 0;
        int y = 0;
        boolean encontrado = false;
        for (int i = 0; i < map[0].length && !encontrado; i++) {
            if (!map[0][i]) {
                encontrado = true;
                oriX = i;

            }

        }
        for (int i = 0; i < map[map.length - 1].length && !encontrado; i++) {
            if (!map[map.length - 1][i]) {
                encontrado = true;
                destX = i;

            }

        }

        // PriorityQueue<Path> caminos = new PriorityQueue<>(new Comparator<Path>() {

        // @Override
        // public int compare(Path o1, Path o2) {
        // return o1.pasos - o2.pasos;
        // }

        // });
        ArrayDeque<Path> caminos = new ArrayDeque<>();
        moverTorbellinos(map, torbs);
        activarTorbellinos(map, torbs);
        caminos.add(new Path(oriX, y, 0, torbs, map));

        HashMap<Integer, Torbellino[]> mapaTorbellinos = new HashMap<>();
        HashSet<ArrayList<Integer>> estados = new HashSet<>();
        mapaTorbellinos.put(-1, torbs);
        int k = 0;
        while (!caminos.isEmpty() && caminos.peek().y != map.length - 1) {
            k++;
            Path p = caminos.poll();

            // pintar(p.map, p.x, p.y);
            // System.out.println(p.pasos);
            // System.out.println();
            ArrayList<Integer> estado = new ArrayList<>();
            estado.add(p.pasos);
            estado.add(p.x);
            estado.add(p.y);
            if (estados.contains(estado))
                continue;
            estados.add(estado);
            boolean p0 = false;
            boolean p1 = false;
            boolean p2 = false;
            boolean p3 = false;
            boolean p4 = false;
            if (!p.map[p.y][p.x]) {
                p0 = true;
            }
            if (!p.map[p.y + 1][p.x]) {
                p1 = true;
            }
            if (p.y > 0 && !p.map[p.y - 1][p.x]) {
                p2 = true;
            }
            if (!p.map[p.y][p.x + 1]) {
                p3 = true;
            }
            if (!p.map[p.y][p.x - 1]) {
                p4 = true;
            }
            Torbellino[] a = mapaTorbellinos.get(p.pasos);
            if (a == null) {
                a = new Torbellino[p.torbellinos.length];
                for (int i = 0; i < p.torbellinos.length; i++) {
                    a[i] = mapaTorbellinos.get(p.pasos - 1)[i].clone();
                }
                moverTorbellinos(p.map, a);
                activarTorbellinos(p.map, a);
                mapaTorbellinos.put(p.pasos, a);
            }

            p.pasos++;
            if (p0) {
                caminos.add(p);
            }
            if (p1) {

                caminos.add(new Path(p.x, p.y + 1, p.pasos, a, p.map));
            }
            if (p2) {

                caminos.add(new Path(p.x, p.y - 1, p.pasos, a, p.map));
            }
            if (p3) {

                caminos.add(new Path(p.x + 1, p.y, p.pasos, a, p.map));
            }
            if (p4) {

                caminos.add(new Path(p.x - 1, p.y, p.pasos, a, p.map));
            }
            // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);

        }
        // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);
        Path jk = caminos.poll();
        caminos.clear();
        caminos.add(jk);
        while (!caminos.isEmpty() && caminos.peek().y != 0) {
            k++;
            Path p = caminos.poll();

            // pintar(p.map, p.x, p.y);
            // System.out.println(p.pasos);
            // System.out.println();
            ArrayList<Integer> estado = new ArrayList<>();
            estado.add(p.pasos);
            estado.add(p.x);
            estado.add(p.y);
            if (estados.contains(estado))
                continue;
            estados.add(estado);
            boolean p0 = false;
            boolean p1 = false;
            boolean p2 = false;
            boolean p3 = false;
            boolean p4 = false;
            if (!p.map[p.y][p.x]) {
                p0 = true;
            }
            if (p.y<p.map.length-1&&!p.map[p.y + 1][p.x]) {
                p1 = true;
            }
            if (p.y > 0 && !p.map[p.y - 1][p.x]) {
                p2 = true;
            }
            if (!p.map[p.y][p.x + 1]) {
                p3 = true;
            }
            if (!p.map[p.y][p.x - 1]) {
                p4 = true;
            }
            Torbellino[] a = mapaTorbellinos.get(p.pasos);
            if (a == null) {
                a = new Torbellino[p.torbellinos.length];
                for (int i = 0; i < p.torbellinos.length; i++) {
                    a[i] = mapaTorbellinos.get(p.pasos - 1)[i].clone();
                }
                moverTorbellinos(p.map, a);
                activarTorbellinos(p.map, a);
                mapaTorbellinos.put(p.pasos, a);
            }

            p.pasos++;
            if (p0) {
                caminos.add(p);
            }
            if (p1) {

                caminos.add(new Path(p.x, p.y + 1, p.pasos, a, p.map));
            }
            if (p2) {

                caminos.add(new Path(p.x, p.y - 1, p.pasos, a, p.map));
            }
            if (p3) {

                caminos.add(new Path(p.x + 1, p.y, p.pasos, a, p.map));
            }
            if (p4) {

                caminos.add(new Path(p.x - 1, p.y, p.pasos, a, p.map));
            }
            // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);

        }
        jk = caminos.poll();
        caminos.clear();
        caminos.add(jk);
        while (!caminos.isEmpty() && caminos.peek().y != map.length - 1) {
            k++;
            Path p = caminos.poll();

            // pintar(p.map, p.x, p.y);
            // System.out.println(p.pasos);
            // System.out.println();
            ArrayList<Integer> estado = new ArrayList<>();
            estado.add(p.pasos);
            estado.add(p.x);
            estado.add(p.y);
            if (estados.contains(estado))
                continue;
            estados.add(estado);
            boolean p0 = false;
            boolean p1 = false;
            boolean p2 = false;
            boolean p3 = false;
            boolean p4 = false;
            if (!p.map[p.y][p.x]) {
                p0 = true;
            }
            if (!p.map[p.y + 1][p.x]) {
                p1 = true;
            }
            if (p.y > 0 && !p.map[p.y - 1][p.x]) {
                p2 = true;
            }
            if (!p.map[p.y][p.x + 1]) {
                p3 = true;
            }
            if (!p.map[p.y][p.x - 1]) {
                p4 = true;
            }
            Torbellino[] a = mapaTorbellinos.get(p.pasos);
            if (a == null) {
                a = new Torbellino[p.torbellinos.length];
                for (int i = 0; i < p.torbellinos.length; i++) {
                    a[i] = mapaTorbellinos.get(p.pasos - 1)[i].clone();
                }
                moverTorbellinos(p.map, a);
                activarTorbellinos(p.map, a);
                mapaTorbellinos.put(p.pasos, a);
            }

            p.pasos++;
            if (p0) {
                caminos.add(p);
            }
            if (p1) {

                caminos.add(new Path(p.x, p.y + 1, p.pasos, a, p.map));
            }
            if (p2) {

                caminos.add(new Path(p.x, p.y - 1, p.pasos, a, p.map));
            }
            if (p3) {

                caminos.add(new Path(p.x + 1, p.y, p.pasos, a, p.map));
            }
            if (p4) {

                caminos.add(new Path(p.x - 1, p.y, p.pasos, a, p.map));
            }
            // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);

        }

        System.out.println(caminos.peek().pasos + 1);

    }

    private static void parte1(String file) throws FileNotFoundException, CloneNotSupportedException {
        boolean[][] map = new boolean[37][102];
        ArrayList<Torbellino> torbellinos = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            int i = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '#') {
                        map[i][j] = true;
                    } else if (line.charAt(j) != '.') {
                        map[i][j] = true;
                        torbellinos.add(new Torbellino(j, i, line.charAt(j)));
                    }
                }
                i++;
            }

        }
        Torbellino[] torbs = new Torbellino[torbellinos.size()];
        torbellinos.toArray(torbs);

        oriX = 0;
        int y = 0;
        boolean encontrado = false;
        for (int i = 0; i < map[0].length && !encontrado; i++) {
            if (!map[0][i]) {
                encontrado = true;
                oriX = i;

            }

        }
        for (int i = 0; i < map[map.length - 1].length && !encontrado; i++) {
            if (!map[map.length - 1][i]) {
                encontrado = true;
                destX = i;

            }

        }

        // PriorityQueue<Path> caminos = new PriorityQueue<>(new Comparator<Path>() {

        // @Override
        // public int compare(Path o1, Path o2) {
        // return o1.pasos - o2.pasos;
        // }

        // });
        ArrayDeque<Path> caminos = new ArrayDeque<>();
        moverTorbellinos(map, torbs);
        activarTorbellinos(map, torbs);
        caminos.add(new Path(oriX, y, 0, torbs, map));

        HashMap<Integer, Torbellino[]> mapaTorbellinos = new HashMap<>();
        HashSet<ArrayList<Integer>> estados = new HashSet<>();
        mapaTorbellinos.put(-1, torbs);
        int k = 0;
        while (!caminos.isEmpty() && caminos.peek().y != map.length - 1) {
            k++;
            Path p = caminos.poll();

            // pintar(p.map, p.x, p.y);
            // System.out.println(p.pasos);
            // System.out.println();
            ArrayList<Integer> estado = new ArrayList<>();
            estado.add(p.pasos);
            estado.add(p.x);
            estado.add(p.y);
            if (estados.contains(estado))
                continue;
            estados.add(estado);
            boolean p0 = false;
            boolean p1 = false;
            boolean p2 = false;
            boolean p3 = false;
            boolean p4 = false;
            if (!p.map[p.y][p.x]) {
                p0 = true;
            }
            if (!p.map[p.y + 1][p.x]) {
                p1 = true;
            }
            if (p.y > 0 && !p.map[p.y - 1][p.x]) {
                p2 = true;
            }
            if (!p.map[p.y][p.x + 1]) {
                p3 = true;
            }
            if (!p.map[p.y][p.x - 1]) {
                p4 = true;
            }
            Torbellino[] a = mapaTorbellinos.get(p.pasos);
            if (a == null) {
                a = new Torbellino[p.torbellinos.length];
                for (int i = 0; i < p.torbellinos.length; i++) {
                    a[i] = mapaTorbellinos.get(p.pasos - 1)[i].clone();
                }
                moverTorbellinos(p.map, a);
                activarTorbellinos(p.map, a);
                mapaTorbellinos.put(p.pasos, a);
            }

            p.pasos++;
            if (p0) {
                caminos.add(p);
            }
            if (p1) {

                caminos.add(new Path(p.x, p.y + 1, p.pasos, a, p.map));
            }
            if (p2) {

                caminos.add(new Path(p.x, p.y - 1, p.pasos, a, p.map));
            }
            if (p3) {

                caminos.add(new Path(p.x + 1, p.y, p.pasos, a, p.map));
            }
            if (p4) {

                caminos.add(new Path(p.x - 1, p.y, p.pasos, a, p.map));
            }
            // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);

        }
        // pintar(caminos.peek().map, caminos.peek().x, caminos.peek().y);
        System.out.println(caminos.peek().pasos + 1);
        System.out.println(k);

    }

    private static void pintar(boolean[][] map, int x, int y) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == y && x == j) {
                    System.out.print("A");
                } else if (map[i][j]) {
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
        Torbellino[] torbellinos;
        boolean[][] map;

        public Path(int x, int y, int pasos, Torbellino[] torbs, boolean[][] map2) {
            this.x = x;
            this.y = y;
            this.pasos = pasos;
            this.torbellinos = torbs;
            this.map = Arrays.copyOf(map2, map2.length);
        }
    }

    public static void moverTorbellinos(boolean[][] map, Torbellino[] torbs) {

        for (int i = 0; i < torbs.length; i++) {
            Torbellino t = torbs[i];
            map[t.y][t.x] = false;
            t.x += t.dir[0];
            t.y += t.dir[1];
            if (t.x == 0) {
                t.x = map[0].length - 2;
            } else if (t.x == map[0].length - 1) {
                t.x = 1;
            } else if (t.y == -1 || (t.y == 0 && t.x != oriX)) {
                t.y = map.length - 2;
            } else if (t.y == map.length || (t.y == map.length - 1 && t.x != destX)) {
                t.y = 1;
            }
        }

    }

    public static void activarTorbellinos(boolean[][] map, Torbellino[] torbs) {
        for (int i = 0; i < torbs.length; i++) {
            Torbellino t = torbs[i];
            map[t.y][t.x] = true;
        }
    }

    public static class Torbellino implements Cloneable {
        int x;
        int y;
        int[] dir;

        public Torbellino(int x, int y, char c) {
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

        public Torbellino(int x, int y, int[] dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }

        @Override
        protected Torbellino clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return new Torbellino(this.x, this.y, dir);
        }

    }

}
