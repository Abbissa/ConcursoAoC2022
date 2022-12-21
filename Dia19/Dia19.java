package Dia19;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dia19 {

    private static String EXAMPLE = "Dia19\\example.txt";
    private static String INPUT = "Dia19\\input.txt";

    private static HashMap<Integer, HashMap<Integer, Integer>> mejoresCasos;

    public static void main(String[] args) throws FileNotFoundException {
        long init = System.nanoTime();
        mejoresCasos = new HashMap<>();
        for (int i = 1; i < 32; i++) {
            mejoresCasos.put(i, new HashMap<>());
            for (int j = 0; j < 10; j++) {
                mejoresCasos.get(i).put(j, mejorCaso(i, j));
            }

        }
        long aux = System.nanoTime();
        System.out.println("Calculos: " + ((double) (aux - init)) / 1000_000 + " ms");
        init = aux;
        ArrayList<Blueprint> blueprints = leer(EXAMPLE);
        aux = System.nanoTime();
        System.out.println("Lectura: " + ((double) (aux - init)) / 1000_000 + " ms");
        init = aux;

        parte1(blueprints);

        aux = System.nanoTime();
        System.out.println("Parte 1 ejemplo: " + ((double) (aux - init)) / 1000_000_000 + " s");
        init = aux;


        blueprints = leer(INPUT);

        parte1(blueprints);
        aux = System.nanoTime();
        System.out.println("Parte 1 input: " + ((double)(aux - init))/ 1000_000_000+"s");
        init=aux;

        parte2(blueprints, 32);
        aux = System.nanoTime();
        System.out.println("Parte 2(32) input: " + ((double) (aux - init)) / 1000_000_000 + " s");
    }

    private static void parte2(ArrayList<Blueprint> blueprints, int time) throws FileNotFoundException {
        int[] res = solve(blueprints, time, true);
        int result = 1;
        for (int i = 0; i < 3 && i < res.length; i++) {
            System.out.println(i+": "+res[i]);
            result *= res[i];
        }
        System.out.println(result);

    }

    private static void parte1(ArrayList<Blueprint> blueprints) throws FileNotFoundException {
        int[] res = solve(blueprints, 24, false);
        int result = 0;
        for (int i = 0; i < res.length; i++) {
            result += (i + 1) * res[i];
        }
        System.out.println(result);
    }

    private static int[] solve(ArrayList<Blueprint> blueprints, int time, boolean parte2) {

        PriorityQueue<Path> paths = new PriorityQueue<Path>(new Comparator<Path>() {

            @Override
            public int compare(Path o1, Path o2) {

                return o1.timeLeft - o2.timeLeft;
            }

        });
        int i = 0;
        int max[] = new int[blueprints.size()];
        int n = 0;
        int pa = 0;

        for (Blueprint b : blueprints) {
            Path p = new Path(0, 0, 0, 0, 1, 0, 0, 0, time);
            paths.add(p);

            if (i == 3 && parte2)
                return max;
            int maxOC = Math.max(b.costeOreOreRobot, b.costeOreClayRobot);
            maxOC = Math.max(maxOC, b.costeOreGeodeRobot);
            maxOC = Math.max(maxOC, b.costeOreObsidianRobot);
            while (!paths.isEmpty()) {
                p = paths.poll();
                n++;

                // String key =
                // p.clay+","+p.clayRobot+","+p.geode+","+p.geodeRobot+","+p.obsidian+","+p.obsidianRobot+","+p.ore+","+p.oreRobot+","+p.timeLeft;
                if (p.geode + mejorCaso(p.timeLeft, p.geodeRobot) < max[i]) {
                    continue;
                }
                pa++;
                // estadosComprobados.add(key);

                int nOre = p.oreRobot;
                int nClay = p.clayRobot;
                int nObs = p.obsidianRobot;
                int nGeode = p.geodeRobot;

                if (p.ore >= b.costeOreGeodeRobot && p.obsidian >= b.costeObsidianGeodeRobot && p.timeLeft > 1) {
                    paths.add(new Path(p.ore + nOre - b.costeOreGeodeRobot, p.clay + nClay,
                            p.obsidian + nObs - b.costeObsidianGeodeRobot, p.geode + nGeode, p.oreRobot,
                            p.clayRobot, p.obsidianRobot, p.geodeRobot + 1, p.timeLeft - 1));
                }
                if (p.ore >= b.costeOreObsidianRobot && p.clay >= b.costeClayObsidianRobot && p.timeLeft > 3
                        && p.obsidianRobot < b.costeObsidianGeodeRobot) {
                    paths.add(new Path(p.ore + nOre - b.costeOreObsidianRobot,
                            p.clay + nClay - b.costeClayObsidianRobot, p.obsidian + nObs, p.geode + nGeode, p.oreRobot,
                            p.clayRobot, p.obsidianRobot + 1, p.geodeRobot, p.timeLeft - 1));
                }
                if (p.ore >= b.costeOreOreRobot && p.timeLeft > 3 + b.costeOreOreRobot && p.oreRobot < maxOC) {
                    paths.add(new Path(p.ore + nOre - b.costeOreOreRobot, p.clay + nClay, p.obsidian + nObs,
                            p.geode + nGeode,
                            p.oreRobot + 1, p.clayRobot, p.obsidianRobot, p.geodeRobot, p.timeLeft - 1));
                }
                if (p.ore >= b.costeOreClayRobot && p.timeLeft > 5 && p.clayRobot < b.costeClayObsidianRobot) {
                    paths.add(new Path(p.ore + nOre - b.costeOreClayRobot, p.clay + nClay, p.obsidian + nObs,
                            p.geode + nGeode, p.oreRobot,
                            p.clayRobot + 1, p.obsidianRobot, p.geodeRobot, p.timeLeft - 1));
                }

                p.ore += nOre;
                p.clay += nClay;
                p.obsidian += nObs;
                p.geode += nGeode;

                max[i] = Math.max(max[i], p.geode);
                p.timeLeft--;
                if (p.timeLeft > 0)
                    paths.add(p);
            }
            i++;

        }
        System.out.println(n);
        System.out.println(pa);
        return max;

    }

    private static int mejorCaso(int timeLeft, int GR) {
        int res = 0;
        if (GR != 0)
            for (int i = 0; i < timeLeft; i++) {
                res += (timeLeft - i) * GR;

            }
        for (int i = 1; i < timeLeft; i++) {
            res += i;

        }

        return res;
    }

    private static ArrayList<Blueprint> leer(String file) throws FileNotFoundException {
        ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(sc.nextLine());

                matcher.find();

                matcher.find();
                int cOOr = Integer.parseInt(matcher.group());
                matcher.find();

                int cOCr = Integer.parseInt(matcher.group());
                matcher.find();

                int cOObsr = Integer.parseInt(matcher.group());
                matcher.find();

                int cCObsr = Integer.parseInt(matcher.group());
                matcher.find();

                int cOGr = Integer.parseInt(matcher.group());
                matcher.find();

                int cObsGr = Integer.parseInt(matcher.group());

                Blueprint b = new Blueprint(cOOr, cOCr, cOObsr, cCObsr, cOGr, cObsGr);

                blueprints.add(b);

            }

        }

        return blueprints;
    }

    private static class Blueprint {

        int costeOreOreRobot;
        int costeOreClayRobot;
        int costeOreObsidianRobot;
        int costeClayObsidianRobot;
        int costeOreGeodeRobot;

        public Blueprint(int costeOreOreRobot, int costeOreClayRobot, int costeOreObsidianRobot,
                int costeClayObsidianRobot, int costeOreGeodeRobot, int costeObsidianGeodeRobot) {
            this.costeOreOreRobot = costeOreOreRobot;
            this.costeOreClayRobot = costeOreClayRobot;
            this.costeOreObsidianRobot = costeOreObsidianRobot;
            this.costeClayObsidianRobot = costeClayObsidianRobot;
            this.costeOreGeodeRobot = costeOreGeodeRobot;
            this.costeObsidianGeodeRobot = costeObsidianGeodeRobot;
        }

        int costeObsidianGeodeRobot;

    }

    private static class Path {

        int ore;
        int clay;
        int obsidian;
        int geode;
        int oreRobot;
        int clayRobot;
        int obsidianRobot;
        int geodeRobot;

        int timeLeft;

        public Path(int ore, int clay, int obsidian, int geode, int oreRobot, int clayRobot, int obsidianRobot,
                int geodeRobot, int timeLeft) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.geode = geode;
            this.oreRobot = oreRobot;
            this.clayRobot = clayRobot;
            this.obsidianRobot = obsidianRobot;
            this.geodeRobot = geodeRobot;
            this.timeLeft = timeLeft;
        }

    }
}
