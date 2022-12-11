package Dia11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Dia11 {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Monkey> monkeys = leer();

        int mcm = 1;
        for (int i = 0; i < monkeys.size(); i++) {
            mcm = mcm(monkeys.get(i).divisible, mcm);

        }

        parte1(monkeys, mcm);
        monkeys = leer();

        parte2(monkeys, mcm);
        
    }

    private static void parte2(ArrayList<Monkey> monkeys, int mcm) {
        iterate(monkeys, 10000, 1, mcm);
        monkeys.forEach((e) -> System.out.println("Mono "+  e.inspected));

        sort(monkeys);
        Long res = (long) (monkeys.get(0).inspected * monkeys.get(1).inspected);
        System.out.println(res);

    }

    private static void parte1(ArrayList<Monkey> monkeys, int mcm) throws FileNotFoundException {

        iterate(monkeys, 20, 3, mcm);

        sort(monkeys);
        System.out.println(monkeys.get(0).inspected * monkeys.get(1).inspected);

    }

    private static void iterate(ArrayList<Monkey> monkeys, int n, int worryDec, int mcm) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < monkeys.size(); j++) {

                Monkey m = monkeys.get(j);
                while (m.items.size() > 0) {
                    long res = m.inspect() % mcm;
                    res = Math.round(res / worryDec);
                    if (res % m.divisible == 0)
                        monkeys.get(m.tru).items.add(res);
                    else
                        monkeys.get(m.fals).items.add(res);
                }

            }

        }
    }

    private static void sort(ArrayList<Monkey> monkeys) {
        monkeys.sort(new Comparator<Monkey>() {

            @Override
            public int compare(Monkey o1, Monkey o2) {
                return (int) (o2.inspected - o1.inspected);
            }

        });
    }
    private static ArrayList<Monkey> leer() throws FileNotFoundException {
        ArrayList<Monkey> monkeys = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileInputStream("Dia11\\input.txt"))) {
            int nMonkey = 0;
            while (sc.hasNext()) {
                sc.nextLine();
                String[] stItemes = sc.nextLine().trim().split(":")[1].split(",");
                Monkey m = new Monkey();
                for (int i = 0; i < stItemes.length; i++) {
                    m.items.add(Long.parseLong(stItemes[i].trim()));
                }
                String[] operation = sc.nextLine().split("=")[1].split(" ");
                m.op1 = operation[1];
                m.op = operation[2];
                m.op2 = operation[3];
                m.divisible = Integer.parseInt(sc.nextLine().split("by")[1].trim());
                m.tru = Integer.parseInt(sc.nextLine().split("monkey")[1].trim());
                m.fals = Integer.parseInt(sc.nextLine().split("monkey")[1].trim());

                monkeys.add(nMonkey, m);
                nMonkey++;
                if (sc.hasNext())
                    sc.nextLine();

            }
        }
        return monkeys;
    }

    private static class Monkey {

        public ArrayList<Long> items;
        public String op1;
        public String op;
        public String op2;
        public int divisible;
        public int tru;
        public int fals;
        public long inspected;

        public Monkey() {
            items = new ArrayList<>();
        }

        public long inspect() {
            long item = this.items.remove(0);
            this.inspected++;
            long res = this.operate(item);
            return res;

        }

       

        private long operate(long item) {
            long op1;
            long op2;
            if (this.op1.equals("old"))
                op1 = item;
            else
                op1 = Integer.parseInt(this.op1);
            if (this.op2.equals("old"))
                op2 = item;
            else
                op2 = Integer.parseInt(this.op2);

            if (op.equals("*"))
                return op1 * op2;
            else
                return op1 + op2;
        }

    }

    public static int mcm(int num1, int num2) {
        int a = Math.max(num1, num2);
        int b = Math.min(num1, num2);

        int resultado = (a / mcd(num1, num2)) * b;

        return resultado;

    }

    public static int mcd(int num1, int num2) {

        int a = Math.max(num1, num2);
        int b = Math.min(num1, num2);

        int resultado = 0;
        do {
            resultado = b;
            b = a % b;
            a = resultado;

        } while (b != 0);

        return resultado;

    }

}
