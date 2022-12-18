package Dia16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Map.Entry;

public class Dia16 {

    private static String EXAMPLE = "Dia16\\example.txt";
    private static String INPUT = "Dia16\\input.txt";

    private static Valve[] valves;
    private static int Aid;

    public static void main(String[] args) throws FileNotFoundException {
        parte1(EXAMPLE);
        parte2(EXAMPLE);
        parte1(INPUT);
        parte2(INPUT);
    }

    private static void parte2(String file) throws FileNotFoundException {
        HashMap<String, Valve> mapa = leer(file);
        // System.out.println(solve(mapa, 30));
        System.out.println(solve(floydWarshall(mapa), 26, true));
    }

    private static void parte1(String file) throws FileNotFoundException {

        HashMap<String, Valve> mapa = leer(file);
        // System.out.println(solve(mapa, 30));
        System.out.println(solve(floydWarshall(mapa), 30, false));
    }

    public static int[][] floydWarshall(HashMap<String, Valve> mapa) {

        int[][] conexiones = new int[mapa.size()][mapa.size()];
        for (int i = 0; i < conexiones.length; i++) {
            for (int j = 0; j < conexiones[i].length; j++) {
                conexiones[i][j] = 30;

            }
        }
        for (Entry<String, Valve> entry : mapa.entrySet()) {
            Valve from = entry.getValue();
            for (int i = 0; i < from.leadTo.size(); i++) {
                Valve to = mapa.get(from.leadTo.get(i));

                conexiones[from.id][to.id] = 1;

            }

        }

        for (int k = 0; k < mapa.size(); k++)
            for (int i = 0; i < mapa.size(); i++)
                for (int j = 0; j < mapa.size(); j++)
                    conexiones[i][j] = Math.min(conexiones[i][j], (conexiones[i][k] + conexiones[k][j]));

        for (int i = 0; i < conexiones.length; i++) {
            for (int j = 0; j < conexiones.length; j++) {
                if (i == Aid && valves[j].flowRate != 0
                        || (valves[i].flowRate != 0 && valves[j].flowRate != 0 && i != j))
                    continue;
                // if (valves[i].flowRate == 0 || valves[j].flowRate == 0 || i == j) {
                conexiones[i][j] = 30;

            }
        }
        // System.out.print(" ");
        // for (int i = 0; i < conexiones.length; i++) {
        // System.out.print(i + " ");
        // }
        // System.out.println();
        // System.out.println();
        // for (int i = 0; i < conexiones.length; i++) {
        // System.out.print(i + " ");
        // for (int j = 0; j < conexiones.length; j++) {
        // System.out.print(conexiones[i][j] + " ");
        // }
        // System.out.println();
        // }

        return conexiones;

    }

    private static int solve(int[][] conexiones, int time, boolean parte2) {
        PriorityQueue<Path> queue = new PriorityQueue<>(new Comparator<Path>() {

            @Override
            public int compare(Path o1, Path o2) {
                return o2.pressureReleased - o1.pressureReleased;
            }

        });
        HashSet<Integer> all = new HashSet<>();
        for (int i = 0; i < conexiones.length; i++) {
            if (time - conexiones[Aid][i] > 0) {
                queue.add(new Path(0, new HashSet<Integer>(), i, time - conexiones[Aid][i]));
            }
        }
        for (int i = 0; i < conexiones.length; i++) {
            if (valves[i].flowRate != 0)
                all.add(i);
        }

        int max = 0;
        int max2 = 0;

        HashMap<String, Integer> busquedas = new HashMap<String,Integer>();
        while (!queue.isEmpty()) {

            Path p = queue.poll();

            p.valvesActivated.add(p.pos);
            p.pressureReleased += --p.timeLeft * valves[p.pos].flowRate;
            for (int i = 0; i < conexiones.length; i++) {
                if (!p.valvesActivated.contains(i) && p.timeLeft - conexiones[p.pos][i] >= 0) {
                    queue.add(new Path(p.pressureReleased, p.valvesActivated, i, p.timeLeft - conexiones[p.pos][i]));
                }
            }
            max = Math.max(max, p.pressureReleased);
            if (busquedas.get(Arrays.toString(p.valvesActivated.toArray())) == null
                    || busquedas.get(Arrays.toString(p.valvesActivated.toArray())) < p.pressureReleased)
                busquedas.put(Arrays.toString(p.valvesActivated.toArray()), p.pressureReleased);

            HashSet<Integer> set = (HashSet<Integer>) all.clone();

            set.removeAll(p.valvesActivated);
            // System.out.println(Arrays.toString(set.toArray()));
            Integer res = busquedas.get(Arrays.toString(set.toArray()));

            if (res != null) {
                max2 = Math.max(max2, p.pressureReleased + res);
            }

        }

        if (!parte2)
            return max;

        HashSet<Integer>[] sets = new HashSet[busquedas.size()];
        int[] resultados = new int[busquedas.size()];
        int i = 0;
        for (Entry<String, Integer> entry : busquedas.entrySet()) {
            sets[i] = new HashSet<>();
            String[] string = entry.getKey().replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .split(",");
            for (int j = 0; j < string.length; j++) {
                sets[i].add(Integer.valueOf(string[j].trim()));
            }
            resultados[i] = entry.getValue();
            i++;
        }

        for (int j = 0; j < sets.length; j++) {
            HashSet<Integer> set = (HashSet<Integer>) all.clone();
            set.removeAll(sets[j]);
            for (int j2 = j + 1; j2 < sets.length; j2++) {

                if (set.containsAll(sets[j2])) {
                    max2 = Math.max(max2, resultados[j] + resultados[j2]);
                }

            }

        }

        if (parte2)
            max = max2;
        return max;

    }
    // private static int solve(HashMap<String, Valve> mapa, int time) {

    // time--;
    // Path[][] caminos = new Path[mapa.size()][30];
    // for (String valve : mapa.get("AA").leadTo) {
    // ArrayList<String> a = new ArrayList<>();
    // a.add("AA");
    // caminos[mapa.get(valve).id][0] = new Path(0, new HashSet<>(), a);
    // }
    // for (int i = time; i > 0; i--) {
    // System.out.println();
    // System.out.println("Minutos restantes: " + i);
    // System.out.println();

    // for (int j = 0; j < caminos.length; j++) {
    // Path p = caminos[j][time - i];
    // if (p == null)
    // continue;
    // Valve valve = valves[j];

    // System.out.println("Name: " + valve.name);
    // System.out.println("Preasure: " + p.pressureReleased);
    // p.camino.add(valve.name);

    // if (!p.valvesActivated.contains(valve.name) && valve.flowRate > 0
    // && (caminos[valve.id][time - i + 1] == null || p.pressureReleased
    // + valve.flowRate * (i - 1) > caminos[valve.id][time - i +
    // 1].pressureReleased)) {
    // p.valvesActivated.add(valve.name);
    // p.pressureReleased += valve.flowRate * (i - 1);
    // caminos[valve.id][time - i + 1] = new Path(p.pressureReleased,
    // p.valvesActivated, p.camino);
    // p.pressureReleased -= valve.flowRate * (i - 1);
    // p.valvesActivated.remove(valve.name);
    // }

    // for (int j2 = 0; j2 < valve.leadTo.size(); j2++) {

    // int idTo = mapa.get(valve.leadTo.get(j2)).id;
    // if (caminos[idTo][time - i + 1] == null
    // || caminos[idTo][time - i + 1].pressureReleased <= p.pressureReleased)
    // caminos[idTo][time - i + 1] = new Path(p.pressureReleased, p.valvesActivated,
    // p.camino);
    // }

    // }
    // }

    // int max = 0;
    // for (int j = 1; j < caminos.length; j++) {
    // if (caminos[j][caminos[j].length
    // - 1].pressureReleased > (caminos[max][caminos[max].length -
    // 1].pressureReleased))
    // max = j;

    // }

    // System.out.println(caminos[max][caminos[max].length - 1].camino);
    // return caminos[max][caminos[max].length - 1].pressureReleased;
    // }

    private static HashMap<String, Valve> leer(String file) throws FileNotFoundException {
        HashMap<String, Valve> mapa = new HashMap<>();
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(" ");
                String cur = line[1];

                ArrayList<String> leadTo = new ArrayList<>();
                String num = line[4].split("=")[1];
                int flowRate = Integer.parseInt(num.substring(0, num.length() - 1));

                for (int i = 9; i < line.length - 1; i++) {

                    leadTo.add(line[i].substring(0, line[i].length() - 1));
                }
                leadTo.add(line[line.length - 1]);
                Valve valve = new Valve(cur, leadTo, flowRate);
                mapa.put(cur, valve);

            }

        }

        // for (Entry<String, Valve> entry : mapa.entrySet()) {
        // System.out.println("valve: "+entry.getKey()+ " leads to: "+
        // entry.getValue().leadTo.toString());
        int id = 0;
        valves = new Valve[mapa.size()];
        for (Entry<String, Valve> valve : mapa.entrySet()) {
            valve.getValue().id = id;
            valves[id] = valve.getValue();
            if (valve.getValue().name.equals("AA")) {
                Aid = id;
            }
            id++;

        }

        // }
        return mapa;
    }

    private static class Valve {
        String name;
        ArrayList<String> leadTo;
        int flowRate;
        int id;

        public Valve(String name, ArrayList<String> leadTo, int flowRate) {
            this.name = name;
            this.leadTo = leadTo;
            this.flowRate = flowRate;

        }

    }

    private static class Path {

        int pressureReleased;
        HashSet<Integer> valvesActivated;
        int pos;
        int timeLeft;

        public Path(int pressureReleased, HashSet<Integer> valvesActivated, int pos, int timeLeft) {
            this.pressureReleased = pressureReleased;
            this.valvesActivated = (HashSet<Integer>) valvesActivated.clone();
            this.pos = pos;
            this.timeLeft = timeLeft;
            // this.camino = (ArrayList<String>) camino.clone();
        }

    }
}
