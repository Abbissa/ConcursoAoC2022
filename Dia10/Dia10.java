package Dia10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dia10 {

    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();
    }

    private static void parte2() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia10\\input.txt"))) {
            int ciclo = 0;
            int X = 1;
            int res = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();
                ciclo++;
                String[] accion = line.split(" ");
                String mandato = accion[0];

                switch (mandato) {
                    case "noop":
                        res += calcularPotenciaSeñal(ciclo, X);
                        paint(ciclo, X);
                        break;
                    case "addx":
                        int valor = Integer.parseInt(accion[1]);
                        res += calcularPotenciaSeñal(ciclo, X);
                        paint(ciclo, X);
                        ciclo++;
                        res += calcularPotenciaSeñal(ciclo, X);
                        paint(ciclo, X);
                        X += valor;

                        break;
                    default:
                        break;

                }
            }
            
        }

    }

    private static void parte1() throws FileNotFoundException {

        try (Scanner sc = new Scanner(new FileInputStream("Dia10\\input.txt"))) {
            int ciclo = 0;
            int X = 1;
            int res = 0;

            while (sc.hasNext()) {
                String line = sc.nextLine();
                ciclo++;
                String[] accion = line.split(" ");
                String mandato = accion[0];

                switch (mandato) {
                    case "noop":
                        res += calcularPotenciaSeñal(ciclo, X);

                        break;
                    case "addx":
                        int valor = Integer.parseInt(accion[1]);
                        res += calcularPotenciaSeñal(ciclo, X);
                        ciclo++;
                        res += calcularPotenciaSeñal(ciclo, X);
                        X += valor;

                        break;
                    default:
                        break;

                }
            }
            System.out.println(res);
        }
    }

    private static int calcularPotenciaSeñal(int ciclo, int X) {

        if ((ciclo - 20) % 40 == 0) {
            return ciclo * X;

        }

        return 0;
    }

    private static void paint(int ciclo, int X) {
        if (Math.abs((ciclo-1) % 40 - X) <= 1) {
            System.out.print("#");
        }else{
            System.out.print(".");
        }
        if(ciclo % 40==0&&ciclo>1)
        System.out.println();
    }
}
