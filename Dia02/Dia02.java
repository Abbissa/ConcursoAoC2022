package Dia02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Dia02 {
    public static HashMap<String, HashMap<String, Integer>> resultados;

    public static void main(String[] args) throws FileNotFoundException {
        setMapParte1();
        ejecutar();
        setMapParte2();
        ejecutar();
    }

    private static void setMapParte1() {

        resultados = new HashMap<>();
        resultados.put("A", new HashMap<>());
        resultados.put("B", new HashMap<>());
        resultados.put("C", new HashMap<>());
        resultados.get("A").put("X", 4);
        resultados.get("A").put("Y", 8);
        resultados.get("A").put("Z", 3);

        resultados.get("B").put("X", 1);
        resultados.get("B").put("Y", 5);
        resultados.get("B").put("Z", 9);

        resultados.get("C").put("X", 7);
        resultados.get("C").put("Y", 2);
        resultados.get("C").put("Z", 6);

    }

    private static void ejecutar() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia02\\input.txt"))) {
            int res=0;
            while (sc.hasNext()) {
                String el1 = sc.next();
                String el2 = sc.next();

                res += resultados.get(el1).get(el2);
            }
            System.out.println(res);
        }
    }

    private static void setMapParte2() {

        resultados = new HashMap<>();
        resultados.put("A", new HashMap<>());
        resultados.put("B", new HashMap<>());
        resultados.put("C", new HashMap<>());
        resultados.get("A").put("X", 3);
        resultados.get("A").put("Y", 4);
        resultados.get("A").put("Z", 8);

        resultados.get("B").put("X", 1);
        resultados.get("B").put("Y", 5);
        resultados.get("B").put("Z", 9);

        resultados.get("C").put("X", 2);
        resultados.get("C").put("Y", 6);
        resultados.get("C").put("Z", 7);

    }

}
