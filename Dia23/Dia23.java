package Dia23;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import Dia09.Dia09.CustomPair;

public class Dia23 {

    private static CustomPair[] norte = { new CustomPair(-1, -1), new CustomPair(0, -1), new CustomPair(1, -1) };
    private static CustomPair[] sur = { new CustomPair(-1, 1), new CustomPair(0, 1), new CustomPair(1, 1) };
    private static CustomPair[] este = { new CustomPair(1, -1), new CustomPair(1, 0), new CustomPair(1, 1) };
    private static CustomPair[] oeste = { new CustomPair(-1, 1), new CustomPair(-1, 0), new CustomPair(-1, -1) };

    private static ArrayList<Character> dirs;

    private static int blank;
    private static boolean moved = false;

    private static String EXAMPLE = "Dia23\\example.txt";

    private static String INPUT = "Dia23\\input.txt";

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        restrart();

        parte1(EXAMPLE);
        restrart();

        parte2(EXAMPLE);
        restrart();

        parte1(INPUT);
        restrart();

        parte2(INPUT);

    }

    private static void restrart() {
        dirs = new ArrayList<>();
        dirs.add('N');
        dirs.add('S');
        dirs.add('W');
        dirs.add('E');
    }

    private static void parte2(String file) throws CloneNotSupportedException, FileNotFoundException {
        ArrayList<Elf> elfs = new ArrayList<>();
        HashMap<CustomPair, Boolean> map = new HashMap<>();
        HashSet<String> estados = new HashSet();
        String line;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            int i = 0;
            while (sc.hasNext()) {
                line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == ('#')) {
                        elfs.add(new Elf(i, j));
                    }

                }
                i++;
            }

            for (Elf elf : elfs) {
                map.put(new CustomPair(elf.x, elf.y), true);
            }
        }
        String a = pintarMapa(map);
        for (int i = 0; i < 10000; i++) {
            moved = false;
            HashMap<CustomPair, Integer> intents = new HashMap<>();
            for (int j = 0; j < elfs.size(); j++) {
                Elf e = elfs.get(j);
                checkElf(e, map, intents);
            }
            moveElfs(elfs, map, intents);
            String b = pintarMapa(map);

            Character c = dirs.get(0);
            dirs.remove(c);
            dirs.add(c);

            if (a.equals(b)) {
                System.out.println(i + 1);
                return;

            }
            a = b;
        }

    }

    private static void parte1(String file) throws FileNotFoundException, CloneNotSupportedException {
        ArrayList<Elf> elfs = new ArrayList<>();
        HashMap<CustomPair, Boolean> map = new HashMap<>();
        HashMap<String, Integer> estados = new HashMap<>();
        String line;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            int i = 0;
            while (sc.hasNext()) {
                line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == ('#')) {
                        elfs.add(new Elf(i, j));
                    }

                }
                i++;
            }

            for (Elf elf : elfs) {
                map.put(new CustomPair(elf.x, elf.y), true);
            }
        }
        for (int i = 0; i < 10; i++) {
            HashMap<CustomPair, Integer> intents = new HashMap<>();
            for (int j = 0; j < elfs.size(); j++) {
                Elf e = elfs.get(j);

                checkElf(e, map, intents);

            }
            moveElfs(elfs, map, intents);

            Character c = dirs.get(0);
            dirs.remove(c);
            dirs.add(c);
        }
        pintarMapa(map);

        System.out.println(blank);

    }

    private static String pintarMapa(HashMap<CustomPair, Boolean> map) {
        Integer maxX = 0;
        Integer minX = 100;
        Integer maxY = 0;
        Integer minY = 100;
        for (CustomPair pos : map.keySet()) {
            maxX = Math.max(maxX, pos.key);
            minX = Math.min(minX, pos.key);
            maxY = Math.max(maxY, pos.value);
            minY = Math.min(minY, pos.value);
        }
        int tall = maxY - minY + 1;
        int wide = maxX - minX + 1;
        // pintarMapa(map);

        blank = tall * wide - map.size();
        String res = "";
        for (int i = minY; i < maxY + 1; i++) {
            for (int j = minX; j < maxX + 1; j++) {
                if (map.containsKey(new CustomPair(j, i))) {
                    res += "#";
                } else {
                    res += ".";
                }
            }
            res += "\n";
        }

        return res;

    }

    private static void moveElfs(ArrayList<Elf> elfs, HashMap<CustomPair, Boolean> map,
            HashMap<CustomPair, Integer> intents) {

        for (int i = 0; i < elfs.size(); i++) {
            Elf e = elfs.get(i);
            if (e.destX == null)
                continue;
            CustomPair dest = new CustomPair(e.destX, e.destY);
            if (intents.get(dest) != null && intents.get(dest) == 1) {
                CustomPair pos = new CustomPair(e.x, e.y);
                map.put(dest, true);
                map.remove(pos);
                e.x = dest.key;
                e.y = dest.value;
                moved = true;

            }

        }
    }

    private static void checkElf(Elf e, HashMap<CustomPair, Boolean> map, HashMap<CustomPair, Integer> intents)
            throws CloneNotSupportedException {
        e.destX = null;
        e.destY = null;
        int solo = 0;
        for (int i = -1; i < 2 && true; i++) {
            for (int j = -1; j < 2 && true; j++) {
                if (map.containsKey(new CustomPair(e.x + i, e.y + j)))
                    solo++;

            }
        }
        if (solo == 1)
            return;

        for (int i = 0; i < dirs.size(); i++) {
            CustomPair[] comp = new CustomPair[3];

            switch (dirs.get(i)) {
                case 'N':
                    comp[0] = norte[0].clone();
                    comp[1] = norte[1].clone();
                    comp[2] = norte[2].clone();

                    break;
                case ('S'):
                    comp[0] = sur[0].clone();
                    comp[1] = sur[1].clone();
                    comp[2] = sur[2].clone();
                    break;
                case ('W'):
                    comp[0] = oeste[0].clone();
                    comp[1] = oeste[1].clone();
                    comp[2] = oeste[2].clone();
                    break;
                case ('E'):
                    comp[0] = este[0].clone();
                    comp[1] = este[1].clone();
                    comp[2] = este[2].clone();
                    break;
                default:
                    break;
            }
            boolean valido = true;
            for (int j = 0; j < comp.length && valido; j++) {
                comp[j].key += e.x;
                comp[j].value += e.y;

                if (map.containsKey(comp[j]))
                    valido = false;
            }
            if (valido) {
                e.destX = comp[1].key;
                e.destY = comp[1].value;
                // switch (e.dirs.get(i)) {
                // case 'N':
                // e.destX = e.x;
                // e.destY = e.y - 1;
                // break;
                // case ('S'):
                // e.destX = e.x;
                // e.destY = e.y + 1;
                // break;
                // case ('W'):
                // e.destX = e.x - 1;
                // e.destY = e.y;
                // break;
                // case ('E'):
                // e.destX = e.x + 1;
                // e.destY = e.y;

                // break;
                // default:
                // break;
                // }

                CustomPair dest = new CustomPair(e.destX, e.destY);
                Integer n = intents.get(dest);
                if (n == null)
                    n = 0;
                intents.put(dest, n + 1);
                break;
            }

        }

    }

    private static class Elf {
        private int x;
        private int y;

        private Integer destX;
        private Integer destY;

        public Elf(int y, int x) {
            this.x = x;
            this.y = y;

        }
    }
}
