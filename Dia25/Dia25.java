package Dia25;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Dia25 {

    private static final String EXAMPLE = "Dia25\\example.txt";
    private static final String INPUT = "Dia25\\input.txt";

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<Integer, Character> nToC = new HashMap<>();
        nToC.put(0, '0');
        nToC.put(1, '1');
        nToC.put(2, '2');
        nToC.put(3, '=');
        nToC.put(4, '-');

        HashMap<Character, Integer> cToN = new HashMap<>();

        cToN.put('0', 0);
        cToN.put('1', 1);
        cToN.put('2', 2);
        cToN.put('-', -1);
        cToN.put('=', -2);

        try (Scanner sc = new Scanner(new FileInputStream(INPUT))) {
            long sum = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                long res = 0;
                for (int i = 0; i < line.length(); i++) {
                    res *= 5;
                    res += cToN.get(line.charAt(i));

                }
                sum += res;
            }
            System.out.println(sum);
            String convert = "";

            while (sum != 0) {
                int resto = (int) (sum % 5);
                convert = nToC.get(resto) + convert;
                sum -= (sum + 2) % 5 - 2;
                sum /= 5;

            }
            System.out.println(convert);
        }

    }

}
