package Dia21;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Dia21 {
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static ArrayList<Operation> operaciones;
    private static HashMap<String, Long> monos;

    private static String EXAMPLE = "Dia21\\example.txt";
    private static String INPUT = "Dia21\\input.txt";

    public static void main(String[] args) throws FileNotFoundException {

        parte1(EXAMPLE);
        parte1(INPUT);
        parte2(EXAMPLE);
        parte2(INPUT);

    }

    private static void parte2(String file) throws FileNotFoundException {

        operaciones = new ArrayList<Operation>();
        monos = new HashMap<String, Long>();
        leer(file);

        System.out.println(solve2());
    }

    private static long solve2() {


        monos.remove("humn");

        while (monos.get("humn") == null) {
            for (int i = 0; i < operaciones.size(); i++) {

                Operation op = operaciones.get(i);
                Long mono1 = monos.get(op.mono1);
                Long mono2 = monos.get(op.mono2);
                if (op.monoDest.equals("root") && (mono1 != null || mono2 != null)) {
                    if (mono1 == null)
                        monos.put(op.mono1, mono2);
                    else
                        monos.put(op.mono2, mono1);

                    operaciones.remove(i);
                    i--;
                } else if (mono1 != null && mono2 != null) {
                    switch (op.operacion) {
                        case "+":
                            monos.put(op.monoDest, mono1 + mono2);
                            break;
                        case "-":
                            monos.put(op.monoDest, mono1 - mono2);

                            break;
                        case "*":
                            monos.put(op.monoDest, mono1 * mono2);

                            break;
                        case "/":
                            monos.put(op.monoDest, mono1 / mono2);

                            break;

                        default:
                            break;
                    }
                    operaciones.remove(i);
                    i--;

                } else if (monos.get(op.monoDest) != null && (mono1 != null || mono2 != null)) {
                    String monoDest;
                    long monoConocido;
                    long res;

                    switch (op.operacion) {
                        case "+":
                            if (mono1 == null) {
                                monoDest = op.mono1;
                                monoConocido = mono2;
                            } else {
                                monoDest = op.mono2;
                                monoConocido = mono1;
                            }

                            monos.put(monoDest,  monos.get(op.monoDest)-monoConocido);
                            break;
                        case "-":
                            if (mono1 == null) {
                                monoDest = op.mono1;
                                monoConocido = mono2;
                                res = monos.get(op.monoDest) + monoConocido;
                            } else {
                                monoDest = op.mono2;
                                monoConocido = mono1;
                                res = monoConocido - monos.get(op.monoDest);

                            }

                            monos.put(monoDest, res);

                            break;
                        case "*":
                            if (mono1 == null) {
                                monoDest = op.mono1;
                                monoConocido = mono2;
                            } else {
                                monoDest = op.mono2;
                                monoConocido = mono1;
                            }

                            monos.put(monoDest, monos.get(op.monoDest) / monoConocido);

                            break;
                        case "/":

                            if (mono1 == null) {
                                monoDest = op.mono1;
                                monoConocido = mono2;
                                res = monos.get(op.monoDest) * monoConocido;
                            } else {
                                monoDest = op.mono2;
                                monoConocido = mono1;
                                res = monoConocido / monos.get(op.monoDest);

                            }

                            monos.put(monoDest, res);

                            break;
                    }
                    operaciones.remove(i);
                    i--;

                }

            }
        }
        return monos.get("humn");

    }

    private static void parte1(String file) throws FileNotFoundException {
        operaciones = new ArrayList<Operation>();
        monos = new HashMap<String, Long>();
        leer(file);

        System.out.println(solve());
    }

    private static long solve() {

        while (monos.get("root") == null) {
            for (int i = 0; i < operaciones.size(); i++) {

                Operation op = operaciones.get(i);
                Long mono1 = monos.get(op.mono1);
                Long mono2 = monos.get(op.mono2);
                if (mono1 != null && mono2 != null) {
                    switch (op.operacion) {
                        case "+":
                            monos.put(op.monoDest, mono1 + mono2);
                            break;
                        case "-":
                            monos.put(op.monoDest, mono1 - mono2);

                            break;
                        case "*":
                            monos.put(op.monoDest, mono1 * mono2);

                            break;
                        case "/":
                            monos.put(op.monoDest, mono1 / mono2);

                            break;

                        default:
                            break;
                    }
                    operaciones.remove(i);
                    i--;

                }

            }
        }
        return monos.get("root");
    }

    private static void leer(String file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(file))) {

            while (sc.hasNext()) {
                String line = sc.nextLine();
                String mono = line.split(":")[0];

                String[] yell = line.split(" ");
                if (isNumeric(yell[1])) {
                    monos.put(mono, Long.parseLong(yell[1]));
                } else {
                    operaciones.add(new Operation(mono, yell[1], yell[2], yell[3]));

                }

            }

        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private static class Operation {
        String monoDest;
        String mono1;
        String operacion;
        String mono2;

        public Operation(String monoDest, String mono1, String operacion, String mono2) {
            this.monoDest = monoDest;
            this.mono1 = mono1;
            this.operacion = operacion;
            this.mono2 = mono2;
        }

    }

}
