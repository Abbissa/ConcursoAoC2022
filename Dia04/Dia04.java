import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dia04 {

    public static void main(String[] args) throws FileNotFoundException {

        parte1();
        parte2();
    }

    private static void parte1() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia04\\input.txt"))) {
            int res = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] sections = line.split(",");

                String[] section1 = sections[0].split("-");
                String[] section2 = sections[1].split("-");

                int ini1 = Integer.parseInt(section1[0]);
                int fin1 = Integer.parseInt(section1[1]);
                int ini2 = Integer.parseInt(section2[0]);
                int fin2 = Integer.parseInt(section2[1]);

                if (ini1<=ini2&&fin1>=fin2||ini2<=ini1&&fin2>=fin1) {
                    res++;
                }
            }
            System.out.println(res);
        }
    }
    

    private static void parte2() throws FileNotFoundException {

        try (Scanner sc = new Scanner(new FileInputStream("Dia04\\input.txt"))) {
            int res = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] sections = line.split(",");

                String[] section1 = sections[0].split("-");
                String[] section2 = sections[1].split("-");

                int ini1 = Integer.parseInt(section1[0]);
                int fin1 = Integer.parseInt(section1[1]);
                int ini2 = Integer.parseInt(section2[0]);
                int fin2 = Integer.parseInt(section2[1]);

                if (!(ini1<ini2&&fin1<ini2||ini1>fin2&&fin1>fin2)) {
                    res++;
                }
            }
            System.out.println(res);
        }
    }

}
