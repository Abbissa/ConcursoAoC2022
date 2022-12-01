import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dia01 {
    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();
    }

    private static void parte1() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia01\\input.txt"))) {
            int max = 0;
            int cur = 0;
            while (sc.hasNext()) {

                String line = sc.nextLine();
                if (isNumeric(line)) {
                    cur += Integer.parseInt(line);
                } else {
                    max = Math.max(max, cur);
                    cur = 0;
                }
            }
            System.out.println(max);
        }
    }

    private static void parte2() throws FileNotFoundException{
        try(Scanner sc = new Scanner(new FileInputStream("Dia01\\input.txt"))){
            int max = 0;
            int max1 = 0;
            int max2 = 0;
            int cur = 0;
            while(sc.hasNext()){
                
                String line = sc.nextLine();
                if(isNumeric(line)){
                    cur += Integer.parseInt(line);
                }else{
                    if(max < cur){
                        max = cur;
                    }else{
                        if(max1<cur)
                            max1 = cur;
                        else
                            max2 = Math.max(max2, cur);
                    }
                    cur=0;
                }
            }
            System.out.println(max+max1+max2);
        }
        
    }

    public static boolean isNumeric(String cadena) {

        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException excepcion) {
            return false;
        }
    }
}
