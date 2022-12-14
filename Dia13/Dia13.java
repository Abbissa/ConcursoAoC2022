package Dia13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Dia13 {

    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();

    }

    private static void parte2() throws FileNotFoundException {

        ArrayList<String> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream("Dia13\\input.txt"))) {
            CharSequence replace1 = "[";
            CharSequence replace2 = "[ ";
            CharSequence replace3 = "]";
            CharSequence replace4 = " ]";
            int i = 1;
            while (sc.hasNext()) {

                String l1 = sc.nextLine().replace(',', ' ').replace(replace1, replace2).replace(replace3, replace4);
                if (i % 2 == 0 && sc.hasNextLine())
                    sc.nextLine();
                i++;

                lista.add(l1);

            }
            String clave1 = "[[2]]".replace(',', ' ').replace(replace1, replace2).replace(replace3, replace4);
            String clave2 = "[[6]]".replace(',', ' ').replace(replace1, replace2).replace(replace3, replace4);
            lista.add(clave1);
            lista.add(clave2);

            lista.sort(new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    Scanner line1 = new Scanner(o1);
                    Scanner line2 = new Scanner(o2);
                    Boolean res = comprobarLista(line1, line2);
                    if (res == null)
                        return 0;
                    if (res)
                        return -1;
                    return 1;
                }

            });
            System.out.println(lista.toString());
            System.out.println((1 + lista.indexOf(clave1)) * (1 + lista.indexOf(clave2)));
        }

    }

    private static void parte1() throws FileNotFoundException {

        try (Scanner sc = new Scanner(new FileInputStream("Dia13\\input.txt"))) {
            int n = 1;
            int resN = 0;
            CharSequence replace1 = "[";
            CharSequence replace2 = "[ ";
            CharSequence replace3 = "]";
            CharSequence replace4 = " ]";
            while (sc.hasNextLine()) {

                String l1 = sc.nextLine().replace(',', ' ').replace(replace1, replace2).replace(replace3, replace4);
                String l2 = sc.nextLine().replace(',', ' ').replace(replace1, replace2).replace(replace3, replace4);
                // System.out.println(l1);
                // System.out.println(l2);
                // System.out.println();

                Scanner line1 = new Scanner(l1);
                Scanner line2 = new Scanner(l2);
                if (sc.hasNextLine())
                    sc.nextLine();
                Boolean res = comprobarLista(line1, line2);
                // if (res != null && !res)
                // System.out.println(n);
                if (res == null || res) {
                    System.out.print(n + " ");
                    resN += n;
                }
                n++;
            }
            System.out.println(resN);

        }
    }

    private static Boolean comprobarListaElto(Scanner line1, Scanner line2, String next2) {

        String next1 = line1.next();

        if (next1.equals("[")) {
            return comprobarListaElto(line1, line2, next2);
        } else if (next1.equals("]")) {
            return true;
        } else {
            int int1 = Integer.parseInt(next1);

            int int2 = Integer.parseInt(next2);
            if (int1 == int2) {
                if (line1.hasNext("([0-9\\[])")) {

                    return false;
                } else {
                    line1.next();
                    return null;

                }
            } else {
                return int2 > int1;
            }
        }

    }

    private static Boolean comprobarLista(Scanner line1, Scanner line2) {
        boolean fin = false;

        while (!fin && line1.hasNext() && line2.hasNext()) {
            String next1 = line1.next();
            String next2 = line2.next();
            Boolean res = null;
            if (next1.equals("[")) {

                if (next2.equals("[")) {

                    res = comprobarLista(line1, line2);
                    if (res != null)
                        return res;
                } else if (next2.equals("]")) {
                    return false;
                } else {
                    res = comprobarListaElto(line1, line2, next2);
                    if (res != null)
                        return res;
                }
            } else if (next1.equals("]")) {
                if (next2.equals("]")) {
                    return null;
                } else {
                    return true;
                }

            } else if (next2.equals("]")) {
                return false;
            } else {

                if (next2.equals("[")) {

                    res = comprobarListaElto(line2, line1, next1);
                    if (res != null)
                        return !res;
                } else {
                    if (Integer.parseInt(next1) > Integer.parseInt(next2))
                        return false;
                    else if (Integer.parseInt(next1) < Integer.parseInt(next2))
                        return true;
                }
            }
        }
        return null;
    }
}